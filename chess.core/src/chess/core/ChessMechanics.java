package chess.core;

import chess.core.VerktetteListe.Queue;

import java.util.ArrayList;


public class ChessMechanics
{

    private ChessBoardTile[][] chessBoard; // the chess board
    private ArrayList<ChessPiece> deadPieces = new ArrayList<ChessPiece>() ;   // the dead pieces
    private int targetTurn = 0;// the targeted turn of simulation
    private PlayerId player = PlayerId.WHITE; // the current player
    private Queue madeMoves;               // the moves made

    public ChessMechanics(ChessBoardTile[][] chessBoard, ArrayList<ChessPiece> deadPieces, int targetTurn, PlayerId player, Queue madeMoves, ChessMove move)
    {
        this.deadPieces = deadPieces;
        this.targetTurn = targetTurn;
        this.player = player;
        this.madeMoves = madeMoves;
        cloneBoard(chessBoard);
        executeMove(move);
    }
    public ChessMechanics()
    {
        madeMoves = new Queue();
        makeBoard();
        StartPosition();
        getAllMoves();

    }

    public void getAllMoves()
    {
        removeAllMoves();
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

    public void removeAllMoves()
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

    public boolean RulesPawn(int x, int y)
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


        if(x+a >= 0 & x+a <= 7 & y-1 >= 0 & y+1 <= 7 )
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
        return true;
    }

    public boolean RulesTower(int x,int y)
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
        return true;
    }

    public boolean RulesBishop(int x, int y)
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
        return true;
    }

    public boolean RulesHorse(int x, int y)
    {
        TestMovePiece(x,y,x+1,y+2);
        TestMovePiece(x,y,x+1,y-2);
        TestMovePiece(x,y,x-1,y+2);
        TestMovePiece(x,y,x-1,y-2);
        TestMovePiece(x,y,x+2,y+1);
        TestMovePiece(x,y,x+2,y-1);
        TestMovePiece(x,y,x-2,y+1);
        TestMovePiece(x,y,x-2,y-1);
        return true;
    }

    public boolean RulesQueen(int x, int y)
    {
        RulesBishop(x,y);
        RulesTower(x,y);
        return true;
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
        /*private void testMoveKing(int x, int y, int x2, int y2)
        {
            if(x2>=0 && x2<8 && y2>=0 && y2<8)
            {
                if(!chessBoard[x2][y2].hasPiece())
                {
                    ChessMove move = new ChessMove(x,y,x2,y2,chessBoard[x][y].getPlayerId(), new Event(EventID.Move));
                    chessBoard[x][y].getPiece().addPossibleMove(move);
                    chessBoard[x2][y2].addTargetingMove(move);
                }
            }
            else if(chessBoard[x2][y2].hasPiece())
            {
                if(chessBoard[x2][y2].getPiece().getPlayerId() == chessBoard[x][y].getPiece().getPlayerId().opposite())
                {
                    ChessMove move = new ChessMove(x,y,x2,y2,chessBoard[x][y].getPlayerId(), new Event(EventID.Capture));
                    chessBoard[x][y].getPiece().addPossibleMove(move);
                    chessBoard[x2][y2].addTargetingMove(move);
                }
            }
            else if(chessBoard[x2][y2].getPiece().getPlayerId() == chessBoard[x][y].getPiece().getPlayerId()){
                ChessMove move = new ChessMove(x,y,x2,y2,chessBoard[x][y].getPlayerId(), new Event(EventID.Blocked));
            }
        }*/
    }

    public boolean TestMovePiece(int x, int y, int x2, int y2)
    {
        if(x2<0 | x2>7 | y2<0 | y2>7)
        {
            return false;
        }
         else if(!chessBoard[x2][y2].hasPiece())// if the tile is empty
        {
            ChessMove move = new ChessMove(x,y,x2,y2,chessBoard[x][y].getPlayerId(),new Event(EventID.Move));

            if(chessBoard[x][y].getPlayerId() == player)
            {
                if(targetTurn == 0)
                {
                    ChessMechanics test = new ChessMechanics(chessBoard, deadPieces,madeMoves.getNumberOfElements()+1 , player, madeMoves, move);
                }
                else if(targetTurn <= madeMoves.getNumberOfElements())
                {
                    ChessMechanics test = new ChessMechanics(chessBoard, deadPieces, targetTurn, player, madeMoves, move);
                    if(test.isLegal(player.opposite()))
                    {
                        chessBoard[x][y].getPiece().addPossibleMove(move);
                        chessBoard[x2][y2].addTargetingMove(move);
                    }
                    else return false;
                }
            }

            return true;
        }
        else if(chessBoard[x2][y2].getPlayerId() == chessBoard[x][y].getPlayerId().opposite())           // if the tile is occupied by an enemy piece
        {
            ChessMove move = new ChessMove(x,y,x2,y2,chessBoard[x][y].getPlayerId(),new Event(EventID.Capture));

            if(chessBoard[x][y].getPlayerId() == player)
            {
                if(targetTurn == 0)
                {
                    ChessMechanics test = new ChessMechanics(chessBoard, deadPieces,madeMoves.getNumberOfElements()+1 , player, madeMoves, move);
                }
                else if(targetTurn <= madeMoves.getNumberOfElements())
                {
                    ChessMechanics test = new ChessMechanics(chessBoard, deadPieces, targetTurn, player, madeMoves, move);
                    if(test.isLegal(player.opposite()))
                    {
                        chessBoard[x][y].getPiece().addPossibleMove(move);
                        chessBoard[x2][y2].addTargetingMove(move);
                    }
                    else return false;
                }
            }
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
                    chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.PAWN,player));
                }

                else if(i == 0 || i == 7)
                {
                    switch (j) {
                        case 0, 7 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.TOWER,player));
                        case 1, 6 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.HORSE,player));
                        case 2, 5 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.BISHOP,player));
                        case 3 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.QUEEN,player));
                        case 4 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.KING,player));
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


