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
    private List<PublicUser> viewers;
    private volatile List<Move> moves;
    private volatile boolean isActive;
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
        this.startPlayer = this.creator;
        this.observers = new ArrayList<>();
        this.isActive = false;
        this.board = new TTTBoard();
        this.currentTurn = creator;
        this.rules = new TTTRules();
        this.moves = new ArrayList<>();
        this.viewers = new ArrayList<>();

        // todo fix this
        setToken(new TTTToken("1"), creator);
    }

    @Override
    public void setToken(Token token, PublicUser user) {
        tokenMap.put(user.getUsername(), token);
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
        if (time != null) {
            sb.append(time.toLocalDate().getMonth().getValue());
            sb.append("/");
            sb.append(time.toLocalDate().getDayOfMonth());
            sb.append("/");
            sb.append(time.toLocalDate().getYear());
            sb.append(" at ");
            sb.append(time.getHour());
            sb.append(":");
            sb.append(time.getMinute());
        }
        return sb.toString();
    }


    @Override
    public void setStartTime(LocalDateTime newTime) {
        if (newTime != null) {
            this.start = newTime;
        }
    }

    @Override
    public void setEndTime(LocalDateTime endTime) {
        if (endTime != null) {
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
        synchronized (this) {
            if (user != null && startPlayer == null) {
                startPlayer = user;
            }
        }
    }

    @Override
    public void setCreator(PublicUser user) {
        synchronized (this) {
            if (user != null && creator == null) {
                this.creator = user;
            }
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
        synchronized (this) {
            if (otherPlayer == null && user != creator) {
                otherPlayer = user;

                // todo handle this better
                setToken(new TTTToken("2"), user);
            }
        }
    }

    @Override
    public Moves getMoves() {
        return null;
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
        // if the person trying to move is the one whose turn it is
        if (isActive && move.getPlayerID().equals(currentTurn.getId())) {
            synchronized (this) {
                if (checker.validMove(board, move)) {
                    int r = move.getCoordinate().getXCoord();
                    int c = move.getCoordinate().getYCoord();
                    board.setToken(r, c, tokenMap.get(currentTurn.getUsername()));
                    moves.add(move);

                    ValidMoveMessage validMoveMsg =
                            new ValidMoveMessage(new TTTPosition(r, c),
                            gameId,tokenMap.get(currentTurn.getUsername()),
                            currentTurn.getUsername());
                    System.out.println("Return move");

                    GameInfoService.getInstance().writeMove(move);
                    notifyObserver(new MessageEvent(validMoveMsg));
                    return true;
                }
                System.out.println(move.getCoordinate().getXCoord() + "," + move.getCoordinate().getYCoord());
                System.out.println("invalid move");
                //InvalidMoveMessage invalidMove = (InvalidMoveMessage) factory.createMessage(MsgTypes.INVALID_MOVE.getType());
                //notifyObserver(new MessageEvent(invalidMove));
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
            ob.update(e);
        }
    }

    @Override
    public void run() {
        waitForPlayers();
        System.out.println("Both players have joined");
        isActive = true;
        winner = null;
        while (isActive && moves.size() < 9) {
            waitForMove();
            System.out.println("Move was made");
            String winningToken = checker.findWinner(board);
            if (winningToken != null) {
                winner = currentTurn;
                isActive = false;
                break;
            }
            swapCurrentTurn();
        }

        GameResultMessage resMsg = (GameResultMessage) factory.createMessage(MsgTypes.GAME_RESULT.getType());
        resMsg.setGameId(gameId);
        setEndTime(LocalDateTime.now());
        if (winner != null) {
            resMsg.setWinner(currentTurn.getUsername());
        }
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyObserver(new MessageEvent(resMsg));

        System.out.println("Game ended");
    }

    // hold in an infinite loop until another move has been added to the game
    private void waitForMove() {
        int moveCnt = moves.size();
        while (moves.size() == moveCnt) {}
    }

    // hold in an infinite loop until both players have joined
    private void waitForPlayers() {
        while (otherPlayer == null) {}
    }

    private void swapCurrentTurn() {
        if (currentTurn == startPlayer) {
            currentTurn = otherPlayer;
        } else {
            currentTurn = startPlayer;
        }
    }
}
