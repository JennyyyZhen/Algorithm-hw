package coursera;

public class Percolation {
	private int n;
	private int openSites;
	private int[] size;
	private int[] connection;
	private boolean percolate;

	public Percolation(int n) { // create n-by-n grid, with all sites blocked
		this.n = n;
		this.size = new int[n * n + 1];
		this.connection = new int[n * n + 1];
		this.openSites = 0;
		this.percolate = false;
	}

	public void open(int row, int col) { // open site (row, col) if it is not open already
		if (row < 1 || col < 1 || row > n || col > n) {
			throw new java.lang.IllegalArgumentException();
		}
		int index = (row - 1) * n + col - 1;
		size[index] = 1;
		connection[index] = index;
		if (row == 1)
			connection[index] = n * n;
		if (row - 1 >= 1 && isOpen(row - 1, col)) {
			union(index, index - n);
		}
		if (row + 1 <= n && isOpen(row + 1, col)) {
			union(index, index + n);
		}
		if (col - 1 >= 1 && isOpen(row, col - 1)) {
			union(index, index - 1);
		}
		if (col + 1 <= n && isOpen(row, col + 1)) {
			union(index, index + 1);
		}
		openSites++;
		// System.out.println(row+" "+col+" "+connection[index]);
	}

	private void union(int index1, int index2) {
		if (root(index1) == n * n) {
			connection[root(index2)] = n * n;
			return;
		}
		if (root(index2) == n * n) {
			connection[root(index1)] = n * n;
			return;
		}
		if (size[root(index1)] > size[root(index2)]) {
			size[root(index1)] += size[root(index2)];
			connection[root(index2)] = connection[root(index1)];
		} else {
			size[root(index2)] += size[root(index1)];
			connection[root(index1)] = connection[root(index2)];
		}
		// System.out.println(index1+" "+index2+" "+connection[index1]);
	}

	private int root(int leaf) {
		if (connection[leaf] == leaf)
			return leaf;
		if (connection[leaf] == n * n || connection[leaf] == n * n + 1) {
			return connection[leaf];
		} else
			return root(connection[leaf]);
	}

	public boolean isOpen(int row, int col) { // is site (row, col) open?
		if (row < 1 || col < 1 || row > n || col > n) {
			throw new java.lang.IllegalArgumentException();
		}
		row = row - 1;
		col = col - 1;
		return !(size[row * n + col] == 0);
	}

	public boolean isFull(int row, int col) { // is site (row, col) full?
		if (row < 1 || col < 1 || row > n || col > n) {
			throw new java.lang.IllegalArgumentException();
		}
		if (!isOpen(row, col))
			return false;

		// addWater();
		// System.out.println(row+" "+col+" "+ root((row-1)*n+col-1));
		boolean f = root((row - 1) * n + col - 1) == n * n;
		if (f && row == n)
			percolate = true;
		return f;
	}

	public int numberOfOpenSites() { // number of open sites
		return openSites;
	}

	public boolean percolates() { // does the system percolate?

		return percolate;
	}

	private void addWater() {
		connection[n * n] = n * n;
		connection[n * n + 1] = n * n + 1;
		size[n * n] = 1;
		size[n * n + 1] = 1;
		for (int i = 1; i <= n; i++) {
			if (isOpen(1, i)) {
				union2(n * n, i - 1);
			}
		}
	}

	private void union2(int node, int index) {
		connection[root(index)] = node;
	}
	/*
	 * public static void main(String[] args) { // test client (optional)
	 * Percolation p= new Percolation(3); p.open(1,1); p.open(1,2); p.open(0,1);
	 * p.open(2,2); p.open(2,0); //System.out.println(p.isOpen(1,1));
	 * //System.out.println(p.size[4]); System.out.println(p.isFull(2,0)); }
	 */
}