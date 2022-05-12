import chess.core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.function.Supplier;

public class ChessBoard extends JLayeredPane implements MouseMotionListener, MouseListener
{


    GridLayout layout;

    private final JChessPiece[][] jChessPieces = new JChessPiece[8][8];
    private final JPanel[][] boardFields = new JPanel[8][8];
    private final JPanel _glassPane = new JPanel();
    private ChessMechanics _mechanics;

    public void onResize()
    {
        _glassPane.setSize(this.getWidth(), this.getHeight());
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                var p = jChessPieces[y][x];
                if (p != null)
                {
                    p.setSize(getWidth() / 8, getHeight() / 8);
                    p.setLocation(x * getWidth() / 8, y * getHeight() / 8);
                    p.revalidate();
                }

                var f = boardFields[y][x];
                f.setSize(getWidth() / 8, getHeight() / 8);
                f.setLocation(x * getWidth() / 8, y * getHeight() / 8);
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

    public int SelectedX;
    public int SelectedY;
    public boolean IsSelected;


    private void addFields()
    {
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                var p = new JPanel();
                p.setBackground(isWhite(r, c) ? Color.green : Color.black);
                boardFields[r][c] = p;

                this.add(p, new Integer(-10));
            }
        }
    }

    private void updateBoard()
    {

    }

    private void recreateBoard()
    {
        for (int y = 0; y < 8; y++)
        {
            for (int x = 0; x < 8; x++)
            {
                var old = jChessPieces[y][x];
                if (old != null) {
                    this.remove(old);
                    jChessPieces[y][x] = null;
                }
                var tile = _mechanics.getChessBoard()[y][x];
                if (tile != null && tile.getPiece() != null)
                {
                    var p = new JChessPiece(tile.getPiece().getChessPieceId(), tile.getPlayerId().isBlack());
                    p.setSelectable(true);
                    jChessPieces[y][x] = p;
                }
            }
        }
    }


    public ChessBoard(ChessMechanics _mechanics)
    {
        this._mechanics = _mechanics;


        this.setLayout(null);

        recreateBoard();
        /*        jChessPieces[0][0] = new JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][2] = new JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][1] = new JChessPiece(ChessPieceId.BISHOP, false);*/

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
                && jChessPieces[fieldVec.y][fieldVec.x] != null) // if field is empty
        {
            this._selectedPiece = fieldVec;
            this.add(jChessPieces[fieldVec.y][fieldVec.x], new Integer(1));
        }


    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (_selectedPiece != null)
        {
            this.add(jChessPieces[_selectedPiece.y][_selectedPiece.x], new Integer(1));
            var target = vecFromPoint(e.getPoint());
            var moves = _mechanics.getChessBoard()[_selectedPiece.y][_selectedPiece.x].getPiece().getPossibleMoves();
            for (int i = 1; i <= moves.getNumberOfElements(); i++)
            {
                var move = ((ChessMove) moves.getByIndex(i));
                if (move.get_xTarget() == target.y && move.get_yTarget() == target.x)
                    _mechanics.executeMove(move);
            }
        }
        this._selectedPiece = null;
        recreateBoard();
        addPieces();
        onResize();
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }


    private Vec _selectedPiece;

    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (_selectedPiece != null)
        {
            var piece = jChessPieces[_selectedPiece.y][_selectedPiece.x];
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

