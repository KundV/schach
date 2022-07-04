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


    /**
     * Array mit den Schachfigur-UI-Komponenten. Wird mit der Logik synchronisiert.
     */
    private final JChessPiece[][] chessPieceUIComponents = new JChessPiece[8][8];
    /**
     * Array mit den Spielfeld-UI-Komponenten.
     */
    private final JChessField[][] boardFields = new JChessField[8][8];
    /**
     * Eine Unsichtbare "Glasscheibe" um Interaktionen hier abzufangen und nicht in den anderen Komponenten
     */
    private final JPanel _glassPane = new JPanel();
    /**
     * Die Logik, die den Zustand des Spielbretts repräsentiert.
     */
    private ChessMechanics _mechanics;
    /**
     * Ausgewähltes (gezogene) Figurenkoordinaten.
     */
    private Vec _selectedPiece;
    /**
     * Feld über dem die Maus ist.
     */
    private Vec _hoveringField;
    /**
     * Abstand von der oberen, linken Ecke der gezogenen Figur zur Maus.
     */
    private Vec _selectedPieceDragOffset;
    /**
     * Optionen des Spielers
     */
    private ChessOptionsModel _options;
    /**
     * Gibt an, ob auf eine Figur gedrückt wird
     */
    private boolean _piecePressed = false;

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

    /**
     * Gibt die Farbe für das Schachfeld an der gegebenen Position
     */
    private static boolean isWhite(int row, int col)
    {
        //return row + (column % 2) % 2 == 0;
        return (row + col) % 2 == 0;
    }

    /**
     * Gibt eine Kopie der Optionen zurück
     */
    public ChessOptionsModel getOptions()
    {
        return _options.clone();
    }

    /**
     * Setzt die Optionen. Werden davor kopiert
     */
    public void setOptions(ChessOptionsModel options)
    {
        _options = options.clone();
        updateSelectableHints();
        updateTargetHints();
    }

    /**
     * Lädt das Spiel neu
     */
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

    /**
     * Aktualisiert die Positionen der Schachfiguren anhand des UI Arrays. Hat nichts mit der Logik zu tun
     */
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
                    setLayer(p, -9);
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

    /**
     * Aktualisiert die Zielmarkierungen der Figuren anhand der Nutzerinteraktion und den Einstellungen
     */
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
                    if (move.event == EventID.Promotion || move.event == EventID.Castling)
                        b.setSpecial(_mechanics.getChessBoard()[move.xTarget][move.yTarget].getPiece() != null);
                    else b.setTarget(
                            move.event == EventID.Capture || move.event == EventID.enPassant,
                            _mechanics.getChessBoard()[move.xTarget][move.yTarget].getPiece() != null);
                }
            }

        }
        repaint();
    }

    /**
     * Aktualisiert die Markierungen für die ziehbaren Figuren anhand der Nutzerinteraktion und den Einstellungen
     */
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

    /**
     * Initialisiert das UI Array für Spielfelder (Dunkel, Hell)
     */
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

    /**
     * Aktualisiert das UI-Array anhand der Logik; Wird für Schnelles aktualisieren nach Zügen verwendet
     */
    private void updateUiArray()
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                var old = chessPieceUIComponents[x][y];
                var tile = _mechanics.getChessBoard()[x][y];
                var piece = _mechanics.getChessBoard()[x][y].getPiece();
                if (old == null || !old.equals(piece))
                {
                    if (old != null)
                    {
                        this.remove(old);
                    }
                    chessPieceUIComponents[x][y] = null;

                    if (piece != null)
                    {
                        var p = new JChessPiece(tile.getPiece().getChessPieceId(), tile.getPlayerId().isBlack());
                        p.setSelectable(JChessPiece.State.None);
                        this.add(p, new Integer(-9));
                        chessPieceUIComponents[x][y] = p;
                    }
                }

            }
        }
        updatePlacement();
    }

    /**
     * Reinitialisiert das UI-Array
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

    }

    /**
     * Wird von Swing aufgerufen, wenn die Maustaste gedrückt wird.
     * Beim Drücken auf eine Figur wird die UI für das Ziehen der Figur vorbereitet.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        var fieldVec = posFromPoint(e.getPoint()); // Feld aus der Mausposition ermitteln
        // Bricht ab, wenn out of bounds
        if (fieldVec.x < 0 || fieldVec.y < 0
                || fieldVec.x >= 8 || fieldVec.y >= 8)
        {
            return;
        }
        // Ruft die potentielle Figur an der Position aus der Logik auf
        var p = _mechanics.getChessBoard()[fieldVec.y][fieldVec.x].getPiece(); // current piece
        if (p != null) _piecePressed = true;
        // Prüft ob die Figur existiert, sie Züge hat und dem Spieler der am Zug ist gehört
        if (p != null && p.hasNonBlockedMoves() && p.getPlayerId() == _mechanics.getCurrentPlayer())
        {
            if (chessPieceUIComponents[fieldVec.y][fieldVec.x] != null) // if field is empty
            {
                // Speichert die Position der Figur als ausgewählt
                this._selectedPiece = fieldVec;
                // Kalkulationen für die visuelle Positionierung der Figur
                var upperLeft = pointFromVec(this._selectedPiece);
                this._selectedPieceDragOffset = new Vec(e.getX() - upperLeft.x, e.getY() - upperLeft.y);
                // Setzt die Figur in den Vordergrund
                this.setLayer(chessPieceUIComponents[fieldVec.y][fieldVec.x], -2);
            }

        }
        updateTargetHints();


    }

    /**
     * Wird von Swing aufgerufen, wenn die Maustaste losgelassen wird.
     * Führt beim Loslassen einer Figur den Zug aus oder setzt die Figur zurück, wenn der Zug nicht möglich ist.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        boolean moveExecuted = false;
        _piecePressed = false;
        if (_selectedPiece != null)
        {
            // Berechnet das Zielfeld aus der Mausposition
            var target = posFromPoint(e.getPoint());
            // Liste aller Züge der ausgewählten Figur
            var moves = _mechanics.getChessBoard()[_selectedPiece.y][_selectedPiece.x].getPiece().getPossibleMoves();
            // Iteriert durch alle Züge, bis es den Zug findet, der auf das Zielfeld passt
            for (int i = 1; i <= moves.size(); i++)
            {
                var move = ((ChessMove) moves.get(i - 1));
                if (move.xTarget == target.y && move.yTarget == target.x && move.event != EventID.Blocked)
                {
                    // Prüft, ob der Zug eine Promotion ist
                    if (move.event == EventID.Promotion)
                    {
                        // Fragt die gewünschte Promotionsfigur vom Spieler ab und setzt sie im ChessMove fest. Zug kann auch abgebrochen werden
                        var dialog = JOptionPane.showOptionDialog(this,
                                "Wähle die Figur zu der befördert werden soll",
                                "Beförderung",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                Arrays.stream(ChessPieceId.promotionValues()).map(ChessPieceId::toGermanString).toArray(), null);

                        if (dialog == JOptionPane.CLOSED_OPTION)
                            break;
                        move.setPromotion(ChessPieceId.promotionValues()[dialog]);
                    }

                    _mechanics.executeMove(move);
                    moveExecuted = true;
                    break;
                }
            }
            this._hoveringField = target;
        }
        // setzt das ausgewählte Feld zurück
        this._selectedPiece = null;
        // aktualisiert UI
        updateUiArray();
        updatePlacement();
        updateSelectableHints();
        updateTargetHints();

        // Zeigt einen Dialog, wenn das Spiel zu Ende
        if (!moveExecuted) return;
        if (_mechanics.checkMate())
        {
            JOptionPane.showMessageDialog(this, "Schachmatt! " + (_mechanics.getCurrentPlayer().isBlack() ? "Weiß" : "Schwarz") + " hat gewonnen.", "Schachmatt", JOptionPane.INFORMATION_MESSAGE);
        } else if (_mechanics.patt())
        {
            JOptionPane.showMessageDialog(this, "Es sind keine Züge möglich.", "Patt", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    /**
     * Wird von Swing aufgerufen, wenn der Mauszeiger außerhalb des Schachbretts bewegt wird.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e)
    {
        _hoveringField = null;
        updateSelectableHints();
        updateTargetHints();
    }

    /**
     * Wird von Swing aufgerufen, wenn der Mauszeiger in das Schachbrett hinein bewegt wird.
     *
     * @param e the event to be processed
     */
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
        updateSelectableHints();
    }

    /**
     * Ruft die größe für ein einzelnes Schachfeld ab
     */
    private Point getTileSize()
    {
        return new Point(getWidth() / 8, getHeight() / 8);
    }

    /**
     * Berechnet die Koordinaten des Felds aus den angegebenen Bildschirmkoordinaten ab
     */
    private Vec posFromPoint(Point p)
    {
        return new Vec(fieldFromX(p.x), fieldFromY(p.y));
    }

    /**
     * Berechnet die Bildschirmkoordinaten aus den angegebenen Koordinaten eines Feldes
     */
    private Point pointFromVec(Vec v)
    {
        return new Point(v.x * getWidth() / 8, v.y * getHeight() / 8);
    }

    /**
     * Berechnet die X-Koordinaten des Felds aus der angegebenen X-Bildschirmkoordinate
     */
    private int fieldFromX(int x)
    {
        return x / (getWidth() / 8);
    }

    /**
     * Berechnet die Y-Koordinaten des Felds aus der angegebenen Y-Bildschirmkoordinate
     */
    private int fieldFromY(int y)
    {
        return y / (getHeight() / 8);
    }

    /**
     * Wird von Swing aufgerufen, wenn die Maus bewegt wird ohne das eine Maustaste gedrückt wird
     *
     * @param e the event to be processed
     */
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

    /**
     * Gibt die Koordinaten des Feldes zurück, auf dem sich der Mauszeiger befindet
     */
    public Vec getMouseVec()
    {
        return getMouseVec(null);
    }

    /**
     * Gibt die Koordinaten des Feldes zurück, dessen Bildschirmkoordinaten man gegeben hat
     */
    public Vec getMouseVec(@Nullable Point p)
    {
        if (p == null) p = this.getMousePosition(true);
        if (p == null) return null;
        var vec = posFromPoint(p);
        return (vec.y < 0 || vec.x < 0 || vec.y >= 8 || vec.x >= 8) ? null : vec;
    }

    /**
     * Macht den Zug rückgängig
     */
    public void Undo()
    {
        _mechanics.reverseMove();
        reInitializeUiArray();
        updatePlacement();
        updateSelectableHints();
    }
}

