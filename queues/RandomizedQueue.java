/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == a.length) {
            resize(2 * a.length);
        }
        a[size++] = item;
        swap();
    }

    private void swap() {
        int i = StdRandom.uniform(size);
        Item temp = a[i];
        a[i] = a[size - 1];
        a[size - 1] = temp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        if (size == a.length / 4) {
            resize(a.length / 2);
        }
        Item item = a[--size];
        a[size] = null;
        return item;
    }

    private void resize(int capacity) {
        assert capacity >= size;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = a[i];
        }
        a = copy;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        int i = StdRandom.uniform(size);
        return a[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        // Item[] b = create();
        // StdRandom.shuffle(a, 0, size - 1);
        Iterator<Item> randomizedIterator = new RandomizedArrayIterator();
        // restore(b);
        return randomizedIterator;
    }

    private class RandomizedArrayIterator implements Iterator<Item> {
        private int i = size;

        public boolean hasNext() {
            try {
                return i != 0;
            }
            catch (ConcurrentModificationException ignored) {
                return i != 0;
            }
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            else {
                Item item = a[--i];
                return item;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Item[] create() {
        // create a copy of the current array
        Item[] b = (Item[]) new Object[size];
        for (int j = 0; j < size; j++) {
            b[j] = a[j];
        }
        return b;
    }

    private void restore(Item[] b) {
        // restore from saved copy
        for (int k = 0; k < size; k++) {
            a[k] = b[k];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }
}
