package chess.core;

import java.util.ArrayList;

public class ChessRules
{
    public ChessRules()
    {

    }
    public ArrayList<ChessMove> CheckMoves(ChessPiece Piece)
    {
        switch(Piece)
        {
            case(Piece.chessPieceId==ChessPieceId.PAWN)     -> RulesPawn(Piece);
            case(Piece.chessPieceId==ChessPieceId.TOWER)    -> RulesTower(Piece);
            case(Piece.chessPieceId==ChessPieceId.BISHOP)   -> RulesBishop(Piece);
            case(Piece.chessPieceId==ChessPieceId.HORSE)    -> RulesHorse(Piece);
            case(Piece.chessPieceId==ChessPieceId.KING)     -> RulesKing(Piece);
            case(Piece.chessPieceId==ChessPieceId.QUEEN)    -> RulesQueen(Piece);
        }
    }
    public ArrayList<ChessMove> RulesPawn(ChessPiece Piece)
    {

    }
    public ArrayList<ChessMove> RulesTower(ChessPiece Piece)
    {

    }
    public ArrayList<ChessMove> RulesBishop(ChessPiece Piece)
    {

    }
    public ArrayList<ChessMove> RulesHorse(ChessPiece Piece)
    {

    }
    public ArrayList<ChessMove> RulesKing(ChessPiece Piece)
    {

    }
    public ArrayList<ChessMove> RulesQueen(ChessPiece Piece)
    {

    }
}

