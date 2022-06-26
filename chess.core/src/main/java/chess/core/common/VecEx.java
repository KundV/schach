package chess.core.common;

// A 2d Vector using doubles
public class VecEx
{
    public final double x;
    public final double y;

    public VecEx(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public VecEx add(VecEx other)
    {
        return new VecEx(x + other.x, y + other.y);
    }

    public VecEx sub(VecEx other)
    {
        return new VecEx(x - other.x, y - other.y);
    }

    public VecEx mul(double scalar)
    {
        return new VecEx(x * scalar, y * scalar);
    }

    public VecEx mirrorX()
    {
        return new VecEx(-x, y);
    }

    public VecEx mirrorY()
    {
        return new VecEx(x, -y);
    }
}
