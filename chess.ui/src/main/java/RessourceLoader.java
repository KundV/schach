import chess.core.ChessPieceId;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.MissingResourceException;

public class RessourceLoader
{
    public enum WikimediaPieceType
    {
        King('k'), Queen('q'), Tower('r'), Bishop('b'), Horse('n'), Pawn('p');
        final char c;


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

        WikimediaPieceType(char c)
        {
            this.c = c;
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

    public static URI getRessource(WikimediaPieceColor color, WikimediaPieceType type) throws MissingResourceException
    {
        var url = RessourceLoader.class.getResource("./wikimedia/Chess_" + type.c + color.c + "t45.svg");
        if (url == null)
            throw new MissingResourceException("Could not find a SVG-ressource for a " + color + " " + type + " color", "", "");
        // TODO Remove logging
        System.out.println(url);
        try
        {
            return url.toURI();
        } catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static URI getRessource(WikimediaPieceColor color, ChessPieceId id) {
        return getRessource(color, WikimediaPieceType.fromChessPieceId(id));
    }


    private RessourceLoader()
    {
        throw new UnsupportedOperationException("This class is not meant to be instantiated");
    }

    public static final String Author = "By Cburnett - Own work, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=1499811";
}
