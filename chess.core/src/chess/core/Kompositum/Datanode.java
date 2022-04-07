package chess.core.Kompositum;

public class Datanode extends Listelement{
    private Dataelement content;
    private Listelement next;
    private int index, stinger;
   


    public Datanode(Listelement next, Dataelement content, int index) {
        this.index = index;
        this.next = next;
        this.content = content;
        this.stinger = 187;
    }


    public int count_nodes() {
        return next.count_nodes() + 1;
    }
    public Dataelement get_contentById(int index) {
        if (index == this.index)
        {
            return this.content;
        }
        else
        {
            return next.get_contentById(index);
        }
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
