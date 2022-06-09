import chess.core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

@SuppressWarnings("removal")
public class ChessBoard extends JLayeredPane implements MouseMotionListener, MouseListener
{


    GridLayout layout;

    private final JChessPiece[][] chessPieceUIComponents = new JChessPiece[8][8];
    private final JChessField[][] boardFields = new JChessField[8][8];
    private final JPanel _glassPane = new JPanel();
    private ChessMechanics _mechanics;

    public void updatePlacement()
    {
        _glassPane.setSize(this.getWidth(), this.getHeight());
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                // Placement of pieces
                var p = chessPieceUIComponents[y][x];
                if (p != null)
                {
                    p.setSize(getWidth() / 8, getHeight() / 8);
                    p.setLocation(x * getWidth() / 8, y * getHeight() / 8);
                    p.revalidate();
                }

                // Placement of fields
                var f = boardFields[y][x];
                f.setSize(getWidth() / 8, getHeight() / 8);
                f.setLocation(x * getWidth() / 8, y * getHeight() / 8);
                f.revalidate();
            }
        }
        repaint();
    }

    private void updateTargetHints()
    {
        Arrays.stream(boardFields).forEach(row -> Arrays.stream(row).forEach(JChessField::removeTarget));

        if (this._selectedPiece != null)
        {
            var moves = this._mechanics.getChessBoard()[_selectedPiece.y][_selectedPiece.x].getPiece().getPossibleMoves();
            for (var move : moves)
            {
                if (move.event.getID() == EventID.Blocked) continue;

                var b = boardFields[move.xTarget][move.yTarget];
                b.setTarget(
                        move.event.getID() == EventID.Capture || move.event.getID() == EventID.enPassant,
                        _mechanics.getChessBoard()[move.xTarget][move.yTarget].getPiece() != null);
            }
        }
        repaint();
    }

    private void updateSelectableHints()
    {
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {

                var t = _mechanics.getChessBoard()[r][c];
                if (t != null && t.getPiece() != null)
                {
                    var selectable = t.getPiece().getPlayerId() == _mechanics.getCurrentPlayer() && t.getPiece().hasNonBlockedMoves();
                    chessPieceUIComponents[r][c].setSelectable(selectable);
                }


            }
        }
    }

    private void addPieces()
    {


        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                var p = chessPieceUIComponents[r][c];
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
                var p = new JChessField(!isWhite(r, c));
                //p.setBackground(isWhite(r, c) ? Color.white : Color.black);
                boardFields[r][c] = p;

                this.add(p, new Integer(-10));
            }
        }
    }

    private void processMove(ChessMove move)
    {
        var piece = chessPieceUIComponents[move.xStart][move.yStart];
        var captured = chessPieceUIComponents[move.xTarget][move.yTarget];
        if (captured != null)
            this.remove(captured);
        chessPieceUIComponents[move.xTarget][move.yTarget] = piece;
        chessPieceUIComponents[move.xStart][move.yStart] = null;
        updatePlacement();
    }

    /**
     * Recreates the piece-Array. Removes all pieces from the board and adds them again.
     */
    private void reInitializeUiArray()
    {
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                var old = chessPieceUIComponents[r][c];
                if (old != null)
                {
                    this.remove(old);
                    chessPieceUIComponents[r][c] = null;
                }
                var tile = _mechanics.getChessBoard()[r][c];
                if (tile != null && tile.getPiece() != null)
                {
                    var p = new JChessPiece(tile.getPiece().getChessPieceId(), tile.getPlayerId().isBlack());
                    p.setSelectable(true);
                    this.add(p, new Integer(-9));
                    chessPieceUIComponents[r][c] = p;
                }
            }
        }
    }


    public ChessBoard(ChessMechanics _mechanics)
    {
        this._mechanics = _mechanics;


        this.setLayout(null);


        reInitializeUiArray();
        /*        jChessPieces[0][0] = new JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][2] = new JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][1] = new JChessPiece(ChessPieceId.BISHOP, false);*/

        _glassPane.addMouseListener(this);
        _glassPane.addMouseMotionListener(this);
        _glassPane.setOpaque(false);
        this.add(_glassPane, new Integer(0));

        // jChessPieces[0][0].addMouseListener(mouseListener);
        //addPieces();
        addFields();
        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                updatePlacement();
            }

        });


        updateSelectableHints();
    }


    private static boolean isWhite(int row, int col)
    {
        //return row + (column % 2) % 2 == 0;
        return (row + col) % 2 == 0;
    }


    @Override
    public void mouseClicked(MouseEvent e)
    {

        //System.out.println(e.getX() / (getWidth() / 8));
    }

    @Override
    public void mousePressed(MouseEvent e)
    {


        var fieldVec = vecFromPoint(e.getPoint());
        if (fieldVec.x < 0 && fieldVec.y < 0 // if field is negative
                && fieldVec.x >= 8 && fieldVec.y >= 8)
        {
            return;
        }
        var p = _mechanics.getChessBoard()[fieldVec.y][fieldVec.x].getPiece();

        if (p == null || !p.hasNonBlockedMoves() || p.getPlayerId() != _mechanics.getCurrentPlayer())
        {
            return;
        }

        if (chessPieceUIComponents[fieldVec.y][fieldVec.x] != null) // if field is empty
        {
            this._selectedPiece = fieldVec;
            this.setLayer(chessPieceUIComponents[fieldVec.y][fieldVec.x], 1);
            //this.add(chessPieceUIComponents[fieldVec.y][fieldVec.x], new Integer(1));
        }

        updateTargetHints();


    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

        if (_selectedPiece != null)
        {
            this.add(chessPieceUIComponents[_selectedPiece.y][_selectedPiece.x], new Integer(1));
            var target = vecFromPoint(e.getPoint());
            var moves = _mechanics.getChessBoard()[_selectedPiece.y][_selectedPiece.x].getPiece().getPossibleMoves();
            for (int i = 1; i <= moves.getNumberOfElements(); i++)
            {
                var move = ((ChessMove) moves.getByIndex(i));
                if (move.xTarget == target.y && move.yTarget == target.x && move.event.getID() != EventID.Blocked)
                {
                    _mechanics.executeMove(move);
                    processMove(move);
                }
            }
        }
        this._selectedPiece = null;
        updatePlacement();
        updateSelectableHints();
        updateTargetHints();
        //recreateBoard();
        //addPieces();
        //onResize();
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
            var piece = chessPieceUIComponents[_selectedPiece.y][_selectedPiece.x];
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

    public void Undo()
    {
        _mechanics.reverseMove();
        reInitializeUiArray();
        updatePlacement();
        updateSelectableHints();
    }
}

