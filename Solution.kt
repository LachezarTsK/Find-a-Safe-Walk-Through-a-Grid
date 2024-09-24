
import kotlin.collections.ArrayDeque

class Solution {

    private companion object {
        val UP = intArrayOf(-1, 0)
        val DOWN = intArrayOf(1, 0)
        val LEFT = intArrayOf(0, -1)
        val RIGHT = intArrayOf(0, 1)
        val DIRECTIONS = arrayOf(UP, DOWN, LEFT, RIGHT)
        val MATRIX_VALUES = intArrayOf(0, 1)
        const val MIN_SAFE_HEALTH = 1
    }

    private data class Point(val row: Int, val column: Int, val health: Int) {}

    private var rows: Int = 0
    private var columns: Int = 0
    private lateinit var start: Point
    private lateinit var goal: Point

    fun findSafeWalk(matrix: List<List<Int>>, health: Int): Boolean {
        rows = matrix.size
        columns = matrix[0].size

        start = Point(0, 0, health - matrix[0][0])
        goal = Point(rows - 1, columns - 1, MIN_SAFE_HEALTH)

        return searchForSafePathWithZeroOneBreadthFirstSearch(matrix)
    }

    private fun searchForSafePathWithZeroOneBreadthFirstSearch(matrix: List<List<Int>>): Boolean {
        val deque = ArrayDeque<Point>()
        deque.add(start)

        val visited = Array<BooleanArray>(rows) { BooleanArray(columns) }
        visited[start.row][start.column] = true

        var finalHealth = 0

        while (!deque.isEmpty()) {

            val currentPoint = deque.removeFirst()

            if (currentPoint.row == goal.row && currentPoint.column == goal.column) {
                finalHealth = currentPoint.health
                break
            }

            for (move in DIRECTIONS) {
                val nextRow = currentPoint.row + move[0]
                val nextColumn = currentPoint.column + move[1]

                if (!isInMatrix(nextRow, nextColumn)
                    || visited[nextRow][nextColumn]
                    || currentPoint.health - matrix[nextRow][nextColumn] < MIN_SAFE_HEALTH
                ) {
                    continue
                }
                visited[nextRow][nextColumn] = true

                if (matrix[nextRow][nextColumn] == MATRIX_VALUES[0]) {
                    deque.addFirst(Point(nextRow, nextColumn, currentPoint.health))
                    continue
                }
                deque.addLast(Point(nextRow, nextColumn, currentPoint.health - MATRIX_VALUES[1]))
            }
        }
        return finalHealth >= goal.health
    }

    private fun isInMatrix(row: Int, column: Int): Boolean {
        return row in 0..<rows && column in 0..<columns
    }
}
