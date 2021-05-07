/* *****************************************************************************
 *  Name: Mingqian Yu
 *  Date: August 17th
 *  Description: 2DTree API
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;

public class KdTree {

    private static final boolean VERTICAL = true;
    private int size;
    private Node root;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D p) {
            this.p = p;
        }
    }

    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!contains(p)) {
            size++;
            root = insert(root, p, VERTICAL);
            if (root.rect == null) root.rect = new RectHV(0, 0, 1, 1);
        }
    }

    private Node insert(Node node, Point2D p, boolean orient) {
        if (node == null) return new Node(p);
        int cmp;
        // todo: do NOT go to right subtree, break tie instead
        if (orient) {
            cmp = Double.compare(p.x(), node.p.x());
            if (cmp == 0)
                cmp = Double.compare(p.y(), node.p.y());
        }
        else {
            cmp = Double.compare(p.y(), node.p.y());
            if (cmp == 0)
                cmp = Double.compare(p.x(), node.p.x());
        }

        if (cmp < 0) {
            node.lb = insert(node.lb, p, !orient);
            // update RectHV for LB nodes
            if (node.lb.rect == null)
                node.lb.rect = setRect(node, cmp, orient);
        }
        else if (cmp > 0) {
            node.rt = insert(node.rt, p, !orient);
            // update RectHV for RT nodes
            if (node.rt.rect == null)
                node.rt.rect = setRect(node, cmp, orient);
        }
        return node;
    }

    private RectHV setRect(Node node, int cmp, boolean orient) {
        double x1 = node.rect.xmin();
        double x2 = node.rect.xmax();
        double y1 = node.rect.ymin();
        double y2 = node.rect.ymax();
        if (cmp < 0) {
            if (orient) x2 = node.p.x();
            else y2 = node.p.y();
        }
        else if (cmp > 0) {
            if (orient) x1 = node.p.x();
            else y1 = node.p.y();
        }
        return new RectHV(x1, y1, x2, y2);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node node, Point2D p, boolean orient) {
        if (node == null) return false;
        int cmp;
        if (orient) {
            cmp = Double.compare(p.x(), node.p.x());
            if (cmp == 0) cmp = Double.compare(p.y(), node.p.y());
        }
        else {
            cmp = Double.compare(p.y(), node.p.y());
            if (cmp == 0) cmp = Double.compare(p.x(), node.p.x());
        }
        if (cmp < 0) return contains(node.lb, p, !orient);
        else if (cmp > 0) return contains(node.rt, p, !orient);

        return true;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, VERTICAL);
    }

    private void draw(Node node, boolean orient) {
        if (node == null) return;
        // draw points
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(node.p.x(), node.p.y());
        // draw partitions
        if (orient) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        if (!orient) {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        // draw recursively
        draw(node.lb, !orient);
        draw(node.rt, !orient);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<>();
        stack = range(root, rect, stack);
        return stack;
    }

    private Stack<Point2D> range(Node node, RectHV rect, Stack<Point2D> stack) {
        // todo: placeholder for null rectangle input
        if (rect == null) rect = new RectHV(0, 0, 0, 0);
        if (node == null) return stack;

        if (node.lb != null) {
            if (node.lb.rect.intersects(rect))
                stack = range(node.lb, rect, stack);
        }
        if (node.rt != null) {
            if (node.rt.rect.intersects(rect))
                stack = range(node.rt, rect, stack);
        }
        if (rect.contains(node.p))
            stack.push(node.p);
        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        assert root != null;
        Point2D point = root.p;
        point = nearest(root, p, point);
        return point;
    }

    private Point2D nearest(Node node, Point2D target, Point2D nearest) {
        if (target == null) return null;
        if (node == null) return nearest;

        if (target.distanceSquaredTo(node.p) < target.distanceSquaredTo(nearest))
            nearest = node.p;

        if (node.lb != null) {
            if (node.lb.rect.contains(target))
                nearest = nearest(node.lb, target, nearest);
        }

        if (node.rt != null) {
            if (node.rt.rect.contains(target))
                nearest = nearest(node.rt, target, nearest);
        }

        if (node.rt != null)
            if (!node.rt.rect.contains(target))
                if (node.rt.rect.distanceSquaredTo(target) < target.distanceSquaredTo(nearest))
                    nearest = nearest(node.rt, target, nearest);

        if (node.lb != null)
            if (!node.lb.rect.contains(target))
                if (node.lb.rect.distanceSquaredTo(target) < target.distanceSquaredTo(nearest))
                    nearest = nearest(node.lb, target, nearest);
                
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        // draw all of the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        kdtree.draw();
        StdDraw.pause(30);
    }

}
