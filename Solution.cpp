
#include <span>
#include <deque>
#include <array>
#include <vector>
using namespace std;

/*
 The code will run faster with ios::sync_with_stdio(0).
 However, this should not be used in production code and interactive problems.
 In this particular problem, it is ok to apply ios::sync_with_stdio(0).

 Many of the top-ranked C++ solutions for time on leetcode apply this trick,
 so, for a fairer assessment of the time percentile of my code
 you could outcomment the lambda expression below for a faster run.
*/

/*
 const static auto speedup = [] {
    ios::sync_with_stdio(0);
    return nullptr;
}();
*/

class Solution {

    static constexpr array<int, 2> UP = { -1, 0 };
    static constexpr array<int, 2> DOWN = { 1, 0 };
    static constexpr array<int, 2> LEFT = { 0, -1 };
    static constexpr array<int, 2> RIGHT = { 0, 1 };
    static constexpr array< array<int, 2>, 4> DIRECTIONS = { UP, DOWN, LEFT, RIGHT };
    static constexpr array<int, 2> MATRIX_VALUES = { 0, 1 };
    static const int MIN_SAFE_HEALTH = 1;

    struct Point {
        size_t row{};
        size_t column{};
        int health{};

        Point(size_t row, size_t column, int health) :
                row{ row }, column{ column }, health{ health } {};

        Point() = default;
        ~Point() = default;
    };

    size_t rows{};
    size_t columns{};
    Point start;
    Point goal;

public:
    bool findSafeWalk(const vector<vector<int>>& matrix, int health) {
        rows = matrix.size();
        columns = matrix[0].size();

        start = Point(0, 0, health - matrix[0][0]);
        goal = Point(rows - 1, columns - 1, MIN_SAFE_HEALTH);

        return searchForSafePathWithZeroOneBreadthFirstSearch(matrix);
    }

private:
    bool searchForSafePathWithZeroOneBreadthFirstSearch(span<const vector<int>> matrix) const {
        deque<Point> deque;
        deque.push_back(start);

        vector<vector<int>> visited(rows, vector<int>(columns));
        visited[start.row][start.column] = true;

        int finalHealth = 0;

        while (!deque.empty()) {

            Point currentPoint = deque.front();
            deque.pop_front();

            if (currentPoint.row == goal.row && currentPoint.column == goal.column) {
                finalHealth = currentPoint.health;
                break;
            }

            for (const auto& move : DIRECTIONS) {
                size_t nextRow = currentPoint.row + move[0];
                size_t nextColumn = currentPoint.column + move[1];

                if (!isInMatrix(nextRow, nextColumn)
                    || visited[nextRow][nextColumn]
                    || currentPoint.health - matrix[nextRow][nextColumn] < MIN_SAFE_HEALTH) {
                    continue;
                }
                visited[nextRow][nextColumn] = true;

                if (matrix[nextRow][nextColumn] == MATRIX_VALUES[0]) {
                    deque.emplace_front(nextRow, nextColumn, currentPoint.health);
                    continue;
                }
                deque.emplace_back(nextRow, nextColumn, currentPoint.health - MATRIX_VALUES[1]);
            }
        }
        return finalHealth >= goal.health;
    }


    bool isInMatrix(size_t row, size_t column) const {
        return row < rows && column < columns;
    }
};
