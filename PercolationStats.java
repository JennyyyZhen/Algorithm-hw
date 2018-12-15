package coursera;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private double[] thresholds;
	private int trials;
	private int size;

	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		if (N <= 0)
			throw new java.lang.IllegalArgumentException("N is out of bounds");
		if (T <= 0)
			throw new java.lang.IllegalArgumentException("T is out of bounds");

		this.size = N;
		this.trials = T;
		this.thresholds = new double[trials];

		for (int i = 0; i < T; i++) {
			thresholds[i] = findThreshold();
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(thresholds);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		if (trials == 1)
			return Double.NaN;
		return StdStats.stddev(thresholds);
	}

	// returns lower bound of the 95% confidence interval
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(trials);
	}

	// returns upper bound of the 95% confidence interval
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(trials);
	}

	private double findThreshold() {
		Percolation perc = new Percolation(size);
		int i, j;
		int count = 0;
		while (!perc.percolates()) {
			do {
				i = StdRandom.uniform(size) + 1;
				j = StdRandom.uniform(size) + 1;
			} while (perc.isOpen(i, j));
			count++;
			perc.open(i, j);
			perc.isFull(i,j);
			System.out.println(count);
		}
		return count / (Math.pow(size, 2));
	}

	// test client, described below
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats stats = new PercolationStats(N, T);
		StdOut.printf("mean = %f\n", stats.mean());
		StdOut.printf("stddev = %f\n", stats.stddev());
		StdOut.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
	}
}
