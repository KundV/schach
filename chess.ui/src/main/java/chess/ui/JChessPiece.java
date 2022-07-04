package chess.ui;

import chess.core.ChessPiece;
import chess.core.ChessPieceId;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGOMDocument;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class JChessPiece extends JPanel
{

    private final JSVGCanvas svg;
    public final ChessPieceId id;
    public final Boolean isBlack;

    public boolean equals(ChessPiece chessPiece)
    {
        return chessPiece != null && chessPiece.getChessPieceId() == id && chessPiece.getPlayerId().isBlack() == isBlack;
    }



    public JChessPiece(ChessPieceId id, Boolean isBlack)
    {
        this.id = id;
        this.isBlack = isBlack;

        this.setOpaque(false);
        this.setBorder(new CornerBorder());

        var doc = RessourceLoader.getRessourceFromCache(isBlack ? RessourceLoader.WikimediaPieceColor.Black : RessourceLoader.WikimediaPieceColor.White, id);

        setLayout(null);
        svg = new JSVGCanvas();
        svg.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);


        svg.setDocument(doc);
        //svg.setURI(uri.toString());
        svg.setOpaque(false);
        svg.setBackground(new Color(0, 0, 0, 0));

        add(svg);

        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                updateSvg();
            }

            public void componentShown(ComponentEvent e)
            {
                updateSvg();
            }
        });
    }


    /**
     * Aktualisiert die Große des SVG-Elements
     */
    private void updateSvg()
    {
        //svg.getSVGDocument();
        svg.setSize(new Dimension(getWidth(), getHeight()));
        svg.setLocation(0, 0);
        svg.revalidate();
        repaint();

    }

    /**
     * Ändert den Ausgewähltenstatus der Figur
     */
    public void setSelectable(State s)
    {
        if (s == State.None)
            this.setBorder(null);
        else
            this.setBorder(new CornerBorder(s.c));
    }

    /**
     * Status der Auswahl der Figur
     */
    enum State
    {
        None(null), // Keine Auswahl möglich / Auswahl verstecken
        Selectable(new Color(65, 65, 255, 255)), // Auswählbar
        Hovering(new Color(118, 118, 255, 255)); // Auswählbar und der Mauszeiger ist über der Figur


        final Color c; // Farbe

        State(Color c)
        {
            this.c = c;
        }
    }

}