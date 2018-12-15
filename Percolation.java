package coursera;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

	  private int size;
	  private int width;
	  private int[] status;
	  private WeightedQuickUnionUF uf;
	  private boolean percolate;
	  private int openSites;

	  // create N-by-N grid, with all sites blocked
	  public Percolation(int N) {
		  if(N<=0) {
			  throw new IllegalArgumentException();
		  }
	    size = N*N;
	    width = N;
	    status = new int[size];
	    uf = new WeightedQuickUnionUF(size);
	    openSites=0;
	    percolate=false;
	    for(int i=0;i<size;i++) {
	    	if(i>=0 && i<width) {
	    		byte b=010;//blocked,connected to top,not connected to bottom
	    		status[i]=status[i]|2;
	    	}
	    	if(i>=size-width && i<size) {
	    		status[i]=status[i]|1;
	    	}
	    }
	  }

	  // open site (row i, column j) if it is not already
	  public void open(int i, int j) {
	    checkBounds(i, j);
	    if(isOpen(i,j)) return;
	    int num = ijTo1D(i, j);
	    status[num]= status[num]|4;
	    connectToOpenNeighbors(i, j);
	    int root=uf.find(num);
	    status[root]=status[num]|status[root];
	   // System.out.println(root+ " "+status[root]);
	    if(status[root]==7)
	    	percolate=true;
	    openSites++;
	  }

	  // is site (row i, column j) open?
	  public boolean isOpen(int i, int j) {
	    checkBounds(i, j);
	    int num = ijTo1D(i, j);
	    return (status[num] >=4);
	  }

	  // is site (row i, column j) full?
	  public boolean isFull(int i, int j) {
	    checkBounds(i, j);
	    return status[uf.find(ijTo1D(i,j))]>=6;
	  }

	  // does the system percolate?
	  public boolean percolates() {
	    return percolate;
	  }
	  public int numberOfOpenSites() {
		  return openSites;
	  }

	  private void connectToOpenNeighbors(int i, int j) {
	    int index = ijTo1D(i, j);
	    if (j < width) attemptUnion(i, j+1, index);
	    if (j > 1) attemptUnion(i, j-1, index);

	    if (i < width) {
	      attemptUnion(i+1, j, index);
	    }
	    /*else {
	      uf.union(index, size+1);
	    }
	    */
	    if (i > 1) {
	      attemptUnion(i-1, j, index);
	    }/* else {
	      uf.union(index, size);
	    }*/
	  }

	  private void attemptUnion(int i, int j, int index) {
	    if (isOpen(i, j)) {
	     int num = ijTo1D(i, j);
	     status[index]=status[uf.find(num)]|status[index];
	      uf.union(num, index);
	      int root=uf.find(index);
	      status[root]=status[index];
	    }
	  }

	  // converts index in ij notation to a single 1D index (zero-indexed)
	  private int ijTo1D(int i, int j) {
	    return ((i*width - width) + j) - 1;
	  }

	  private void checkBounds(int i, int j) {
	    if (i <= 0 || i > width) {
	      throw new java.lang.IllegalArgumentException("row index i out of bounds");
	    }
	    if (j <= 0 || j > width) {
	      throw new java.lang.IllegalArgumentException("row index i out of bounds");
	    }
	  }

	  public static void main(String[] args) {
	    Percolation perc = new Percolation(3);
	    perc.open(1, 2);
	    perc.open(2, 2);
	    perc.open(2, 3);
	    perc.open(3, 3);
	    boolean c = perc.isFull(1, 1);
	    //boolean c1 = perc.uf.connected(perc.ijTo1D(1, 1), perc.ijTo1D(2, 1));
	    //boolean c2 = perc.percolates();
	    StdOut.println(perc.numberOfOpenSites());
	    //StdOut.println(c1);
	    //StdOut.println(c2);
	  }

