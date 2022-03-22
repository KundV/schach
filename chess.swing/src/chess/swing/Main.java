package chess.swing;

import chess.core.ChessPieceId;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Collections;

public class Main
{

    public static void main(String[] args)
    {
        var f = new JFrame();
        var piece = new JChessPiece(ChessPieceId.KING, true);
        f.add(piece);
        f.setSize(700, 700);
        f.setVisible(true);
    }
}
