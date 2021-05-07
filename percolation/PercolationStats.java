import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int counts;
    private double mean;
    private double stddev;
    private double[] fractions;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Given n <= 0 || t <= 0");
        }
        counts = trials;
        fractions = new double[counts];
        Percolation p;

        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while (!p.percolates()) {
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);
                while (!p.isOpen(x, y)) {
                    p.open(x, y);
                }
            }
            fractions[i] = (double) p.numberOfOpenSites() / (n * n);
            //StdOut.println(fractions[i]);
        }
        //Initiate instance variables to be used by confidenceLo and confidenceHi
        mean = StdStats.mean(fractions);
        stddev = StdStats.stddev(fractions);
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(fractions);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(fractions);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - 1.96 * stddev / (Math.sqrt(counts));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + 1.96 * stddev / (Math.sqrt(counts));
    }

    // test client (see below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
