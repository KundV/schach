package chess.core.Kompositum;

public abstract class Dataelement {
    public int number;

    public Dataelement(int number){
        this.number = number;
    }


    public abstract boolean equals(Dataelement de);
    public abstract int get_number();
}
