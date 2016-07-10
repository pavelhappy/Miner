package Minesweeper;

import javax.swing.*;


public class Minesweeper
{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new Minefield();
            }
        });
    }

}
