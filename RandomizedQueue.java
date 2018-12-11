package coursera;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int size;
	private Item[] arr;

	public RandomizedQueue() { // construct an empty randomized queue
		this.arr = (Item[]) new Object[1];
		this.size = 0;
	}

	public boolean isEmpty() { // is the randomized queue empty?
		return size == 0;
	}

	public int size() { // return the number of items on the randomized queue
		return size;
	}

	public void enqueue(Item item) { // add the item
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if (size == arr.length) {
			this.resize(2 * size);
		}
		arr[size++] = item;
	}

	public Item dequeue() { // remove and return a random item
		if (size == 0) {
			throw new java.util.NoSuchElementException();
		}
		int i = (int) (Math.random() * size);
		if (size == arr.length / 4) {
			resize(arr.length / 2);
		}
		size--;
		Item it = arr[i];
		arr[i] = arr[size];
		arr[size] = null;
		return it;
	}

	public Item sample() { // return a random item (but do not remove it)
		if (size == 0) {
			throw new java.util.NoSuchElementException();
		}
		int i = (int) (Math.random() * size);
		Item it = arr[i];
		return it;
	}

	private void shuffle() {
		if (size > 1) {
			for (int i = 0; i < size; i++) {
				int temp = (int) (Math.random() * (i + 1));
				Item it1 = arr[i];
				arr[i] = arr[temp];
				arr[temp] = it1;
			}
		}
	}

	public Iterator<Item> iterator() { // return an independent iterator over items in random order
		this.shuffle();
		return new Iterator<Item>() {
			int index = 0;

			@Override
			public boolean hasNext() {
				if (index >= size)
					return false;
				else
					return true;
			}

			@Override
			public Item next() {
				if (index >= size) {
					throw new java.util.NoSuchElementException();
				}
				Item it = arr[index];
				index++;
				return it;
			}

			public void remove() {
				throw new java.lang.UnsupportedOperationException();
			}
		};
	}

	private void resize(int n) {
		Item[] newArr = (Item[]) new Object[n];
		for (int i = 0; i < size; i++) {
			newArr[i] = this.arr[i];
		}
		this.arr = newArr;
	}

	public static void main(String[] args) { // unit testing (optional)
		RandomizedQueue<String> rq = new RandomizedQueue<>();
		rq.enqueue("sss");
		rq.enqueue("aaa");
		rq.enqueue("bbb");
		rq.enqueue("ccc");
		rq.enqueue("ddd");
		rq.enqueue("eee");
		Iterator it = rq.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println();
		Iterator it2 = rq.iterator();
		while (it2.hasNext()) {
			System.out.println(it2.next());
		}

	}
}