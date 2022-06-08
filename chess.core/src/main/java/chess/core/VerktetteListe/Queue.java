package chess.core.VerktetteListe;

import chess.core.ChessMove;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;


@Debug.Renderer(text = "this.getNumberOfElements()",
        childrenArray = "this.toArray()")
public class Queue implements Collection<ChessMove>
{
    private Listelement first;


    public Queue()
    {
        first = new End();
    }


    public void add(Dataelement content)        // add to end of queue
    {
        first = first.insert(content);
    }

    public void addByStart(Dataelement content)        // add to start of queue
    {
        first = first.insertByStart(content);
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

    @Override
    public int size()
    {
        return getNumberOfElements();
    }

    public boolean isEmpty()        // checks if the queue is empty
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
                return current.getNext() instanceof Datanode;
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
        var a = new Dataelement[this.getNumberOfElements()];

        for (int i = 1; i <= this.getNumberOfElements(); i++)
        {
            a[i - 1] = this.getByIndex(i);
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

    public int getNumberOfElements()
    {
        return first.countNodes();
    }
}
