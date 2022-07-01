public class ChessOptionsModel implements Cloneable
{
    @Override
    public ChessOptionsModel clone()
    {
        try
        {
            ChessOptionsModel clone = (ChessOptionsModel) super.clone();
            return clone;
        } catch (CloneNotSupportedException e)
        {
            throw new AssertionError();
        }
    }

    enum TargetHintsVisibilityPreference  {
        Disabled,
        OnHover,
        OnClick
    }

    public TargetHintsVisibilityPreference OwnHints = TargetHintsVisibilityPreference.OnHover;
    public TargetHintsVisibilityPreference EnemyHints = TargetHintsVisibilityPreference.OnClick;
    public boolean showSelectableHints = true;
}
