package chess.core;


import chess.core.common.Vec;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static chess.core.ChessPieceId.King;
import java.util.Arrays;

import static chess.core.ChessPieceId.*;
import static chess.core.ChessPieceId.Bishop;


public class ChessMechanics    //Klasse zum berechnen des Schachfeldes, der Möglichen Züge und Stadien
{
    private ChessBoardTile[][] chessBoard;                                    // das Schachbrett   bestehend aus Schachfeldern
    private ArrayList<ChessPiece> deadPieces = new ArrayList<ChessPiece>();   // die Toten Figuren
    private int targetTurn = 0;                                               // abbruchfunktion für die rekursive Simulation
    private PlayerId player = PlayerId.WHITE;                                 // der aktive Spieler
    private ArrayList<ChessMove> madeMoves = new ArrayList<ChessMove>();      // bisher getätigte Züge


    private ChessBoardTile[][] initialBoard;

    public ChessMechanics(ChessBoardTile[][] chessBoard, ArrayList<ChessPiece> deadPieces, ArrayList<ChessMove> madeMoves,int targetTurn, PlayerId player, ChessMove move) //konstruktor für die rekursive Zug simulation
    {

        for(int i = madeMoves.size()-1; i > 0;i--)            //Kopieren der gemachten Züge (dereferenzieren)
        {
            this.madeMoves.add(madeMoves.get(i));
        }
        /*for (int i = deadPieces.size() - 1; i > 0; i--)
        {
            this.deadPieces.add(deadPieces.get(i));
        }*/
        this.targetTurn = targetTurn;
        this.player = player;
        cloneBoard(chessBoard);
        executeMove(move);
    }



    public ChessMechanics()
    {
        makeBoard();            //erstellung des Schachbretts
        StartPosition();        //einfügen der Figuren in Startposition
    }


