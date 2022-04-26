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

    public ChessPiece removePiece()
    {
        piece = null;
        return piece;
    }

    public Queue getTagetingMoves()
    {
        return TagetingMoves;
    }

    public void addTargetingMove(ChessMove move)
    {
        if (TagetingMoves == null)
        {
            TagetingMoves = new Queue();
        }
        TagetingMoves.add(move);
    }



    public boolean hasTargetingMoves()
    {
        return (TagetingMoves != null);
    }

    public boolean hasPiece()
    {
        return (piece != null);
    }

    public boolean isOccupiedBy()
    {
        return (this.piece.isBlack());
    }

    public voidd




}
