package chess.core.VerktetteListe;

public class Stack
{
    private Listelement first;
    private int index =0;

    public Stack()
    {
        first = new End();
    }


    public void add(Dataelement content)
    {
        first = new Datanode(first, content, index++);
    }

    public Listelement remove()
    {
        Listelement oldFirst = first;
        first = first.getNext();
        return oldFirst;
    }

    public Listelement getFirst()
    {
        return first;
    }

    public int countNodes()
    {
        return first.countNodes();
    }

    public void clear()
    {
        first = new End();
    }

    public boolean isEmpty()
    {
        return first.isEmpty();
    }


}