/*
    public PlayerId executeMove(int x, int y, int x2, int y2)
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

    public void executeMove(ChessMove move)
    {
        ChessMove moveTempKing ;
        Queue movesTemp[] = new Queue[2];
        ChessMove moveTemp;
        int i = 0;

        if (move != null && (move.getEvent().getID() == EventID.Capture || move.getEvent().getID() == EventID.Move))
        {
            chessBoard[move.get_xStart()][move.get_yStart()].getPiece().addMoveCount();
            while (chessBoard[move.get_xStart()][move.get_yStart()].getPiece().hasPossibleMove())
            {
                moveTemp = (ChessMove) chessBoard[move.get_xStart()][move.get_yStart()].getPiece().removePossibleMove();
                chessBoard[moveTemp.get_xTarget()][moveTemp.get_yTarget()].removeTargetingMove(moveTemp);
            }

            if (move.getEvent().getID() == EventID.Capture)                                               // if the piece has been captured, remove the Piece on the targeted tile
            {
                while (chessBoard[move.get_xTarget()][move.get_yTarget()].getPiece().hasPossibleMove())       //remove all possible moves from the captured piece
                {
                    moveTemp = (ChessMove) chessBoard[move.get_xStart()][move.get_yStart()].getPiece().removePossibleMove();
                    chessBoard[moveTemp.get_xTarget()][moveTemp.get_yTarget()].removeTargetingMove(moveTemp);
                }
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

            for(int j = i; j>0; j--)
            {
                while (!movesTemp[j].isEmpty())
                {

                    moveTemp = (ChessMove) movesTemp[j].remove();
                    if(chessBoard[moveTemp.get_xStart()][moveTemp.get_yStart()].getPiece().getChessPieceId() == ChessPieceId.KING && moveTemp.getPlayerId() == player)
                    {
                        moveTempKing = moveTemp;
                    }
                    else
                    {
                    chessBoard[moveTemp.get_xStart()][moveTemp.get_yStart()].getPiece().removeAllPossibleMoves();
                    CheckMoves(moveTemp.get_xStart(), moveTemp.get_yStart());
                    }
                }
            }
            player = player.opposite();
        }

    }

    public PlayerId reverseMove()
    {
        if(!madeMoves.isEmpty())
        {
        ChessMove move = (ChessMove) madeMoves.remove();
        Queue movesTemp = new Queue();
        ChessMove moveTemp;
        int i = 0;

        if (move.getEvent().getID() == EventID.Capture || move.getEvent().getID() == EventID.Move)
        {
            chessBoard[move.get_xTarget()][move.get_yTarget()].getPiece().removeMoveCount();
            while (!chessBoard[move.get_xTarget()][move.get_yTarget()].getPiece().hasPossibleMove())          // if the piece has possible moves, remove them in targeting moves
            {
                if (chessBoard[move.get_xTarget()][move.get_yTarget()].getPiece().hasPossibleMove())
                {
                    moveTemp = (ChessMove) chessBoard[move.get_xTarget()][move.get_yTarget()].getPiece().removePossibleMove();
                    chessBoard[moveTemp.get_xTarget()][moveTemp.get_yTarget()].removeTargetingMove(moveTemp);
                }
            }
            if (move.getEvent().getID() == EventID.Capture)                                               // if the piece has been captured, remove the Piece on the targeted tile
            {
                chessBoard[move.get_xStart()][move.get_yStart()].setPiece(chessBoard[move.get_xTarget()][move.get_yTarget()].removePiece());
                chessBoard[move.get_xTarget()][move.get_yTarget()].setPiece(deadPieces.get(deadPieces.size() - 1));
                deadPieces.remove(deadPieces.size() - 1);
            } else                                                                                          // if the piece has been moved, remove from start tile and add to target tile
            {
                chessBoard[move.get_xStart()][move.get_yStart()].setPiece(chessBoard[move.get_xTarget()][move.get_yTarget()].removePiece());
            }

            CheckMoves(move.get_xStart(), move.get_yStart());
            CheckMoves(move.get_xTarget(), move.get_yTarget());


            if (!chessBoard[move.get_xStart()][move.get_yStart()].hasTargetingMoves())                          // if the piece has possible moves, remove them in targeting moves, in order to update the possible moves
            {
                movesTemp = chessBoard[move.get_xStart()][move.get_yStart()].removeAllTargetingMoves();
                while (!movesTemp.isEmpty())
                {
                    moveTemp = (ChessMove) movesTemp.remove();
                    chessBoard[moveTemp.get_xStart()][moveTemp.get_yStart()].getPiece().removeAllPossibleMoves();
                    CheckMoves(moveTemp.get_xStart(), moveTemp.get_yStart());
                }
            }
            if (!chessBoard[move.get_xTarget()][move.get_yTarget()].hasTargetingMoves())                          // if the piece has possible moves, remove them in targeting moves, in order to update the possible moves
            {
                movesTemp = chessBoard[move.get_xTarget()][move.get_yTarget()].removeAllTargetingMoves();
                while (!movesTemp.isEmpty())
                {
                    moveTemp = (ChessMove) movesTemp.remove();
                    chessBoard[moveTemp.get_xStart()][moveTemp.get_yStart()].getPiece().removeAllPossibleMoves();
                    CheckMoves(moveTemp.get_xStart(), moveTemp.get_yStart());
                }
            }

            return player.opposite();
        }
        }

        return player;
    }

    public void cloneBoard(ChessBoardTile[][] oldBord)
    {
        makeBoard();
        for(int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8 ; j++)
            {
                if(oldBord[i][j].hasPiece())
                {
                chessBoard[i][j].setPiece(new ChessPiece(oldBord[i][j].getPiece().getChessPieceId(), oldBord[i][j].getPiece().getPlayerId(), oldBord[i][j].getPiece().getMoveCount()));
                }
                chessBoard[i][j].setTargetingMoves(oldBord[i][j].cloneTargetingMoves());
            }

        }

    }

    public boolean isLegal(PlayerId player)
    {
        for(int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8 && (chessBoard[i][j].hasPiece()) && (chessBoard[i][j].getPiece().getChessPieceId() == ChessPieceId.KING) && (chessBoard[i][j].getPiece().getPlayerId() == player) &&  (chessBoard[i][j].hasTargetingMoves()) ; j++)
            {
                for(int k = chessBoard[i][j].getTargetingMoves().getNumberOfElements(); i > 0;i--)
                {
                        if (chessBoard[i][j].getTargetingMoves().getByIndex(k).getEvent().getID() != EventID.Blocked)
                        {
                            return false;
                        }

                }
            }
        }
        return true;
    }

    public boolean checkMate()
    {
        for(int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8 && (chessBoard[i][j].hasPiece()) && (chessBoard[i][j].getPiece().getChessPieceId() == ChessPieceId.KING) && (chessBoard[i][j].getPiece().getPlayerId() == player) &&  (chessBoard[i][j].getPiece().hasPossibleMove()) ; j++)
            {
                for(int k = chessBoard[i][j].getPiece().getPossibleMoves().getNumberOfElements(); i > 0;i--)
                {
                    if (chessBoard[i][j].getTargetingMoves().getByIndex(k).getEvent().getID() != EventID.Blocked)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

