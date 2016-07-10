package Minesweeper;
import java.awt.*;

public interface GraphicsInterface {
    void createImagesMap();
    /*
    0 - empty cell
    9 - mine cell
    10 - closed cell
    1-8 - number cell
    */

    Image getImage(String filename);

}
