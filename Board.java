
//package coursera;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
	private final int[][] blocks;
	private int size;

	public Board(int[][] blocks) { // construct a board from an n-by-n array of blocks
		if (blocks == null || blocks.length != blocks[0].length)
			throw new java.lang.IllegalArgumentException();
		this.size = blocks.length;
		this.blocks = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (blocks[i] == null)
					throw new java.lang.IllegalArgumentException();
				this.blocks[i][j] = blocks[i][j];
			}
		}
	}

	// (where blocks[i][j] = block in row i, column j)
	public int dimension() { // board dimension n
		return size;
	}

	public int hamming() { // number of blocks out of place
		int count = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (blocks[i][j] != 0 && blocks[i][j] != i * size + j + 1)
					count++;
			}
		}
		return count;
	}

	public int manhattan() { // sum of Manhattan distances between blocks and goal
		int sum = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int num = blocks[i][j];
				if (num != 0) {
					int expX = (num - 1) / size;
					int expY = (num - 1) % size;
					sum += Math.abs(expX - i);
					sum += Math.abs(expY - j);
				}
			}
		}
		return sum;
	}

	public boolean isGoal() { // is this board the goal board?
		if (this.hamming() == 0)
			return true;
		else
			return false;
	}

	public Board twin() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - 1; j++) {
				if (blocks[i][j] != 0 && blocks[i][j + 1] != 0)
					return swap(i, j, i, j + 1);
			}
		}
		return null;
	}

	public boolean equals(Object y) { // does this board equal y?
		if (y instanceof Board) {
			Board b2 = (Board) y;
			if (this.size == b2.dimension()) {
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (blocks[i][j] != b2.blocks[i][j])
							return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	public Iterable<Board> neighbors() { // all neighboring boards
		ArrayList<Board> arr = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (blocks[i][j] == 0) {
					if (i - 1 >= 0)
						arr.add(this.swap(i, j, i - 1, j));
					if (j - 1 >= 0)
						arr.add(this.swap(i, j, i, j - 1));
					if (i + 1 < size)
						arr.add(this.swap(i, j, i + 1, j));
					if (j + 1 < size)
						arr.add(this.swap(i, j, i, j + 1));
					return arr;
				}
			}
		}
		return null;
	}

	public String toString() { // string representation of this board (in the output format specified below)
		StringBuilder str = new StringBuilder();
		str.append(size + "\n");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				str.append(String.format("%2d ", blocks[i][j]));

			}
			str.append("\n");
		}
		return str.toString();
	}

	private Board swap(int x1, int y1, int x2, int y2) { // a board that is obtained by swaping given blocks
		int[][] newBlocks = copy();
		newBlocks[x1][y1] = blocks[x2][y2];
		newBlocks[x2][y2] = blocks[x1][y1];
		Board newBoard = new Board(newBlocks);
		return newBoard;
	}

	private int[][] copy() {
		int[][] newBoard = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				newBoard[i][j] = blocks[i][j];
			}
		}
		return newBoard;
	}

	public static void main(String[] args) { // unit tests (not graded)
		int n = StdIn.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				blocks[i][j] = StdIn.readInt();
			}
		}
		Board b = new Board(blocks);
		Board c = new Board(new int[3][3]);
		System.out.println(b.hamming());
		ArrayList<Board> neighbor = (ArrayList<Board>) b.neighbors();
		for (Board btemp : neighbor)
			System.out.println(btemp);
		System.out.println(b.twin());
		System.out.println(b.equals(c));
	}
}