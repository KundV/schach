package chess.core.move;

import chess.core.Pos;

public class BoardUpdateOperation extends BoardOperation
{
    public final Pos start;
    public final Pos end;

    public BoardUpdateOperation(Pos start, Pos end)
    {
        this.start = start;
        this.end = end;
    }
}
