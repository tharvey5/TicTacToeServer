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

    private String horizontalWinner(Board board) {
        for (int r = 0; r < board.rows(); ++r) {
            if (board.getToken(r, 0).equals(board.getToken(r, 1)) &&
                board.getToken(r, 1).equals(board.getToken(r, 2)) &&
                board.getToken(r, 0) != null) {
                return board.getToken(r, 0).getTokenID();
            }
        }
        return null;
    }
}
