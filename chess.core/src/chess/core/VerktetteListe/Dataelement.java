package chess.core.VerktetteListe;

import chess.core.PlayerId;

public abstract class Dataelement {
    public PlayerId Player;


    public Dataelement(PlayerId Player)
    {
        this.Player = Player;
    }
    public abstract boolean equals(Dataelement de);

    public abstract PlayerId getPlayerId();

}
