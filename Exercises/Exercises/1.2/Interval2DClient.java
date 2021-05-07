import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.Interval2D;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.HashMap;
import java.util.Map;


public class Interval2DClient {
    private static final double RADIUS = 0.01;
    private static Map<Interval2D, Interval1D[]> intervalMap = new HashMap<>();

    private static boolean isContained(Interval2D box1, Interval2D box2) {
        Interval1D[] itv1 = intervalMap.get(box1);
        double x1Min = itv1[0].min();
        double x1Max = itv1[0].max();
        double y1Min = itv1[1].min();
        double y1Max = itv1[1].max();

        Interval1D[] itv2 = intervalMap.get(box2);
        double x2Min = itv2[0].min();
        double x2Max = itv2[0].max();
        double y2Min = itv2[1].min();
        double y2Max = itv2[1].max();

        Point2D p1a = new Point2D(x1Min, y1Min);
        Point2D p1b = new Point2D(x1Max, y1Min);
        Point2D p1c = new Point2D(x1Max, y1Min);
        Point2D p1d = new Point2D(x1Max, y1Max);

        Point2D p2a = new Point2D(x2Min, y2Min);
        Point2D p2b = new Point2D(x2Max, y2Min);
        Point2D p2c = new Point2D(x2Max, y2Min);
        Point2D p2d = new Point2D(x2Max, y2Max);

        if (box1.contains(p2a) && box1.contains(p2b) && box1.contains(p2c) && box1.contains(p2d)) {
            return true;
        }
        else if (box2.contains(p1a) && box2.contains(p1b) && box2.contains(p1c) && box2
                .contains(p1d)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        double min = Double.parseDouble(args[1]);
        double max = Double.parseDouble(args[2]);
        Interval2D[] box = new Interval2D[n];

        int countIntersect = 0;
        int countContains = 0;

        for (int i = 0; i < n; i++) {
            double x1 = StdRandom.uniform(min, max);
            double x2 = StdRandom.uniform(min, max);
            Interval1D x = new Interval1D(Math.min(x1, x2), Math.max(x1, x2));

            double y1 = StdRandom.uniform(min, max);
            double y2 = StdRandom.uniform(min, max);
            Interval1D y = new Interval1D(Math.min(y1, y2), Math.max(y1, y2));

            box[i] = new Interval2D(x, y);
            intervalMap.put(box[i], new Interval1D[] { x, y });

            StdDraw.setPenRadius(RADIUS);
            StdDraw.setPenColor(StdDraw.BLUE);
            box[i].draw();

            for (int j = 0; j < i; j++) {
                if (box[i].intersects(box[j])) {
                    StdOut.printf("Interval %s intersects Interval %s", box[i], box[j]);
                    StdOut.println();
                    countIntersect++;
                }
                if (isContained(box[i], box[j])) {
                    countContains++;
                }
            }
        }
        StdOut.println("Total intersections: " + countIntersect);
        StdOut.println("Total contained: " + countContains);
    }

}
