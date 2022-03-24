package chess.swing;

import chess.core.ChessPieceId;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class JChessPiece extends JPanel
{

    private final JSVGCanvas svg;

    public JChessPiece(ChessPieceId id, Boolean isBlack)
    {

        URI uri;
        try
        {
            uri = loadUri(id, isBlack);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        svg = new JSVGCanvas();

        svg.setURI(uri.toString());
        add(svg);
    }


    public static class ResourceHelper
    {
        public static String IdToKaHu(ChessPieceId id)
        {
            // Correspond to the files in resources/ka-hu/chess_kaneo/
            return switch (id)
                    {
                        case KÖNIG -> "K";
                        case KÖNIGIN -> "Q";
                        case TURM -> "R";


                        case BISHOP -> "B";
                        case KNIGHT -> "N";
                        case PAWN -> "P";
                    };
        }


    }

    static URI loadUri(ChessPieceId id, Boolean isBlack) throws IOException
    {
        var c = (isBlack ? "b" : "w") + ResourceHelper.IdToKaHu(id) + ".svg";
        var res = ClassLoader.getSystemClassLoader().getResource("./ka-hu/chess_kaneo/" + c);
        if (res == null)
            throw new IOException("Could not find a SVG-ressource for " + id.toString() + " in " + (isBlack ? "black" : "white") + " color");
        try
        {
            return res.toURI();
        } catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }

}
