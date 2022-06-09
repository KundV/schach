package chess.core.move;

import chess.core.ChessPieceId;
import chess.core.Pos;

public class BoardInsertOperation extends BoardOperation
{
    public final Pos pos;
    public final ChessPieceId pieceId;
    public BoardInsertOperation(Pos pos, ChessPieceId pieceId)
    {
        this.pos = pos;
        this.pieceId = pieceId;
    }
}
