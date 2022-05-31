package chess.core.VerktetteListe;

import chess.core.ChessMove;

public class Datanode extends Listelement
{
    private Dataelement content;        //contents of the node (move)
    private Listelement next;           //pointer to the next node in the list
    private int index;



    public Datanode(Listelement next, Dataelement content, int index)
    {
        this.index = index;
        this.next = next;
        this.content = content;
    }



    public int countNodes()     //counts the number of nodes in the list
    {
        return next.countNodes() + 1;
    }

    public Dataelement getContentById(int index)        //returns if the index is the same as the index of the node
    {
        if (index == 1)
        {
            return this.content;
        }

        else
        {
            index--;
            return next.getContentById(index);        //if not, it goes to the next node
        }
    }

    public Listelement getNext()
    {
        return next;
    }       //returns the next node

    public Datanode insert(Dataelement in)          //inserts a new node at the end of the list
    {

        if(this.content.equals(in))
        {
            this.content = in;
            return this;
        }

        else
        {
            next = next.insert(in);
            return this;
        }
    }

    public Datanode insertByStart(Dataelement in)          //inserts a new node at the end of the list
    {

        if(this.content.equals(in))
        {
            this.content = in;
            return this;
        }

        else
        {
            next = next.insert(in);
            return this;
        }
    }

    public Dataelement getContent()         //returns the content of the node
    {
        return this.content;
    }

    public int getIndex()        //returns the index of the node
    {
        return this.index;
    }

    public boolean isEmpty()        //checks if the list is empty
    {
        return false;
    }

    public Listelement remove(ChessMove move)        //removes a specific node from the list
    {
        if (content.equals(move))     //if the move is the same as the move of the node
        {
            return next;
        }

        else
        {
            next = next.remove(move);    //if not, it goes to the next node
            return this;
        }
    }

    /** search remove. not needed yet
    public Listelement remove(Dataelement search)
     {
        if(content.equals(search))
        {
            return next;
        }

        else
        {
            next = next.remove(search);
            return this;
        }
    }
    **/
}