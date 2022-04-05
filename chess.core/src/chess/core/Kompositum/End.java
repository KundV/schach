package chess.core.Kompositum;

public class End extends Listelement{

    public End(){

    }


    public int count_nodes() {
        return 0;
    }

    public Listelement get_next() {
        return this;
    }

    public Datanode insert(Dataelement in) {
        return new Datanode(this, in);
    }

    public Dataelement get_content() {
        return null;
    }

    /**
    public Listelement remove(Dataelement search) {
        System.out.println("Cant remove the searched element");
        return null;
    }
     **/
}
