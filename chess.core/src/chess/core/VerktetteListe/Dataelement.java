package chess.core.VerktetteListe;

public abstract class Dataelement {
    public boolean isBlackMove;


    public Dataelement(boolean isBlackMove)
    {
        this.isBlackMove = isBlackMove;
    }


    public abstract boolean equals(Dataelement de);
    public abstract boolean get_color();

}
