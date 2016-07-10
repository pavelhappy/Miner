package Minesweeper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Minefield extends JFrame implements GraphicsInterface {

    Map<Integer, Image> imgName = new HashMap<Integer, Image>();
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

    public static final int ROWS = 6;
    public static final int COLS = 6;
    public static final int IMAGE_SIZE = 48;
    public static final int PADDING = 0;
    public static final int CELL_SIZE = IMAGE_SIZE + 2 * PADDING;
    public static final int CANVAS_SIZEX = CELL_SIZE * COLS;
    public static final int CANVAS_SIZEY = CELL_SIZE * ROWS;
    public static final int BOMBS = 7;
    public static int mines[][] = new int[ROWS][COLS];
    public static int tile[][] = new int[ROWS][COLS];
    public int rowSelected;
    public int colSelected;

    GameLogic logic = new GameLogic();
    private DrawCanvas canvas;


    public Minefield()
    {
        createImagesMap();
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_SIZEX, CANVAS_SIZEY));
        logic.StartGame(ROWS, COLS, tile, mines, BOMBS);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                rowSelected = mouseY / CELL_SIZE;
                colSelected = mouseX / CELL_SIZE;
                logic.openCell(rowSelected,colSelected);
                repaint();
            }
        });

        setContentPane(canvas);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Minesweeper");
        setVisible(true);
    }


    @Override
    public Image getImage(String filename)
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

    @Override
    public void createImagesMap()
    {
        imgName.put(0,getImage(emptyCellFilename));
        imgName.put(1,getImage(numberOneFilename));
        imgName.put(2,getImage(numberTwoFilename));
        imgName.put(3,getImage(numberThreeFilename));
        imgName.put(4,getImage(numberFourFilename));
        imgName.put(5,getImage(numberFiveFilename));
        imgName.put(6,getImage(numberSixFilename));
        imgName.put(7,getImage(numberSevenFilename));
        imgName.put(8,getImage(numberEightFilename));
        imgName.put(9,getImage(mineCellFilename));
        imgName.put(10,getImage(closedCellFilename));
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
                    g.drawImage(imgName.get(logic.tile[row][col])  ,
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

}
