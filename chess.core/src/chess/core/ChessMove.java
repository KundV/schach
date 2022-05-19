package chess.core;

import chess.core.VerktetteListe.Dataelement;

//a move is an action that a player can take
//every move has a start and end position and contains an Event that describes the action (move, capture, etc)
//


public class ChessMove extends Dataelement
{
    private int xStart;
    private int yStart;
    private int xTarget;
    private int yTarget;
    private Event event;



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

    public int get_xStart()
    {
        return xStart;
    }

    public int get_yStart()
    {
        return yStart;
    }

    public int get_xTarget()
    {
        return xTarget;
    }

    public int get_yTarget()
    {
        return yTarget;
    }

    public Event getEvent()
    {
        return event;
    }
}

