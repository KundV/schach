import chess.core.ChessMechanics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameWindow extends JFrame
{
    @SuppressWarnings("FieldCanBeLocal")

    private JPanel _content;
    private GridBagLayout _layout;
    private JPanel _sidebar;
    private ChessBoard _board;
    private ChessMechanics _mechanics;



    public GameWindow()
    {
        _mechanics = new ChessMechanics();
        setTitle("CHESS");
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        _board = new ChessBoard(_mechanics);
        _content = new JPanel();
        // Set _layout to BoxLayout
        _layout = new GridBagLayout();
        _content.setLayout(_layout);

        var c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        _content.add(_board, c);

        _sidebar = new JPanel();
        _sidebar.setLayout(new BoxLayout(_sidebar, BoxLayout.Y_AXIS));
        _sidebar.add(new JLabel("Sidebar"));
        _sidebar.add(new JLabel("Very looooong text"));
        _sidebar.add(new JLabel("1"));
        c.gridx = 1;
        c.gridy = 0;

        _content.add(_sidebar, c);
        // Add _content to JFrame

        getContentPane().add(_content, BorderLayout.CENTER);

        this.getContentPane().addComponentListener
        (
            new ComponentAdapter()
            {
                @Override public void componentResized(ComponentEvent e)
                {

                    var maxW = _content.getSize().width - _sidebar.getMinimumSize().width;
                    var maxH = _content.getSize().height;
                    var fitSize = Math.min(maxW, maxH);
                    if (fitSize <= 0) return;


                    //_board.setSize(fitSize, fitSize);
                    _board.setPreferredSize(new Dimension(fitSize, fitSize));
                    _board.revalidate();
                    repaint();
                }
            }
        );

        pack();
        setVisible(true);
    }
}