//package coursera;

import java.util.ArrayList;
import java.awt.Color;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

public class KdTree {
	private class Node {
		public boolean vertical;
		public Node left;
		public Node right;
		public Point2D point;
		public RectHV rect;

		public Node(Point2D p) {
			if (p == null)
				throw new java.lang.IllegalArgumentException();
			this.point = p;
			this.left = null;
			this.right = null;
			this.vertical = true;
			this.rect=null;
		}
	}

	private Node root;
	private int size;

	public KdTree() { // construct an empty set of points
		this.root = null;
		this.size = 0;
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
		if (root == null) {
			root = new Node(p);
			size++;
		} else {
			if(!contains(p)) {
			insert(p, root, true);
			size++;
			}
		}
		
	}

	private Node insert(Point2D p, Node n, boolean direction) {
		if (n == null) {
			Node newN = new Node(p);
			newN.vertical = direction;
			return newN;
		}
		if (direction && p.x() < n.point.x() || !direction && p.y() < n.point.y()) {
			n.left = insert(p, n.left, !direction);
		} else {
			n.right = insert(p, n.right, !direction);
		}
		return n;
	}

	public boolean contains(Point2D p) { // does the set contain point p?
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return contains(p, root);
	}

	private boolean contains(Point2D p, Node n) {
		if (n == null)
			return false;
		if (n.point.equals(p))
			return true;
		if (n.vertical && p.x() < n.point.x() || !n.vertical && p.y() < n.point.y()) {
			return contains(p, n.left);
		} else {
			return contains(p, n.right);
		}
	}

	public void draw() { // draw all points to standard draw
		draw(root,0,1,0,1);
	}

	private void draw(Node n,double xmin,double xmax,double ymin,double ymax) {
		if (n == null)
			return;
		double x=n.point.x();
		double y=n.point.y();
		n.rect=new RectHV(xmin,ymin,xmax,ymax);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledCircle(x,y,0.01);
		if(n.vertical) {
			StdDraw.setPenColor(Color.RED);
			StdDraw.line(x,ymin,x,ymax);
			draw(n.left,xmin,x,ymin,ymax);
			draw(n.right,x,xmax,ymin,ymax);
		}
		else {
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.line(xmin,y,xmax,y);
			draw(n.left,xmin,xmax,ymin,y);
			draw(n.right,xmin,xmax,y,ymax);
		}
		
	}
	public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
		if (rect == null) {
			throw new java.lang.IllegalArgumentException();
		}
		ArrayList<Point2D> list = new ArrayList<>();
		range(rect, list, root);
		return list;
	}

	private void range(RectHV rect, ArrayList<Point2D> list, Node n) {
		if (n == null)
			return;
		if (rect.contains(n.point)) {
			list.add(n.point);
			range(rect, list, n.left);
			range(rect, list, n.right);
		} 
		else {
			double xmin = rect.xmin();
			double xmax = rect.xmax();
			double ymin = rect.ymin();
			double ymax = rect.ymax();
			if (n.vertical) {
				if (n.point.x() < xmin)
					range(rect, list, n.right);
				else if (n.point.x() > xmax) {
					range(rect, list, n.left);}
				else {
					range(rect, list, n.left);
					range(rect, list, n.right);
				}
			} else {
				if (n.point.y() < ymin) {
					range(rect, list, n.right);}
				else if (n.point.y() > ymax) {
					range(rect, list, n.left);}
				else {
					range(rect, list, n.left);
					range(rect, list, n.right);
				}
			}
		}
	}

	public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		draw();
		double[] minDistance = new double[] { Double.MAX_VALUE };
		Point2D[] minP = new Point2D[1];
		nearest(root, minP, minDistance, p);
		return minP[0];
	}

	private void nearest(Node n, Point2D[] minP, double[] minDistance, Point2D p) {
		if (n == null)
			return;
		double distance = n.point.distanceTo(p);
		if (distance < minDistance[0]) {
			minP[0] = n.point;
			minDistance[0] = distance;
		}
		//System.out.println(n.point+""+minP[0]);
		if (n.vertical) {
			if (p.x() < n.point.x()) {
				if (n.right!=null && minDistance[0] > n.right.rect.distanceTo(p)) {
					nearest(n.left,minP,minDistance,p);
					nearest(n.right, minP, minDistance, p);
				}
			} else {
				nearest(n.right, minP, minDistance, p);
				if (n.left!=null && minDistance[0] > n.left.rect.distanceTo(p)) {
					nearest(n.left, minP, minDistance, p);
					
				}
			}
		} else {
			if (p.y() < n.point.y()) {
				nearest(n.left, minP, minDistance, p);
				if (n.right!=null && minDistance[0] > n.right.rect.distanceTo(p)) {
					nearest(n.right, minP, minDistance, p);
				}
			} else {
				nearest(n.right, minP, minDistance, p);
				if (n.left!=null && minDistance[0] > n.left.rect.distanceTo(p)) {
					nearest(n.left, minP, minDistance, p);	
				}
			}
		}
	}

	public static void main(String[] args) { // unit testing of the methods (optional)
		KdTree kt = new KdTree();
		int num=StdIn.readInt();
		for(int i=0;i<num;i++) {
			String a=StdIn.readString();
			double x=StdIn.readDouble();
			double y=StdIn.readDouble();
			kt.insert(new Point2D(x,y));
		}
		//kt.insert(new Point2D(0, 0.375));
		//kt.insert(new Point2D(0.375, 0.625));
		RectHV rect = new RectHV(0.063, 0.214, 0.483, 0.63);
		kt.draw();
		Point2D p= new Point2D(1.0,0.375);
		StdDraw.filledCircle(p.x(),p.y(),0.01);
		System.out.println(kt.nearest(p));
		
		for (Point2D q : kt.range(rect)) {
			//System.out.println(q);
		}
	}
}