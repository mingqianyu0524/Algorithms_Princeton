import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Interval1DClient {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Interval1D[] line = new Interval1D[n];

        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform();
            double y = StdRandom.uniform();
            double min = Math.min(x, y);
            double max = Math.max(x, y);
            line[i] = new Interval1D(min, max);
            for (int j = 0; j < i; j++) {
                if (line[i].intersects(line[j])) {
                    StdOut.printf("Interval 1 - Min: %.3f Max: %.3f  ", line[i].min(),
                                  line[i].max());
                    StdOut.printf("Interval 2 - Min: %.3f Max: %.3f  ", line[j].min(),
                                  line[j].max());
                    StdOut.println();
                }
            }
        }

    }
}
