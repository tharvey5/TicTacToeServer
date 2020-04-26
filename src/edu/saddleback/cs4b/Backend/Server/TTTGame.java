package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Objects.*;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.Subject;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;

import java.time.LocalDateTime;
import java.util.List;

public class TTTGame implements Subject, Runnable, Game {

    @Override
    public void setToken(Token token, PublicUser user) {

    }

    @Override
    public Token getToken(PublicUser user) {
        return null;
    }

    @Override
    public String getStartTime() {
        return null;
    }

    @Override
    public void setStartTime(LocalDateTime newTime) {

    }

    @Override
    public void setEndTime(LocalDateTime endTime) {

    }

    @Override
    public String getEndTime() {
        return null;
    }

    @Override
    public PublicUser getStartPlayer() {
        return null;
    }

    @Override
    public void setStartPlayer(PublicUser user) {

    }

    @Override
    public void setCreator(PublicUser user) {

    }

    @Override
    public PublicUser getCreator() {
        return null;
    }

    @Override
    public PublicUser getOtherPlayer() {
        return null;
    }

    @Override
    public void setOtherPlayer(PublicUser user) {

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
        return null;
    }

    @Override
    public void addViewer(PublicUser user) {

    }

    @Override
    public void addObserver(Observer o) {

    }

    @Override
    public void removeObserver(Observer o) {

    }

    @Override
    public void notifyObserver(SystemEvent e) {

    }

    @Override
    public void run() {

    }
}
