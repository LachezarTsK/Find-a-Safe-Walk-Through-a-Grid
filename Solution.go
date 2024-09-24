
package main

import (
    "container/list"
    "fmt"
)

var UP = [2]int{-1, 0}
var DOWN = [2]int{1, 0}
var LEFT = [2]int{0, -1}
var RIGHT = [2]int{0, 1}
var DIRECTIONS = [4][2]int{UP, DOWN, LEFT, RIGHT}
var MATRIX_VALUES = [2]int{0, 1}

const MIN_SAFE_HEALTH = 1

type Point struct {
    row    int
    column int
    health int
}

func NewPoint(row int, column int, health int) Point {
    return Point{row: row, column: column, health: health}
}

var rows int
var columns int
var start Point
var goal Point

func findSafeWalk(matrix [][]int, health int) bool {
    rows = len(matrix)
    columns = len(matrix[0])

    start = NewPoint(0, 0, health-matrix[0][0])
    goal = NewPoint(rows-1, columns-1, MIN_SAFE_HEALTH)

    return searchForSafePathWithZeroOneBreadthFirstSearch(matrix)
    return false
}

func searchForSafePathWithZeroOneBreadthFirstSearch(matrix [][]int) bool {
    deque := list.List{}
    deque.PushBack(start)

    visited := make([][]bool, rows)
    for row := 0; row < rows; row++ {
        visited[row] = make([]bool, columns)
    }
    visited[start.row][start.column] = true

    finalHealth := 0

    for deque.Len() > 0 {

        currentPoint := deque.Front().Value.(Point)
        deque.Remove(deque.Front())

        if currentPoint.row == goal.row && currentPoint.column == goal.column {
            finalHealth = currentPoint.health
            break
        }

        for _, move := range DIRECTIONS {
            nextRow := currentPoint.row + move[0]
            nextColumn := currentPoint.column + move[1]

            if !isInMatrix(nextRow, nextColumn) ||
                visited[nextRow][nextColumn] ||
                currentPoint.health-matrix[nextRow][nextColumn] < MIN_SAFE_HEALTH {
                continue
            }
            visited[nextRow][nextColumn] = true

            if matrix[nextRow][nextColumn] == MATRIX_VALUES[0] {
                deque.PushFront(NewPoint(nextRow, nextColumn, currentPoint.health))
                continue
            }
            deque.PushBack(NewPoint(nextRow, nextColumn, currentPoint.health-MATRIX_VALUES[1]))
        }
    }
    return finalHealth >= goal.health
}

func isInMatrix(row int, column int) bool {
    return row >= 0 && row < rows && column >= 0 && column < columns
}
