package chess.core;

import chess.core.Kompositum.Dataelement;

import java.util.ArrayList;

public class ChessPiece
{
    //ChessPiece class is a dataelement.
    //It contains the information about the chess piece.
    //after the piece is created, it is added to the board.
    //after a move is made, all possible moves are calculated.
    private ChessPieceId chessPieceId;
    private boolean isBlack;
    private ArrayList<ChessMove> possibleMoves;
    private int ID;


    public ChessPiece(ChessPieceId chessPieceId, boolean isBlack, int ID)
    {
        this.chessPieceId = chessPieceId;
        this.isBlack = isBlack;
        this.possibleMoves = new ArrayList<>();

    }

    public ChessPieceId getChessPieceId()
    {
        return chessPieceId;
    }

    public void setChessPieceId(ChessPieceId chessPieceId)
    {
        this.chessPieceId = chessPieceId;
    }

    public boolean isBlack()
    {
        return isBlack;
    }

    public void setBlack(boolean black)
    {
        isBlack = black;
    }


    public void setPossibleMoves(ArrayList<ChessMove> possibleMoves)
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

    public ArrayList<ChessMove> getPossibleMoves()
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
