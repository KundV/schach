import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessBoard extends JPanel
{

    GridLayout layout;

    Dimension getPreferredSize()
    {
        // Relies on being the only component
        // in a layout that will center it without
        // expanding it to fill all the space.
        Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        newSize = newSize == 0 ? 100 : newSize;
        return new Dimension(newSize, newSize);

    }

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
        return (row + col) % 2 == 0;
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
