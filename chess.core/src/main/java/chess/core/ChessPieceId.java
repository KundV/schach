package chess.core;

import java.util.Arrays;

//TODO Vielleicht auf Deutsch übersetzen?
public enum ChessPieceId
{
    /**
     * König
     */
    King,
    /**
     * Königin
     */
    Queen,
    /**
     * Turm
     */
    Tower,
    /**
     * Läufer
     */
    Bishop,
    /**
     * Pferd
     */
    Horse,
    /**
     * Frau Bauer
     */
    Pawn;

    public String toGermanString()
    {
        return switch (this)
                {
                    case King -> "König";
                    case Queen -> "Königin";
                    case Tower -> "Turm";
                    case Bishop -> "Läufer";
                    case Horse -> "Pferd";
                    case Pawn -> "Bauer";
                };

    }

    /**
     * Returns all pieces a pawn can promote to.
     *
     * @return
     */
    public static ChessPieceId[] promotionValues()
    {
        return Arrays.stream(ChessPieceId.values()).filter(chessPieceId -> chessPieceId != King && chessPieceId != Pawn).toArray(ChessPieceId[]::new);
    }

}
