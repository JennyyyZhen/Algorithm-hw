
//package coursera;

import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
	private Point[] points;
	private LineSegment[] lineSegments;

	public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
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
		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				for (int k = j + 1; k < points.length; k++) {
					for (int l = k + 1; l < points.length; l++) {
						double slopeij = points[i].slopeTo(points[j]);
						double slopeik = points[i].slopeTo(points[k]);
						double slopeil = points[i].slopeTo(points[l]);
						if (slopeij == slopeik && slopeik == slopeil) {
							Point[] arr = new Point[4];
							arr[0] = points[i];
							arr[1] = points[j];
							arr[2] = points[l];
							arr[3] = points[k];
							Arrays.sort(arr, new Comparator<Point>() {
								@Override
								public int compare(Point o1, Point o2) {
									return o1.compareTo(o2);
								}
							});
							LineSegment ls = new LineSegment(arr[0], arr[3]);
							result.add(ls);
						}
					}
				}
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
		BruteCollinearPoints bcp = new BruteCollinearPoints(input);
		LineSegment[] result = bcp.segments();
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
		System.out.println(bcp.numberOfSegments());
		bcp.segments();
		bcp.segments();
		System.out.println(bcp.numberOfSegments());
	}
}
