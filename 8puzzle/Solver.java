/* *****************************************************************************
 *  Name: Mingqian Yu
 *  Date: August 10, 2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private boolean solvable;
    private final Stack<Node> path = new Stack<>();

    private class Node {
        private final Board board;
        private int moves;
        private final Node prev;
        private final int manhattan;

        private Node(Board c, int m, Node p) {
            board = c;
            moves = m;
            prev = p;
            manhattan = c.manhattan();
        }
    }

    private class NodesOrder implements Comparator<Node> {
        public int compare(Node o1, Node o2) {
            int o1priority = o1.manhattan + o1.moves;
            int o2priority = o2.manhattan + o2.moves;
            if (o1priority < o2priority) {
                return -1;
            }
            else if (o1priority > o2priority) {
                return 1;
            }
            else {
                return Integer.compare(o1.manhattan, o2.manhattan);
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        Board twin = initial.twin();

        Node start = new Node(initial, 0, null);
        Node twin_start = new Node(twin, 0, null);

        MinPQ<Node> pq = new MinPQ<>(new NodesOrder());
        MinPQ<Node> twin_pq = new MinPQ<>(new NodesOrder());

        pq.insert(start);
        twin_pq.insert(twin_start);

        while (true) {
            Node current = pq.delMin();
            Node twin_current = twin_pq.delMin();

            if (current.board.isGoal()) {
                solvable = true;
                reconstruct(current);
                break;
            }
            else if (twin_current.board.isGoal()) {
                solvable = false;
                reconstruct(twin_current);
                break;
            }

            Iterable<Board> neighbors = current.board.neighbors();
            for (Board neighbor : neighbors) {
                // critical optimization
                if (!current.board.equals(initial)) {
                    if (neighbor.equals(current.prev.board)) continue;
                }
                pq.insert(new Node(neighbor, ++current.moves, current));
                current.moves--;
            }

            Iterable<Board> twin_neighbors = twin_current.board.neighbors();
            for (Board neighbor : twin_neighbors) {
                // critical optimization
                if (!twin_current.board.equals(twin)) {
                    if (neighbor.equals(twin_current.prev.board)) continue;
                }
                twin_pq.insert(new Node(neighbor, ++twin_current.moves, twin_current));
                twin_current.moves--;
            }
        }
    }

    private void reconstruct(Node current) {
        if (current.prev != null) {
            path.push(current);
            reconstruct(current.prev);
        }
        else path.push(current);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        assert path.size() > 0;
        return solvable ? path.size() - 1 : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Queue<Board> q = new Queue<>();
        for (Node n : path) {
            q.enqueue(path.pop().board);
        }
        return q;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board + "Manhattan: " + board.manhattan());
        }
    }
}
