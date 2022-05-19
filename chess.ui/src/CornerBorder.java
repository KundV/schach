import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.concurrent.Semaphore;

public class CornerBorder extends AbstractBorder
{
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
        var p = new Polygon();
    }

    private enum Alignment
    {
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight;

        boolean isBottom()
        {
            return this == BottomLeft || this == BottomRight;
        }

        boolean isTop()
        {
            return this == TopLeft || this == TopRight;
        }

        boolean isLeft()
        {
            return this == TopLeft || this == BottomLeft;
        }

        boolean isRight()
        {
            return this == TopRight || this == BottomRight;
        }
    }

    private Polygon generatePolygon(Alignment alignment, boolean isHorizontal, int x, int y, int width, int height)
    {
        var p = new Polygon();

        var horizontalInset = (int) (width * 0.01);
        var verticalInset = (int) (height * 0.01);
        //int leftX = alignment.isLeft() ? 3;
        //var xPoints = new int[4] { alignment.isLeft() ? x + horizontalInset : x + width +  , x + width, x + width, x};
        //var yPonts = new int[4] {x, x + width, x + width, x};
        return null;
    }

    public Insets getBorderInsets(Component c)
    {
        int w = (int) (c.getWidth() * 0.01);
        int h = (int) (c.getHeight() * 0.01);
        return new Insets(h, w, h, w);
    }

    @Override
    public boolean isBorderOpaque()
    {
        return false;
    }
}
