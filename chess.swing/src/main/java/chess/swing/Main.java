package chess.swing;

import javax.swing.*;

public class Main
{


    public static void main(String[] args)
    {

        var f = new JFrame();

        var piece = new ChessBoard();
        f.add(piece);
        f.setSize(700, 700);
        f.setVisible(true);
    }

}

/*

class ChessSnapshot
{
    ChessPieceId[][] board;

    ChessSnapshot applyAction()
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}

class ChessGame
{

    private final List<ChessAction> gameHistory = new ArrayList<>();
    void addAction(ChessAction action)
    {

    }

    bool itsBlacksTurn()
    {

    }

    List<ChessAction> getValidActions()
    {
        // Gibt mögliche Funktionen zurück
    }

}

class ChessAction
{
    int xStart; //Startposition der Figur
    int yStart;
    int xMove; //Bewegung
    int yMove;
    int xKill; //Töten
    int yKill;
}*/
