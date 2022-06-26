package chess.core;


import chess.core.common.Vec;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


public class ChessMechanics implements Cloneable
{
    private ChessBoardTile[][] chessBoard; // the chess board
    private ArrayList<ChessPiece> deadPieces = new ArrayList<ChessPiece>();   // the dead pieces
    private int targetTurn = 0;// the targeted turn of simulation
    private PlayerId player = PlayerId.WHITE; // the current player
    private ArrayList<ChessMove> madeMoves = new ArrayList<ChessMove>();               // the moves made


    private ChessBoardTile[][] initialBoard;

    public ChessMechanics(ChessBoardTile[][] chessBoard, ArrayList<ChessPiece> deadPieces, int targetTurn, PlayerId player, ChessMove move)
    {

       /* for(int i = madeMoves.getNumberOfElements(); i > 0;i--)//TODO: Possible bug.!!!
        {
            this.madeMoves.add(madeMoves.getByIndex(i));
        }*/
        for (int i = deadPieces.size() - 1; i > 0; i--)//TODO: Possible bug.!!!
        {
            this.deadPieces.add(deadPieces.get(i));
        }
        //this.deadPieces = deadPieces;//TODO make a deep copy
        this.targetTurn = targetTurn;
        this.player = player;

        cloneBoard(chessBoard);
        executeMove(move);
    }



    public ChessMechanics()
    {
        makeBoard();
        StartPosition();
        //initialBoard = cloneBoard(chessBoard);
        //targetTurn--;
    }


