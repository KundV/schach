package chess.swing;

import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;

public class ChessPiece extends JPanel {
    public ChessPiece() {
        add(new JSVGCanvas())
    }
}
