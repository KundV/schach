package chess.core;

/*
Immutable 2D int Vector
 */
public class Pos
{
    public final int c;
    public final int r;



    public Pos(int c, int r)
    {
        this.c = c;
        this.r = r;
    }



    public Pos add(Pos other)
    {
        return new Pos(c + other.c, r + other.r);
    }

    public Pos sub(Pos other)
    {
        return new Pos(c - other.c, r - other.r);
    }

    public Pos mul(int scalar)
    {
        return new Pos(c * scalar, r * scalar);
    }

    public Pos mirrorCol()
    {
        return new Pos(-c, r);
    }

    public Pos mirrorRow()
    {
        return new Pos(c, -r);
    }
}
