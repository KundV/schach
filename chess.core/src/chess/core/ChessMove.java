package chess.core;

public class ChessMove
{
    int xStart;
    int yStart;
    int xTarget;
    int yTarget;
    //Event event;
    public ChessMove(int xStart,int yStart,int xTarget,int yTarget)
    {
        this.xStart = xStart;
        this.yStart = yTarget;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
    }
}
