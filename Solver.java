
//package coursera;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Solver {
	private class Node {
		public Board curr;
		public Node prev;
		public int priority;
		public int move;

		public Node(Board curr, Node prev, int move) {
			this.curr = curr;
			this.prev = prev;
			this.priority = curr.manhattan() + move;
			this.move = move;
		}
	}

	private Node lastMove;
	private boolean solved;

	public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)

		MinPQ<Node> priorityQ = new MinPQ<>(compareNode());
		Node n = new Node(initial, null, 0);
		priorityQ.insert(n);

		Node n2 = new Node(initial.twin(), null, 0);
		priorityQ.insert(n2);

		while (true) {
			Node node1 = priorityQ.delMin();
			if (node1.curr.isGoal()) {
				this.lastMove = node1;
				this.solved = true;
				break;
			}
			run(node1, priorityQ);
		}
		Node temp = lastMove;
		while (temp.prev != null) {
			temp = temp.prev;
		}
		if (temp.equals(n2)) {
			this.solved = false;
			this.lastMove = null;
		}
	}

	private void run(Node node, MinPQ<Node> queue) {
		Iterable<Board> arr = node.curr.neighbors();
		int move = node.move;
		for (Board b1 : arr) {
			if (node.prev == null || !b1.equals(node.prev.curr)) {
				Node newNode = new Node(b1, node, move + 1);
				queue.insert(newNode);
			}
		}
	}

	private Comparator<Node> compareNode() {
		return new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				if (o1.priority > o2.priority)
					return 1;
				else if (o1.priority < o2.priority)
					return -1;
				else
					return 0;
			}

		};
	}

	public boolean isSolvable() { // is the initial board solvable?
		return this.solved;
	}

	public int moves() { // min number of moves to solve initial board; -1 if unsolvable
		if (this.solved)
			return lastMove.move;
		return -1;
	}

	public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if unsolvable
		if (!this.solved)
			return null;
		ArrayList<Board> result = new ArrayList<>();
		Node temp = lastMove;
		while (temp != null) {
			result.add(temp.curr);
			temp = temp.prev;
		}
		Collections.reverse(result);
		return result;
	}

	public static void main(String[] args) { // solve a slider puzzle (given below)
		int n = StdIn.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = StdIn.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
