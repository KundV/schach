package chess.ui;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class JChessOptions extends JComponent
{

    public JChessOptions()
    {
        this(null);
    }

    public ChessOptionsModel model;



    public JChessOptions(@Nullable ChessOptionsModel inputModel)
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.model = inputModel == null ? new ChessOptionsModel() : inputModel;

        {
            var checkbox = new JCheckBox("Ziehbare Figuren markieren");
            checkbox.setSelected(model.showSelectableHints);
            checkbox.addActionListener(e -> model.showSelectableHints = checkbox.isSelected());
            this.add(checkbox);
        }

        {
            var border = BorderFactory.createTitledBorder("Eigene Zielfelder markieren");
            var panel = new JPanel();
            panel.setBorder(border);

            var radioGroup = new ButtonGroup();
            {
                var radio = new JRadioButton("Nie");
                radio.setSelected(model.OwnHints == ChessOptionsModel.TargetHintsVisibilityPreference.Disabled);
                radio.addActionListener(e -> model.OwnHints = ChessOptionsModel.TargetHintsVisibilityPreference.Disabled);
                radioGroup.add(radio);
                panel.add(radio);
            }
            {
                var radio = new JRadioButton("Beim Überfahren");
                radio.setSelected(model.OwnHints == ChessOptionsModel.TargetHintsVisibilityPreference.OnHover);
                radio.addActionListener(e -> model.OwnHints = ChessOptionsModel.TargetHintsVisibilityPreference.OnHover);
                radioGroup.add(radio);
                panel.add(radio);
            }
            {
                var radio = new JRadioButton("Beim Klicken");
                radio.setSelected(model.OwnHints == ChessOptionsModel.TargetHintsVisibilityPreference.OnClick);
                radio.addActionListener(e -> model.OwnHints = ChessOptionsModel.TargetHintsVisibilityPreference.OnClick);
                radioGroup.add(radio);
                panel.add(radio);
            }
            this.add(panel);
        }

        {
            var border = BorderFactory.createTitledBorder("Gegnerische Zielfelder markieren");
            var panel = new JPanel();
            panel.setBorder(border);

            var radioGroup = new ButtonGroup();
            {
                var radio = new JRadioButton("Nie");
                radio.setSelected(model.EnemyHints == ChessOptionsModel.TargetHintsVisibilityPreference.Disabled);
                radio.addActionListener(e -> model.EnemyHints = ChessOptionsModel.TargetHintsVisibilityPreference.Disabled);
                radioGroup.add(radio);
                panel.add(radio);
            }
            {
                var radio = new JRadioButton("Beim Überfahren");
                radio.setSelected(model.EnemyHints == ChessOptionsModel.TargetHintsVisibilityPreference.OnHover);
                radio.addActionListener(e -> model.EnemyHints = ChessOptionsModel.TargetHintsVisibilityPreference.OnHover);
                radioGroup.add(radio);
                panel.add(radio);
            }
            {
                var radio = new JRadioButton("Beim Klicken");
                radio.setSelected(model.EnemyHints == ChessOptionsModel.TargetHintsVisibilityPreference.OnClick);
                radio.addActionListener(e -> model.EnemyHints = ChessOptionsModel.TargetHintsVisibilityPreference.OnClick);
                radioGroup.add(radio);
                panel.add(radio);
            }
            this.add(panel);


        }

    }
}
