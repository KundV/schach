package chess.core;

/*
Immutable 2D int Vector
 */
public class Vec
{
    public final int x;
    public final int y;



    public Vec(int x, int y)
    {
        this.x = x;
        this.y = y;
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
}
