package chess.core.common;

/*
Immutable 2D int Vector
 */

import java.awt.*;

public class Vec
{
    public final int x;
    public final int y;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec vec = (Vec) o;

        if (x != vec.x) return false;
        return y == vec.y;
    }

    @Override
    public int hashCode()
    {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public Vec(int x, int r)
    {
        this.x = x;
        this.y = r;
    }

    public Vec(Vec vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public Vec(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point toPoint()
    {
        return new Point(x, y);
    }



    public Vec add(Vec other)
    {
        return add(other.x, other.y);
    }

    public Vec add(int x, int y)
    {
        return new Vec(this.x + x, this.y + y);
    }

    public Vec sub(Vec other)
    {
        return sub(other.x, other.y);
    }

    public Vec sub(int x, int y)
    {
        return new Vec(this.x - x, this.y - y);
    }

    public Vec mul(double scalar)
    {
        return new Vec((int) (x * scalar), (int) (y * scalar));
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


