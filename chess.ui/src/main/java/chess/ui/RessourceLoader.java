package chess.ui;

import chess.core.ChessPieceId;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGOMDocument;
import org.apache.batik.dom.util.HashTableStack;
import org.apache.batik.util.XMLResourceDescriptor;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.svg.SVGDocument;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class RessourceLoader
{
    public static final String Author = "By Cburnett - Own work, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=1499811";
    private static final Hashtable<WikimediaPieceColor, Hashtable<WikimediaPieceType, SVGOMDocument>> cache = initializeCache();

    private RessourceLoader()
    {
        throw new UnsupportedOperationException("This class is not meant to be instantiated");
    }

    public static synchronized SVGOMDocument getRessourceFromCache(WikimediaPieceColor color, ChessPieceId id)
    {
        return getRessourceFromCache(color, WikimediaPieceType.fromChessPieceId(id));
    }

    private static Hashtable<WikimediaPieceColor, Hashtable<WikimediaPieceType, SVGOMDocument>> initializeCache()
    {
        var c = new Hashtable<WikimediaPieceColor, Hashtable<WikimediaPieceType, SVGOMDocument>>();
        Arrays.stream(WikimediaPieceColor.values())
                .forEach(color -> c.put(color, new Hashtable<>()));
        return c;
    }

    public static SVGOMDocument getRessourceFromCache(@NotNull WikimediaPieceColor color, @NotNull WikimediaPieceType type)
    {
        System.out.println(color +" " + type);
        var cached = cache.get(color).getOrDefault(type, null);
        if (cached == null)
        {
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);

            try
            {

                cached = (SVGOMDocument) f.createSVGDocument(getRessource(color, type).toString());
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            cached.getRootElement().setAttribute("viewBox", "0 0 45 45");


            cache.get(color).put(type, cached);
        } else {
            cached =  (SVGOMDocument) cached.cloneNode(true);
        }

        return cached;

    }

    public static URI getRessource(WikimediaPieceColor color, WikimediaPieceType type) throws MissingResourceException
    {
        var url = RessourceLoader.class.getResource("/wikimedia/Chess_" + type.c + color.c + "t45.svg");
        if (url == null)
            throw new MissingResourceException("Could not find a SVG-ressource for a " + color + " " + type + " color", "", "");
        //  Remove logging
        //System.out.println(url);
        try
        {
            return url.toURI();
        } catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static URI getRessource(WikimediaPieceColor color, ChessPieceId id)
    {
        return getRessource(color, WikimediaPieceType.fromChessPieceId(id));
    }


    public enum WikimediaPieceType
    {
        King('k'), Queen('q'), Tower('r'), Bishop('b'), Horse('n'), Pawn('p');
        final char c;


        WikimediaPieceType(char c)
        {
            this.c = c;
        }

        public static WikimediaPieceType fromChessPieceId(ChessPieceId id)
        {
            return switch (id)
                    {
                        case King -> King;
                        case Queen -> Queen;
                        case Tower -> Tower;
                        case Bishop -> Bishop;
                        case Horse -> Horse;
                        case Pawn -> Pawn;
                        //default -> throw new IllegalArgumentException("Unknown chess piece type: " + id);
                    };
        }
    }

    public enum WikimediaPieceColor
    {
        Black('d'), White('l'), Green('g'), Red('r'), Yellow('y');
        final char c;

        WikimediaPieceColor(char c)
        {
            this.c = c;
        }
    }
}
