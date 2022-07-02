package chess.ui;

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
       this(new ChessMechanics());
    }

    public GameWindow(ChessMechanics mechanics)
    {
        _mechanics = mechanics;
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
        {
            var undoButton = new JButton("Rückgängig");
            undoButton.addActionListener(e -> _board.Undo());
            _sidebar.add(undoButton);

            var newButton = new JButton("Neues Spiel");
            newButton.addActionListener(e -> _board.ChangeGame(new ChessMechanics()));
            _sidebar.add(newButton);

            var preferencesButton = new JButton("Einstellungen");
            preferencesButton.addActionListener(e -> {
                var dialog = new JChessOptions(_board.getOptions());
                var res = JOptionPane.showConfirmDialog(this, dialog, "Einstellungen", JOptionPane.OK_CANCEL_OPTION);
                if (res == JOptionPane.OK_OPTION)
                {
                    _board.setOptions(dialog.model);
                }
            });
            _sidebar.add(preferencesButton);

            var loadKingsSimulations = new Button("Load Kings Simulations");
            var lm = new DefaultListModel<String>();

            var jl = new JList<String>(lm);
            loadKingsSimulations.addActionListener(e ->
            {
                lm.clear();
                _mechanics.blackKingSimulations.forEach(b -> lm.addElement(b.toString()));
            });

            jl.addListSelectionListener(e ->
            {
                if (!e.getValueIsAdjusting() && jl.getSelectedIndex() != -1)
                {
                    new GameWindow(_mechanics.blackKingSimulations.get(jl.getSelectedIndex()));
                }
            });


            _sidebar.add(loadKingsSimulations);
            _sidebar.add(jl);
        }
        c.gridx = 1;
        c.gridy = 0;

        _content.add(_sidebar, c);
        // Add _content to JFrame

        getContentPane().add(_content, BorderLayout.CENTER);

        this.getContentPane().addComponentListener
                (
                        new ComponentAdapter()
                        {
                            @Override
                            public void componentResized(ComponentEvent e)
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