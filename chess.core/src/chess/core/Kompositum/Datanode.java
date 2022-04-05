package chess.core.Kompositum;

public class Datanode extends Listelement{
    private Dataelement content;
    private Listelement next;


    public Datanode(Listelement next, Dataelement content){
        this.next = next;
        this.content = content;
    }

    public int count_nodes() {
        return next.count_nodes() + 1;
    }


    public Listelement get_next() {
        return next;
    }

    public Datanode insert(Dataelement in) {
        next = next.insert(in);
        return this;
    }

    public Dataelement get_content() {
        return this.content;
    }

    /** search remove. not needed yet
    public Listelement remove(Dataelement search) {
        if(content.equals(search)){
            return next;
        }
        else{
            next = next.remove(search);
            return this;
        }
    }
    **/
}
