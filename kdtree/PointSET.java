/* *****************************************************************************
 *  Name: Mingqian Yu
 *  Date: August 17th
 *  Description: Brute Force API
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!set.contains(p)) {
            set.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : set)
            point.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<>();
        for (Point2D point : set) {
            if (rect.contains(point))
                stack.push(point);
        }
        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        double dist = Double.POSITIVE_INFINITY;
        Point2D nearest = null;
        for (Point2D point : set) {
            if (p.distanceSquaredTo(point) < dist) {
                dist = p.distanceSquaredTo(point);
                nearest = point;
            }
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET pointSET = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            pointSET.insert(p);
        }

        // draw all of the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        pointSET.draw();
        StdDraw.pause(30);
    }
}
