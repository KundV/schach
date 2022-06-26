package chess.core.move;

import chess.core.ChessPieceId;
import chess.core.common.Vec;

public class BoardInsertOperation extends BoardOperation
{
    public final Vec vec;
    public final ChessPieceId pieceId;
    public BoardInsertOperation(Vec vec, ChessPieceId pieceId)
    {
        this.vec = vec;
        this.pieceId = pieceId;
    }
}
