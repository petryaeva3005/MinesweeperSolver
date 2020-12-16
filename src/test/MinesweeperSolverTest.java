import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperSolverTest {

    @Test
    void openCell() {
        Coordinate[] coordinates = new Coordinate[2];
        coordinates[0] = new Coordinate(2, 2);
        coordinates[1] = new Coordinate(1, 2);
        Field inputField = new Field(5, 5);
        inputField.createArray(coordinates);
        MinesweeperSolver minesweeperSolver = new MinesweeperSolver(5, 5, inputField);
        minesweeperSolver.openCell(minesweeperSolver.cells[0][0]);
        inputField.field[0][2] = -1;
        inputField.field[1][2] = -1;
        inputField.field[2][2] = -1;
        int[][] outputField = new int[5][5];
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                outputField[i][j] = minesweeperSolver.cells[i][j].neighboursMines;
            }
        assertArrayEquals(inputField.field, outputField);
        assertTrue(minesweeperSolver.cellsToAnalyze.contains(minesweeperSolver.cells[2][3]));
        assertTrue(minesweeperSolver.cellsToAnalyze.contains(minesweeperSolver.cells[3][1]));
        assertTrue(minesweeperSolver.cellsToAnalyze.contains(minesweeperSolver.cells[0][1]));
    }

    @Test
    void iteration() {
        Coordinate[] coordinates = new Coordinate[2];
        coordinates[0] = new Coordinate(2, 2);
        coordinates[1] = new Coordinate(1, 2);
        Field inputField = new Field(5, 6);
        inputField.createArray(coordinates);
        MinesweeperSolver minesweeperSolver = new MinesweeperSolver(5, 6, inputField);
        minesweeperSolver.openCell(minesweeperSolver.cells[0][0]);
        int count = minesweeperSolver.iteration();
        assertEquals(4, count);
        assertEquals(1, minesweeperSolver.cells[0][2].neighboursMines);
        assertTrue(minesweeperSolver.cells[1][2].flagged);
        assertTrue(minesweeperSolver.cells[2][2].flagged);
    }

    @Test
    void solver() {
        Coordinate[] coordinates = new Coordinate[2];
        coordinates[0] = new Coordinate(2, 2);
        coordinates[1] = new Coordinate(1, 2);
        Field inputField = new Field(5, 6);
        inputField.createArray(coordinates);
        MinesweeperSolver minesweeperSolver = new MinesweeperSolver(5, 6, inputField);
        minesweeperSolver.solver();
        int[][] outputField = new int[5][6];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                outputField[i][j] = minesweeperSolver.cells[i][j].neighboursMines;
                System.out.print(outputField[i][j] + " ");
            }
            System.out.println();
        }
        assertArrayEquals(inputField.field, outputField);

        System.out.println("\n Новый тест");
        Coordinate[] coordinates1 = new Coordinate[6];
        coordinates1[0] = new Coordinate(0, 7);
        coordinates1[1] = new Coordinate(1, 3);
        coordinates1[2] = new Coordinate(3, 5);
        coordinates1[3] = new Coordinate(5, 5);
        coordinates1[4] = new Coordinate(6, 3);
        coordinates1[5] = new Coordinate(6, 4);
        Field inputField1 = new Field(7, 8);
        inputField1.createArray(coordinates1);
        MinesweeperSolver minesweeperSolver1 = new MinesweeperSolver(7, 8, inputField1);
        minesweeperSolver1.solver();
        int[][] outputField1 = new int[7][8];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                outputField1[i][j] = minesweeperSolver1.cells[i][j].neighboursMines;
                System.out.print(outputField1[i][j] + " ");
            }
            System.out.println();
        }
        assertArrayEquals(inputField1.field, outputField1);
    }
}