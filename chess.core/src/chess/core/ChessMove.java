package chess.core;

import chess.core.VerktetteListe.Dataelement;

//a move is an action that a player can take
//every move has a start and end position and contains an Event that describes the action (move, capture, etc)
//


public class ChessMove extends Dataelement
{
    public final int xStart;
    public final int yStart;
    public final int xTarget;
    public final int yTarget;
    public final Event event;

    public ChessMove(int xStart,int yStart,int xTarget,int yTarget,PlayerId Player,Event event)
    {
        super(Player);
        this.event = event;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
    }

    @Override
    public boolean equals(ChessMove cm)     //if either the start or the target position is the same, the move is the same
    {
        return (this == cm) || ((cm.xStart == xStart) && (cm.yStart == yStart) && (cm.xTarget == xTarget) && (cm.yTarget == yTarget));
}
    public boolean sameStart(ChessMove cm)
    {
        return ((this == cm) || (cm.xStart == xStart && cm.yStart == yStart));
    }



    public PlayerId getPlayerId()
    {
        return Player;
    }


    public Event getEvent()
    {
        return event;
    }
}

