package chess.core.VerktetteListe;

import chess.core.ChessMove;

public class End extends Listelement{

    private int index;

    public End(){
        index = 0;
    }

    public int count_nodes() {
        return 0;
    }       //counts the number of nodes in the list

    public Listelement get_next() {
        return this;
    }       // End of list

    public Datanode insert(Dataelement in) {    //creates new node to insert at the end of the list
        index++;
        return new Datanode(this, in, index);
    }

    public Dataelement get_content() {
        return null;
    }   // End of list

    public Dataelement get_contentById(int index) {
        return null;
    }  // End of list

    public boolean is_empty() {
        return true;
    }    //if end is first in list then list is empty

    public Listelement remove(ChessMove move) {
        return this;
    }   //specific move not in list

}
