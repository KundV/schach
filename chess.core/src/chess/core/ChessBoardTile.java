package chess.core;

import chess.core.VerktetteListe.Queue;

public class ChessBoardTile
{

    private ChessPiece piece;

    private Queue TargetingMoves;

    public ChessBoardTile()
    {
        piece = null;
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

    public boolean hasTargetingMoves()
    {
        return (TargetingMoves != null);
    }

    public boolean hasPiece()
    {
        return (piece != null);
    }

    public PlayerId isOccupiedBy()
    {
        return (this.piece.getPlayerId());
    }



}
