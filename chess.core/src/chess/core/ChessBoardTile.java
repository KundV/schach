package chess.core;

import chess.core.VerktetteListe.Queue;

public class ChessBoardTile
{

    private ChessPiece piece;

    private Queue TargetingMoves;

    public ChessBoardTile()
    {
        piece = null;
        this.TargetingMoves = new Queue();
    }

    public ChessBoardTile(ChessPiece piece)
    {
        this.piece = piece;

    }

    public ChessPiece getPiece()
    {
        return piece;
    }

    public void setPiece(ChessPiece piece)
    {
        this.piece = piece;
    }

    public ChessPiece removePiece()
    {
        ChessPiece temp = piece;
        piece = null;
        return temp;
    }

    public Queue getTargetingMoves()
    {
        return TargetingMoves;
    }

    public void addTargetingMove(ChessMove move)
    {
        if (TargetingMoves == null)
        {
            TargetingMoves = new Queue();
        }
        TargetingMoves.add(move);
    }
    public PlayerId getPlayerId()
    {
        return piece.getPlayerId();
    }

    public boolean hasTargetingMoves()
    {
        return (TargetingMoves != null);
    }

    public boolean hasPiece()
    {
        return (piece != null);
    }

    public void clearTargetingMoves()
    {
        TargetingMoves.clear();
    }






}
