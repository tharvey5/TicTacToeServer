package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Objects.*;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.Subject;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;

import java.time.LocalDateTime;
import java.util.*;

public class TTTGame implements Subject, Runnable, Game {
    private Map<String, Token> tokenMap; // maps players to tokens
    private LocalDateTime start;
    private LocalDateTime end;
    private PublicUser startPlayer;
    private PublicUser otherPlayer;
    private PublicUser creator;
    private List<Observer> observers;
    private List<PublicUser> viewers;  // todo this seems kind of redundant
                                       // couldn't we deduce if not player its a viewer

    /**
     * note creator is by default the start player
     */
    public TTTGame(PublicUser creator) {
        tokenMap = new Hashtable<>();
        setStartTime(LocalDateTime.now());
        setCreator(creator);
        startPlayer = this.creator;
        this.observers = new ArrayList<>();
    }

    @Override
    public void setToken(Token token, PublicUser user) {

    }

    @Override
    public Token getToken(PublicUser user) {
        return null;
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
        if (user.equals(otherPlayer)) {
            PublicUser temp = startPlayer;
            startPlayer = otherPlayer;
            otherPlayer = temp;
        }
    }

    @Override
    public void setCreator(PublicUser user) {
        if (creator != null) {
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
        return null;
    }

    @Override
    public void setMoves(Moves newMoves) {

    }

    @Override
    public void addMove(Move newMove) {

    }

    @Override
    public PublicUser getWinner() {
        return null;
    }

    @Override
    public void setWinner(PublicUser user) {

    }

    @Override
    public String getGameID() {
        return null;
    }

    @Override
    public void setGameID(String newGameID) {

    }

    @Override
    public Board getGameBoard() {
        return null;
    }

    @Override
    public void setGameBoard(Board newBoard) {

    }

    @Override
    public List<PublicUser> viewers() {
        return Collections.unmodifiableList(viewers);
    }

    @Override
    public void addViewer(PublicUser user) {

    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
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

    }
}
