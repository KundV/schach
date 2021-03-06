package chess.core;

import chess.core.VerktetteListe.Dataelement;

import java.util.ArrayList;


public class ChessPiece
{
    //It contains the information about the chess piece.
    //after the piece is created, it is added to the board.
    //Die Klasse enthält die Informationen über das Schachfigur.
    //nachdem die Figur erstellt wurde, wird sie zur Schachbrett hinzugefügt.

    private ChessPieceId chessPieceId;      //Art der Figur
    private PlayerId playerId;              //Spieler
    private ArrayList<ChessMove> possibleMoves;     //Mögliche Züge der Figur
    private int moveCount = 0;              //getätigte Züge
    private int ID;


    public ChessPiece(ChessPieceId chessPieceId, PlayerId playerId)
    {
        this.chessPieceId = chessPieceId;
        this.playerId = playerId;
        this.possibleMoves = new ArrayList();

    }

    public ChessPiece(ChessPieceId chessPieceId, PlayerId playerId, int moveCount) //constructor für Simulations kopie
    {
        this.moveCount = moveCount;
        this.chessPieceId = chessPieceId;
        this.playerId = playerId;
        this.possibleMoves = new ArrayList();

    }


    public ChessPieceId getChessPieceId()
    {
        return chessPieceId;
    }

    public PlayerId getPlayerId()
    {
        return playerId;
    }

    public ArrayList<ChessMove> removeAllPossibleMoves()   //returns the ArrayList<ChessMove> of possible moves and deletes it
    {
        ArrayList<ChessMove> temp = possibleMoves;
        possibleMoves = new ArrayList();
        return possibleMoves;
    }

    public ArrayList<ChessMove> getPossibleMoves()        //returns the ArrayList<ChessMove> of possible moves
    {
        return possibleMoves;
    }

    public void setPossibleMoves(ArrayList<ChessMove> possibleMoves)
    {
        this.possibleMoves = possibleMoves;
    }

    public void addPossibleMove(ChessMove move)     //adds a move to the ArrayList<ChessMove> of possible moves
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

    public void resetAllPossibleMoves()         //resets the ArrayList<ChessMove> of possible moves
    {
        this.possibleMoves.clear();
    }

    public void removePossibleMove(ChessMove move)  //removes a specific move from the ArrayList<ChessMove> of possible moves
    {
        this.possibleMoves.remove(move);
    }

    public ChessMove removePossibleMove()     //removes the first move from the ArrayList<ChessMove> of possible moves
    {
        var rm = this.possibleMoves.get(0);
        this.possibleMoves.remove(0);
        return rm;
    }

    public boolean hasPossibleMove()      //checks if a specific move is in the ArrayList<ChessMove> of possible moves
    {
        return !this.possibleMoves.isEmpty();
    }

    public ArrayList<ChessMove> clonePossibleMoves()
    {
        ArrayList<ChessMove> temp = new ArrayList<>();
        for (int i = possibleMoves.size(); i > 0; i--)
        {
            temp.add(possibleMoves.get(i - 1));
        }
        return temp;
    }

    public boolean hasNonBlockedMoves()
    {
        for (int i = 0; i < possibleMoves.size(); i++)
        {
            if (possibleMoves.get(i + 1 - 1).getEvent() != EventID.Blocked) return true;
        }
        return false;
    }

    public int getMoveCount()
    {
        return moveCount;
    }

    public ChessPiece copy()
    {

        return new ChessPiece(this.chessPieceId, this.playerId, this.moveCount);
    }

    public void setChessPieceIdId(ChessPieceId chessPieceId)
    {
        this.chessPieceId = chessPieceId;
    }
}
