package chess.ui;

import chess.core.ChessMove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        // Lädt die SVG-Dateien vor
        Arrays.stream(RessourceLoader.WikimediaPieceColor.values()).forEach(c -> Arrays.stream(RessourceLoader.WikimediaPieceType.values()).forEach(t -> RessourceLoader.getRessourceFromCache(c, t)));
        // Started das Spielfenster und damit das Spiel
        var w = new GameWindow();

    }

}
