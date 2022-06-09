package chess.core;

import chess.core.VerktetteListe.Dataelement;
import chess.core.move.BoardDeleteOperation;
import chess.core.move.BoardInsertOperation;
import chess.core.move.BoardOperation;
import chess.core.move.BoardUpdateOperation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

//a move is an action that a player can take
//every move has a start and end position and contains an Event that describes the action (move, capture, etc)
//


public class ChessMove// extends Dataelement
{
    public final int xStart;
    public final int yStart;
    public final int xTarget;
    public final int yTarget;
    private PlayerId player;
    public final Event event;

    public final Pos start;
    public final Pos target;

    // TODO state is not used yet
    ChessMechanics game;

    List<BoardOperation> operations = new ArrayList<>();

    public List<BoardOperation> getOperations()
    {
        return Collections.unmodifiableList(operations);
    }

    <T extends BoardOperation> List<T> getOperations(Class<T> clazz)
    {
        return operations.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast).toList();
    }

    public List<BoardDeleteOperation> getDeleteOperations()
    {
        return getOperations(BoardDeleteOperation.class);
    }

    public List<BoardUpdateOperation> getUpdateOperations()
    {
        return getOperations(BoardUpdateOperation.class);
    }

    public List<BoardInsertOperation> getInsertOperations()
    {
        return getOperations(BoardInsertOperation.class);
    }

    //public List<>

    public ChessMove(int xStart, int yStart, int xTarget, int yTarget, PlayerId Player, Event event)
    {
        player = Player;
        //super(Player);
        this.event = event;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.start = new Pos(xStart, yStart);
        this.target = new Pos(xTarget, yTarget);
    }

    public ChessMove(Pos start, Pos target, PlayerId Player, Event event)
    {
        this(start.r, start.c, target.r, target.c, Player, event);
    }

    public boolean sameStart(ChessMove cm)
    {
        return ((this == cm) || (cm.xStart == xStart && cm.yStart == yStart));
    }

    public PlayerId getPlayerId()
    {
        return player;
    }


    public Event getEvent()
    {
        return event;
    }
}

