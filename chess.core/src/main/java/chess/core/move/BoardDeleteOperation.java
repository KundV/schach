package chess.core.move;

import chess.core.common.Vec;

public class BoardDeleteOperation extends BoardOperation
{
    public final Vec vec;

    public BoardDeleteOperation(Vec vec)
    {
        this.vec = vec;
    }
}
