import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FieldTest {

    @Test
    void createArray() {
        Coordinate[] coordinates = new Coordinate[1];
        coordinates[0] = new Coordinate(2,2);
        int[][] expected = new int[3][3];
        expected[2][2] = 9;
        expected[1][2] = 1;
        expected[2][1] = 1;
        expected[1][1] = 1;
        Field actualField = new Field(3,3);
        actualField.createArray(coordinates);
        assertEquals(expected[2][1], actualField.field[2][1]);
        assertEquals(expected[1][2], actualField.field[1][2]);
        assertEquals(expected[1][1], actualField.field[1][1]);
        assertEquals(expected[0][1], actualField.field[0][1]);

    }
}