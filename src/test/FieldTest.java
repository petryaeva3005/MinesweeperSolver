import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FieldTest {

    @Test
    void createArray() {
        Coordinate[] coordinates = new Coordinate[1];
        coordinates[0] = new Coordinate(2, 2);
        int[][] expected = new int[3][3];
        expected[2][2] = 9;
        expected[1][2] = 1;
        expected[2][1] = 1;
        expected[1][1] = 1;
        Field actualField = new Field(3, 3);
        actualField.createArray(coordinates);
        assertArrayEquals(expected, actualField.field);
    }
}