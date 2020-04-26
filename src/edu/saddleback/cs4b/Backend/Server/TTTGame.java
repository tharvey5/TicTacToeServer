package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Messages.*;
import edu.saddleback.cs4b.Backend.Objects.*;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.Subject;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;

import java.time.LocalDateTime;
import java.util.*;

public class TTTGame implements Subject, Runnable, Game {
    private Map<String, Token> tokenMap; // key = username, value = token maps players to tokens
    private LocalDateTime start;
    private LocalDateTime end;
    private volatile PublicUser startPlayer;
    private volatile PublicUser otherPlayer;
    private volatile PublicUser creator;
    private PublicUser currentTurn;
    private PublicUser winner;
    private List<Observer> observers;
    private List<PublicUser> viewers;  // todo this seems kind of redundant
                                       // couldn't we deduce if not player its a viewer
    private Moves moves;
    boolean isActive;
    private Board board;
    private String gameId;
    private GameRules rules;
    private GameLogicService checker = GameLogicService.getInstance();
    private AbstractMessageFactory factory = MessageFactoryProducer.getFactory(FactoryTypes.GAME_FACT.getTypes());

    /**
     * note creator is by default the start player
     */
    public TTTGame(PublicUser creator) {
        tokenMap = new Hashtable<>();
        setStartTime(LocalDateTime.now());
        setCreator(creator);
        startPlayer = this.creator;
        this.observers = new ArrayList<>();
        this.isActive = false;
        this.board = new TTTBoard();
        this.currentTurn = creator;
        this.rules = new TTTRules();
    }

    @Override
    public void setToken(Token token, PublicUser user) {

    }

    @Override
    public Token getToken(PublicUser user) {
        return tokenMap.get(user.getUsername());
    }

    @Override
    public String getStartTime() {
        return formatTime(start);
    }

    private String formatTime(LocalDateTime time) {
        StringBuilder sb = new StringBuilder();
        sb.append(time.getHour());
        sb.append(":");
        sb.append(time.getMinute());
        return sb.toString();
    }


    @Override
    public void setStartTime(LocalDateTime newTime) {
        if (start != null) {
            this.start = newTime;
        }
    }

    @Override
    public void setEndTime(LocalDateTime endTime) {
        if (end != null) {
            this.end = endTime;
        }
    }

    @Override
    public String getEndTime() {
        return formatTime(end);
    }

    @Override
    public PublicUser getStartPlayer() {
        return startPlayer;
    }

    // only use if you want p2 to start, creator is start by default
    @Override
    public void setStartPlayer(PublicUser user) {
        if (user != null && startPlayer == null) {
            startPlayer = user;
        }
    }

    @Override
    public void setCreator(PublicUser user) {
        if (user != null && creator != null) {
            this.creator = user;
        }
    }

    @Override
    public PublicUser getCreator() {
        return creator;
    }

    @Override
    public PublicUser getOtherPlayer() {
        return otherPlayer;
    }

    // will not swap the start players like setStartWill, only used to set
    // the other player
    @Override
    public void setOtherPlayer(PublicUser user) {
        if (otherPlayer == null) {
            otherPlayer = user;
        }
    }

    @Override
    public Moves getMoves() {
        return moves;
    }

    @Override
    public void setMoves(Moves newMoves) { }

    @Override
    public void addMove(Move newMove) { }

    @Override
    public PublicUser getWinner() {
        return winner;
    }

    @Override
    public void setWinner(PublicUser user) {
        if (user != null && winner != null) {
            winner = user;
        }
    }

    @Override
    public String getGameID() {
        return gameId;
    }

    @Override
    public void setGameID(String newGameID) {
        this.gameId = newGameID;
    }

    @Override
    public Board getGameBoard() {
        return board;
    }

    @Override
    public void setGameBoard(Board newBoard) { }

    @Override
    public List<PublicUser> viewers() {
        return Collections.unmodifiableList(viewers);
    }

    @Override
    public void addViewer(PublicUser user) {
        if (user != null) {
            viewers.add(user);
        }
    }

    /**
     *
     * @return false if the move is invalid or its not your turn
     */
    public boolean playMove(Move move) {
        if (move.getPlayerID().equals(currentTurn.getId())) {
            synchronized (this) {
                if (checker.validMove(board, move)) {
                    int r = move.getCoordinate().getXCoord();
                    int c = move.getCoordinate().getYCoord();
                    board.setToken(r, c, tokenMap.get(currentTurn.getUsername()));
                    moves.addMove(move);
                    return true;
                }
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void addObserver(Observer o) {
        if (o != null) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.removeIf(observer-> observer == o );
    }

    @Override
    public void notifyObserver(SystemEvent e) {
        for (Iterator<Observer> iterator = observers.iterator(); iterator.hasNext();) {
            Observer ob = iterator.next();
            // ob.update(null);
            // todo update with the move
        }
    }

    @Override
    public void run() {
        waitForPlayers();
        isActive = true;
        winner = null;
        while (isActive && moves.getMoves().size() < 9) {
            waitForMove();
            String winningToken = checker.findWinner(board);
            if (winningToken != null) {
                isActive = false;
                break;
            }
            swapCurrentTurn();
        }

        // notify all of the observers of this game who has won
        GameResultMessage msg = (GameResultMessage) factory.createMessage(MsgTypes.GAME_RESULT.getType());
        msg.setWinner(currentTurn.getUsername());
        if (winner != null) {
            msg.setWinType(WinType.NORMAL_WIN);
        }
        notifyObserver(new MessageEvent(msg));
    }

    // hold in an infinite loop until another move has been added to the game
    private void waitForMove() {
        int moveCnt = moves.getMoves().size();
        while (moves.getMoves().size() == moveCnt) {}
    }

    // hold in an infinite loop until both players have joined
    private void waitForPlayers() {
        while (startPlayer == null && otherPlayer == null) {}
    }

    private void swapCurrentTurn() {
        if (currentTurn == startPlayer) {
            currentTurn = otherPlayer;
        } else {
            currentTurn = startPlayer;
        }
    }
}
