import chess.core.ChessPieceId;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ChessBoard extends JLayeredPane
{

    GridLayout layout;

    private JChessPiece[][] jChessPieces = new JChessPiece[8][8];
    private JPanel[][] boardFields = new JPanel[8][8];

    public void onResize()
    {
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                var p = jChessPieces[r][c];
                if (p != null)
                {
                    p.setSize(getWidth() / 8, getHeight() / 8);
                    p.setLocation(r * getWidth() / 8, c * getHeight() / 8);
                    p.revalidate();
                }

                var f = boardFields[r][c];
                f.setSize(getWidth() / 8, getHeight() / 8);
                f.setLocation(r * getWidth() / 8, c * getHeight() / 8);
                f.revalidate();
            }
        }
        repaint();
    }

    private void addPieces()
    {
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                var p = jChessPieces[r][c];
                if (p != null)
                {
                    this.add(p, new Integer(3));
                }
            }
        }
    }

    private void addFields()
    {
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                var p = new JPanel();
                p.setBackground(isWhite(r, c) ? Color.white : Color.black);
                boardFields[r][c] = p;

                this.add(p, new Integer(0));
            }
        }
    }

    public ChessBoard()
    {
        //this.setDoubleBuffered(true);

        this.setLayout(null);
        jChessPieces[0][0] = new JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][2] = new JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][1] = new JChessPiece(ChessPieceId.BISHOP, false);

        addPieces();
        addFields();
        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                onResize();
            }
        });
    }

    private static boolean isWhite(int row, int col)
    {
        //return row + (column % 2) % 2 == 0;
        return (row + col) % 2 == 0;
    }

    private static JPanel createBox(boolean isBlack, int row, int col)
    {
        var r = new JPanel();
        r.setBackground(isBlack ? Color.black : Color.white);
        return r;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //drawBoard(g);


    }


    private BufferedImage createBufferedBox(int width, int height, Color color)
    {
        BufferedImage b = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = b.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        return b;
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
