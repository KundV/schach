package chess.core;

import chess.core.VerktetteListe.Queue;

public class ChessBoardTile                        //Is a tile on the chessboard. It can be empty or contain a piece.
{
    private ChessPiece piece;                    //The piece on this tile.
    private Queue TargetingMoves;                //The moves that can be made to this tile.



    public ChessBoardTile()                      //Constructor. Creates a new tile with no piece on it.
    {
        this.TargetingMoves = new Queue();
    }



    public ChessBoardTile(ChessPiece piece)   //Constructor. Creates a new tile with a piece on it.
    {
        this.piece = piece;
    }

    public ChessPiece getPiece()                 //Returns the piece on this tile.
    {
        return piece;
    }

    public void setPiece(ChessPiece piece)                 //Sets the piece on this tile.
    {
        this.piece = piece;
    }

    public ChessPiece removePiece()                 //Removes the piece from this tile and Returns the piece.
    {
        piece.resetAllPossibleMoves();
        ChessPiece temp = piece;
        piece = null;
        return temp;
    }

    public Queue removeAllTargetingMoves()      //Returns the targeting moves and clears the list.
    {
        Queue temp = TargetingMoves;
        TargetingMoves.clear();
        return temp;
    }

    public void removeTargetingMove(ChessMove move)      //Removes a specific targeting move from the list.
    {
        TargetingMoves.remove(move);
    }

    public void addTargetingMove(ChessMove move)    //Adds a targeting move to the list.
    {
        TargetingMoves.add(move);
    }
    public PlayerId getPlayerId()                 //Returns the player id of the piece on this tile.
    {
        return piece.getPlayerId();
    }

    public boolean hasTargetingMoves()                 //Returns true if the tile has targeting moves. Used for King movement.
    {
        return (TargetingMoves.isEmpty());
    }

    public Queue getTargetingMoves()                 //Returns the targeting moves.
    {
        return TargetingMoves;
    }

    public boolean hasPiece()                 //Returns true if the tile has a piece on it.
    {
        return (piece != null);
    }

    public Queue cloneTargetingMoves()
    {
        Queue temp = new Queue();
        for(int i = TargetingMoves.getNumberOfElements(); i > 0;i--)
        {
            temp.add(TargetingMoves.getByIndex(i));
        }
        return temp;
    }

    public void setTargetingMoves(Queue moves)
    {
        this.TargetingMoves = moves;
    }
}
