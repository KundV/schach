package chess.core;

import chess.core.VerktetteListe.Dataelement;
import chess.core.VerktetteListe.Queue;


public class ChessPiece
{
    //It contains the information about the chess piece.
    //after the piece is created, it is added to the board.
    //after a move is made, all possible moves are calculated.
    private ChessPieceId chessPieceId;
    private PlayerId playerId;
    private Queue possibleMoves;
    private int moveCount = 0;
    private int ID;


    public ChessPiece(ChessPieceId chessPieceId, PlayerId playerId)
    {
        this.chessPieceId = chessPieceId;
        this.playerId = playerId;
        this.possibleMoves = new Queue();

    }

    public ChessPiece(ChessPieceId chessPieceId, PlayerId playerId, int moveCount)
    {
        this.moveCount = moveCount;
        this.chessPieceId = chessPieceId;
        this.playerId = playerId;
        this.possibleMoves = new Queue();

    }

    public ChessPieceId getChessPieceId()
    {
        return chessPieceId;
    }

    public PlayerId getPlayerId()
    {
        return playerId;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public Queue removeAllPossibleMoves()   //returns the queue of possible moves and deletes it
    {
        Queue temp = possibleMoves;
        possibleMoves = new Queue();
        return possibleMoves;
    }

    public Queue getPossibleMoves()        //returns the queue of possible moves
    {
        return possibleMoves;
    }

    public void setPossibleMoves(Queue possibleMoves)
    {
        this.possibleMoves = possibleMoves;
    }

    public void addPossibleMove(ChessMove move)     //adds a move to the queue of possible moves
    {
        this.possibleMoves.add(move);
    }

    public boolean isFirstMove()            //returns if the piece has been moved or not
    {
        return moveCount == 0;
    }

    public void addMoveCount()       //sets if the piece has been moved or not
    {
        moveCount++;
    }

    public void removeMoveCount()
    {
        moveCount--;
    }

    public void resetAllPossibleMoves()         //resets the queue of possible moves
    {
        this.possibleMoves.clear();
    }

    public void removePossibleMove(ChessMove move)  //removes a specific move from the queue of possible moves
    {
        this.possibleMoves.remove(move);
    }

    public Dataelement removePossibleMove()     //removes the first move from the queue of possible moves
    {
        return this.possibleMoves.remove();
    }

    public boolean hasPossibleMove()      //checks if a specific move is in the queue of possible moves
    {
        return !this.possibleMoves.isEmpty();
    }

    public Queue clonePossibleMoves()
    {
        Queue temp = new Queue();
        for (int i = possibleMoves.getNumberOfElements(); i > 0; i--)
        {
            temp.add(possibleMoves.getByIndex(i));
        }
        return temp;
    }

    public boolean hasNonBlockedMoves()
    {
        for (int i = 0; i < possibleMoves.getNumberOfElements(); i++)
        {
            if (possibleMoves.getByIndex(i + 1).getEvent().getID() != EventID.Blocked) return true;
        }
        return false;
    }

    public int getMoveCount()
    {
        return moveCount;
    }
}
