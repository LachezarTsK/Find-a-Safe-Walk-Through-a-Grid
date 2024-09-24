
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Solution {

    private static final int[] UP = {-1, 0};
    private static final int[] DOWN = {1, 0};
    private static final int[] LEFT = {0, -1};
    private static final int[] RIGHT = {0, 1};
    private static final int[][] DIRECTIONS = {UP, DOWN, LEFT, RIGHT};
    private static final int[] MATRIX_VALUES = {0, 1};
    private static final int MIN_SAFE_HEALTH = 1;

    private record Point(int row, int column, int health) {}

    private int rows;
    private int columns;
    private Point start;
    private Point goal;

    public boolean findSafeWalk(List<List<Integer>> matrix, int health) {
        rows = matrix.size();
        columns = matrix.get(0).size();

        start = new Point(0, 0, health - matrix.get(0).get(0));
        goal = new Point(rows - 1, columns - 1, MIN_SAFE_HEALTH);

        return searchForSafePathWithZeroOneBreadthFirstSearch(matrix);
    }

    private boolean searchForSafePathWithZeroOneBreadthFirstSearch(List<List<Integer>> matrix) {
        Deque<Point> deque = new ArrayDeque<>();
        deque.add(start);

        boolean[][] visited = new boolean[rows][columns];
        visited[start.row][start.column] = true;

        int finalHealth = 0;

        while (!deque.isEmpty()) {

            Point currentPoint = deque.pollFirst();

            if (currentPoint.row == goal.row && currentPoint.column == goal.column) {
                finalHealth = currentPoint.health;
                break;
            }

            for (int[] move : DIRECTIONS) {
                int nextRow = currentPoint.row + move[0];
                int nextColumn = currentPoint.column + move[1];

                if (!isInMatrix(nextRow, nextColumn)
                        || visited[nextRow][nextColumn]
                        || currentPoint.health - matrix.get(nextRow).get(nextColumn) < MIN_SAFE_HEALTH) {
                    continue;
                }
                visited[nextRow][nextColumn] = true;

                if (matrix.get(nextRow).get(nextColumn) == MATRIX_VALUES[0]) {
                    deque.addFirst(new Point(nextRow, nextColumn, currentPoint.health));
                    continue;
                }
                deque.addLast(new Point(nextRow, nextColumn, currentPoint.health - MATRIX_VALUES[1]));
            }
        }
        return finalHealth >= goal.health;
    }

    private boolean isInMatrix(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }
}
