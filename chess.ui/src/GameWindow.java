import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame
{
    @SuppressWarnings("FieldCanBeLocal")
    private ChessBoard _b = new ChessBoard();

    private JPanel _layout;

    public GameWindow()
    {
        setTitle("CHESS");
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);







        _b = new ChessBoard();
        _layout = new JPanel();
        // Set _layout to BoxLayout
        _layout.setLayout(new BoxLayout(_layout, BoxLayout.PAGE_AXIS));
        _layout.add(_b);
        _layout.add(Box.createHorizontalGlue());
        _layout.add(new JLabel("Hello \r\n World \n This is a test"));

        getContentPane().add(_layout);
        pack();
    }
}
