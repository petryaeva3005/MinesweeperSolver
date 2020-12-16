import java.util.ArrayList;
import java.util.List;

public class Cell {
    int x;
    int y;
    int neighboursMines;
    boolean hasMine;
    boolean opened;
    boolean flagged;
    Double probability;

    public Cell(int x, int y, int neighboursMines) {
        this.x = x;
        this.y = y;
        this.neighboursMines = neighboursMines;
        this.hasMine = false;
        this.opened = false;
        this.flagged = false;
        this.probability = 10.0;
    }

    public int neighboursFlags;
    public int neighboursClosed;

    public List<Cell> neighbours(Cell[][] cells) {
        neighboursFlags = 0;
        neighboursClosed = 0;
        List<Cell> listNeighboursClosed = new ArrayList<>();
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                if (a != 0 || b != 0) {
                    if (x + a > -1 && x + a < cells.length)
                        if (y + b > -1 && y + b < cells[x].length)
                            if (cells[x + a][y + b].flagged) neighboursFlags++;
                            else if (!cells[x + a][y + b].opened) {
                                neighboursClosed++;
                                listNeighboursClosed.add(cells[x + a][y + b]);
                            }
                }
            }
        }
        return listNeighboursClosed;
    }

}
