package chess.core;

import chess.core.common.Vec;

//a move is an action that a player can take
//every move has a start and end position and contains an Event that describes the action (move, capture, etc)
//


public class ChessMove// extends Dataelement
{
    public final int xStart;        //x start position des zuges
    public final int yStart;        //y start position des zuges
    public final int xTarget;       //x ziel position des zuges
    public final int yTarget;       //y ziel position des zuges
    public int xSecondaryPieceStart;    //Zweiter Zug bei rochade && position des enPassant
    public int ySecondaryPieceStart;
    public int xSecondaryPieceTarget;
    public int ySecondaryPieceTarget;
    private PlayerId player;
    public  EventID event;
    private ChessPieceId promotion;

    public final Vec start;
    public final Vec target;

    public void setPromotion(ChessPieceId promotion) //setzt die Promotion
    {
        if (event != EventID.Promotion)
            throw new UnsupportedOperationException("Setting the promotion is only supported for users");
        this.promotion = promotion;
    }

    public ChessPieceId getPromotion()
    {
        return promotion;
    }



    public ChessMove(int xStart, int yStart, int xTarget, int yTarget, PlayerId Player, EventID event)
    {
        player = Player;
        //super(Player);
        this.event = event;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.start = new Vec(xStart, yStart);
        this.target = new Vec(xTarget, yTarget);
    }

    public ChessMove(int xStart, int yStart, int xTarget, int yTarget, PlayerId Player, EventID event, int xSecondaryPieceStart, int ySecondaryPieceStart, int xSecondaryPieceTarget, int ySecondaryPieceTarget)
    {
        player = Player;
        //super(Player);
        this.event = event;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.start = new Vec(xStart, yStart);
        this.target = new Vec(xTarget, yTarget);
        this.xSecondaryPieceStart = xSecondaryPieceStart;
        this.ySecondaryPieceStart = ySecondaryPieceStart;
        this.xSecondaryPieceTarget = xSecondaryPieceTarget;
        this.ySecondaryPieceTarget = ySecondaryPieceTarget;
    }



    public boolean sameStart(ChessMove cm)
    {
        return ((this == cm) || (cm.xStart == xStart && cm.yStart == yStart));
    }

    public PlayerId getPlayerId()
    {
        return player;
    }


    public EventID getEvent()
    {
        return event;
    }

    public void setEvent(EventID event)
    {
        this.event = event;
    }
}

