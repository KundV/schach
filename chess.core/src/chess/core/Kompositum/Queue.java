package chess.core.Kompositum;

public class Queue {
    private Listelement first;

    public Queue() {
        first = new End();
    }

    public void insert(Dataelement content){
        first=first.insert(content);
    }

    public Listelement remove(){
        Listelement oldFirst = first;
        first = first.get_next();
        return oldFirst;
    }

    public int count_nodes() {
        return first.count_nodes();
    }
}
