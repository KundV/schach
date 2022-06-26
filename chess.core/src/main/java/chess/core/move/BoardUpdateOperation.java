package chess.core.move;

import chess.core.common.Vec;

public class BoardUpdateOperation extends BoardOperation
{
    public final Vec start;
    public final Vec end;

    public BoardUpdateOperation(Vec start, Vec end)
    {
        this.start = start;
        this.end = end;
    }
}
