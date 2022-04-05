package chess.core;

import chess.core.Kompositum.Dataelement;

import java.util.ArrayList;

public class ChessPiece extends Dataelement
{
    private String name; // name of the piece
    private String color; // color of the piece
    private String type; // type of the piece
    private int x; // x-coordinate of the piece
    private int y; // y-coordinate of the piece
    private boolean hasMoved; // has the piece moved?
    private boolean isCaptured; // is the piece captured?
    private boolean isChecked; // is the piece checked?
    private boolean isCheckedMate; // is the piece checkedmate?
    private boolean isStalemate; // is the piece stalemate?
    private boolean isPromoted; // is the piece promoted?
    private boolean isPromotedToQueen; // is the piece promoted to queen?
    private boolean isPromotedToRook; // is the piece promoted to rook?
    private boolean isPromotedToBishop; // is the piece promoted to bishop?
    private boolean isPromotedToKnight; // is the piece promoted to knight?
    private boolean isPromotedToPawn; // is the piece promoted to pawn?
    private boolean isPromotedToKing; // is the piece promoted to king?
    private boolean isPromotedToQueenRook; // is the piece promoted to queen-rook?
    private boolean isPromotedToQueenBishop; // is the piece promoted to queen-bishop?
    private boolean isPromotedToQueenKnight; // is the piece promoted to queen-knight?
    private boolean isPromotedToQueenPawn; // is the piece promoted to queen-pawn?
    private boolean isPromotedToKingRook; // is the piece promoted to king-rook?
    private boolean isPromotedToKingBishop; // is the piece promoted to king-bishop?
    private boolean isPromotedToKingKnight; // is the piece promoted to king-knight?
    private boolean isPromotedToKingPawn; // is the piece promoted to king-pawn?
    private boolean isPromotedToQueenKing; // is the piece promoted to queen-king?
    private boolean isPromotedToRookKing; // is the piece promoted to rook-king?
    private boolean isPromotedToBishopKing; // is the piece promoted to bishop-king?
    private boolean isPromotedToKnightKing; // is the piece promoted to knight-king?
    private boolean isPromotedToPawnKing; // is the piece promoted to pawn-king?
    private boolean isPromotedToQueenKingRook; // is the piece promoted to queen-king-rook?
    private boolean isPromotedToQueenKingBishop; // is the piece promoted to queen-king-bishop?
    private boolean isPromotedToQueenKingKnight; // is the piece promoted to queen-king-knight?
    private boolean isPromotedToQueenKingPawn; // is the piece promoted to queen-king-pawn?
    private boolean isPromotedToRookKingRook; // is the piece promoted to rook-king-rook?
    private boolean isPromotedToRookKingBishop; // is the piece promoted to rook-king-bishop?
    private boolean isPromotedToRookKingKnight; // is the piece promoted to rook-king-knight?
    private boolean isPromotedToRookKingPawn; // is the piece promoted to rook-king-pawn?
    private boolean isPromotedToBishopKingRook; // is the piece promoted to bishop-king-rook?
    private boolean isPromotedToBishopKingBishop; // is the piece promoted to bishop-king-bishop?
    private boolean isPromotedToBishopKingKnight; // is the piece promoted to bishop-king-knight?
    private boolean isPromotedToBishopKingPawn; // is the piece promoted to bishop-king-pawn?
    private boolean isPromotedToKnightKingRook; // is the piece promoted to knight-king-rook?
    private boolean isPromotedToKnightKingBishop; // is the piece promoted to knight-king-bishop?
    private boolean isPromotedToKnightKingKnight; // is the piece promoted to knight-king-knight?
    private boolean isPromotedToKnightKingPawn; // is the piece promoted to knight-king-pawn?
    private boolean isPromotedToPawnKingRook; // is the piece promoted to pawn-king-rook?
    private boolean isPromotedToPawnKingBishop; // is the piece promoted to pawn-king-bishop?
    private boolean isPromotedToPawnKingKnight; // is the piece promoted to pawn-king-knight?
    private boolean isPromotedToPawnKingPawn; // is the piece promoted to pawn-king-pawn?
    private boolean isPromotedToQueenKingKing; // is the piece promoted to queen-king-king?
    private boolean isPromotedToRookKingKing; // is the piece promoted to rook-king-king?
    private boolean isPromotedToBishopKingKing; // is the piece promoted to bishop-king-king?
    private boolean isPromotedToKnightKingKing; // is the piece promoted to knight-king-king?
    private boolean isPromotedToPawnKingKing; // is the piece promoted to pawn-king-king?
    private boolean isPromotedToQueenKingKingRook; // is the piece promoted to queen-king-king-rook?
    private boolean isPromotedToQueenKingKingBishop; // is the piece promoted to queen-king-king-bishop?
    private boolean isPromotedToQueenKingKingKnight; // is the piece promoted to queen-king-king-knight?
    private boolean isPromotedToQueenKingKingPawn; // is the piece promoted to queen-king-king-pawn?
    private boolean isPromotedToRookKingKingRook; // is the piece promoted to rook-king-king-rook?
    private boolean isPromotedToRookKingKingBishop; // is the piece promoted to rook-king-king-bishop?
    private boolean isPromotedToRookKingKingKnight; // is the piece promoted to rook-king-king-knight?
    private boolean isPromotedToRookKingKingPawn; // is the piece promoted to rook-king-king-pawn?
    private boolean isPromotedToBishopKingKingRook; // is the piece promoted to bishop-king-king-rook?
    private boolean isPromotedToBishopKingKingBishop; // is the piece promoted to bishop-king-king-bishop?
    private boolean isPromotedToBishopKingKingKnight; // is the piece promoted to bishop-king-king-knight?
    private boolean isPromotedToBishopKingKingPawn; // is the piece promoted to bishop-king-king-pawn?
    private boolean isPromotedToKnightKingKingRook; // is the piece promoted to knight-king-king-rook?
    private boolean isPromotedToKnightKingKingBishop; // is the piece promoted to knight-king-king-bishop?
    private boolean isPromotedToKnightKingKingKnight; // is the piece promoted to knight-king-king-knight?
    private boolean isPromotedToKnightKingKingPawn; // is the piece promoted to knight-king-king-pawn?
    private boolean isPromotedToPawnKingKingRook; // is the piece promoted to pawn-king-king-rook?
    private boolean isPromotedToPawnKingKingBishop; // is the piece promoted to pawn-king-king-bishop?
    private boolean isPromotedToPawnKingKingKnight; // is the piece promoted to pawn-king-king-knight?
    private boolean isPromotedToPawnKingKingPawn; // is the piece promoted to pawn-king-king-pawn?


