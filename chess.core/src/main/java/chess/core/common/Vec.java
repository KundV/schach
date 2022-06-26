package chess.core.common;

/*
Immutable 2D int Vector
 */

public class Vec
{
    public final int x;
    public final int y;



    public Vec(int x, int r)
    {
        this.x = x;
        this.y = r;
    }



    public Vec add(Vec other)
    {
        return new Vec(x + other.x, y + other.y);
    }

    public Vec sub(Vec other)
    {
        return new Vec(x - other.x, y - other.y);
    }

    public Vec mul(int scalar)
    {
        return new Vec(x * scalar, y * scalar);
    }

    public Vec mirrorX()
    {
        return new Vec(-x, y);
    }

    public Vec mirrorY()
    {
        return new Vec(x, -y);
    }
}