    public void getAllMoves()
    {
        //removeAllMoves();
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (chessBoard[i][j].hasPiece())
                {
                    CheckMoves(i, j);
                }
            }
        }
    }

    public void removeAllMoves()
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

    public void CheckMoves(int x, int y)
    {
        switch (chessBoard[x][y].getPiece().getChessPieceId())                                                                      // switch statement for the piece
        {
            case Pawn ->
            {
                RulesPawn(x, y);
            }
            case Tower ->
            {
                RulesTower(x, y);
            }
            case Bishop ->
            {
                RulesBishop(x, y);
            }
            case Horse ->
            {
                RulesHorse(x, y);
            }
            case King ->
            {
                RulesKing(x, y);
            }
            case Queen ->
            {
                RulesQueen(x, y);
            }
        }
    }

    @Nullable
    ChessMove checkFinalizeTemplate(ChessMove input, boolean areCapturesRequired)
    {
        var p = chessBoard[input.xStart][input.xTarget].getPiece();
        if (p == null)
        {
            return null;
        }

        throw new UnsupportedOperationException("Not implemented yet");
    }

    void generateImmediateMoves()
    {
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                var piecePos = new Vec(col, row);
                if (chessBoard[row][col].hasPiece())
                {
                    var piece = chessBoard[row][col].getPiece();

                    //region PAWN
                    if (piece.getChessPieceId() == ChessPieceId.Pawn)
                    {

                    }
                    //endregion


                }
            }
        }
    }

    public boolean RulesPawn(int x, int y)
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
                    } else if (chessBoard[x + (a * 2)][y].hasPiece())
                    {
                        move = new ChessMove(x, y, x + (a * 2), y, chessBoard[x][y].getPlayerId(), EventID.Blocked);                               // if the tile is empty
                        chessBoard[x][y].getPiece().addPossibleMove(move);
                        chessBoard[x + (a * 2)][y].addTargetingMove(move);
                    }
                }

            } else if (chessBoard[x + a][y].hasPiece())
            {
                ChessMove move = new ChessMove(x, y, x + a, y, chessBoard[x][y].getPlayerId(), EventID.Blocked);                               // if the tile is empty
                chessBoard[x][y].getPiece().addPossibleMove(move);
                chessBoard[x + a][y].addTargetingMove(move);
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
                    } else
                    {
                        ChessMove move;
                        move = new ChessMove(x, y, x + a, y + 1, chessBoard[x][y].getPlayerId(), EventID.Blocked);
                        chessBoard[x][y].getPiece().addPossibleMove(move);
                        chessBoard[x + a][y + 1].addTargetingMove(move);
                    }
                } else
                {
                    ChessMove move;
                    move = new ChessMove(x, y, x + a, y + 1, chessBoard[x][y].getPlayerId(), EventID.Blocked);
                    chessBoard[x][y].getPiece().addPossibleMove(move);
                    chessBoard[x + a][y + 1].addTargetingMove(move);
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
                    } else
                    {
                        ChessMove move;
                        move = new ChessMove(x, y, x + a, y - 1, chessBoard[x][y].getPlayerId(), EventID.Blocked);
                        chessBoard[x][y].getPiece().addPossibleMove(move);
                        chessBoard[x + a][y - 1].addTargetingMove(move);
                    }
                } else
                {
                    ChessMove move;
                    move = new ChessMove(x, y, x + a, y - 1, chessBoard[x][y].getPlayerId(), EventID.Blocked);
                    chessBoard[x][y].getPiece().addPossibleMove(move);
                    chessBoard[x + a][y - 1].addTargetingMove(move);
                }
            }
        }
        return true;
    }

    public boolean RulesTower(int x, int y)
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
        return true;
    }

    public boolean RulesBishop(int x, int y)
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
        return true;
    }

    public boolean RulesHorse(int x, int y)
    {
        TestMovePiece(x, y, x + 1, y + 2);
        TestMovePiece(x, y, x + 1, y - 2);
        TestMovePiece(x, y, x - 1, y + 2);
        TestMovePiece(x, y, x - 1, y - 2);
        TestMovePiece(x, y, x + 2, y + 1);
        TestMovePiece(x, y, x + 2, y - 1);
        TestMovePiece(x, y, x - 2, y + 1);
        TestMovePiece(x, y, x - 2, y - 1);
        return true;
    }

    public boolean RulesQueen(int x, int y)
    {
        RulesBishop(x, y);
        RulesTower(x, y);
        return true;
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
                    ChessMove move = new ChessMove(x,y,x2,y2,chessBoard[x][y].getPlayerId(), EventID.Capture);
                    chessBoard[x][y].getPiece().addPossibleMove(move);
                    chessBoard[x2][y2].addTargetingMove(move);
                }
            }

            else if(chessBoard[x2][y2].getPiece().getPlayerId() == chessBoard[x][y].getPiece().getPlayerId()){
                ChessMove move = new ChessMove(x,y,x2,y2,chessBoard[x][y].getPlayerId(), EventID.Blocked);
            }
        }*/
    }

    public ArrayList<ChessMechanics> blackKingSimulations = new ArrayList<ChessMechanics>();



    public boolean TestMovePiece(int x, int y, int x2, int y2)
    {
        if (x2 < 0 | x2 > 7 | y2 < 0 | y2 > 7)
        {
            return false;
        } else if (!chessBoard[x2][y2].hasPiece())// if the tile is empty
        {
            ChessMove move = new ChessMove(x, y, x2, y2, chessBoard[x][y].getPlayerId(), EventID.Move);


            if (targetTurn == 0)
            {
                // Check legality
                ChessMechanics test = new ChessMechanics(chessBoard, deadPieces, madeMoves.size() + 1, player, move);
                boolean isBlackKing = chessBoard[x][y].getPiece().getPlayerId().isBlack() && chessBoard[x][y].getPiece().getChessPieceId() == ChessPieceId.King;
                blackKingSimulations.add(test);
                if (test.isLegal(player))
                {
                    chessBoard[x][y].getPiece().addPossibleMove(move);
                    chessBoard[x2][y2].addTargetingMove(move);
                    return true;
                } else return false;
            } else
            {
                chessBoard[x][y].getPiece().addPossibleMove(move);
                chessBoard[x2][y2].addTargetingMove(move);
                return true;
            }

        } else if (chessBoard[x2][y2].getPlayerId() == chessBoard[x][y].getPlayerId().opposite())           // if the tile is occupied by an enemy piece
        {
            ChessMove move = new ChessMove(x, y, x2, y2, chessBoard[x][y].getPlayerId(), EventID.Capture);


            if (targetTurn == 0)
            {
                ChessMechanics test = new ChessMechanics(chessBoard, deadPieces, madeMoves.size() + 1, player, move);
                blackKingSimulations.add(test);

                if (test.isLegal(player))
                {
                    chessBoard[x][y].getPiece().addPossibleMove(move);
                    chessBoard[x2][y2].addTargetingMove(move);
                    return false;
                } else return false;
            } else
            {
                chessBoard[x][y].getPiece().addPossibleMove(move);
                chessBoard[x2][y2].addTargetingMove(move);
                return false;
            }

        } else if (chessBoard[x2][y2].getPlayerId() == chessBoard[x][y].getPlayerId())                      // if the tile is occupied by a friendly piece
        {
            ChessMove move = new ChessMove(x, y, x2, y2, chessBoard[x][y].getPlayerId(), EventID.Blocked);
            chessBoard[x][y].getPiece().addPossibleMove(move);
            chessBoard[x2][y2].addTargetingMove(move);
            return false;
        }
        return false;
    }


    public void StartPosition()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                PlayerId player = i > 2 ? PlayerId.WHITE : PlayerId.BLACK;
                if (i == 1 || i == 6)
                {
                    chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.Pawn, player));
                } else if (i == 0 || i == 7)
                {
                    switch (j)
                    {
                        case 0, 7 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.Tower, player));
                        case 1, 6 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.Horse, player));
                        case 2, 5 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.Bishop, player));
                        case 3 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.Queen, player));
                        case 4 -> chessBoard[i][j].setPiece(new ChessPiece(ChessPieceId.King, player));
                    }
                }
            }
        }
        getAllMoves();
    }

    public void makeBoard()
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

    public ChessBoardTile[][] getChessBoard()
    {
        return chessBoard;
    }


