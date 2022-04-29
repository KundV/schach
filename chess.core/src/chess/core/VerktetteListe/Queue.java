package chess.core.VerktetteListe;

import chess.core.ChessMove;

public class Queue {
    private Listelement first;

    public Queue() {
        first = new End();
    }

    public void add(Dataelement content){   // add to end of queue
        first=first.insert(content);
    }

    public Dataelement remove(){    // remove from front of queue
        Listelement oldFirst = first;
        first = first.get_next();
        return oldFirst.get_content();
    }

    public void remove(ChessMove move){  // removes a specific move from the queue
        first = first.remove(move);

    }
    public Dataelement getByIndex(int index){   // returns the element at the given index
        return first.get_contentById(index);
    }

    public int count_nodes() {                 // counts the number of nodes in the queue
        return first.count_nodes();
    }
    public void clear(){                  // clears the queue
        first = new End();
    }

    public boolean isEmpty(){                  // checks if the queue is empty
        return first.get_next() == null;
    }
}
