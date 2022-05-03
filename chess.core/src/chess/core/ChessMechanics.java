package chess.core;

import chess.core.VerktetteListe.Queue;

import java.util.ArrayList;


public class ChessMechanics
{

    private ChessBoardTile[][] chessBoard; // the chess board
    private ArrayList<ChessPiece> deadPieces = new ArrayList<ChessPiece>() ;   // the dead pieces
    private int turn;                      // the current turn
    private PlayerId player = PlayerId.WHITE; // the current player
    private Queue madeMoves;               // the moves made

    public ChessMechanics(ChessBoardTile[][] chessBoard, ArrayList<ChessPiece> deadPieces, int turn, PlayerId player, Queue madeMoves, ChessMove move)
    {
        this.chessBoard = chessBoard;
        getAllMoves();
    }
    public ChessMechanics()
    {
        makeBoard();
        StartPosition();
        chessBoard[2][2].setPiece(new ChessPiece(ChessPieceId.PAWN, PlayerId.WHITE,187)); //Test position  //TODO: Remove this

        getAllMoves();

        executeMove((ChessMove) chessBoard[2][2].getPiece().getPossibleMoves().getByIndex(3));
        executeMove((ChessMove) chessBoard[1][7].getPiece().getPossibleMoves().getByIndex(2));

    }

