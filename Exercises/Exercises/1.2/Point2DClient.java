import edu.princeton.cs.algs4.Point2D;

public class Point2DClient {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Point2D[] p = new Point2D[N];
        double result = Double.MAX_VALUE;
        StdOut.println(result);

        for (int i = 0; i < N; i++) {
            double x = Math.random();
            double y = Math.random();
            p[i] = new Point2D(x, y);
            for (int j = 0; j < i; j++) {
                if (p[i].distanceTo(p[j]) < result) {
                    result = p[i].distanceTo(p[j]);
                }
            }
        }
        StdOut.printf("%7.5f", result);
    }
}
