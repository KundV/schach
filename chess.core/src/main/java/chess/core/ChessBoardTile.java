package chess.core;


import java.util.ArrayList;

public class ChessBoardTile implements Cloneable                        //Is a tile on the chessboard. It can be empty or contain a piece.
{
    private ChessPiece piece;                    //The piece on this tile.
    private ArrayList<ChessMove> TargetingMoves;                //The moves that can be made to this tile.



    public ChessBoardTile()                      //Constructor. Creates a new tile with no piece on it.
    {
        this.TargetingMoves = new ArrayList();
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

    public ArrayList<ChessMove> extractAllTargetingMoves()      //Returns the targeting moves and clears the list.
    {
        ArrayList<ChessMove> temp = cloneTargetingMoves();
        TargetingMoves.clear();
        return temp;
    }
    public void extractAllTargetingMoves(ArrayList<ChessMove> temp)      //Returns the targeting moves and clears the list.
    {
        cloneTargetingMoves(temp);
        TargetingMoves.clear();
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

    public ArrayList<ChessMove> getTargetingMoves()                 //Returns the targeting moves.
    {
        return TargetingMoves;
    }

    public boolean hasPiece()                 //Returns true if the tile has a piece on it.
    {
        return (piece != null);
    }

    public ArrayList<ChessMove> cloneTargetingMoves()
    {
        ArrayList<ChessMove> temp = new ArrayList<ChessMove>();
        for(int i = TargetingMoves.size(); i > 0; i--)//TODO: Possible bug.!!!
        {
            temp.add(TargetingMoves.get(i - 1));
        }
        return temp;
    }
    public void cloneTargetingMoves(ArrayList<ChessMove> temp)
    {
        for(int i = TargetingMoves.size(); i > 0; i--)//TODO: Possible bug.!!!
        {
            temp.add(0, TargetingMoves.get(i - 1));
        }
    }

    public void setTargetingMoves(ArrayList<ChessMove> moves)
    {
        this.TargetingMoves = moves;
    }

    @Override
    public ChessBoardTile clone()
    {
        try
        {

            ChessBoardTile clone = (ChessBoardTile) super.clone();

            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e)
        {
            throw new AssertionError();
        }
    }
}
