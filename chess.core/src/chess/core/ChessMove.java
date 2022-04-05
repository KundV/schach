package chess.core;

import chess.core.Kompositum.Dataelement;

//a move is an action that a player can take
//every move has a start and end position and contains an Event that describes the action (move, capture, etc)
//
public class ChessMove extends Dataelement
{
    int xStart;
    int yStart;
    int xTarget;
    int yTarget;
    //Event event;
    public ChessMove(int xStart,int yStart,int xTarget,int yTarget,int id)
    {
        super(id);
        this.xStart = xStart;
        this.yStart = yTarget;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
    }
}

