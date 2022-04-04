import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        JFrame f = new JFrame("Game");
        ChessBoard b = new ChessBoard();
        f.getContentPane().add(b);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 800);
        f.pack();
        f.setVisible(true);

    }
}
