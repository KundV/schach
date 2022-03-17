package chess.swing;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        var f = new JFrame();
        f.setSize(700, 700);
        var s = new ChessBoard();
        f.add(s);
        s.setVisible(true);
        f.setVisible(true);
    }
}