/*
    public PlayerId executeMove(int x, int y, int x2, int y2)
    {
        //ArrayList<ChessMove> movesTemp1 = chessBoard[x][y].getPiece().getPossibleMoves();
        ArrayList<ChessMove> movesTemp[] = new ArrayList[2];
        movesTemp[1] = chessBoard[x][y].removeAllTargetingMoves();
        movesTemp[2] = chessBoard[x2][y2].removeAllTargetingMoves();
        //chessBoard[x][y].getPiece().removePossibleMove(x,y,x2,y2);
        chessBoard[x2][y2].setPiece(chessBoard[x][y].removePiece());
        CheckMoves(x2,y2);
        for(int i = 0; i<2; i++)
        while(!movesTemp[i].isEmpty())
        {
        CheckMoves(movesTemp[i].remove().get_content().xStart,movesTemp[i].remove().get_content().yStart);
        }
        return null;
    }*/

    /*public void executeMove(ChessMove move)
    {
        if (move.getPlayerId() == player)
        {
            ChessMove moveTempKing = null;
            ArrayList<ChessMove> movesTemp = new ArrayList();
            ChessMove moveTemp;

        if ((move.getEvent() == EventID.Capture || move.getEvent() == EventID.Move))
        {
            chessBoard[move.xStart][move.yStart].getPiece().addMoveCount();

                while (chessBoard[move.xStart][move.yStart].getPiece().hasPossibleMove())           //because the piece is moved(capture also moves), we need to remove all possible moves
                {
                    moveTemp = (ChessMove) chessBoard[move.xStart][move.yStart].getPiece().removePossibleMove();
                    chessBoard[moveTemp.xTarget][moveTemp.yTarget].removeTargetingMove(moveTemp);
                }

                if (move.getEvent() == EventID.Capture)                                               // if the piece has been captured, remove the Piece on the targeted tile
                {
                    while (chessBoard[move.xTarget][move.yTarget].getPiece().hasPossibleMove())       //remove all possible moves from the captured piece
                    {
                        moveTemp = (ChessMove) chessBoard[move.xTarget][move.yTarget].getPiece().removePossibleMove();
                        chessBoard[moveTemp.xTarget][moveTemp.yTarget].removeTargetingMove(moveTemp);
                    }
                    deadPieces.add(chessBoard[move.xTarget][move.yTarget].removePiece());
                    chessBoard[move.xTarget][move.yTarget].setPiece(chessBoard[move.xStart][move.yStart].removePiece());
                } else                                                                           // if the piece has been moved, remove from start tile and add to target tile
                {
                    chessBoard[move.xTarget][move.yTarget].setPiece(chessBoard[move.xStart][move.yStart].removePiece());
                }


                CheckMoves(move.xTarget, move.yTarget);

                chessBoard[move.xStart][move.yStart].extractAllTargetingMoves(movesTemp);
                chessBoard[move.xTarget][move.yTarget].extractAllTargetingMoves(movesTemp);


                while (!movesTemp.isEmpty())
                {

                    moveTemp = (ChessMove) movesTemp.remove();
                    if (chessBoard[moveTemp.xStart][moveTemp.yStart].getPiece().getChessPieceId() == ChessPieceId.King && moveTemp.getPlayerId() != player)
                    {
                        moveTempKing = moveTemp;
                    } else
                    {
                        while (chessBoard[moveTemp.xStart][moveTemp.yStart].getPiece().hasPossibleMove())
                        {
                            moveTemp = (ChessMove) chessBoard[moveTemp.xStart][moveTemp.yStart].getPiece().removePossibleMove();
                            chessBoard[moveTemp.xTarget][moveTemp.yTarget].removeTargetingMove(moveTemp);
                        }
                        CheckMoves(moveTemp.xStart, moveTemp.yStart);
                    }
                }

                if (moveTempKing != null)
                {
                    chessBoard[moveTempKing.xStart][moveTempKing.yStart].getPiece().removeAllPossibleMoves();
                    CheckMoves(moveTempKing.xStart, moveTempKing.yStart);
                }
            }
            player = player.opposite();

        }
    }*/

    public void executeMove(ChessMove move)
    {
        if (move.getPlayerId() == player)
        {

            if ((move.getEvent() == EventID.Capture || move.getEvent() == EventID.Move))
            {
                chessBoard[move.xStart][move.yStart].getPiece().addMoveCount();


                if (move.getEvent() == EventID.Capture)                                               // if the piece has been captured, remove the Piece on the targeted tile
                {
                    deadPieces.add(chessBoard[move.xTarget][move.yTarget].removePiece());
                    chessBoard[move.xTarget][move.yTarget].setPiece(chessBoard[move.xStart][move.yStart].removePiece());
                } else                                                                           // if the piece has been moved, remove from start tile and add to target tile
                {
                    chessBoard[move.xTarget][move.yTarget].setPiece(chessBoard[move.xStart][move.yStart].removePiece());
                }
                madeMoves.add(move);
            }

            removeAllMoves();
            blackKingSimulations = new ArrayList<>();
            getAllMoves();

            player = player.opposite();

        }
    }

    public PlayerId reverseMove()
    {
        if (!madeMoves.isEmpty())
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

                    chessBoard[move.xStart][move.yStart].getPiece().removeMoveCount();
                }

                removeAllMoves();
                getAllMoves();

                player = player.opposite();
            }
        }

        return player;
    }



    public void cloneBoard(ChessBoardTile[][] oldBord)
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
                chessBoard[i][j].setTargetingMoves(oldBord[i][j].cloneTargetingMoves());
            }
        }
    }

    public boolean isLegal(PlayerId player)
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8 && (chessBoard[i][j].hasPiece()) && (chessBoard[i][j].getPiece().getChessPieceId() == ChessPieceId.King) && (chessBoard[i][j].getPiece().getPlayerId() == player) && (chessBoard[i][j].hasTargetingMoves()); j++)
            {
                for (int k = chessBoard[i][j].getTargetingMoves().size(); i > 0; i--)
                {
                    if (chessBoard[i][j].getTargetingMoves().get(k - 1).getEvent() != EventID.Blocked)
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
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8 && (chessBoard[i][j].hasPiece()) && (chessBoard[i][j].getPiece().getChessPieceId() == ChessPieceId.King) && (chessBoard[i][j].getPiece().getPlayerId() == player) && (chessBoard[i][j].getPiece().hasPossibleMove()); j++)
            {
                for (int k = chessBoard[i][j].getPiece().getPossibleMoves().size(); i > 0; i--)
                {
                    if (chessBoard[i][j].getTargetingMoves().get(k - 1).getEvent() != EventID.Blocked)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public PlayerId getCurrentPlayer()
    {
        return player;
    }

    @Override
    public ChessMechanics clone()
    {
        try
        {
            ChessMechanics clone = (ChessMechanics) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e)
        {
            throw new AssertionError();
        }
    }
}

