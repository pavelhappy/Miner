package Minesweeper;

import java.util.Random;
import java.awt.event.MouseEvent;

public class GameLogic implements LogicInterface{
    int ROWS;
    int COLS;
    int mines[][];
    int tile[][];
    int BOMBS=0;
    int clickCount = 0;
    static Random ranrow = new Random();
    static Random rancol = new Random();
    boolean gameOver = false;

    @Override
    public void StartGame(int ROWS, int COLS, int mines[][], int tile[][], int BOMBS)
    {
        this.ROWS = ROWS;
        this.COLS = COLS;
        this.tile = tile;
        this.mines = mines;
        this.BOMBS = BOMBS;
        putBombs(BOMBS);
        for (int i = 1; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (mines[i][j] !=9)
                {
                    mines[i][j] = mines(i,j);
                }
            }
        }

        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                tile[i][j]=10;
            }
        }
    }
    @Override
    public void putBombs(int BOMBS)
    {
        this.BOMBS = BOMBS;
        for (int i = 0; i < BOMBS;) {
            int row = ranrow.nextInt(ROWS);
            int col = rancol.nextInt(COLS);

            if (mines[row][col]!=9)
            {
                mines[row][col] = 9;
                i++;
            }
        }
    }

    @Override
    public int mines(int row, int col) {
        int k=0;
        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                if(((col+x >= 0) & (col+x < COLS)) & ((row+y >= 0) & (row+y < ROWS))) {
                    if(mines[row+y][col+x] == 9) k++;
                }
            }
        }
        return k;
    }

    public void empty(int row, int col)
    {
        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                int newRow = row + y;
                int newCol = col + x;

                    if (((newCol >= 0) & (newCol < COLS)) & ((newRow >= 0) & (newRow < ROWS))) {
                        int num = mines(newRow, newCol);
                        if (tile[newRow][newCol] != 0) {
                        if (mines[newRow][newCol] == 0) {
                            tile[newRow][newCol] = 0;
                            empty(newRow, newCol);
                        }
                            if ((num>=1) && (num<=8))
                            {
                                tile[newRow][newCol] = num;
                            }
                    }
                }
            }
        }
    }

    @Override
    public void openCell(int row, int col, int key)
    {
        int k = tile[row][col];
        if(key == MouseEvent.BUTTON3) {
            if(k == 10) {
                tile[row][col] = 11;
            } else if(k == 11) {
                tile[row][col] = 10;
            }
            return;
        }


        if (gameOver)
        {
            StartGame(ROWS, COLS, mines,  tile, 0);
            gameOver = false;
            clickCount = 0;
            ranrow = new Random();
            rancol = new Random();
            return;
        }
        int n=mines[row][col];
        if ((n==9)&& (clickCount == 0)) {
            tile[row][col]=0;
            mines[row][col]=0;
            for (int i = 0; i < 1;) {
                int randRow = ranrow.nextInt(ROWS);
                int randCol = rancol.nextInt(COLS);
                if (mines[randRow][randCol]!=9)
                {
                    mines[randRow][randCol] = 9;
                    i++;
                }
            }
        }
        else if (n ==9)
        {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if (mines[i][j] == 9)
                    {
                        tile[i][j]=9;
                    }
                }
            }
            clickCount++;
            gameOver = true;
        }
        else if (n==0)
        {
            empty(row, col);
            clickCount++;
        }
        else
        {
            clickCount++;
        }
        tile[row][col]=n;

    }

}
