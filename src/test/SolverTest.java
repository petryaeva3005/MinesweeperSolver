import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    @Test
    void open() {
        Coordinate[] coordinates = new Coordinate[1];
        coordinates[0] = new Coordinate(2, 2);
        Field inputField = new Field(3, 3);
        inputField.createArray(coordinates);
        Solver solver = new Solver(3, 3, coordinates);
        solver.open(new Coordinate(0, 0), null, new Coordinate(-1,-1), inputField);
        assertEquals(inputField.field[2][1], solver.outputField.field[2][1]);
        assertEquals(inputField.field[2][0], solver.outputField.field[2][0]);
        assertEquals(inputField.field[1][1], solver.outputField.field[1][1]);
        assertEquals(inputField.field[1][2], solver.outputField.field[1][2]);
        double[][] expected = new double[3][3];
        expected[2][2] = -1;
        assertArrayEquals(expected, solver.probability);

    }

    @Test
    void probabilityForOne() {
        Coordinate[] coordinates = new Coordinate[1];
        coordinates[0] = new Coordinate(2, 2);
        Field inputField = new Field(3, 3);
        inputField.createArray(coordinates);
        Solver solver = new Solver(3, 3, coordinates);
        solver.open(new Coordinate(0, 0), null, new Coordinate(-1,-1), inputField);
        solver.probabilityForOne(new Coordinate(1,1), inputField);
        double[][] expected = new double[3][3];
        expected[2][2] = 1;
        assertArrayEquals(expected, solver.probability);
    }
}