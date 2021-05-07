/* *****************************************************************************
 *  Name: Mingqian Yu
 *  Date: August 9, 2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {
    private int[][] board;
    private final int n;
    private Integer[] t1;
    private Integer[] t2;
    private static int[][] goal;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        assert n >= 2 && n < 128;
        // clone 2D Array to local https://stackoverflow.com/questions/1686425/copy-a-2d-array-in-java
        board = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++)
            board[i] = tiles[i].clone();
        // create the goal board
        goal = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = n * i + j + 1;
                if (val < n * n) goal[i][j] = val;
                else goal[i][j] = 0;
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(Integer.toString(n) + System.lineSeparator());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(String.format("%2d ", board[i][j]));
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        if (isGoal()) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = board[i][j];
                int i0 = n - 1;
                int j0 = n - 1;
                if (val != 0) {
                    i0 = (val - 1) / n;
                    j0 = val - 1 - i0 * n;
                    if (i != i0 || j != j0) count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (isGoal()) {
            return 0;
        }
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = board[i][j];
                int i0 = n - 1;
                int j0 = n - 1;
                if (val != 0) {
                    i0 = (val - 1) / n;
                    j0 = val - 1 - i0 * n;
                    manhattan += Math.abs(i - i0) + Math.abs(j - j0);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (Arrays.deepEquals(board, goal)) {
            return true;
        }
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        // https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#deepEquals-java.lang.Object:A-java.lang.Object:A-
        if (!Arrays.deepEquals(this.board, that.board)) return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        final int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        // use stack/queue to store neighboring boards
        Stack<Board> neighbors = new Stack<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    for (int[] k : dir) {
                        Board copy = new Board(board);
                        if (isValid(i, j, k)) {
                            swap(copy, i, j, k);
                            neighbors.push(copy);
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    private boolean isValid(int i, int j, int[] k) {
        int i1 = i + k[0];
        int j1 = j + k[1];
        if (i1 >= 0 && i1 < n && j1 >= 0 && j1 < n) {
            return true;
        }
        return false;
    }

    private void swap(Board neighbors, int i, int j, int[] k) {
        int i1 = i + k[0];
        int j1 = j + k[1];
        int val = neighbors.board[i1][j1];
        neighbors.board[i][j] = val;
        neighbors.board[i1][j1] = 0;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        Board copy = new Board(board);
        if (t1 != null && t2 != null) {
            swapPair(copy, t1, t2);
            return copy;
        }
        Integer[] tile1 = new Integer[2];
        Integer[] tile2 = new Integer[2];
        while (Arrays.deepEquals(tile1, tile2)) {
            tile1 = randomTile();
            tile2 = randomTile();
        }
        t1 = tile1;
        t2 = tile2;
        swapPair(copy, tile1, tile2);
        return copy;
    }

    private void swapPair(Board copy, Integer[] tile1, Integer[] tile2) {
        int val1 = copy.board[tile1[0]][tile1[1]];
        int val2 = copy.board[tile2[0]][tile2[1]];
        copy.board[tile1[0]][tile1[1]] = val2;
        copy.board[tile2[0]][tile2[1]] = val1;
    }

    private Integer[] randomTile() {
        Integer[] tile = new Integer[2];
        while (true) {
            int i0 = StdRandom.uniform(0, n);
            int j0 = StdRandom.uniform(0, n);
            // exclude tile of value 0
            if (board[i0][j0] != 0) {
                tile[0] = i0;
                tile[1] = j0;
                break;
            }
        }
        return tile;
    }

    // helper method for testing - print goal as string
    private void printGoal() {
        System.out.println("Goal");
        StringBuilder result = new StringBuilder();
        result.append(Integer.toString(n) + System.lineSeparator());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(String.format("%2d ", goal[i][j]));
            }
            result.append(System.lineSeparator());
        }
        System.out.print(result.toString());
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        System.out.println("Input");
        System.out.print(initial.toString());
        initial.printGoal();
        System.out.println("Dimension: " + initial.dimension());
        System.out.println("Hamming; " + initial.hamming());
        System.out.println("Manhattan: " + initial.manhattan());
        System.out.println("Is goal? " + initial.isGoal());
        assert initial.twin() != null;

        System.out.println("Twin:\n " + initial.twin());
        System.out.println("Twin:\n " + initial.twin());
        System.out.println("Twin:\n " + initial.twin());

        System.out.println("Neighbors:\n " + initial.neighbors());
    }
}
