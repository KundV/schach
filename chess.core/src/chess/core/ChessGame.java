package chess.core;

import java.util.ArrayList;

public class ChessGame
{
    private ChessPiece[][] Spielbrett = new ChessPiece[8][8] ;
    private ChessPiece[] ToteFigurern;

    public ChessGame()
    {
        StartAufstellung();
    }

    public boolean setMove(ChessGame move)
    {
        return true;
    }

    public ArrayList<ChessMove> getPossibleMoves(int x, int y)
    {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        possibleMoves.add(new ChessMove(x,y,1,1));
        return possibleMoves;
    }

    public void StartAufstellung()
    {
        for(int i = 0; i<8; i++)
        {
            for (int j = 0; j <= 7; j++)
            {
                if(i==1 || i == 6)
                {
                    Spielbrett[i][j] = new ChessPiece(ChessPieceId.PAWN,i < 2);
                }

                else if(i == 0 || i == 7)
                {
                    switch (j) {
                        case 0 -> Spielbrett[i][j] = new ChessPiece(ChessPieceId.TOWER,i < 2);
                        case 1 -> Spielbrett[i][j] = new ChessPiece(ChessPieceId.HORSE,i < 2);
                        case 2 -> Spielbrett[i][j] = new ChessPiece(ChessPieceId.BISHOP,i < 2);
                        case 3 -> Spielbrett[i][j] = new ChessPiece(ChessPieceId.QUEEN,i < 2);
                        case 4 -> Spielbrett[i][j] = new ChessPiece(ChessPieceId.KING,i < 2);
                        case 5 -> Spielbrett[i][j] = new ChessPiece(ChessPieceId.BISHOP,i < 2);
                        case 6 -> Spielbrett[i][j] = new ChessPiece(ChessPieceId.HORSE,i < 2);
                        case 7 -> Spielbrett[i][j] = new ChessPiece(ChessPieceId.TOWER,i < 2);
                    }

                }
            }



        }

    }
}
