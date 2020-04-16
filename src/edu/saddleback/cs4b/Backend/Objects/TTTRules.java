package edu.saddleback.cs4b.Backend.Objects;

public class TTTRules implements GameRules {
    @Override
    public boolean acceptablePlacement(Board board, Move move) {
        return false;
    }

    @Override
    public boolean inBounds(Move move) {
        return false;
    }

    @Override
    public String gameWinner(Board board) {
        return checkForWinner(board);
    }

    private String checkForWinner(Board board) {
        String winningToken = "";
        if (!(winningToken = horizontalWinner(board)).equals("")) {
            return winningToken;
        }
        return winningToken;
    }
}
