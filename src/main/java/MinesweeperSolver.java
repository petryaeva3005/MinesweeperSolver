import java.util.*;

public class MinesweeperSolver {
    int fieldWidth;
    int fieldHeight;
    Cell[][] cells;
    public LinkedList<Cell> cellsToAnalyze;
    Field inputField;

    public MinesweeperSolver(int fieldWidth, int fieldHeight, Field inputField) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.cells = new Cell[fieldWidth][fieldHeight];
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                cells[i][j] = new Cell(i, j, -1);
            }
        }
        this.cellsToAnalyze = new LinkedList<>();
        this.inputField = inputField;
    }

    public void openCell(Cell cell) {
        cell.opened = true;
        cell.neighboursMines = inputField.field[cell.x][cell.y];
        if (inputField.field[cell.x][cell.y] == 9)
            System.out.printf("The opening of the mines (%d; %d) \n", cell.x, cell.y);
        else if (cell.neighboursMines == 0)
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    if (a != 0 || b != 0)
                        if (cell.x + a > -1 && cell.x + a < fieldWidth)
                            if (cell.y + b > -1 && cell.y + b < fieldHeight)
                                if (!cells[cell.x + a][cell.y + b].opened) {
                                    this.openCell(cells[cell.x + a][cell.y + b]);
                                }
                }
            }
        else cellsToAnalyze.add(cell);
    }

    public void flagCell(Cell cell) {
        cell.flagged = true;
        cell.neighboursMines = 9;
    }

    public Coordinate random() {
        int randWidth = (int) (Math.random() * fieldWidth);
        int randHeight = (int) (Math.random() * fieldHeight);
        System.out.printf("Сгенерированы координаты клетки (%d; %d) \n", randWidth, randHeight);
        if (inputField.field[randWidth][randHeight] == 9) {
            System.out.println("К сожалению, это мина. Поэтому генерируем новые коородинаты клетки");
            return null;
        } else {
            if (inputField.field[randWidth][randHeight] != 0)
                System.out.println("К сожалению, клетка не пустая. Поэтому открываем эту клетку и генерируем новые коородинаты клетки");
            else System.out.println("Ура! Клетка пустая, поэтому открываем область");
            return new Coordinate(randWidth, randHeight);
        }
    }

    public int iteration() {
        Set<Cell> cellsToOpening = new HashSet<>();
        Iterator<Cell> iterator = cellsToAnalyze.iterator();
        int actions = 0;
        while (iterator.hasNext()) {
            Cell element = iterator.next();
            List<Cell> listNeighboursClosed = element.neighbours(cells);
            if (element.neighboursMines == element.neighboursFlags) {
                for (Cell e : listNeighboursClosed) {
                    cellsToOpening.add(e);
                    actions++;
                }
                iterator.remove();
            } else {
                if (element.neighboursMines - element.neighboursFlags == element.neighboursClosed) {
                    for (Cell e : listNeighboursClosed) {
                        this.flagCell(e);
                        actions++;
                    }
                    iterator.remove();
                }
            }
        }
        for (Cell e : cellsToOpening) {
            this.openCell(e);
        }
        return actions;
    }

    public void solver() {
        Coordinate coordinate;
        do {
            coordinate = this.random();
            if (coordinate != null) this.openCell(cells[coordinate.x][coordinate.y]);
        } while (coordinate == null || inputField.field[coordinate.x][coordinate.y] != 0);

        int actions = this.iteration();
        while (actions > 0) actions = this.iteration();
    }

}