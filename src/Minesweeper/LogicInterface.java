package Minesweeper;

public interface LogicInterface {
    void StartGame(int ROWS, int COLS, int mines[][], int tile[][], int BOMBS);
    void putBombs(int BOMBS);
    int mines(int row, int col);
    void openCell(int row, int col);

}
