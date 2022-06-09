package chess.core.move;

import chess.core.Pos;

public class BoardDeleteOperation extends BoardOperation
{
    public final Pos pos;

    public BoardDeleteOperation(Pos pos)
    {
        this.pos = pos;
    }
}
