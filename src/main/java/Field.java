public class Field {

    int width;
    int height;
    int[][] field;


    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.field = new int[width][height];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Field field = (Field) obj;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (field.field[i][j] != 9 && field.field[i][j] != this.field[i][j]) return false;
            }
        }
        return true;
    }

    public void createArray(Coordinate[] coordinates) {
        for (Coordinate coordinate : coordinates) {
            field[coordinate.x][coordinate.y] = 9;
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int neighbours = 0;
                if (field[i][j] != 9) {
                    for (int a = -1; a <= 1; a++) {
                        for (int b = -1; b <= 1; b++) {
                            if ((a != 0 || b != 0) && i + a > -1 && i + a < width && j + b > -1 && j + b < height)
                                if (field[i + a][j + b] == 9) neighbours++;
                        }
                    }
                    field[i][j] = neighbours;
                }
            }
        }
    }
}


