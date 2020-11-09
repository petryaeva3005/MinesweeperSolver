public class MinesweeperSolver {
    Coordinate[] coordinates;
    int fieldWidth;
    int fieldHeight;
    Solver solver;

    public MinesweeperSolver(int fieldWidth, int fieldHeight) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.solver = new Solver(fieldWidth,fieldHeight, coordinates);
    }

    public void solver(){
        Field inputField = solver.createArray();
        Coordinate nullCoordinate = solver.randomSelection(inputField);
        Coordinate lCoordinate = new Coordinate(nullCoordinate.x - 1, nullCoordinate.y - 1);
        solver.open(nullCoordinate, null, lCoordinate, inputField);

    }

}