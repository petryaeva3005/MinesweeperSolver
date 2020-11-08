import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperSolverTest {

    @Test
    void createArray() {
    }

    @Test
    void randomSelection() {
    }

    @Test
    void open() {
        Coordinate[] coordinates = new Coordinate[1];
        coordinates[0] = new Coordinate(2,2);
        Field inputField = new Field(3,3);
        inputField.createArray(coordinates);
        MinesweeperSolver minesweeperSolver =
                new MinesweeperSolver(1, 3,3, coordinates);
        minesweeperSolver.open(new Coordinate(0,0), null, inputField);
        assertEquals(inputField.field[2][1], minesweeperSolver.field[2][1]);
        assertEquals(inputField.field[2][0], minesweeperSolver.field[2][0]);
        assertEquals(inputField.field[1][1], minesweeperSolver.field[1][1]);
        assertEquals(inputField.field[1][2], minesweeperSolver.field[1][2]);
    }
}