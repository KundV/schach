package chess.core.Kompositum;

public abstract class Dataelement {
    public boolean isBlackMove;


    public Dataelement(boolean isBlackMove)
    {
        this.isBlackMove = isBlackMove;
    }


    public abstract boolean equals(Dataelement de);
    public abstract boolean get_color();

}
