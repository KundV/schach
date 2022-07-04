package chess.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;

/**
 * Schachfeld UI-Komponente.
 * Kann Visuelle hinweise für die Zielfelder der Züge anzeigen (kleinen Punkte || rote Rahmen um Figuren)
 */
public class JChessField extends JPanel
{
    public JChessField(boolean isBlack)
    {
        this.setBackground(isBlack ? darkFieldColor : lightFieldColor);
        isDark = isBlack;
    }

    private final boolean isDark;

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (!isTarget) return;

        g.setColor(isCapture ? captureColor : (isDark ? nonCaptureColorOnDark : nonCaptureColorOnLight));
        int size = (int) Math.ceil(Math.min(getWidth(), getHeight()) * 0.2);

        if (isOccupied) {
            g.fillPolygon(new int[]{0, size, 0}, new int[]{0, 0, size}, 3);
            g.fillPolygon(new int[]{getWidth(), getWidth(), getWidth() - size}, new int[]{0, size, 0}, 3);
            g.fillPolygon(new int[]{getWidth(), getWidth() - size, getWidth()}, new int[]{getHeight(), getHeight(), getHeight() - size}, 3);
            g.fillPolygon(new int[]{0, 0, size}, new int[]{getHeight(), getHeight() - size, getHeight()}, 3);
            return;
        }
        // draw a circle in the center with the diameter of size
        g.fillOval(getWidth() / 2 - size / 2, getHeight() / 2 - size / 2, size, size);
    }

    public void setTarget(boolean isCapture, boolean isOccupied)
    {
        isTarget = true;
        this.isCapture = isCapture;
        this.isOccupied = isOccupied;
    }

    public void removeTarget()
    {
        isTarget = false;
    }

    private boolean isTarget = false;
    private boolean isCapture = false;
    private boolean isOccupied = false;

    private final Color darkFieldColor = new Color(180, 135, 99, 255);

    private final Color lightFieldColor = new Color(239, 216, 180, 255);

    private final Color captureColor = new Color(255, 0, 0, 179);
    private final Color nonCaptureColorOnLight = new Color(88, 122, 0, 102);
    private final Color nonCaptureColorOnDark = new Color(201, 253, 64, 102);
}
