package chess.core;


import java.util.ArrayList;

class Main
{
    public static void main(String[]args)
    {

        ArrayList<ChessGame> board = new ArrayList<ChessGame>();
        for (int i = 0; i < 100000; i++)
        {
            board.add(new ChessGame());
        }
    }

}
