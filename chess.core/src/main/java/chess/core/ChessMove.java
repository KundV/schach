package chess.core;

import chess.core.common.Vec;
import chess.core.move.BoardDeleteOperation;
import chess.core.move.BoardInsertOperation;
import chess.core.move.BoardOperation;
import chess.core.move.BoardUpdateOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public final EventID event;

    public final Vec start;
    public final Vec target;

    // TODO state is not used yet
    ChessMechanics game;
    private ChessPieceId promotion;

    public void setPromotion(ChessPieceId promotion)
    {
        if (event != EventID.Promotion)
            throw new UnsupportedOperationException("Setting the promotion is only supported for users");
        this.promotion = promotion;
    }


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

    public ChessMove(int xStart, int yStart, int xTarget, int yTarget, PlayerId Player, EventID event)
    {
        player = Player;
        //super(Player);
        this.event = event;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.start = new Vec(xStart, yStart);
        this.target = new Vec(xTarget, yTarget);
    }



    public boolean sameStart(ChessMove cm)
    {
        return ((this == cm) || (cm.xStart == xStart && cm.yStart == yStart));
    }

    public PlayerId getPlayerId()
    {
        return player;
    }


    public EventID getEvent()
    {
        return event;
    }
}

