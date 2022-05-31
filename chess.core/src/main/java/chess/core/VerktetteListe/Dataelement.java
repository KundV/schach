package chess.core.VerktetteListe;

import chess.core.ChessMove;
import chess.core.Event;
import chess.core.PlayerId;

public abstract class Dataelement
{
    public PlayerId Player;



    public Dataelement(PlayerId Player)
    {
        this.Player = Player;
    }


    public abstract boolean equals(ChessMove cm);

    public abstract PlayerId getPlayerId();

    public abstract Event getEvent();
}


