package chess.core;


class Main
{
    public static void main(String[]args)
    {
        ChessMechanics chessMechanics = new ChessMechanics();
        chessMechanics.getAllMoves();
        chessMechanics.executeMove(2,2,3,4);
    }

}
