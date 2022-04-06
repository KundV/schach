package chess.core.Kompositum;

public class End extends Listelement{
    private int index;

    public End(){
        index = 0;
    }
    public int count_nodes() {
        return 0;
    }

    public Listelement get_next() {
        return this;
    }

    public Datanode insert(Dataelement in) {
        index++;
        return new Datanode(this, in, index);
    }

    public Dataelement get_content() {
        return null;
    }

    public Dataelement get_contentById(int index) {
        return null;
    }

    /**
    public Listelement remove(Dataelement search) {
        System.out.println("Cant remove the searched element");
        return null;
    }
     **/
}
