package chess.core.VerktetteListe;

import chess.core.ChessMove;

public class Queue
{
    private Listelement first;

    public Queue()
    {
        first = new End();
    }


    public void add(Dataelement content)        // add to end of queue
    {
        first=first.insert(content);
    }

    public Dataelement remove()     // remove from front of queue
    {
        Listelement oldFirst = first;
        first = first.getNext();
        return oldFirst.getContent();
    }

    public void remove(ChessMove move)      // removes a specific move from the queue
    {
        first = first.remove(move);
    }

    public Dataelement getByIndex(int index)        // returns the element at the given index
    {
        return first.getContentById(index);
    }

    public int count_nodes()        // counts the number of nodes in the queue
    {
        return first.countNodes();
    }

    public void clear()         // clears the queue
    {
        first = new End();
    }

    public boolean isEmpty()        // checks if the queue is empty
    {
        return first.isEmpty();
    }

    public int getNumberOfElements()
    {
        return first.countNodes();
    }
}
