import chess.core.ChessPieceId;
import chess.core.Vec;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ChessBoard extends JLayeredPane implements MouseMotionListener, MouseListener
{

    private final JChessPiece[][] jChessPieces = new JChessPiece[8][8];
    private final JPanel[][] boardFields = new JPanel[8][8];
    private final JPanel _glassPane = new JPanel();
    public int SelectedX;
    public int SelectedY;
    public boolean IsSelected;
    GridLayout layout;
    private Vec _selectedPiece;
    public ChessBoard()
    {


        this.setLayout(null);
        jChessPieces[0][0] = new JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][2] = new JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][1] = new JChessPiece(ChessPieceId.BISHOP, false);

        _glassPane.addMouseListener(this);
        _glassPane.addMouseMotionListener(this);
        _glassPane.setOpaque(false);
        this.add(_glassPane, new Integer(0));

        // jChessPieces[0][0].addMouseListener(mouseListener);
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

    public void onResize()
    {
        _glassPane.setSize(this.getWidth(), this.getHeight());
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
                    this.add(p, new Integer(-9));
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

                this.add(p, new Integer(-10));
            }
        }
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

    @Override
    public void mouseClicked(MouseEvent e)
    {

        System.out.println(e.getX() / (getWidth() / 8));
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

        var fieldVec = vecFromPoint(e.getPoint());
        if (fieldVec.x >= 0 && fieldVec.y >= 0 // if field is negative
                && fieldVec.x < 8 && fieldVec.y < 8 // if field is out of bounds
                && jChessPieces[fieldVec.x][fieldVec.y] != null) // if field is empty
        {
            this._selectedPiece = fieldVec;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        onResize();
        this._selectedPiece = null;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (_selectedPiece != null)
        {
            var piece = jChessPieces[_selectedPiece.x][_selectedPiece.y];
            piece.setLocation(e.getPoint());

        }
    }


    private Vec vecFromPoint(Point p)
    {
        return new Vec(fieldFromX(p.x), fieldFromY(p.y));
    }

    private Point pointFromVec(Vec v)
    {
        return new Point(v.x * getWidth() / 8, v.y * getHeight() / 8);
    }

    private int fieldFromX(int x)
    {
        return x / (getWidth() / 8);
    }

    private int fieldFromY(int y)
    {
        return y / (getHeight() / 8);
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {

    }
}

