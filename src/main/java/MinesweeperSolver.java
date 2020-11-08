public class MinesweeperSolver {

    int mines;
    int fieldWidth;
    int fieldHeight;
    Coordinate[] coordinates;
    int[][] field;

    public MinesweeperSolver(int mines, int fieldWidth, int fieldHeight, Coordinate[] coordinates) {
        this.mines = mines;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.coordinates = coordinates;
        this.field = new int[fieldWidth][fieldHeight];
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                field[i][j] = -1;
            }
        }
    }

    public void createArray() {
        Field inputField = new Field(fieldWidth, fieldHeight);
        inputField.createArray(coordinates);
    }

    public Coordinate randomSelection() {
        int randWidth = (int) (Math.random() * fieldWidth);
        int randHeight = (int) (Math.random() * fieldHeight);
        if (field[randWidth][randHeight] == 9) System.out.println("вы проиграли");
        // if (field[randWidth][randHeight] != 0) то открываем точку
        return new Coordinate(randWidth, randHeight);
    }

    public Coordinate lCoordinate = new Coordinate(-1, -1);

    public void open(Coordinate nullCoordinate, Coordinate lastCoordinate, Field inputField) {
        while (lastCoordinate == null || lCoordinate.x != lastCoordinate.x || lCoordinate.y != lastCoordinate.y) {
            int differenceX;
            int differenceY;
            if (lastCoordinate == null) {
                lastCoordinate = new Coordinate(lCoordinate.x, lCoordinate.y);
            }
            differenceX = lastCoordinate.x - nullCoordinate.x;
            differenceY = lastCoordinate.y - nullCoordinate.y;
            if (differenceX == -1)
                if (differenceY == 1) differenceX += 1;
                else differenceY += 1;
            else if (differenceX == 0) differenceX = differenceX + differenceY;
            else if (differenceY == -1) differenceX += -1;
            else differenceY += -1;
            int nullX = nullCoordinate.x;
            int nullY = nullCoordinate.y;
            if (nullX + differenceX >= fieldWidth || nullX + differenceX < 0 || nullY + differenceY >= fieldHeight || nullY + differenceY < 0) {
                lastCoordinate.x = nullX + differenceX;
                lastCoordinate.y = nullY + differenceY;
                continue;
            }
            if (inputField.field[nullX + differenceX][nullY + differenceY] == 0) {
                field[nullX + differenceX][nullY + differenceY] = inputField.field[nullX + differenceX][nullY + differenceY];
                open(new Coordinate(nullX + differenceX, nullY + differenceY), lastCoordinate, inputField);
            } else {
                lastCoordinate.x = nullX + differenceX;
                lastCoordinate.y = nullY + differenceY;
            }
            field[nullX + differenceX][nullY + differenceY] = inputField.field[nullX + differenceX][nullY + differenceY];
        }
    }



}


