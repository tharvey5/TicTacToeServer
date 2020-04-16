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
        String winningToken = null;
        if ((winningToken = horizontalWinner(board)) != null) {
            return winningToken;
        } else if ((winningToken = verticalWinner(board)) != null) {
            return winningToken;
        } else if ((winningToken = checkDiagonalWinner(board)) != null) {
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

    private String verticalWinner(Board board) {
        for (int c = 0; c < board.cols(); ++c) {
            if (board.getToken(0, c).equals(board.getToken(1, c)) &&
                board.getToken(1, c).equals(board.getToken(2, c)) &&
                board.getToken(0, c) != null) {
                return board.getToken(0, c).getTokenID();
            }
        }
        return null;
    }

    private String checkDiagonalWinner(Board board) {
        if (board.getToken(0, 0).equals(board.getToken(1, 1)) &&
            board.getToken(1, 1).equals(board.getToken(2, 2)) &&
            board.getToken(0, 0) != null) {
            return board.getToken(1, 1).getTokenID();
        }

        if (board.getToken(0,2).equals(board.getToken(1,1)) &&
            board.getToken(1, 1).equals(board.getToken(2,0)) &&
            board.getToken(0,2) != null) {
            return board.getToken(1,1).getTokenID();
        }
        return null;
    }
}