    public void getAllMoves()   //berechnen aller Züge
    {
        removeAllMoves();
        int k = 0;
        int[] tempCoords = new int[4];
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (chessBoard[i][j].hasPiece())
                {
                    if (chessBoard[i][j].getPiece().getChessPieceId() == King)      //gesonderte Prüfung der Könige als letzte Figuren --> um verbotene Züge vorzubeugen
                    {
                        tempCoords[k] = i;
                        k++;
                        tempCoords[k] = j;
                        k++;
                    }
                    else
                    {
                        CheckMoves(i, j); //Berechnung
                    }
                }
            }
        }
        for(int i = k; i > 0 ; i -= 2)
        {
            CheckMoves(tempCoords[i - 2], tempCoords[i - 1]);
        }

    }

    public void removeAllMoves()  //entfernung aller Züge aus Figuren und Schachfeldern
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                chessBoard[i][j].extractAllTargetingMoves();
                if (chessBoard[i][j].hasPiece())
                {
                    chessBoard[i][j].getPiece().removeAllPossibleMoves();
                }
            }
        }
    }
    public void validateAllMoves()  //Überprüfung aller berechneten Züge nach Regelverstößen nach ausübung --> Simulation
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(chessBoard[i][j].hasPiece() && chessBoard[i][j].getPiece().hasPossibleMove())
                {
                    ArrayList<ChessPieceId> ChessPieceIDs = new ArrayList<ChessPieceId>(Arrays.asList(ChessPieceId.Bishop, ChessPieceId.Horse, ChessPieceId.Tower, ChessPieceId.Pawn, ChessPieceId.Queen));
                    ArrayList<ChessMove> illegalMoves = new ArrayList<>();
                    for (int k = 0; k < chessBoard[i][j].getPiece().getPossibleMoves().size(); k++)
                    {
                        ChessMove move = chessBoard[i][j].getPiece().getPossibleMoves().get(k);
                        int t = 1;
                        if(move.getEvent() == EventID.Promotion)
                        {
                            t = 5;
                        }
                        for(int promotionPossebileties = 0; promotionPossebileties < t; promotionPossebileties++)
                        {
                            if(move.getEvent() == EventID.Promotion)
                            {
                                move.setPromotion(ChessPieceIDs.get(promotionPossebileties));
                            }
                            ChessMechanics test = new ChessMechanics(chessBoard, deadPieces, madeMoves, madeMoves.size() + 1, player, move);
                            if (test.check(move.getPlayerId()))                                 //Falls der König in der eigne König nach dem Zug noch oder wieder bedroht ist
                            {
                                illegalMoves.add(move);                                         //wird er zur Löschung gespeichert
                            }
                        }
                    }
                    for (ChessMove illegalMove : illegalMoves)                                  //löschung der verbotenen Züge
                    {
                        chessBoard[i][j].getTargetingMoves().remove(illegalMove);
                        chessBoard[i][j].getPiece().getPossibleMoves().remove(illegalMove);
                    }
                }
            }
        }
    }

    public void CheckMoves(int x, int y)   //Aufruf des zugehörigen Regelwerkes
    {
        if(chessBoard[x][y].hasPiece())
        {
            switch (chessBoard[x][y].getPiece().getChessPieceId())          // switch statement for the piece
            {
                case Pawn -> {
                    RulesPawn(x, y);
                }
                case Tower -> {
                    RulesTower(x, y);
                }
                case Bishop -> {
                    RulesBishop(x, y);
                }
                case Horse -> {
                    RulesHorse(x, y);
                }
                case King -> {
                    RulesKing(x, y);
                }
                case Queen -> {
                    RulesQueen(x, y);
                }
            }
        }
    }


    public void RulesPawn(int x, int y)
    {
        int a = chessBoard[x][y].getPiece().getPlayerId() == PlayerId.BLACK ? 1 : -1;

        if (x + a >= 0 && x + a <= 7)
        {
            if (!chessBoard[x + a][y].hasPiece())
            {
                ChessMove move;
                if (x + a == 0 || x + a == 7)
                {
                    move = new ChessMove(x, y, x + a, y, chessBoard[x][y].getPlayerId(), EventID.Promotion);
                } else
                {
                    move = new ChessMove(x, y, x + a, y, chessBoard[x][y].getPlayerId(), EventID.Move);
                }
                // if the tile is empty
                chessBoard[x][y].getPiece().addPossibleMove(move);
                chessBoard[x + a][y].addTargetingMove(move);


                if (chessBoard[x][y].getPiece().isFirstMove())
                {
                    if (!chessBoard[x + (a * 2)][y].hasPiece())
                    {
                        if (x + (a * 2) == 0 || x + (a * 2) == 7)
                        {
                            move = new ChessMove(x, y, x + (a * 2), y, chessBoard[x][y].getPlayerId(), EventID.Promotion);
                        } else
                        {
                            move = new ChessMove(x, y, x + (a * 2), y, chessBoard[x][y].getPlayerId(), EventID.Move);
                        }                               // if the tile is empty
                        chessBoard[x][y].getPiece().addPossibleMove(move);
                        chessBoard[x + (a * 2)][y].addTargetingMove(move);
                    }
                }

            }

            if (y + 1 <= 7)
            {
                if (chessBoard[x + a][y + 1].hasPiece())
                {
                    if (chessBoard[x][y].getPiece().getPlayerId() == chessBoard[x + a][y + 1].getPiece().getPlayerId().opposite())
                    {
                        ChessMove move;
                        if (x + a == 0 || x + a == 7)
                        {
                            move = new ChessMove(x, y, x + a, y + 1, chessBoard[x][y].getPlayerId(), EventID.Promotion);
                        } else
                        {
                            move = new ChessMove(x, y, x + a, y + 1, chessBoard[x][y].getPlayerId(), EventID.Capture);
                        }
                        // if the tile is empty
                        chessBoard[x][y].getPiece().addPossibleMove(move);
                        chessBoard[x + a][y + 1].addTargetingMove(move);
                    }
                }
                else if(chessBoard[x][y+1].hasPiece() && chessBoard[x][y+1].getPlayerId() != chessBoard[x][y].getPlayerId())
                {
                    if (!madeMoves.isEmpty() && chessBoard[madeMoves.get(madeMoves.size() - 1).xTarget][madeMoves.get(madeMoves.size() - 1).yTarget].getPiece().getChessPieceId() == Pawn)
                    {
                        if (Math.abs(madeMoves.get(madeMoves.size() - 1).xStart - madeMoves.get(madeMoves.size() - 1).xTarget) == 2 && madeMoves.get(madeMoves.size()-1).yTarget == y + 1)
                        {
                            ChessMove move;
                            move = new ChessMove(x, y, x + a, y + 1, chessBoard[x][y].getPlayerId(), EventID.enPassant, madeMoves.get(madeMoves.size() - 1).xTarget, madeMoves.get(madeMoves.size() - 1).yTarget, -1, -1);
                            chessBoard[x][y].getPiece().addPossibleMove(move);
                            chessBoard[x + a][y + 1].addTargetingMove(move);
                        }
                    }
                }
            }

            if (y - 1 >= 0)
            {
                if (chessBoard[x + a][y - 1].hasPiece())
                {
                    if (chessBoard[x][y].getPiece().getPlayerId() == chessBoard[x + a][y - 1].getPiece().getPlayerId().opposite())
                    {
                        ChessMove move;
                        if (x + a == 0 || x + a == 7)
                        {
                            move = new ChessMove(x, y, x + a, y - 1, chessBoard[x][y].getPlayerId(), EventID.Promotion);
                        } else
                        {
                            move = new ChessMove(x, y, x + a, y - 1, chessBoard[x][y].getPlayerId(), EventID.Capture);
                        }// if the tile is empty

                        chessBoard[x][y].getPiece().addPossibleMove(move);
                        chessBoard[x + a][y - 1].addTargetingMove(move);
                    }
                } else if (chessBoard[x][y - 1].hasPiece() && chessBoard[x][y - 1].getPlayerId() != chessBoard[x][y].getPlayerId())
                {
                    if (!madeMoves.isEmpty() && chessBoard[madeMoves.get(madeMoves.size() - 1).xTarget][madeMoves.get(madeMoves.size() - 1).yTarget].getPiece().getChessPieceId() == Pawn)
                    {
                        if (Math.abs(madeMoves.get(madeMoves.size() - 1).xStart - madeMoves.get(madeMoves.size() - 1).xTarget) == 2 && madeMoves.get(madeMoves.size()-1).yTarget == y - 1)
                        {
                            ChessMove move;
                            move = new ChessMove(x, y, x + a, y - 1, chessBoard[x][y].getPlayerId(), EventID.enPassant, madeMoves.get(madeMoves.size() - 1).xTarget, madeMoves.get(madeMoves.size() - 1).yTarget, -1, -1);
                            chessBoard[x][y].getPiece().addPossibleMove(move);
                            chessBoard[x + a][y - 1].addTargetingMove(move);
                        }
                    }
                }
            }
        }
    }

    public void RulesTower(int x, int y)
    {
        boolean notBlocked = true;
        for (int i = x + 1; i < 8 && notBlocked; i++)
        {
            notBlocked = TestMovePiece(x, y, i, y);
        }
        notBlocked = true;
        for (int i = x - 1; i >= 0 && notBlocked; i--)
        {
            notBlocked = TestMovePiece(x, y, i, y);
        }
        notBlocked = true;
        for (int i = y + 1; i < 8 && notBlocked; i++)
        {
            notBlocked = TestMovePiece(x, y, x, i);
        }
        notBlocked = true;
        for (int i = y - 1; i >= 0 && notBlocked; i--)
        {
            notBlocked = TestMovePiece(x, y, x, i);
        }
    }

    public void RulesBishop(int x, int y)
    {
        boolean notBlocked = true;
        for (int i = x + 1, j = y + 1; i < 8 && j < 8 && notBlocked; i++, j++)
        {
            notBlocked = TestMovePiece(x, y, i, j);
        }
        notBlocked = true;
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0 && notBlocked; i--, j--)
        {
            notBlocked = TestMovePiece(x, y, i, j);
        }
        notBlocked = true;
        for (int i = x + 1, j = y - 1; i < 8 && j >= 0 && notBlocked; i++, j--)
        {
            notBlocked = TestMovePiece(x, y, i, j);
        }
        notBlocked = true;
        for (int i = x - 1, j = y + 1; i >= 0 && j < 8 && notBlocked; i--, j++)
        {
            notBlocked = TestMovePiece(x, y, i, j);
        }
    }

    public void RulesHorse(int x, int y)
    {
        TestMovePiece(x, y, x + 1, y + 2);
        TestMovePiece(x, y, x + 1, y - 2);
        TestMovePiece(x, y, x - 1, y + 2);
        TestMovePiece(x, y, x - 1, y - 2);
        TestMovePiece(x, y, x + 2, y + 1);
        TestMovePiece(x, y, x + 2, y - 1);
        TestMovePiece(x, y, x - 2, y + 1);
        TestMovePiece(x, y, x - 2, y - 1);
    }

    public void RulesQueen(int x, int y)
    {
        RulesBishop(x, y);
        RulesTower(x, y);
    }

    public void RulesKing(int x, int y)
    {
        TestMovePiece(x, y, x + 1, y);
        TestMovePiece(x, y, x - 1, y);
        TestMovePiece(x, y, x, y + 1);
        TestMovePiece(x, y, x, y - 1);
        TestMovePiece(x, y, x + 1, y + 1);
        TestMovePiece(x, y, x + 1, y - 1);
        TestMovePiece(x, y, x - 1, y + 1);
        TestMovePiece(x, y, x - 1, y - 1);
        if(!check(chessBoard[x][y].getPlayerId()) && chessBoard[x][y].getPiece().isFirstMove())
        {
            for (int direction = -1; direction < 2; direction += 2)
            {
                for (int i = y + direction; i < 8 && i >= 0; i += direction)
                {
                    if (chessBoard[x][i].hasPiece())
                    {
                        if (chessBoard[x][i].getPiece().getChessPieceId() == Tower && chessBoard[x][i].getPiece().isFirstMove())
                        {
                            ChessMove newMove = new ChessMove(x, y, x, y + (2*direction), chessBoard[x][y].getPlayerId(), EventID.Castling, x, i, x,y + direction);
                            chessBoard[x][y].getPiece().addPossibleMove(newMove);
                            chessBoard[x][i].getPiece().addPossibleMove(newMove);
                        }
                        i = 99;
                    } else if (chessBoard[x][i].hasTargetingMoves())
                    {
                        for (int MovIndex = 0; i < 8 && MovIndex < chessBoard[x][i].getTargetingMoves().size(); MovIndex++)
                        {
                            if (chessBoard[x][i].getTargetingMoves().get(MovIndex).getPlayerId() == chessBoard[x][y].getPlayerId().opposite())
                            {
                                i = 99;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean TestMovePiece(int x, int y, int x2, int y2)
    {
        if (x2 < 0 | x2 > 7 | y2 < 0 | y2 > 7)    //gibt falsch zurück, falls der Zug das Brett verlassen würde
        {
            return false;
        } else if (!chessBoard[x2][y2].hasPiece())      // wenn das Zielfeld leer ist
        {
             ChessMove move = new ChessMove(x, y, x2, y2, chessBoard[x][y].getPlayerId(),EventID.Move);

            chessBoard[x][y].getPiece().addPossibleMove(move);
            chessBoard[x2][y2].addTargetingMove(move);
            return true;
        }
        else if (chessBoard[x2][y2].getPlayerId() == chessBoard[x][y].getPlayerId().opposite())           // wenn eine generische Figur auf dem Zielfeld steht
        {
            ChessMove move = new ChessMove(x, y, x2, y2, chessBoard[x][y].getPlayerId(),EventID.Capture); // gefangennehmen
            chessBoard[x][y].getPiece().addPossibleMove(move);
            chessBoard[x2][y2].addTargetingMove(move);
            return false;

        }
        return false;
    }


    public void StartPosition()     //Startaufstellung
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                PlayerId player = i > 2 ? PlayerId.WHITE : PlayerId.BLACK;
                if (i == 1 || i == 6)
                {
                    chessBoard[i][j].setPiece(new ChessPiece(Pawn, player));
                } else if (i == 0 || i == 7)
                {
                    switch (j)
                    {
                        case 0, 7 -> chessBoard[i][j].setPiece(new ChessPiece(Tower, player));
                        case 1, 6 -> chessBoard[i][j].setPiece(new ChessPiece(Horse, player));
                        case 2, 5 -> chessBoard[i][j].setPiece(new ChessPiece(Bishop, player));
                        case 3 -> chessBoard[i][j].setPiece(new ChessPiece(Queen, player));
                        case 4 -> chessBoard[i][j].setPiece(new ChessPiece(King, player));
                    }
                }
            }
        }
        getAllMoves();
    }

    public void makeBoard()     //Initialisierung der Schachfelder
    {
        chessBoard = new ChessBoardTile[8][8];
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j <= 7; j++)
            {
                chessBoard[i][j] = new ChessBoardTile();
            }
        }
    }

    public ChessBoardTile[][] getChessBoard()  //getter Methode
    {
        return chessBoard;
    }



    public void executeMove(ChessMove move)     //Ausführen
    {
        if (move.getPlayerId() == player)
        {

            if ((move.getEvent() == EventID.Capture || move.getEvent() == EventID.Move))
            {
                if (move.getEvent() == EventID.Capture)                                               // wenn die Figur gefangen wird
                {
                    deadPieces.add(chessBoard[move.xTarget][move.yTarget].removePiece());
                    chessBoard[move.xTarget][move.yTarget].setPiece(chessBoard[move.xStart][move.yStart].removePiece());
                } else                                                                                // wenn die Figur gefangen wird
                {
                    chessBoard[move.xTarget][move.yTarget].setPiece(chessBoard[move.xStart][move.yStart].removePiece());
                }
                madeMoves.add(move);
            }
            else if(move.getEvent() == EventID.Castling)                                                // wenn eine Rochade ausgeführt wird
            {
                chessBoard[move.xTarget][move.yTarget].setPiece(chessBoard[move.xStart][move.yStart].removePiece());    //König verschieben
                chessBoard[move.xSecondaryPieceTarget][move.ySecondaryPieceTarget].setPiece(chessBoard[move.xSecondaryPieceStart][move.ySecondaryPieceStart].removePiece()); //Turm verschieben
                chessBoard[move.xSecondaryPieceTarget][move.ySecondaryPieceTarget].getPiece().addMoveCount();
                madeMoves.add(move);
            }
            else if(move.getEvent() == EventID.Promotion)                                               //wenn eine Figur promoviert wird
            {
                if(chessBoard[move.xTarget][move.yTarget].hasPiece())                                   //falls eine Figur dabei geschlagen wird, kommt diese zu der Liste der getöteten Figuren
                {
                    deadPieces.add(chessBoard[move.xTarget][move.yTarget].removePiece());
                }
                chessBoard[move.xTarget][move.yTarget].setPiece(chessBoard[move.xStart][move.yStart].removePiece());    //Figur verschieben
                chessBoard[move.xTarget][move.yTarget].getPiece().setChessPieceIdId(move.getPromotion());               //Neue Figur setzen
                madeMoves.add(move);
            }
            else if(move.getEvent() == EventID.enPassant)
            {
                chessBoard[move.xTarget][move.yTarget].setPiece(chessBoard[move.xStart][move.yStart].removePiece());
                deadPieces.add(chessBoard[move.xSecondaryPieceStart][move.ySecondaryPieceStart].removePiece());
                madeMoves.add(move);
            }

            chessBoard[move.xTarget][move.yTarget].getPiece().addMoveCount(); //Zähler für die Anzahl der Züge einer Figur hochzählen

            player = player.opposite();                //Spielerwechsel

            getAllMoves();                          //alle Züge erneuern

            if(targetTurn == 0) //wenn es keine Simulation ist, wird auf legalität überprüft
            {
                validateAllMoves();
            }

        }
    }

    public PlayerId reverseMove()                                           //macht den letzten Zug rückgängig
    {
        if (!madeMoves.isEmpty())                                           //nur, wenn bereits züge gemacht wurden
        {
            ChessMove move = madeMoves.get(madeMoves.size() - 1);
            madeMoves.remove(move);

            if (move.getPlayerId() != player)
            {

                if ((move.getEvent() == EventID.Capture || move.getEvent() == EventID.Move))
                {

                    if (move.getEvent() == EventID.Capture)                                               // if the piece has been captured, remove the Piece on the targeted tile
                    {
                        chessBoard[move.xStart][move.yStart].setPiece(chessBoard[move.xTarget][move.yTarget].removePiece());
                        chessBoard[move.xTarget][move.yTarget].setPiece(deadPieces.remove(deadPieces.size() - 1));
                    } else                                                                           // if the piece has been moved, remove from start tile and add to target tile
                    {
                        chessBoard[move.xStart][move.yStart].setPiece(chessBoard[move.xTarget][move.yTarget].removePiece());
                    }
                }
                else if(move.getEvent() == EventID.Castling)
                {
                    chessBoard[move.xStart][move.yStart].setPiece(chessBoard[move.xTarget][move.yTarget].removePiece());
                    chessBoard[move.xSecondaryPieceStart][move.ySecondaryPieceStart].setPiece(chessBoard[move.xSecondaryPieceTarget][move.ySecondaryPieceTarget].removePiece());
                    chessBoard[move.xSecondaryPieceStart][move.ySecondaryPieceStart].getPiece().removeMoveCount();
                }
                else if(move.getEvent() == EventID.Promotion)
                {
                    chessBoard[move.xTarget][move.yTarget].getPiece().setChessPieceIdId(Pawn);          //move.getPromotion()
                    chessBoard[move.xStart][move.yStart].setPiece(chessBoard[move.xTarget][move.yTarget].removePiece());
                    if(move.yTarget != move.yStart)
                    {
                        chessBoard[move.xTarget][move.yTarget].setPiece(deadPieces.remove(deadPieces.size() - 1));
                    }
                }
                else if(move.getEvent() == EventID.enPassant)
                {
                    chessBoard[move.xStart][move.yStart].setPiece(chessBoard[move.xTarget][move.yTarget].removePiece());
                    chessBoard[move.xSecondaryPieceStart][move.ySecondaryPieceStart].setPiece(deadPieces.remove(deadPieces.size() - 1));
                }

                chessBoard[move.xStart][move.yStart].getPiece().removeMoveCount();

                getAllMoves();

                player = player.opposite();
            }
        }

        return player;
    }


    public ChessBoardTile get(int x, int y)
    {
        return chessBoard[y][x];
    }

    public void cloneBoard(ChessBoardTile[][] oldBord)  //kopiert das alte Brett für die Simulation mit neuen Objekten
    {
        makeBoard();
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (oldBord[i][j].hasPiece())
                {
                    chessBoard[i][j].setPiece(new ChessPiece(oldBord[i][j].getPiece().getChessPieceId(), oldBord[i][j].getPiece().getPlayerId(), oldBord[i][j].getPiece().getMoveCount()));
                }
            }
        }
    }

    public boolean check(PlayerId player)    //gibt wahr zurück, wenn der König des gegebenen Spielers bedroht ist
    {
        for (int i = 0; i < 8; i++)                                       //iterating through the ChessBoard until:
        {
            for (int j = 0; j < 8 ; j++)
            {
                if(chessBoard[i][j].hasPiece())
                {
                    //System.out.println(chessBoard[i][j].getPiece().getChessPieceId());
                    //System.out.println("Has Piece");
                    if ((chessBoard[i][j].getPiece().getChessPieceId() == King))                //King is Found
                    {
                        //System.out.println("Found King");
                        if((chessBoard[i][j].getPlayerId() == player))                          //of the given Player
                        {
                            //System.out.println("Is King of last Player" + player);
                            if(chessBoard[i][j].hasTargetingMoves())                            //is endangered by Enemy
                            {
                                //System.out.println("Has Targeting Moves");
                                return true;

                            }
                        }
                    }
                }
            }
        }
        return false;                                                                             //not endangered
    }

    public boolean playerHasNoMoves()      //wenn der aktive Spieler keine Züge mehr hat
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (chessBoard[i][j].hasPiece())
                {
                    if ((chessBoard[i][j].getPlayerId() == player))
                    {
                        if(chessBoard[i][j].getPiece().hasPossibleMove())
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean patt()          // wenn der aktive Spieler keine Züge mehr hat aber der König nicht bedroht ist
    {
        if(!check(player))
        {
            if(playerHasNoMoves())
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkMate()     //wenn der aktive Spieler keine Züge mehr hat und der König bedroht ist
    {
        if(playerHasNoMoves())
        {
            if(check(player))
            {
                return true;
            }
        }
        return false;
    }

    public PlayerId getCurrentPlayer()
    {
        return player;
    }
}

