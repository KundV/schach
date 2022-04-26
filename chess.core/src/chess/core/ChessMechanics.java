package chess.core;

import chess.core.VerktetteListe.Queue;
import org.jetbrains.annotations.NotNull;

public class ChessMechanics
{

    private ChessBoardTile[][] chessBoard; // the chess board
    private ChessPiece[] deadPieces;       // the dead pieces
    private int turn;                      // the current turn
    private PlayerId player = PlayerId.WHITE; // the current player

    public ChessMechanics(ChessBoardTile[][] chessBoard)
    {
        this.chessBoard = chessBoard;
        StartPosition();
    }

    public void getAllMoves(ChessBoardTile[][] chessBoard)
    {
        for(int i = 0; i<8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(chessBoard[i][j].getPiece() != null)
                {
                   CheckMoves(i,j);
                }
            }
        }
    }

    public void CheckMoves(int x, int y)
    {

        switch (chessBoard[x][y].getPiece().getChessPieceId())  // switch statement for the piece
        {
            case PAWN   -> { RulesPawn(x,y);}
            case TOWER  -> { RulesTower(chessBoard[x][y].getPiece());}
            case BISHOP -> { RulesBishop(chessBoard[x][y].getPiece());}
            case HORSE  -> { RulesHorse(chessBoard[x][y].getPiece());}
            case KING   -> { RulesKing(chessBoard[x][y].getPiece());}
            case QUEEN  -> { RulesQueen(chessBoard[x][y].getPiece());}
        }
    }

    public void RulesPawn(int x, int y)
    {
        if(chessBoard[x][y+1].getPiece() == null)
        {
            ChessMove move = new ChessMove(x,y,x,y+1,true);
            chessBoard[x][y].getPiece().addPossibleMove(move);
            chessBoard[x][y+1].addTargetingMove();
        }
        if(chessBoard[x][y].getPiece().isFirstMove())
        {

        }

    }
    public void RulesTower(ChessPiece Piece)
    {
        Queue possibleMoves = new Queue();
        possibleMoves.add(new ChessMove(1,1,1,1,true));
    }

    public void RulesBishop(ChessPiece Piece)
    {
        Queue possibleMoves = new Queue();
    }

    public Queue RulesHorse(ChessPiece Piece)
    {
        Queue possibleMoves = new Queue();
    }

    public Queue RulesQueen(ChessPiece Piece)
    {
        Queue possibleMoves = new Queue();
    }
    public Queue RulesKing(ChessPiece Piece)
    {
        Queue possibleMoves = new Queue();
    }

    public void StartPosition()
    {
        for(int i = 0; i<8; i++)
        {
            for (int j = 0; j <= 7; j++)
            {
                if(i==1 || i == 6)
                {
                    chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.PAWN,i < 2,i));
                }

                else if(i == 0 || i == 7)
                {
                    switch (j) {
                        case 0 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.TOWER,i < 2,i));
                        case 1 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.HORSE,i < 2,i));
                        case 2 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.BISHOP,i < 2,i));
                        case 3 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.QUEEN,i < 2,i));
                        case 4 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.KING,i < 2,i));
                        case 5 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.BISHOP,i < 2,i));
                        case 6 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.HORSE,i < 2,i));
                        case 7 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.TOWER,i < 2,i));
                    }

                }
            }



        }

    }
    public boolean setMove(ChessMove setMove)
    {
        return true;
    }
    public ChessBoardTile[][] getPossibleMoves(int x, int y)
    {
        return chessBoard;
    }
}

