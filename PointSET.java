//package coursera;

import java.util.ArrayList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdIn;

public class PointSET {
	private TreeSet<Point2D> set;
	private int size;
	public PointSET() { // construct an empty set of points
		this.set = new TreeSet<Point2D>();
		this.size=0;
	}

	public boolean isEmpty() { // is the set empty?
		return size == 0;
	}

	public int size() { // number of points in the set
		return size;
	}

	public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if(!set.contains(p)) {
		set.add(p);
		size++;
		}
	}

	public boolean contains(Point2D p) { // does the set contain point p?
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return set.contains(p);
	}

	public void draw() { // draw all points to standard draw
		for (Point2D p : set) {
			p.draw();
		}
	}

	public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
		if (rect == null) {
			throw new java.lang.IllegalArgumentException();
		}
		ArrayList<Point2D> list = new ArrayList<Point2D>();
		for (Point2D p : set) {
			if (rect.contains(p)) {
				list.add(p);
			}
		}
		return list;
	}

	public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if (set == null)
			return null;
		double min = Double.MAX_VALUE;
		Point2D result = null;
		for (Point2D q : set) {
			if (min > p.distanceTo(q)) {
				min = p.distanceTo(q);
				result = q;
			}
		}
		return result;
	}

	public static void main(String[] args) { // unit testing of the methods (optional)
		PointSET ps = new PointSET();
		int num = StdIn.readInt();
		for (int i = 0; i < num; i++) {
			double x = StdIn.readDouble();
			double y = StdIn.readDouble();
			Point2D p = new Point2D(x, y);
			ps.insert(p);
			//System.out.println(ps.contains(p));
		}
		//System.out.println(ps.size());
		//System.out.println(ps.nearest(new Point2D(0, 0)));

		RectHV rect = new RectHV(0.45, 0.45, 0.55, 0.55);
		for(Point2D q: ps.range(rect)) {
			System.out.println(q);
			}
	   
		
		ps.draw();
	}
}
