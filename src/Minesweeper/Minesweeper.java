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

    private DrawCanvas canvas;
    private Random random = new Random();

    private String closedCellFilename = "Minesweeper/Resources/closed.png";
    private String emptyCellFilename = "Minesweeper/Resources/empty.png";
    private String mineCellFilename = "Minesweeper/Resources/mine.png";


    private Image imgClosedCell;
    private Image imgEmptyCell;
    private Image imgMineCell;

    public int tile[] = new int[ROWS*COLS];
    public int rowSelected;
    public int colSelected;

    public Minesweeper()
    {
        ImageIcon iconClosed = null;
        ImageIcon iconEmpty = null;
        ImageIcon iconMine = null;

        for (int i = 0; i < tile.length; i++)
        {

        }
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
        tile[20]=1;
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_SIZEX, CANVAS_SIZEY));

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                // Get the row and column clicked
                rowSelected = mouseY / CELL_SIZE;
                colSelected = mouseX / CELL_SIZE;
                repaint();

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

            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if ((tile[rowSelected*COLS+colSelected]==1)&&((rowSelected*COLS+colSelected)==(row*COLS+col)))
                    {
                        g.drawImage(imgMineCell,
                                CELL_SIZE * colSelected + PADDING, CELL_SIZE * rowSelected + PADDING,
                                IMAGE_SIZE, IMAGE_SIZE, null);
                    }
                    else
                    {

                        g.drawImage(imgClosedCell,
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

        return;
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
