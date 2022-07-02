package chess.ui;

import chess.core.*;
import chess.core.common.Vec;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

@SuppressWarnings("removal")
public class ChessBoard extends JLayeredPane implements MouseMotionListener, MouseListener
{



    private final JChessPiece[][] chessPieceUIComponents = new JChessPiece[8][8];
    private final JChessField[][] boardFields = new JChessField[8][8];
    private final JPanel _glassPane = new JPanel();
    GridLayout layout;
    private ChessMechanics _mechanics;
    private Vec _selectedPiece;
    private Vec _hoveringField;
    private Vec _selectedPieceDragOffset;
    private ChessOptionsModel _options;

    public ChessBoard(ChessMechanics _mechanics)
    {
        this._mechanics = _mechanics;
        this._options = new ChessOptionsModel();

        this.setLayout(null);


        reInitializeUiArray();
        /*        jChessPieces[0][0] = new chess.ui.JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][2] = new chess.ui.JChessPiece(ChessPieceId.BISHOP, false);
        jChessPieces[0][1] = new chess.ui.JChessPiece(ChessPieceId.BISHOP, false);*/

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

    public void setOptions(ChessOptionsModel options)
    {
        _options = options.clone();
        updateSelectableHints();
        updateTargetHints();
    }

    public ChessOptionsModel getOptions()
    {
        return _options.clone();
    }

    public void ChangeGame(ChessMechanics newGame)
    {
        _mechanics = newGame;
        _selectedPiece = null;
        _hoveringField = null;
        _selectedPieceDragOffset = null;
        reInitializeUiArray();
        updatePlacement();
        updateSelectableHints();
    }

    private static boolean isWhite(int row, int col)
    {
        //return row + (column % 2) % 2 == 0;
        return (row + col) % 2 == 0;
    }

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

        Vec pieceVec = _selectedPiece == null ? _hoveringField : _selectedPiece;
        var hovering = !_piecePressed;
        if (pieceVec != null && this._mechanics.getChessBoard()[pieceVec.y][pieceVec.x].getPiece() != null)
        {
            ChessPiece piece = this._mechanics.getChessBoard()[pieceVec.y][pieceVec.x].getPiece();
            var isEnemy = piece.getPlayerId() != _mechanics.getCurrentPlayer();
            var state = isEnemy ? _options.EnemyHints : _options.OwnHints;

            if (state != ChessOptionsModel.TargetHintsVisibilityPreference.Disabled && (state == ChessOptionsModel.TargetHintsVisibilityPreference.OnClick) != hovering || state == ChessOptionsModel.TargetHintsVisibilityPreference.OnHover)
            {
                var moves = piece.getPossibleMoves();
                for (var move : moves)
                {
                    if (move.event == EventID.Blocked) continue;

                    var b = boardFields[move.xTarget][move.yTarget];
                    b.setTarget(
                            move.event == EventID.Capture || move.event == EventID.enPassant,
                            _mechanics.getChessBoard()[move.xTarget][move.yTarget].getPiece() != null);
                }
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
                    boolean selectable = t.getPiece().hasNonBlockedMoves() && _mechanics.getCurrentPlayer() == t.getPiece().getPlayerId();
                    JChessPiece.State state = JChessPiece.State.Selectable;
                    if (!selectable || _selectedPiece != null || !_options.showSelectableHints)
                    {
                        state = JChessPiece.State.None;
                    } else if (_hoveringField != null && _hoveringField.equals(new Vec(c, r)))
                    {
                        state = JChessPiece.State.Hovering;
                    }


                    JChessPiece.State s = selectable && _hoveringField != null && _hoveringField.y == r && _hoveringField.x == c && _selectedPiece == null && _options.showSelectableHints ? JChessPiece.State.Hovering : (selectable && _selectedPiece == null ? JChessPiece.State.Selectable : JChessPiece.State.None);
                    chessPieceUIComponents[r][c].setSelectable(state);
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

    private void addFields()
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                var p = new JChessField(!isWhite(x, y));
                //p.setBackground(isWhite(x, y) ? Color.white : Color.black);
                boardFields[x][y] = p;

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
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                var old = chessPieceUIComponents[x][y];
                if (old != null)
                {
                    this.remove(old);
                    chessPieceUIComponents[x][y] = null;
                }
                var tile = _mechanics.getChessBoard()[x][y];
                if (tile != null && tile.getPiece() != null)
                {
                    var p = new JChessPiece(tile.getPiece().getChessPieceId(), tile.getPlayerId().isBlack());
                    p.setSelectable(JChessPiece.State.None);
                    this.add(p, new Integer(-9));
                    chessPieceUIComponents[x][y] = p;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

        //System.out.println(e.getX() / (getWidth() / 8));
    }

    @Override
    public void mousePressed(MouseEvent e)
    {


        var fieldVec = posFromPoint(e.getPoint());
        if (fieldVec.x < 0 || fieldVec.y < 0 // filters all points out of range
                || fieldVec.x >= 8 || fieldVec.y >= 8)
        {
            return;
        }
        var p = _mechanics.getChessBoard()[fieldVec.y][fieldVec.x].getPiece(); // current piece
        if (p != null) _piecePressed = true;
        // stops if there is no piece or it
        if (p != null && p.hasNonBlockedMoves() && p.getPlayerId() == _mechanics.getCurrentPlayer())
        {
            if (chessPieceUIComponents[fieldVec.y][fieldVec.x] != null) // if field is empty
            {
                this._selectedPiece = fieldVec;
                var upperLeft = pointFromVec(this._selectedPiece);
                this._selectedPieceDragOffset = new Vec(e.getX() - upperLeft.x, e.getY() - upperLeft.y);
                this.setLayer(chessPieceUIComponents[fieldVec.y][fieldVec.x], 1);

                //this.add(chessPieceUIComponents[fieldVec.y][fieldVec.x], new Integer(1));
            }

        }
        updateTargetHints();


    }

    private boolean _piecePressed = false;

    @Override
    public void mouseReleased(MouseEvent e)
    {

        _piecePressed = false;
        if (_selectedPiece != null)
        {
            this.add(chessPieceUIComponents[_selectedPiece.y][_selectedPiece.x], new Integer(1));
            var target = posFromPoint(e.getPoint());
            var moves = _mechanics.getChessBoard()[_selectedPiece.y][_selectedPiece.x].getPiece().getPossibleMoves();
            for (int i = 1; i <= moves.size(); i++)
            {
                var move = ((ChessMove) moves.get(i - 1));
                if (move.xTarget == target.y && move.yTarget == target.x && move.event != EventID.Blocked)
                {
                    /*
                    JOptionPane.showOptionDialog(this, "Wähle die Figur zu der befördert werden soll", "Beförderung", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{
                            "Dame", "Turm", "Pferd", "Läufer"
                    }, null);
                    */

                    _mechanics.executeMove(move);
                    processMove(move);
                }
            }
            this._hoveringField = target;
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
        _hoveringField = null;
        updateSelectableHints();
        updateTargetHints();
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (_selectedPiece != null)
        {
            var piece = chessPieceUIComponents[_selectedPiece.y][_selectedPiece.x];
            var size = getTileSize();
            var newSize = new Vec(size).mul(1.2);
            var difference = newSize.sub(size.x, size.y);
            piece.setSize(newSize.x, newSize.y);
            piece.setLocation(new Point(e.getX() - _selectedPieceDragOffset.x - difference.x / 2, e.getY() - _selectedPieceDragOffset.y - difference.y / 2));
        }
        var pos = getMouseVec();
        if (pos != null && _mechanics.getChessBoard()[pos.y][pos.x].getPiece() != null)
        {
            _hoveringField = pos;
        } else
        {
            _hoveringField = null;
        }
        updateTargetHints();
    }

    private Point getTileSize()
    {
        return new Point(getWidth() / 8, getHeight() / 8);
    }

    private Vec posFromPoint(Point p)
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
        var pos = getMouseVec();
        if (pos != null && _mechanics.getChessBoard()[pos.y][pos.x].getPiece() != null)
        {
            _hoveringField = pos;
        } else
        {
            _hoveringField = null;
        }

        updateSelectableHints();
        updateTargetHints();
    }

    public Vec getMouseVec()
    {
        return getMouseVec(null);
    }

    public Vec getMouseVec(@Nullable Point p)
    {
        if (p == null) p = this.getMousePosition(true);
        if (p == null) return null;
        var vec = posFromPoint(p);
        return (vec.y < 0 || vec.x < 0 || vec.y >= 8 || vec.x >= 8) ? null : vec;
    }

    public void Undo()
    {
        _mechanics.reverseMove();
        reInitializeUiArray();
        updatePlacement();
        updateSelectableHints();
    }
}

