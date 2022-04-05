package chess.core.Kompositum;

public abstract class Listelement {
    public abstract int count_nodes();
    public abstract Listelement get_next();
    public abstract Datanode insert(Dataelement in);
    public abstract Dataelement get_content();

    //public abstract Listelement remove(Dataelement search);
}
