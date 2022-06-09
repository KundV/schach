package chess.core.VerktetteListe;

import chess.core.ChessMove;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;


@Debug.Renderer(text = "this.getNumberOfElements()",
        childrenArray = "this.toArray()")
public class Queue implements Collection<ChessMove>
{
    private Listelement first;


    public Queue()
    {
        first = new End();
    }


    // Is OK for migration
    public void add(Dataelement content)        // add to end of ArrayList
    {
        List<ChessMove> moves = null;
        
        first = first.insert(content);
    }

    
    /* Custom Method Removed
    public void addByStart(Dataelement content)        // add to start of ArrayList
    {
        first = first.insertByStart(content);
    }*/
    
    // Dummy method
    public void add(int index, Dataelement content)        // add to index of ArrayList
    {
        first = first.insertByStart(content);
    }

    // Custom method removed
    /*
    public Dataelement remove()     // remove from front of ArrayList
    {
        Listelement oldFirst = first;
        first = first.getNext();
        return oldFirst.getContent();
    }*/
    
    // Dummy
    public Dataelement remove(int index) {
        throw new UnsupportedOperationException("Dummy method for ArrayList migration");
    }

    // Ok for migration
    public void remove(ChessMove move)      // removes a specific move from the ArrayList
    {
        first = first.remove(move);
    }

    // Ok for migration
    public Dataelement get(int index)        // returns the element at the given index
    {
        
        return first.getContentById(index);
    }

    /* Unused
    public int count_nodes()        // counts the number of nodes in the ArrayList
    {
        return first.countNodes();
    }*/

    // Ok for migration
    public void clear()         // clears the ArrayList
    {
        first = new End();
    }

    // Ok for migration
    @Override
    public int size()
    {
        return size();
    }

    public boolean isEmpty()        // checks if the ArrayList is empty
    {
        return first.isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        return this.stream().anyMatch(e -> e.equals(o));
    }

    @NotNull
    @Override
    public Iterator<ChessMove> iterator()
    {
        return new Iterator<ChessMove>()
        {
            private Listelement current = first;

            @Override
            public boolean hasNext()
            {
                return current instanceof Datanode;
            }

            @Override
            public ChessMove next()
            {
                var ret = (ChessMove) current.getContent();
                current = current.getNext();
                return ret;
            }
        };
    }

    public Dataelement[] toArray()
    {
        var a = new Dataelement[this.size()];

        for (int i = 1; i <= this.size(); i++)
        {
            a[i - 1] = this.get(i - 1);
        }
        return a;

    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a)
    {
        //noinspection unchecked
        return (T[]) toArray();
    }

    @Override
    public boolean add(ChessMove chessMove)
    {
        add((Dataelement) chessMove);
        return true;
    }

    @Override
    public boolean remove(Object o)
    {
        remove((ChessMove) o);
        return true;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c)
    {
        return this.stream().allMatch(c::contains);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends ChessMove> c)
    {
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c)
    {
        c.forEach(this::remove);
        return true;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c)
    {
        this.stream().filter(c::contains).forEach(this::remove);
        return true;
    }
/*
    public int size()
    {
        return first.countNodes();
    }
    */
}