    public void getAllMoves()
    {
        for(int i = 0; i<8; i++)
        {
            for(int j = 0; j<8; j++)
            {
                chessBoard[i][j].removeAllTargetingMoves();
                if(chessBoard[i][j].hasPiece())
                {
                    chessBoard[i][j].getPiece().removeAllPossibleMoves();
                }
            }
        }
        for(int i = 0; i<8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(chessBoard[i][j].hasPiece())
                {
                   CheckMoves(i,j);
                }
            }
        }
    }

    public void CheckMoves(int x, int y)
    {

        switch (chessBoard[x][y].getPiece().getChessPieceId())                                                                      // switch statement for the piece
        {
            case PAWN   -> { RulesPawn(x,y);}
            case TOWER  -> { RulesTower(x,y);}
            case BISHOP -> { RulesBishop(x,y);}
            case HORSE  -> { RulesHorse(x,y);}
            case KING   -> { RulesKing(x,y);}
            case QUEEN  -> { RulesQueen(x,y);}
        }
    }

    public void RulesPawn(int x, int y)
    {
            int a = chessBoard[x][y].getPiece().getPlayerId() == PlayerId.BLACK ? 1 : -1;
            if(x+a>=0 && x+a<=7)
            {
                if (!chessBoard[x + a][y].hasPiece())
                {
                    ChessMove move;
                    if(x+a == 0 || x+a == 7)
                    {
                        move = new ChessMove(x, y, x + a, y, chessBoard[x][y].getPlayerId(), new Event(EventID.Promotion));
                    }
                    else
                    {
                        move = new ChessMove(x, y, x + a, y, chessBoard[x][y].getPlayerId(), new Event(EventID.Move));
                    }
                                                   // if the tile is empty
                    chessBoard[x][y].getPiece().addPossibleMove(move);
                    chessBoard[x + a][y].addTargetingMove(move);

                }
                else if (chessBoard[x + a][y].hasPiece())
                {
                    ChessMove move = new ChessMove(x, y, x + a, y, chessBoard[x][y].getPlayerId(), new Event(EventID.Blocked));                               // if the tile is empty
                    chessBoard[x][y].getPiece().addPossibleMove(move);
                    chessBoard[x + a][y].addTargetingMove(move);
                }

                if(x+(a*2)>=0 && x+(a*2)<=7)
                {
                    if(chessBoard[x][y].getPiece().isFirstMove())
                    {
                            if (!chessBoard[x +(a*2)][y].hasPiece())
                            {
                                ChessMove move;
                                if(x+(a*2) == 0 || x+(a*2) == 7)
                                {
                                    move = new ChessMove(x, y, x + (a*2), y, chessBoard[x][y].getPlayerId(), new Event(EventID.Promotion));
                                }
                                else
                                {
                                    move = new ChessMove(x, y, x + (a*2), y, chessBoard[x][y].getPlayerId(), new Event(EventID.Move));
                                }                               // if the tile is empty
                                chessBoard[x][y].getPiece().addPossibleMove(move);
                                chessBoard[x + (a*2)][y].addTargetingMove(move);
                            }
                            else if (chessBoard[x + (a*2)][y].hasPiece())
                            {
                                ChessMove move = new ChessMove(x, y, x + (a*2), y, chessBoard[x][y].getPlayerId(), new Event(EventID.Blocked));                               // if the tile is empty
                                chessBoard[x][y].getPiece().addPossibleMove(move);
                                chessBoard[x + (a*2)][y].addTargetingMove(move);
                            }

                    }
                }
            }


        if(x+a >= 0 & x+a <= 7 & y+1 >= 0 & y-1 >= 0 & y+1 <= 7 & y-1 <= 7)
            {

                if (chessBoard[x + a][y+1].hasPiece())
                    {
                        if (chessBoard[x][y].getPiece().getPlayerId() == chessBoard[x + a][y + 1].getPiece().getPlayerId().opposite())
                        {
                            ChessMove move;
                            if(x+a == 0 || x+a == 7)
                            {
                                move = new ChessMove(x, y, x + a, y + 1, chessBoard[x][y].getPlayerId(), new Event(EventID.Promotion));
                            }
                            else
                            {
                                move = move = new ChessMove(x, y, x + a, y+1, chessBoard[x][y].getPlayerId(), new Event(EventID.Capture));
                            }
                                                     // if the tile is empty
                        chessBoard[x][y].getPiece().addPossibleMove(move);
                        chessBoard[x + a][y+1].addTargetingMove(move);
                        }
                    }

                if (chessBoard[x + a][y-1].hasPiece())
                    {
                        if (chessBoard[x][y].getPiece().getPlayerId() == chessBoard[x + a][y - 1].getPiece().getPlayerId().opposite())
                        {
                            ChessMove move;
                            if(x+a == 0 || x+a == 7)
                            {
                                move = new ChessMove(x, y, x + a, y + 1, chessBoard[x][y].getPlayerId(), new Event(EventID.Promotion));
                            }
                            else
                            {
                                move = move = new ChessMove(x, y, x + a, y+1, chessBoard[x][y].getPlayerId(), new Event(EventID.Capture));
                            }                            // if the tile is empty
                        chessBoard[x][y].getPiece().addPossibleMove(move);
                        chessBoard[x + a][y-1].addTargetingMove(move);
                        }
                    }
            }
    }
    public void RulesTower(int x,int y)
    {
        boolean notBlocked = true;
        for(int i = x+1; i<8 && notBlocked; i++)
        {
            notBlocked= TestMovePiece(x,y,i,y);
        }
        notBlocked = true;
        for(int i = x-1; i>=0 && notBlocked; i--)
        {
            notBlocked= TestMovePiece(x,y,i,y);
        }
        notBlocked = true;
        for(int i = y+1; i<8 && notBlocked; i++)
        {
            notBlocked= TestMovePiece(x,y,x,i);
        }
        notBlocked = true;
        for(int i = y-1; i>=0 && notBlocked; i--)
        {
            notBlocked= TestMovePiece(x,y,x,i);
        }
    }

    public void RulesBishop(int x, int y)
    {
        boolean notBlocked = true;
        for(int i = x+1, j = y+1; i<8 && j<8 && notBlocked; i++, j++)
        {
            notBlocked= TestMovePiece(x,y,i,j);
        }
        notBlocked = true;
        for(int i = x-1, j = y-1; i>=0 && j>=0 && notBlocked; i--, j--)
        {
            notBlocked= TestMovePiece(x,y,i,j);
        }
        notBlocked = true;
        for(int i = x+1, j = y-1; i<8 && j>=0 && notBlocked; i++, j--)
        {
            notBlocked= TestMovePiece(x,y,i,j);
        }
        notBlocked = true;
        for(int i = x-1, j = y+1; i>=0 && j<8 && notBlocked; i--, j++)
        {
            notBlocked= TestMovePiece(x,y,i,j);
        }
    }

    public void RulesHorse(int x, int y)
    {
        TestMovePiece(x,y,x+1,y+2);
        TestMovePiece(x,y,x+1,y-2);
        TestMovePiece(x,y,x-1,y+2);
        TestMovePiece(x,y,x-1,y-2);
        TestMovePiece(x,y,x+2,y+1);
        TestMovePiece(x,y,x+2,y-1);
        TestMovePiece(x,y,x-2,y+1);
        TestMovePiece(x,y,x-2,y-1);
    }

    public void RulesQueen(int x, int y)
    {
        RulesBishop(x,y);
        RulesTower(x,y);
    }

    public void RulesKing(int x, int y)
    {
        TestMovePiece(x,y,x+1,y);
        TestMovePiece(x,y,x-1,y);
        TestMovePiece(x,y,x,y+1);
        TestMovePiece(x,y,x,y-1);
        TestMovePiece(x,y,x+1,y+1);
        TestMovePiece(x,y,x+1,y-1);
        TestMovePiece(x,y,x-1,y+1);
        TestMovePiece(x,y,x-1,y-1);
    }

    public boolean TestMovePiece(int x, int y, int x2, int y2)
    {
        if(x2<0 | x2>7 | y2<0 | y2>7)
        {
            return false;
        }
         else if(!chessBoard[x2][y2].hasPiece())
        {
            ChessMove move = new ChessMove(x,y,x2,y2,chessBoard[x][y].getPlayerId(),new Event(EventID.Move));                               // if the tile is empty
            chessBoard[x][y].getPiece().addPossibleMove(move);
            chessBoard[x2][y2].addTargetingMove(move);
            return true;
        }
        else if(chessBoard[x2][y2].getPlayerId() == chessBoard[x][y].getPlayerId().opposite())           // if the tile is occupied by an enemy piece
        {
            ChessMove move = new ChessMove(x,y,x2,y2,chessBoard[x][y].getPlayerId(),new Event(EventID.Capture));
            chessBoard[x][y].getPiece().addPossibleMove(move);
            chessBoard[x2][y2].addTargetingMove(move);
            return true;
        }
        else if(chessBoard[x2][y2].getPlayerId() == chessBoard[x][y].getPlayerId())                      // if the tile is occupied by a friendly piece
        {
            ChessMove move = new ChessMove(x,y,x2,y2,chessBoard[x][y].getPlayerId(),new Event(EventID.Blocked));
            chessBoard[x][y].getPiece().addPossibleMove(move);
            chessBoard[x2][y2].addTargetingMove(move);
            return false;
        }
        return false;
    }
    public void StartPosition()
    {
        for(int i = 0; i<8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                PlayerId player = i > 2 ? PlayerId.WHITE : PlayerId.BLACK;
                if(i==1 || i == 6)
                {
                    chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.PAWN,player,i));
                }

                else if(i == 0 || i == 7)
                {
                    switch (j) {
                        case 0 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.TOWER,player,i));
                        case 1 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.HORSE,player,i));
                        case 2 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.BISHOP,player,i));
                        case 3 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.QUEEN,player,i));
                        case 4 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.KING,player,i));
                        case 5 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.BISHOP,player,i));
                        case 6 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.HORSE,player,i));
                        case 7 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.TOWER,player,i));
                    }

                }
            }
        }
        getAllMoves();
    }



    public void makeBoard()
    {
        chessBoard = new ChessBoardTile[8][8];
        for(int i = 0; i<8; i++)
        {
            for (int j = 0; j <= 7; j++)
            {
                chessBoard[i][j] = new ChessBoardTile();
            }

        }
    }

    public ChessBoardTile[][] getChessBoard()
    {
        return chessBoard;
    }


    /*public PlayerId executeMove(int x, int y, int x2, int y2)
    {
        //Queue movesTemp1 = chessBoard[x][y].getPiece().getPossibleMoves();
        Queue movesTemp[] = new Queue[2];
        movesTemp[1] = chessBoard[x][y].removeAllTargetingMoves();
        movesTemp[2] = chessBoard[x2][y2].removeAllTargetingMoves();
        //chessBoard[x][y].getPiece().removePossibleMove(x,y,x2,y2);
        chessBoard[x2][y2].setPiece(chessBoard[x][y].removePiece());
        CheckMoves(x2,y2);
        for(int i = 0; i<2; i++)
        while(!movesTemp[i].isEmpty())
        {
        CheckMoves(movesTemp[i].remove().get_content().get_xStart(),movesTemp[i].remove().get_content().get_yStart());
        }
        return null;
    }*/

    public PlayerId executeMove(ChessMove move)
    {
        Queue movesTemp[] = new Queue[2];
        ChessMove moveTemp;
        int i = 0;

        if (move.getEvent().getID() == EventID.Capture || move.getEvent().getID() == EventID.Move)
        {
            chessBoard[move.get_xStart()][move.get_yStart()].getPiece().setFirstMove(false);
            while (!chessBoard[move.get_xStart()][move.get_yStart()].getPiece().hasPossibleMove())          // if the piece has possible moves, remove them in targeting moves
            {
                if (chessBoard[move.get_xStart()][move.get_yStart()].getPiece().hasPossibleMove())
                {
                    moveTemp = (ChessMove) chessBoard[move.get_xStart()][move.get_yStart()].getPiece().removePossibleMove();
                    chessBoard[moveTemp.get_xTarget()][moveTemp.get_yTarget()].removeTargetingMove(moveTemp);
                }
            }
            if (move.getEvent().getID() == EventID.Capture)                                               // if the piece has been captured, remove the Piece on the targeted tile
            {
                deadPieces.add(chessBoard[move.get_xTarget()][move.get_yTarget()].removePiece());
                chessBoard[move.get_xTarget()][move.get_yTarget()].setPiece(chessBoard[move.get_xStart()][move.get_yStart()].removePiece());
            }
            else                                                                                          // if the piece has been moved, remove from start tile and add to target tile
            {
                chessBoard[move.get_xTarget()][move.get_yTarget()].setPiece(chessBoard[move.get_xStart()][move.get_yStart()].removePiece());
            }

            CheckMoves(move.get_xTarget(), move.get_yTarget());

            if(!chessBoard[move.get_xStart()][move.get_yStart()].hasTargetingMoves())                          // if the piece has possible moves, remove them in targeting moves, in order to update the possible moves
            {
                movesTemp[i] = chessBoard[move.get_xStart()][move.get_yStart()].removeAllTargetingMoves();
                i++;
            }
            if(!chessBoard[move.get_xTarget()][move.get_yTarget()].hasTargetingMoves())                          // if the piece has possible moves, remove them in targeting moves, in order to update the possible moves
            {
                movesTemp[i] = chessBoard[move.get_xTarget()][move.get_yTarget()].removeAllTargetingMoves();
            }

            for(int j = i; i<=0; j--)
            {
                while (!movesTemp[j].isEmpty())
                {
                    moveTemp = (ChessMove) movesTemp[j].remove();
                    chessBoard[moveTemp.get_xStart()][moveTemp.get_yStart()].getPiece().removeAllPossibleMoves();
                    CheckMoves(moveTemp.get_xStart(), moveTemp.get_yStart());
                }
            }
            return player.opposite();
        }

        return player;
    }


}

