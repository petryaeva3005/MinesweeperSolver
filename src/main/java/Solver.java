public class Solver {
    int fieldWidth;
    int fieldHeight;
    Coordinate[] coordinates;
    Field outputField;
    double[][] probability;

    public Solver(int fieldWidth, int fieldHeight, Coordinate[] coordinates) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.coordinates = coordinates;
        this.outputField = new Field(fieldWidth, fieldHeight);
        this.probability = new double[fieldWidth][fieldHeight];
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                outputField.field[i][j] = -1;
                probability[i][j] = -1;
            }
        }
    }

    public Field createArray() {
        Field inputField = new Field(fieldWidth, fieldHeight);
        inputField.createArray(coordinates);
        return inputField;
    }

    public Coordinate randomSelection(Field inputField) {
        int randWidth = (int) (Math.random() * fieldWidth);
        int randHeight = (int) (Math.random() * fieldHeight);
        if (inputField.field[randWidth][randHeight] == 9) {
            System.out.println("вы проиграли");
            // пока не уверена что именно выводить для сигнала
            return new Coordinate(-1, -1);
        } else if (inputField.field[randWidth][randHeight] != 0) {
            // открываем цифру и рекурсией вычисляем новый рандом
            open(new Coordinate (randWidth, randHeight), null,
                    new Coordinate(randWidth - 1, randHeight - 1), inputField);
            randomSelection(inputField);
        }
        return new Coordinate(randWidth, randHeight);
    }



    public void open(Coordinate nullCoordinate, Coordinate lastCoordinate, Coordinate lCoordinate, Field inputField) {
        int nullX = nullCoordinate.x;
        int nullY = nullCoordinate.y;
        // в случае если мы открываем цифру
        if (inputField.field[nullX][nullY] != 0)
            outputField.field[nullX][nullY] = inputField.field[nullX][nullY];
        else
            // цикл пока координата lastCoordinate не будет равна lCoordinate(не меняется)
        //lCoordinate это координата nullCoordinate, только на 1 выше и на 1 левее
        while (lastCoordinate == null || lCoordinate.x != lastCoordinate.x || lCoordinate.y != lastCoordinate.y) {
            int differenceX;
            int differenceY;
            if (lastCoordinate == null) {
                lastCoordinate = new Coordinate(lCoordinate.x, lCoordinate.y);
            }
            // вычисляем куда мы сдивинулись относительно nullCoordinate
            differenceX = lastCoordinate.x - nullCoordinate.x;
            differenceY = lastCoordinate.y - nullCoordinate.y;
            // вычисляем куда сдвинемся дальше
            if (differenceX == -1)
                if (differenceY == 1) differenceX += 1;
                else differenceY += 1;
            else if (differenceX == 0) differenceX = differenceX + differenceY;
            else if (differenceY == -1) differenceX += -1;
            else differenceY += -1;
            // рассматриваем края
            if (nullX + differenceX >= fieldWidth || nullX + differenceX < 0 || nullY + differenceY >= fieldHeight || nullY + differenceY < 0) {
                lastCoordinate.x = nullX + differenceX;
                lastCoordinate.y = nullY + differenceY;
                continue;
            }
            // продолжаем ходить по клеткам вокруг nullCoordinate пока не наткнемся на пустую клетку
            // когда наткнемся, рекурсией открываем эту клетку
            if (inputField.field[nullX + differenceX][nullY + differenceY] == 0) {
                open(new Coordinate(nullX + differenceX, nullY + differenceY), lastCoordinate, lCoordinate, inputField);
            } else {
                lastCoordinate.x = nullX + differenceX;
                lastCoordinate.y = nullY + differenceY;
            }
            // заполняем массив вероятностей и поле
            probability[nullX + differenceX][nullY + differenceY] = 0;
            outputField.field[nullX + differenceX][nullY + differenceY] = inputField.field[nullX + differenceX][nullY + differenceY];
        }
    }

    public void probabilityForOne(Coordinate coordinate, Field inputField) {
        int x = coordinate.x;
        int y = coordinate.y;
        int unknownCounter = 0;
        int minesCounter = 0;
        // подсчет неизвестных клеток и обнаруженных мин
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                if (probability[x + a][y + b] == -1) unknownCounter++;
                if (probability[x + a][y + b] == 1) minesCounter++;
            }
        }
        // тут мы выходим из цикла, но сигнал о том, что нет пустых, я пока не придумала
        if (unknownCounter == 0) return;
        // если кол-во неизвестных клеток и обнаруженных мин совпадает с цифрой в клетке
        // заполняем неизвестные клетки минами
        if (unknownCounter + minesCounter == outputField.field[x][y]) {
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    if (probability[x + a][y + b] == -1) {
                        probability[x + a][y + b] = 1;
                        outputField.field[x + a][y + b] = 9;
                    }
                }
            }
        }
        // если кол-во обнаруженных мин совпадает с цифрой в клетке
        // открываем неизвестные клетки
        else if (minesCounter == outputField.field[x][y]) {
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    if (probability[x + a][y + b] == -1) {
                        probability[x + a][y + b] = 0;
                        open(coordinate, null, new Coordinate(x - 1, y - 1), inputField);
                    }
                }
            }
        }
        // в остальных случаях
        // вычисляем вероятности
        else for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                if (a != 0 && b != 0) {
                    // в случае если мы ни разу не вычисляли в этой клетке вероятность
                    if (probability[x + a][y + b] == -1)
                        // вероятность = кол-во оставшихся мин, которые нужно найти / общее кол-во мин
                        // {пометим ее **}
                        probability[x + a][y + b] =
                                ((double) outputField.field[x][y] - minesCounter) / unknownCounter;
                    else if (probability[x + a][y + b] != 1) {
                        // вероятность = 1-(1-исходная вероятность в этой клетке) *
                        // (1-вероятность, поторую пометили **)
                        double value = 1 - (1 - ((double) outputField.field[x][y] - minesCounter) /
                                unknownCounter) * (1 - probability[x + a][y + b]);
                        probability[x + a][y + b] = value;
                    }
                }
            }
        }
    }
}
