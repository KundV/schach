package chess.swing;

import javax.swing.*;
import java.awt.*;

public class ChessBoard extends JPanel
{

    GridLayout layout;

    public ChessBoard()
    {
        layout = new GridLayout(10, 10, 10, 10);
        this.setLayout(layout);
        for (int r = 0; r < layout.getRows(); r++)
        {
            for (int c = 0; c < layout.getColumns(); c++)
            {
                this.add(createBox(isWhite(r, c) ? Color.white : Color.black, r, c)).setLocation(r, c);
            }
        }

    }

    private static boolean isWhite(int row, int col)
    {
        //return row + (column % 2) % 2 == 0;
        return (row + col) % 2 == 1;
    }

    private static JPanel createBox(Color color, int row, int col)
    {
        var r = new JPanel();
        r.setBackground(color);
        var l = new JLabel("" + row + " " + col);
        l.setForeground(Color.red);
        r.add(l);
        return r;
    }
}
