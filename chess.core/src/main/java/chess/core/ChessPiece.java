package chess.core;

import chess.core.VerktetteListe.Queue;


public class ChessPiece
{
    //ChessPiece class is a dataelement.
    //It contains the information about the chess piece.
    //after the piece is created, it is added to the board.
    //after a move is made, all possible moves are calculated.
    private ChessPieceId chessPieceId;
    private PlayerId playerId;
    private Queue possibleMoves;
    private int ID;


    public ChessPiece(ChessPieceId chessPieceId,boolean playerId, int ID)
    {
        this.chessPieceId = chessPieceId;
        if(playerId = true)
        {
        this.playerId = PlayerId.BLACK;
        }
        else
        {
            this.playerId = PlayerId.WHITE;
        }
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
        return possibleMoves;
    }

    public void addPossibleMove(ChessMove move)
    {
        this.possibleMoves.add(move);
    }

    public void resetPossibleMoves(ChessMove move)
    {
        this.possibleMoves.clear();
    }
}
