package chess.core;

public enum EventID
{
    Move,
    Blocked,
    Capture,
    Promotion
            {
                ChessPieceId PlayerPromotedTo;
            },
    Castling
                {
                    ChessMove CastlingMove;
                },
    enPassant
            {
                int xCapture;
                int yCapture;
            },

    Check,
    Checkmate,
    Stalemate
}
