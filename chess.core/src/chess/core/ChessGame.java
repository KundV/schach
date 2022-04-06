package chess.core;

import java.util.ArrayList;

public class ChessGame
{
    private ChessPiece[][] chessBoard = new ChessPiece[8][8] ;
    private ChessPiece[][] chessBoardOld = new ChessPiece[8][8] ;
    private ChessPiece[] ToteFigurern;

    public ChessGame()
    {
        StartPosition();
    }

    public boolean setMove(ChessMove setMove)
    {
        return true;
    }
    public ChessPiece[][] getPossibleMoves(int x, int y)
    {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        possibleMoves.add(new ChessMove(x,y,1,1,0));
        return chessBoard;
    }

    public void StartPosition()
    {
        for(int i = 0; i<8; i++)
        {
            for (int j = 0; j <= 7; j++)
            {
                if(i==1 || i == 6)
                {
                    chessBoard[i][j] = new ChessPiece(ChessPieceId.PAWN,i < 2,i);
                }

                else if(i == 0 || i == 7)
                {
                    switch (j) {
                        case 0 -> chessBoard[i][j] = new ChessPiece(ChessPieceId.TOWER,i < 2,i);
                        case 1 -> chessBoard[i][j] = new ChessPiece(ChessPieceId.HORSE,i < 2,i);
                        case 2 -> chessBoard[i][j] = new ChessPiece(ChessPieceId.BISHOP,i < 2,i);
                        case 3 -> chessBoard[i][j] = new ChessPiece(ChessPieceId.QUEEN,i < 2,i);
                        case 4 -> chessBoard[i][j] = new ChessPiece(ChessPieceId.KING,i < 2 ,i);
                        case 5 -> chessBoard[i][j] = new ChessPiece(ChessPieceId.BISHOP,i < 2 ,i);
                        case 6 -> chessBoard[i][j] = new ChessPiece(ChessPieceId.HORSE,i < 2 ,i);
                        case 7 -> chessBoard[i][j] = new ChessPiece(ChessPieceId.TOWER,i < 2 ,i);
                    }

                }
            }



        }

    }
}
