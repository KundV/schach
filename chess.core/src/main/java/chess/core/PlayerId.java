package chess.core;

public enum PlayerId
{
    /**
     * The player with the white pieces.
     */
    WHITE,
    /**
     * The player with the black pieces.
     */
    BLACK;

    /**
     * Returns the opposite player.
     * @return the opposite player.
     */
    public PlayerId opposite()
    {
        return this == WHITE ? BLACK : WHITE;
    }

    public boolean isBlack()
    {
        return this == BLACK;
    }
}

