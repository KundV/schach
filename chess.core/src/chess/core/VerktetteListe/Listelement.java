package chess.core.VerktetteListe;

import chess.core.ChessMove;

public abstract class Listelement {
    public abstract int countNodes();
    public abstract Listelement getNext();
    public abstract Datanode insert(Dataelement in);
    public abstract Dataelement getContent();
    public abstract Dataelement getContentById(int index);
    public abstract Listelement remove(ChessMove move);
    public abstract boolean isEmpty();



    //public abstract Listelement remove(Dataelement search);
}
