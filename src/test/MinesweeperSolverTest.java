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
        MinesweeperSolver minesweeperSolver = new MinesweeperSolver(5, 5, inputField, 2);
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
        MinesweeperSolver minesweeperSolver = new MinesweeperSolver(5, 6, inputField, 2);
        minesweeperSolver.openCell(minesweeperSolver.cells[0][0]);
        minesweeperSolver.iteration();
        assertEquals(1, minesweeperSolver.cells[0][2].neighboursMines);
        assertTrue(minesweeperSolver.cells[1][2].flagged);
        assertTrue(minesweeperSolver.cells[2][2].flagged);
    }

    @Test
    void solver() {
        Coordinate[] coordinates = new Coordinate[40];
        coordinates[0] = new Coordinate(0, 0);
        coordinates[1] = new Coordinate(0, 9);
        coordinates[2] = new Coordinate(1, 11);
        coordinates[3] = new Coordinate(2, 7);
        coordinates[4] = new Coordinate(3, 0);
        coordinates[5] = new Coordinate(3, 9);
        coordinates[6] = new Coordinate(4, 1);
        coordinates[7] = new Coordinate(4, 9);
        coordinates[8] = new Coordinate(5, 0);
        coordinates[9] = new Coordinate(5, 4);
        coordinates[10] = new Coordinate(5, 11);
        coordinates[11] = new Coordinate(6, 0);
        coordinates[12] = new Coordinate(6, 5);
        coordinates[13] = new Coordinate(6, 7);
        coordinates[14] = new Coordinate(6, 15);
        coordinates[15] = new Coordinate(7, 4);
        coordinates[16] = new Coordinate(7, 11);
        coordinates[17] = new Coordinate(8, 10);
        coordinates[18] = new Coordinate(8, 13);
        coordinates[19] = new Coordinate(9, 0);
        coordinates[20] = new Coordinate(9, 10);
        coordinates[21] = new Coordinate(10, 8);
        coordinates[22] = new Coordinate(10, 10);
        coordinates[23] = new Coordinate(10, 12);
        coordinates[24] = new Coordinate(11, 4);
        coordinates[25] = new Coordinate(11, 12);
        coordinates[26] = new Coordinate(12, 3);
        coordinates[27] = new Coordinate(12, 5);
        coordinates[28] = new Coordinate(12, 6);
        coordinates[29] = new Coordinate(12, 9);
        coordinates[30] = new Coordinate(12, 13);
        coordinates[31] = new Coordinate(13, 1);
        coordinates[32] = new Coordinate(13, 2);
        coordinates[33] = new Coordinate(13, 7);
        coordinates[34] = new Coordinate(13, 11);
        coordinates[35] = new Coordinate(14, 0);
        coordinates[36] = new Coordinate(14, 4);
        coordinates[37] = new Coordinate(14, 7);
        coordinates[38] = new Coordinate(15, 3);
        coordinates[39] = new Coordinate(15, 14);
        Field inputField = new Field(16, 16);
        inputField.createArray(coordinates);
        MinesweeperSolver minesweeperSolver = new MinesweeperSolver(16, 16, inputField, 40);
        minesweeperSolver.solver();
        int[][] outputField = new int[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
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
        MinesweeperSolver minesweeperSolver1 = new MinesweeperSolver(7, 8, inputField1, 6);
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