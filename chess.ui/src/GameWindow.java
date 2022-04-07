import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameWindow extends JFrame
{
    @SuppressWarnings("FieldCanBeLocal")
    private ChessBoard _b = new ChessBoard();

    private JPanel _layout;
    private JPanel _sidebar;
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
        _layout.setLayout(new BoxLayout(_layout, BoxLayout.X_AXIS));


        _sidebar = new JPanel();
        _sidebar.add(new JLabel("Sidebar"));
        _layout.add(_sidebar);
        {
            var _p = new JPanel();
            _p.add(_b);
            _p.setLayout(null);
            _layout.add(_p);
        }
        _layout.add(_b);
        getContentPane().add(_layout);
        pack();

        this.getContentPane().addComponentListener(new ComponentAdapter()
        {
            @Override public void componentResized(ComponentEvent e) {
                var maxW = _layout.getMaximumSize().width;
                var sidebarWidth = _sidebar.getMinimumSize();
                var rightW = maxW - sidebarWidth.width;
                var boardSize = Math.max(rightW, _layout.getMinimumSize().height);
                _b.setSize(new Dimension(boardSize,boardSize));

                _b.revalidate();
                repaint();
            }
        });
    }




}
