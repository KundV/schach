package chess.core;

import java.util.ArrayList;

public class ChessRules
{
    public ChessRules()
    {

    }

    public ArrayList<ChessMove> CheckMoves(ChessPiece Piece)
    {
        switch (Piece.chessPieceId)
        {
            case PAWN   -> {return RulesPawn(Piece);}
            case TOWER  -> {return RulesTower(Piece);}
            case BISHOP -> {return RulesBishop(Piece);}
            case HORSE  -> {return RulesHorse(Piece);}
            case KING   -> {return RulesKing(Piece);}
            case QUEEN  -> {return RulesQueen(Piece);}
        }
        return null;
    }

    public ArrayList<ChessMove> RulesPawn(ChessPiece Piece)
    {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        possibleMoves.add(new ChessMove(1,1,1,1));
        return possibleMoves;
    }
    public ArrayList<ChessMove> RulesTower(ChessPiece Piece)
    {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        possibleMoves.add(new ChessMove(1,1,1,1));
        return possibleMoves;
    }
    public ArrayList<ChessMove> RulesBishop(ChessPiece Piece)
    {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        possibleMoves.add(new ChessMove(1,1,1,1));
        return possibleMoves;
    }
    public ArrayList<ChessMove> RulesHorse(ChessPiece Piece)
    {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        possibleMoves.add(new ChessMove(1,1,1,1));
        return possibleMoves;
    }
    public ArrayList<ChessMove> RulesKing(ChessPiece Piece)
    {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        possibleMoves.add(new ChessMove(1,1,1,1));
        return possibleMoves;
    }
    public ArrayList<ChessMove> RulesQueen(ChessPiece Piece)
    {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        possibleMoves.add(new ChessMove(1,1,1,1));
        return possibleMoves;
    }
}

