package chess.core.VerktetteListe;

public class Queue {
    private Listelement first;

    public Queue() {
        first = new End();
    }

    public void add(Dataelement content){
        first=first.insert(content);
    }

    public Listelement remove(){
        Listelement oldFirst = first;
        first = first.get_next();
        return oldFirst;
    }
    public Dataelement getByIndex(int index){
        return first.get_contentById(index);
    }

    public int count_nodes() {
        return first.count_nodes();
    }
    public void clear(){
        first = new End();
    }

    public boolean isEmpty(){
        return first.get_next() == null;
    }
}