    /**
     * Constructor for the Piece class.
     * @param pieceColor the color of the piece
     * @param pieceType the type of the piece
     * @param piecePosition the position of the piece
     */
    public Piece(PieceColor pieceColor, PieceType pieceType, Position piecePosition) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
    }

    /**
     * Copy constructor for the Piece class.
     * @param piece the piece to copy
     */

    public Piece(Piece piece) {
        this.pieceColor = piece.pieceColor;
        this.pieceType = piece.pieceType;
        this.piecePosition = piece.piecePosition;
        this.isPromotedToKing = piece.isPromotedToKing;
        this.isPromotedToKingRook = piece.isPromotedToKingRook;
        this.isPromotedToKingBishop = piece.isPromotedToKingBishop;
        this.isPromotedToKingKnight = piece.isPromotedToKingKnight;
        this.isPromotedToKingPawn = piece.isPromotedToKingPawn;
        this.isPromotedToQueenKingRook = piece.isPromotedToQueenKingRook;
        this.isPromotedToQueenKingBishop = piece.isPromotedToQueenKingBishop;
        this.isPromotedToQueenKingKnight = piece.isPromotedToQueenKingKnight;
        this.isPromotedToQueenKingPawn = piece.isPromotedToQueenKingPawn;
        this.isPromotedToRookKingRook = piece.isPromotedToRookKingRook;
        this.isPromotedToRookKingBishop = piece.isPromotedToRookKingBishop;
        this.isPromotedToRookKingKnight = piece.isPromotedToRookKingKnight;
        this.isPromotedToRookKingPawn = piece.isPromotedToRookKingPawn;
        this.isPromotedToBishopKingRook = piece.isPromotedToBishopKingRook;
        this.isPromotedToBishopKingBishop = piece.isPromotedToBishopKingBishop;
        this.isPromotedToBishopKingKnight = piece.isPromotedToBishopKingKnight;
        this.isPromotedToBishopKingPawn = piece.isPromotedToBishopKingPawn;
        this.isPromotedToKnightKingRook = piece.isPromotedToKnightKingRook;
        this.isPromotedToKnightKingBishop = piece.isPromotedToKnightKingBishop;
        this.isPromotedToKnightKingKnight = piece.isPromotedToKnightKingKnight;
        this.isPromotedToKnightKingPawn = piece.isPromotedToKnightKingPawn;
        this.isPromotedToPawnKingRook = piece.isPromotedToPawnKingRook;
        this.isPromotedToPawnKingBishop = piece.isPromotedToPawnKingBishop;
        this.isPromotedToPawnKingKnight = piece.isPromotedToPawnKingKnight;
        this.isPromotedToPawnKingPawn = piece.isPromotedToPawnKingPawn;
    }

    /**
     * Get the color of the piece.
     * @return the color of the piece
     */
    public PieceColor getPieceColor() {
        return pieceColor;
    }

    /**
     * Get the type of the piece.
     * @return the type of the piece
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Get the position of the piece.
     * @return the position of the piece
     */
    public Position getPiecePosition() {
        return piecePosition;
    }

    /**
     * Set the position of the piece.
     * @param piecePosition the new position of the piece
     */
    public void setPiecePosition(Position piecePosition) {
        this.piecePosition = piecePosition;
    }

    /**
     * Get the piece's value.
     * @return the piece's value
     */
    public int getPieceValue() {
        return pieceValue;
    }

    /**
     * Set the piece's value.
     * @param pieceValue the new value of the piece
     */
    public void setPieceValue(int pieceValue) {
        this.pieceValue = pieceValue;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     */
    public int getPieceValueForCurrentPosition() {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     */
    public int getPieceValueForCurrentPosition(Position piecePosition) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, Position piecePosition) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     */
    public int getPieceValueForCurrentPosition(int pieceValue) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, int pieceValue) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     */
    public int getPieceValueForCurrentPosition(int pieceValue, Position piecePosition) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, int pieceValue, Position piecePosition) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     */
    public int getPieceValueForCurrentPosition(int pieceValue, int pieceValueForCurrentPosition) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     */
    public int getPieceValueForCurrentPosition(int pieceValue, int pieceValueForCurrentPosition, Position piecePosition) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, Position piecePosition) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     */
    public int getPieceValueForCurrentPosition(int pieceValue, int pieceValueForCurrentPosition, int pieceValueForCurrentPosition) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, int pieceValue) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     */
    public int getPieceValueForCurrentPosition(int pieceValue, int pieceValueForCurrentPosition, int pieceValueForCurrentPosition, Position piecePosition) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, int pieceValue, Position piecePosition) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     */
    public int getPieceValueForCurrentPosition(int pieceValue, int pieceValueForCurrentPosition, int pieceValueForCurrentPosition, int pieceValue) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     */
    public int getPieceValueForCurrentPosition(int pieceValue, int pieceValueForCurrentPosition, int pieceValueForCurrentPosition, int pieceValue, Position piecePosition) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, Position piecePosition) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     */
    public int getPieceValueForCurrentPosition(int pieceValue, int pieceValueForCurrentPosition, int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, int pieceValue) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     * @param pieceValue the piece's value
     * @param pieceValueForCurrentPosition the piece's value for the current position
     * @param pieceValueForCurrentPosition the piece's value for the current position
     * @param piecePosition the piece's position
     */
    public int getPieceValueForCurrentPosition(int pieceValue, int pieceValueForCurrentPosition, int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, Position piecePosition) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     * @param pieceValue the piece's value
     * @param pieceValueForCurrentPosition the piece's value for the current position
     * @param pieceValueForCurrentPosition the piece's value for the current position
     * @param piecePosition the piece's position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, int pieceValue, Position piecePosition) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
    }

    /**
     * Get the piece's value for the current position.
     * @return the piece's value for the current position
     * @param pieceValue the piece's value
     * @param pieceValueForCurrentPosition the piece's value for the current position
     * @param pieceValueForCurrentPosition the piece's value for the current position
     * @param pieceValue the piece's value
     */
    public int getPieceValueForCurrentPosition(int pieceValue, int pieceValueForCurrentPosition, int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, int pieceValue) {
        return pieceValueForCurrentPosition;
    }

    /**
     * Set the piece's value for the current position.
     * @param pieceValueForCurrentPosition the new value of the piece for the current position
     * @param pieceValue the piece's value
     * @param pieceValueForCurrentPosition the piece's value for the current position
     * @param pieceValueForCurrentPosition the piece's value for the current position
     * @param pieceValue the piece's value
     * @param piecePosition the piece's position
     */
    public void setPieceValueForCurrentPosition(int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, int pieceValue, int pieceValueForCurrentPosition, int pieceValue, int pieceValue, Position piecePosition) {
        this.pieceValueForCurrentPosition = pieceValueForCurrentPosition;
}
{
    public ChessPieceId chessPieceId;
    public boolean isBlack;
    public ArrayList<ChessMove> possibleMoves;
    public int number;



    public ChessPiece(ChessPieceId chessPieceId, boolean isBlack, int number)
    {
        this.chessPieceId = chessPieceId;
        this.isBlack = isBlack;
        this.number = number;

    }



    public void setPossibleMoves(ArrayList<ChessMove> possibleMoves)
    {
        this.possibleMoves = possibleMoves;
    }

    public ArrayList<ChessMove> getPossibleMoves()
    {
        return possibleMoves;
    }

