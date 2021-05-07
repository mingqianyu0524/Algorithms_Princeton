import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int N;
    private boolean[][] grid;
    private int top;
    private int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Invalid argument, failed to generate grids");
        N = n;
        top = 0;
        bottom = N * N + 1;
        uf = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > N) throw new IllegalArgumentException("row index out of bounds");
        if (col <= 0 || col > N) throw new IllegalArgumentException("column index out of bounds");
        grid[row - 1][col - 1] = true;
        // site to open is at top row
        if (row == 1) {
            uf.union(xyTo1D(row, col), top);
        }
        if (row == N) {
            uf.union(xyTo1D(row, col), bottom);
        }
        // union neighboring site at left
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
        }
        // union neighboring site at right
        if (col < N) {
            if (isOpen(row, col + 1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
        // union neighboring site at top
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        // union neighboring site at bottom
        if (row < N && isOpen(row + 1, col)) {
            uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
    }

    //map a 2-dimensional pair to a 1-dimensional union find object index
    private int xyTo1D(int x, int y) {
        return (x - 1) * N + y;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > N) throw new IllegalArgumentException("row index out of bounds");
        if (col <= 0 || col > N) throw new IllegalArgumentException("column index out of bounds");
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > N) throw new IllegalArgumentException("row index out of bounds");
        if (col <= 0 || col > N) throw new IllegalArgumentException("column index out of bounds");
        top = uf.find(top);
        if (uf.find(xyTo1D(row, col)) == top) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == true) {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    // does the system percolate?
    public boolean percolates() {
        if (uf.find(top) == uf.find(bottom)) {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        // PercolationVisualizer.main(args);
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            StdOut.println("percolates = " + perc.percolates());
        }
    }
}
