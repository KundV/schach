package chess.core;


public class Event
{
    private EventID id;



    public Event(EventID id)
    {
        this.id = id;
    }



    public EventID getID()
    {
        return id;
    }
}
