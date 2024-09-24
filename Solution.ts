
// const { Deque } = require('@datastructures-js/deque');
/*
 Deque is internally included in the solution file on leetcode.
 So, when running the code on leetcode it should stay commented out. 
 It is mentioned here as a comment, just for information about 
 which external library is applied for this data structure.
 */

function findSafeWalk(matrix: number[][], health: number): boolean {
    this.UP = [-1, 0];
    this.DOWN = [1, 0];
    this.LEFT = [0, -1];
    this.RIGHT = [0, 1];
    this.DIRECTIONS = [this.UP, this.DOWN, this.LEFT, this.RIGHT];
    this.MATRIX_VALUES = [0, 1];
    this.MIN_SAFE_HEALTH = 1;

    this.rows = matrix.length;
    this.columns = matrix[0].length;

    this.start = new Point(0, 0, health - matrix[0][0]);
    this.goal = new Point(this.rows - 1, this.columns - 1, this.MIN_SAFE_HEALTH);

    return searchForSafePathWithZeroOneBreadthFirstSearch(matrix);
};

function searchForSafePathWithZeroOneBreadthFirstSearch(matrix) {
    const deque = new Deque();
    deque.pushBack(this.start);

    const visited: boolean[][] = Array.from(new Array(this.rows), () => new Array(this.columns).fill(false));
    visited[this.start.row][this.start.column] = true;

    let finalHealth = 0;

    while (!deque.isEmpty()) {

        const currentPoint = deque.popFront();

        if (currentPoint.row === this.goal.row && currentPoint.column === this.goal.column) {
            finalHealth = currentPoint.health;
            break;
        }

        for (let move of this.DIRECTIONS) {
            const nextRow = currentPoint.row + move[0];
            const nextColumn = currentPoint.column + move[1];

            if (!isInMatrix(nextRow, nextColumn)
                || visited[nextRow][nextColumn]
                || currentPoint.health - matrix[nextRow][nextColumn] < this.MIN_SAFE_HEALTH) {
                continue;
            }
            visited[nextRow][nextColumn] = true;

            if (matrix[nextRow][nextColumn] === this.MATRIX_VALUES[0]) {
                deque.pushFront(new Point(nextRow, nextColumn, currentPoint.health));
                continue;
            }
            deque.pushBack(new Point(nextRow, nextColumn, currentPoint.health - this.MATRIX_VALUES[1]));
        }
    }
    return finalHealth >= this.goal.health;
}

function Point(row: number, column: number, health: number) {
    this.row = row;
    this.column = column;
    this.health = health;
}

function isInMatrix(row: number, column: number): boolean {
    return row >= 0 && row < this.rows && column >= 0 && column < this.columns;
}
