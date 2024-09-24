
using System;
using System.Collections.Generic;

public class Solution
{
    private static readonly int[] UP = { -1, 0 };
    private static readonly int[] DOWN = { 1, 0 };
    private static readonly int[] LEFT = { 0, -1 };
    private static readonly int[] RIGHT = { 0, 1 };
    private static readonly int[][] DIRECTIONS = { UP, DOWN, LEFT, RIGHT };
    private static readonly int[] MATRIX_VALUES = { 0, 1 };
    private static readonly int MIN_SAFE_HEALTH = 1;

    private sealed record Point(int row, int column, int health) { }

    private int rows;
    private int columns;
    private Point? start;
    private Point? goal;

    public bool FindSafeWalk(IList<IList<int>> matrix, int health)
    {
        rows = matrix.Count;
        columns = matrix[0].Count;

        start = new Point(0, 0, health - matrix[0][0]);
        goal = new Point(rows - 1, columns - 1, MIN_SAFE_HEALTH);

        return SearchForSafePathWithZeroOneBreadthFirstSearch(matrix);
    }

    private bool SearchForSafePathWithZeroOneBreadthFirstSearch(IList<IList<int>> matrix)
    {
        LinkedList<Point> deque = new LinkedList<Point>();
        deque.AddLast(start);

        bool[,] visited = new bool[rows, columns];
        visited[start.row, start.column] = true;

        int finalHealth = 0;

        while (deque.Count > 0)
        {

            Point currentPoint = deque.First();
            deque.RemoveFirst();

            if (currentPoint.row == goal.row && currentPoint.column == goal.column)
            {
                finalHealth = currentPoint.health;
                break;
            }

            foreach (int[] move in DIRECTIONS)
            {
                int nextRow = currentPoint.row + move[0];
                int nextColumn = currentPoint.column + move[1];

                if (!IsInMatrix(nextRow, nextColumn)
                        || visited[nextRow, nextColumn]
                        || currentPoint.health - matrix[nextRow][nextColumn] < MIN_SAFE_HEALTH)
                {
                    continue;
                }
                visited[nextRow, nextColumn] = true;

                if (matrix[nextRow][nextColumn] == MATRIX_VALUES[0])
                {
                    deque.AddFirst(new Point(nextRow, nextColumn, currentPoint.health));
                    continue;
                }
                deque.AddLast(new Point(nextRow, nextColumn, currentPoint.health - MATRIX_VALUES[1]));
            }
        }
        return finalHealth >= goal.health;
    }

    private bool IsInMatrix(int row, int column)
    {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }
}
