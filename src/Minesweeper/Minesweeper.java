package Minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.*;
import java.util.Random;

public class Minesweeper extends JFrame
{
    public static final int ROWS = 6;
    public static final int COLS = 6;
    public static final int IMAGE_SIZE = 48;
    public static final int PADDING = 0;
    public static final int CELL_SIZE = IMAGE_SIZE + 2 * PADDING;
    public static final int CANVAS_SIZEX = CELL_SIZE * COLS;
    public static final int CANVAS_SIZEY = CELL_SIZE * ROWS;
    public static final int BOMBS = 7;

    private DrawCanvas canvas;
    Random ranrow = new Random();
    Random rancol = new Random();

    private final static String closedCellFilename = "Minesweeper/Resources/closed.png";
    private final static String emptyCellFilename = "Minesweeper/Resources/empty.png";
    private final static String mineCellFilename = "Minesweeper/Resources/mine.png";
    private final static String numberOneFilename = "Minesweeper/Resources/1.png";
    private final static String numberTwoFilename = "Minesweeper/Resources/2.png";
    private final static String numberThreeFilename = "Minesweeper/Resources/3.png";
    private final static String numberFourFilename = "Minesweeper/Resources/4.png";
    private final static String numberFiveFilename = "Minesweeper/Resources/5.png";
    private final static String numberSixFilename = "Minesweeper/Resources/6.png";
    private final static String numberSevenFilename = "Minesweeper/Resources/7.png";
    private final static String numberEightFilename = "Minesweeper/Resources/8.png";


    private Image imgClosedCell;
    private Image imgEmptyCell;
    private Image imgMineCell;
    private Image imgNumberOne;
    private Image imgNumberTwo;
    private Image imgNumberThree;
    private Image imgNumberFour;
    private Image imgNumberFive;
    private Image imgNumberSix;
    private Image imgNumberSeven;
    private Image imgNumberEight;

    public int mines[][] = new int[ROWS][COLS];
    public int tile[][] = new int[ROWS][COLS];
    public int rowSelected;
    public int colSelected;
    

    public Minesweeper()
    {
        ImageIcon iconClosed = null;
        ImageIcon iconEmpty = null;
        ImageIcon iconMine = null;
        ImageIcon iconNumberOne = null;
        ImageIcon iconNumberTwo = null;
        ImageIcon iconNumberThree = null;
        ImageIcon iconNumberFour = null;
        ImageIcon iconNumberFive = null;
        ImageIcon iconNumberSix = null;
        ImageIcon iconNumberSeven = null;
        ImageIcon iconNumberEight = null;


        URL imgURL = getClass().getClassLoader().getResource(closedCellFilename);
        if (imgURL != null) {
            iconClosed = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + closedCellFilename);
        }
        imgClosedCell = iconClosed.getImage();

        imgURL = getClass().getClassLoader().getResource(emptyCellFilename);
        if (imgURL != null) {
            iconEmpty = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + emptyCellFilename);
        }
        imgEmptyCell = iconEmpty.getImage();

        imgURL = getClass().getClassLoader().getResource(mineCellFilename);
        if (imgURL != null) {
            iconMine = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + mineCellFilename);
        }
        imgMineCell = iconMine.getImage();

        imgURL = getClass().getClassLoader().getResource(numberOneFilename);
        if (imgURL != null) {
            iconNumberOne = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + numberOneFilename);
        }
        imgNumberOne = iconNumberOne.getImage();

        imgURL = getClass().getClassLoader().getResource(numberTwoFilename);
        if (imgURL != null) {
            iconNumberTwo = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + numberTwoFilename);
        }
        imgNumberTwo = iconNumberTwo.getImage();

        imgURL = getClass().getClassLoader().getResource(numberThreeFilename);
        if (imgURL != null) {
            iconNumberThree = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + numberThreeFilename);
        }
        imgNumberThree = iconNumberThree.getImage();

        imgURL = getClass().getClassLoader().getResource(numberFourFilename);
        if (imgURL != null) {
            iconNumberFour = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + numberFourFilename);
        }
        imgNumberFour = iconNumberFour.getImage();

        imgURL = getClass().getClassLoader().getResource(numberFiveFilename);
        if (imgURL != null) {
            iconNumberFive = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + numberFiveFilename);
        }
        imgNumberFive = iconNumberFive.getImage();

        imgURL = getClass().getClassLoader().getResource(numberSixFilename);
        if (imgURL != null) {
            iconNumberSix = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + numberSixFilename);
        }
        imgNumberSix = iconNumberSix.getImage();

        imgURL = getClass().getClassLoader().getResource(numberSevenFilename);
        if (imgURL != null) {
            iconNumberSeven = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + numberSevenFilename);
        }
        imgNumberSeven = iconNumberSeven.getImage();

        imgURL = getClass().getClassLoader().getResource(numberEightFilename);
        if (imgURL != null) {
            iconNumberEight = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + numberEightFilename);
        }
        imgNumberEight = iconNumberEight.getImage();

        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_SIZEX, CANVAS_SIZEY));
        StartGame();
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                rowSelected = mouseY / CELL_SIZE;
                colSelected = mouseX / CELL_SIZE;
                Logic(rowSelected, colSelected);

            }
        });

        setContentPane(canvas);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Minesweeper");
        setVisible(true);

    }

    private class DrawCanvas extends JPanel
    {
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            setBackground(Color.WHITE);

            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (tile[row][col]==10)
                    {
                        g.drawImage(imgClosedCell,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }
                    else if (tile[row][col]==9)
                    {
                        g.drawImage(imgMineCell,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }
                    else if (tile[row][col]==8)
                    {
                        g.drawImage(imgNumberEight,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }
                    else if (tile[row][col]==7)
                    {
                        g.drawImage(imgNumberSeven,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }
                    else if (tile[row][col]==6)
                    {
                        g.drawImage(imgNumberSix,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }
                    else if (tile[row][col]==5)
                    {
                        g.drawImage(imgNumberFive,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }
                    else if (tile[row][col]==4)
                    {
                        g.drawImage(imgNumberFour,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }
                    else if (tile[row][col]==3)
                    {
                        g.drawImage(imgNumberThree,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }
                    else if (tile[row][col]==2)
                    {
                        g.drawImage(imgNumberTwo,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }
                    else if (tile[row][col]==1)
                    {
                        g.drawImage(imgNumberOne,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }

                    else if (tile[row][col]==0)
                    {
                        g.drawImage(imgEmptyCell,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }

                }
            }

            for (int i = 1; i < COLS; i++) {
                g.fill3DRect(CELL_SIZE*i - 2, 0, 4, CELL_SIZE * ROWS, true);
            }
            for (int i = 0; i < ROWS; i++) {
                g.fill3DRect(0, CELL_SIZE*i - 2, CELL_SIZE * COLS, 4, true);
            }
        }
    }

    public void Logic(int row, int col)
    {
        int n=mines[row][col];
        if (n ==9)
        {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if (mines[i][j] == 9)
                    {
                        tile[i][j]=9;
                    }
                }
            }
        }
        tile[row][col]=n;
        repaint();
    }
    private void putBombs()
    {

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
    private int mines(int row, int col) {
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

    public void StartGame()
    {
        putBombs();
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new Minesweeper();
            }
        });
    }

}
