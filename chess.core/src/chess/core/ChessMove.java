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
    //Event event;
    public ChessMove(int xStart,int yStart,int xTarget,int yTarget,boolean isBlackMove)
    {
        super(isBlackMove);
        this.xStart = xStart;
        this.yStart = yTarget;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
    }

    @Override
    public boolean equals(Dataelement de)
    {
        return false;
    }

    @Override
    public boolean get_color()
    {
        return isBlackMove;
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
}

