/* *****************************************************************************
 *  Name: Mingqian Yu
 *  Date: August 1st, 2020
 *  Description: FastCollinearPoints implementation using Arrays.sort()[TimSort].
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private int segCount = 0;
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException("Empty points set");
        }

        Arrays.sort(points);
        Point[] aux = Arrays.copyOf(points, points.length);
        this.segments = new ArrayList<>();

        for (int i = 0; i < aux.length; i++) {
            aux = Arrays.copyOf(points, points.length);
            Arrays.sort(aux, points[i].slopeOrder());
            Point p = aux[0];
            if (p == null) {
                throw new IllegalArgumentException("Null point in set");
            }

            int count = 0;
            boolean isSeg = false;
            Point startPt;
            Point endPt;

            for (int j = 1; j < aux.length; j++) {
                if (p.slopeTo(aux[j - 1]) == p.slopeTo(aux[j])) {
                    count++;

                    if (count > 1) {
                        isSeg = true;
                    }

                    if (j == aux.length - 1 && isSeg) {
                        startPt = aux[j - count];
                        endPt = aux[j];
                        addSegment(p, startPt, endPt);
                        isSeg = false;
                        count = 0;
                    }
                }
                else {
                    if (isSeg) {
                        startPt = aux[j - count - 1];
                        endPt = aux[j - 1];
                        addSegment(p, startPt, endPt);
                        isSeg = false;
                    }
                    count = 0;
                }
            }
        }
    }

    private void addSegment(Point p0, Point start, Point end) {
        if (p0.compareTo(start) < 0) {
            LineSegment segment = new LineSegment(p0, end);
            this.segments.add(segment);
            this.segCount++;
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segCount;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.segments.toArray(LineSegment[]::new);
    }
}
