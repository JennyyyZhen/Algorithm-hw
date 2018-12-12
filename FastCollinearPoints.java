
//package coursera;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
	private Point[] points;
	private LineSegment[] lineSegments;

	public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
		if (points == null) {
			throw new java.lang.IllegalArgumentException();
		}
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points.length; j++) {
				if (points[j] == null || j != i && points[i].compareTo(points[j]) == 0)
					throw new java.lang.IllegalArgumentException();
			}
		}
		this.points = points;
	}

	public int numberOfSegments() { // the number of line segments
		if (lineSegments == null)
			lineSegments = segments();
		return lineSegments.length;
	}

	public LineSegment[] segments() { // the line segments
		if (lineSegments != null)
			return lineSegments;
		ArrayList<LineSegment> result = new ArrayList<>();
		Arrays.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				return o1.compareTo(o2);
			}
		});
		for (int i = 0; i < points.length; i++) {
			Point[] slope = Arrays.copyOf(points, points.length);
			Arrays.sort(slope, points[i].slopeOrder());
			double tempS = points[i].slopeTo(slope[0]);
			int begin = 0;
			for (int j = 0; j < points.length; j++) {
				if (points[i].slopeTo(slope[j]) == tempS && j != points.length - 1)
					continue;
				else {
					int end;
					if (points[i].slopeTo(slope[j]) != tempS)
						end = j - 1;
					else
						end = j;
					if (end - begin >= 2 && points[i].compareTo(slope[begin]) < 0) {
						Point min = points[i];
						Point max = slope[end];
						LineSegment ls = new LineSegment(min, max);
						result.add(ls);
					}
				}
				begin = j;
				tempS = points[i].slopeTo(slope[j]);
			}
		}
		lineSegments = new LineSegment[result.size()];
		lineSegments = result.toArray(lineSegments);
		return lineSegments;

	}

	public static void main(String[] args) {
		int num = StdIn.readInt();
		Point[] input = new Point[num];
		for (int i = 0; i < num; i++) {
			int x = StdIn.readInt();
			int y = StdIn.readInt();
			Point p = new Point(x, y);
			input[i] = p;
		}
		FastCollinearPoints fcp = new FastCollinearPoints(input);
		LineSegment[] result = fcp.segments();
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
		System.out.println(fcp.numberOfSegments());
	}
}
