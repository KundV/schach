package chess.core;

import java.util.ArrayList;

public class ChessPiece {
    public ChessPieceId chessPieceId;
    public boolean isBlack;
    public ArrayList<ChessMove> possibleMoves;

    public ChessPiece(ChessPieceId chessPieceId, boolean isBlack) {
        this.chessPieceId = chessPieceId;
        this.isBlack = isBlack;

        if(chessPieceId == ChessPieceId.KING)
            System.out.println("König");

    }
}
