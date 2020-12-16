import java.util.*;

public class MinesweeperSolver {
    int mines;
    int fieldWidth;
    int fieldHeight;
    Cell[][] cells;
    public LinkedList<Cell> cellsToAnalyze;
    Field inputField;
    int openMines;

    public MinesweeperSolver(int fieldWidth, int fieldHeight, Field inputField, int mines) {
        this.mines = mines;
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
        this.openMines = 0;
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
        // Добавляем в лист все открытые непустые клетки
        else cellsToAnalyze.add(cell);
    }

    public void flagCell(Cell cell) {
        openMines++;
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
                System.out.println("К сожалению, клетка не пустая. Поэтому открываем эту клетку и генерируем новые координаты клетки");
            else System.out.println("Ура! Клетка пустая, поэтому открываем область");
            return new Coordinate(randWidth, randHeight);
        }
    }

    // метод анализирует все клетки по отдельности
    // для одновременного анализа нескольких клеток я использую вероятности, но это в следующем методе
    public int iteration() {
        int actions = 0;
        Set<Cell> cellsToOpening = new HashSet<>();
        Iterator<Cell> iterator = cellsToAnalyze.iterator();
        while (iterator.hasNext()) {
            Cell element = iterator.next();
            // Рассматриваем соседние клетки
            List<Cell> listNeighboursClosed = element.neighbours(cells);
            // если кол-во флагов = кол-ву мин
            if (element.neighboursMines == element.neighboursFlags) {
                for (Cell e : listNeighboursClosed) {
                    // Добавляем в массив, чтобы после итерирования открыть все эти клетки
                    cellsToOpening.add(e);
                    actions++;
                }
                iterator.remove();
            } else {
                // если кол-во отавшихся мин = кол-ву закрытых клеток
                if (element.neighboursMines - element.neighboursFlags == element.neighboursClosed) {
                    for (Cell e : listNeighboursClosed) {
                        // почечаем мины
                        this.flagCell(e);
                        actions++;
                    }
                    iterator.remove();
                }
            }
        }
        // открываем клетки
        for (Cell e : cellsToOpening) {
            this.openCell(e);
        }
        return actions;
    }

    public void solver() {
        Coordinate coordinate;
        do {
            //Генерируем клетки пока не наткнемся на пустую клетку
            coordinate = this.random();
            if (coordinate != null) this.openCell(cells[coordinate.x][coordinate.y]);
        } while (coordinate == null || inputField.field[coordinate.x][coordinate.y] != 0);
        // открываем и помечаем клетки пока есть есть изменения при итерации
        int actions = this.iteration();
        while (actions > 0) actions = this.iteration();
        while (openMines != mines) {
            LinkedList<Cell> probabilities = new LinkedList<>();
            //одновременный анализ:
            //для соседей оставшихся клеток вычисляем вероятности
            for (Cell element : cellsToAnalyze) {
                List<Cell> listNeighboursClosed = element.neighbours(cells);
                // вероятность клетки, при условии, что для этой клетки мы не разу не вычисляли верооятности
                double probability = (cells[element.x][element.y].neighboursMines -
                        cells[element.x][element.y].neighboursFlags) * 1.0 / cells[element.x][element.y].neighboursClosed;
                //проходимся по закрытым соседям
                for (Cell e : listNeighboursClosed) {
                    // в случае если мы ни разу не вычисляли в этой клетке вероятность
                    if (cells[e.x][e.y].probability == 1.0) {
                        cells[e.x][e.y].probability = probability;
                    } else {
                        // в противном случае мы удаляем старую вероятность и высчитываем новую
                        probabilities.remove(cells[e.x][e.y]);
                        cells[e.x][e.y].probability = 1.0 - (1.0 - probability) * (1.0 - cells[e.x][e.y].probability);
                    }
                    // добавляем в список все вычисленные вероятности
                    probabilities.add(cells[e.x][e.y]);
                }
            }
            double minProbability = Double.MAX_VALUE;
            Coordinate minProbabilityCoordinate = new Coordinate(0, 0);
            // Далее проходимся по списку вычисленных вероятностей
            // параллельно находим минимальную вероятность и возвращаем в исходное значение все остальные вероятности
            for (Cell e : probabilities) {
                if (cells[e.x][e.y].probability <= minProbability) {
                    minProbabilityCoordinate = new Coordinate(e.x, e.y);
                    minProbability = cells[e.x][e.y].probability;
                }
                cells[e.x][e.y].probability = 1.0;
            }
            // открываем минимальную вероятность
            // если минимальных несколько - открываем самую последнюю
            this.openCell(cells[minProbabilityCoordinate.x][minProbabilityCoordinate.y]);
            // снова открываем и помечаем клетки пока есть есть изменения при итереции
            actions = this.iteration();
            while (actions > 0) actions = this.iteration();
        }
    }

}