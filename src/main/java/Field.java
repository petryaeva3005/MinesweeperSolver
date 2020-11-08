public class Field {

    int width;
    int height;
    int[][] field;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.field = new int[width][height];
    }



    public void createArray(Coordinate[] coordinates) {
        for (Coordinate coordinate : coordinates) field[coordinate.x][coordinate.y] = 9;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int count = 0;
                if (field[i][j] != 9) {
                    if (i != 0 && j != 0 && i != width - 1 && j != height - 1) {
                        for (int a = -1; a <= 1; a++)
                            for (int b = -1; b <= 1; b++)
                                if (field[i + a][j + b] == 9)
                                    count += 1;
                    }
                    else
                    if (i == 0) {
                        if (j == 0) count = field[1][1] / 9;
                        else if (j == height - 1) count = field[1][j - 1] / 9;
                        else for (int a = 0; a <= 1; a++)
                                for (int b = -1; b <= 1; b++)
                                    if (field[i + a][j + b] == 9)
                                        count += 1;
                    }
                    else
                    if (i == width - 1) {
                        if (j == 0) count = field[i - 1][1] / 9;
                        else if (j == height - 1) count = field[i - 1][j - 1] / 9;
                        else for (int a = -1; a <= 0; a++)
                                for (int b = -1; b <= 1; b++)
                                    if (field[i + a][j + b] == 9)
                                        count += 1;
                    }
                    else
                    if (j == 0) {
                        for (int a = 1; a <= 1; a++)
                                for (int b = 0; b <= 1; b++)
                                    if (field[i + a][j + b] == 9)
                                        count += 1;
                    }
                    else
                    if (j == width - 1) {
                        for (int a = -1; a <= 1; a++)
                                for (int b = -1; b <= 0; b++)
                                    if (field[i + a][j + b] == 9)
                                        count += 1;
                    }
                    field[i][j] = count;
                }
            }
        }
    }





}


