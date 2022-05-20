import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.concurrent.Semaphore;

public class CornerBorder implements Border
{



    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
        g.setColor(Color.blue);

        for (Alignment alg : Alignment.values())
        {
            //if (alg != Alignment.TopLeft) continue;
            g.fillPolygon(generatePolygon(alg, x, y, width, height));
        }
    }

    private enum Alignment
    {
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight;

        boolean isTop()
        {
            return this == TopLeft || this == TopRight;
        }

        boolean isLeft()
        {
            return this == TopLeft || this == BottomLeft;
        }

    }


    private Polygon generatePolygon(Alignment alignment, int x, int y, int width, int height)
    {
        var p = new Polygon();

        var horizontalInset = (int) Math.ceil(width * 0.05);
        var verticalInset = (int) Math.ceil(height * 0.05);

        var thickness = (int) Math.ceil(Math.min(width, height) * 0.05);
        var length = (int) Math.ceil(Math.min(width, height) * 0.2);

        var c = new Point(alignment.isLeft() ? x + horizontalInset : x + width - horizontalInset, alignment.isTop() ? y + verticalInset : y + height - verticalInset);
        p.addPoint(c.x, c.y);
        c.translate(alignment.isLeft() ? length : -length, 0);
        p.addPoint(c.x, c.y);
        c.translate(0, alignment.isTop() ? thickness : -thickness);
        p.addPoint(c.x, c.y);
        c.translate(alignment.isLeft() ? -length + thickness : length - thickness, 0); // inner corner
        p.addPoint(c.x, c.y);
        c.translate(0, alignment.isTop() ? length - thickness : -length + thickness);
        p.addPoint(c.x, c.y);
        c.translate(alignment.isLeft() ? -thickness : thickness, 0);
        p.addPoint(c.x, c.y);
        c.translate(0, alignment.isTop() ? -length : length);
        //p.addPoint(c.x, c.y);
        return p;
    }


    public Insets getBorderInsets(Component c)
    {
        int w = (int) (c.getWidth() * 0.4);
        int h = (int) (c.getHeight() * 0.4);
        return new Insets(h, w, h, w);
    }

    @Override
    public boolean isBorderOpaque()
    {
        return true;
    }
}
