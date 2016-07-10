package Minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import java.util.Map;
import java.util.HashMap;
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
    Map<Integer, Image> imgName = new HashMap <Integer, Image>();


    public Minesweeper()
    {
        imgClosedCell = getImage(closedCellFilename);
        imgEmptyCell = getImage(emptyCellFilename);
        imgMineCell = getImage(mineCellFilename);
        imgNumberOne = getImage(numberOneFilename);
        imgNumberTwo = getImage(numberTwoFilename);
        imgNumberThree = getImage(numberThreeFilename);
        imgNumberFour = getImage(numberFourFilename);
        imgNumberFive = getImage(numberFiveFilename);
        imgNumberSix = getImage(numberSixFilename);
        imgNumberSeven = getImage(numberSevenFilename);
        imgNumberEight = getImage(numberEightFilename);
        createImagesMap();
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
    public void createImagesMap()
    {
        imgName.put(0,imgEmptyCell);
        imgName.put(1,imgNumberOne);
        imgName.put(2,imgNumberTwo);
        imgName.put(3,imgNumberThree);
        imgName.put(4,imgNumberFour);
        imgName.put(5,imgNumberFive);
        imgName.put(6,imgNumberSix);
        imgName.put(7,imgNumberSeven);
        imgName.put(8,imgNumberEight);
        imgName.put(9,imgMineCell);
        imgName.put(10,imgClosedCell);
    }
    private Image getImage(String filename)
    {
        URL imgURL = getClass().getClassLoader().getResource(filename);
        Image image = null;
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            image = icon.getImage();
        } else {
            System.err.println("Couldn't find file: " + filename);
        }
        return image;
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
                        g.drawImage(imgName.get(tile[row][col])  ,
                                CELL_SIZE * col + PADDING, CELL_SIZE * row + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
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
