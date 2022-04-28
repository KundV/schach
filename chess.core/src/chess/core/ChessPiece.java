package chess.core;

import chess.core.VerktetteListe.Queue;

import java.util.ArrayList;


public class ChessPiece
{
    //It contains the information about the chess piece.
    //after the piece is created, it is added to the board.
    //after a move is made, all possible moves are calculated.
    private ChessPieceId chessPieceId;
    private PlayerId playerId;
    private Queue possibleMoves;
    private boolean isFirstMove = true;
    private int ID;


    public ChessPiece(ChessPieceId chessPieceId,PlayerId playerId, int ID)
    {
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

    public void setPossibleMoves(Queue possibleMoves)
    {
        this.possibleMoves = possibleMoves;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public Queue getPossibleMoves()
    {
        Queue temp = possibleMoves;
        possibleMoves = new Queue();
        return possibleMoves;
    }

    public void addPossibleMove(ChessMove move)
    {
        this.possibleMoves.add(move);
    }

    public boolean isFirstMove()
    {
        return isFirstMove;
    }

    //public void resetPossibleMove(ChessMove move)
    {
        this.possibleMoves.clear();
    }

    public void resetAllPossibleMove()
    {
        this.possibleMoves.clear();
    }
}
