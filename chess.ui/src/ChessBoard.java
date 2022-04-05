import chess.core.ChessPieceId;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ChessBoard extends JPanel
{

    GridLayout layout;

    private JChessPiece[][] jChessPieces = new JChessPiece[8][8];

    public void onResize() {

        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                var p = jChessPieces[r][c];
                if (p != null) {
                    p.setSize(getWidth() / 8, getHeight() / 8);
                    p.setLocation(r * getWidth() / 8, c * getHeight() / 8);
                }
            }
        }

        revalidate();
        repaint();



    }

    private void addPieces() {
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                var p = jChessPieces[r][c];
                if (p != null) {
                    this.add(p);
                }
            }
        }
    }

    public ChessBoard()
    {
        this.setLayout(null);
        jChessPieces[0][0] = new JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][2] = new JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][1] = new JChessPiece(ChessPieceId.BISHOP, false);

        addPieces();


        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                onResize();
            }
        });
/*
        layout = new GridLayout(10, 10, 0, 0);
        this.setLayout(layout);
        for (int r = 0; r < layout.getRows(); r++)
        {
            for (int c = 0; c < layout.getColumns(); c++)
            {
                this.add(createBox(isWhite(r, c) ? Color.white : Color.black, r, c)).setLocation(r, c);
            }
        }
*/

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

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawBoard(g);


    }


    private BufferedImage createBufferedBox(int height, int width, Color color)
    {
        BufferedImage b = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = b.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, height, width);
        return b;
    }

    @Override
    public Dimension getPreferredSize()
    {

        // Relies on being the only component
        // in a layout that will center it without
        // expanding it to fill all the space.
        Dimension d = this.getParent().getSize();
        int newSize = Math.min(d.width, d.height);
        newSize = newSize == 0 ? 100 : newSize;

        // print newSize to console


        return new Dimension(newSize, newSize);



    }

    // Override getMinimumSize() to return the preferred size
    @Override
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }

    // Override getMaximumSize() to return the preferred size
    @Override
    public Dimension getMaximumSize()
    {
        return getPreferredSize();
    }

    private void drawBoard(Graphics g)
    {

        var b = createBufferedBox(getWidth() / 8, getHeight() / 8, Color.black);
        var w = createBufferedBox(getWidth() / 8, getHeight() / 8, Color.white);
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                g.drawImage(isWhite(x, y) ? w : b, x * getWidth() / 8, y * getHeight() / 8, null);
            }
        }
    }

}
