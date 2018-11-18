// Starter code for bounded-size Binary Heap implementation

package ixs171130;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * SP3 Bonus - Binary Heap implementation using Array
 * Tasks Completed: 2, 3, 4(in Huffman Encoder)
 * <p>
 * SP3 Bonus tasks
 *
 * @author Ishan Sharma
 */
public class BinaryHeap<T extends Comparable<? super T>> {
    T[] pq;
    Comparator<T> comp;
    int elements;  // remember the number of elements in priority queue

    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(T[] q) {
        // Use a lambda expression to create comparator from compareTo
        this(q, (T a, T b) -> a.compareTo(b));
    }

    // Constructor for building an empty priority queue with custom comparator
    public BinaryHeap(T[] q, Comparator<T> c) {
        elements = 0;
        pq = q;
        comp = c;
    }

    /**
     * Build a priority queue with a given array q, using q[0..n-1].
     * It is not necessary that n == q.length.
     * Extra space available can be used to add new elements.
     * Implement this if solving optional problem.  To be called from heap sort.
     */
    private BinaryHeap(T[] q, Comparator<T> c, int n) {
        pq = q;
        comp = c;
        elements = n;
    }

    /* sort array arr[].
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
       Implement this for optional problem
     */
    public static <T extends Comparable<? super T>> void heapSort(T[] arr, Comparator<T> c) throws Exception {
        BinaryHeap<T> b = new BinaryHeap<>(arr, c, arr.length);

        b.buildHeap();

        for (int i = arr.length - 1; i >= 0; i--) {
            arr[i] = b.remove();
        }
    }

    // Sort array using natural ordering
    public static <T extends Comparable<? super T>> void heapSort(T[] arr) throws Exception {
        heapSort(arr, (T a, T b) -> a.compareTo(b));
    }

    /**
     * Return the kth largest element of stream using custom comparator.
     * Assume that k is small enough to fit in memory, but the stream is arbitrarily large.
     * If stream has less than k elements return null.
     */
    public static <T extends Comparable<? super T>> Comparable<T> kthLargest(Iterator<T> stream, T[] arr, Comparator<T> c) throws Exception {
        Comparable<T>[] res = (Comparable<T>[]) kLargest(stream, arr, c);
        return res[res.length - 1];
    }

    /**
     * Return the kth largest element of stream using natural ordering.
     * Assume that k is small enough to fit in memory, but the stream is arbitrarily large.
     * If stream has less than k elements return null.
     */
    public static <T extends Comparable<? super T>> T kthLargest(Iterator<T> stream, T[] arr) throws Exception {
        return (T) kthLargest(stream, arr, Comparator.naturalOrder());
    }

    /**
     * Return an array with k largest elements from a stream
     *
     * @param stream Iterator
     * @param arr    Array of size k
     * @param c      Comparator
     * @param <T>    Type
     * @return Array with k largest elements
     * @throws Exception
     */
    public static <T extends Comparable<? super T>> Comparable<? super T>[] kLargest(Iterator<T> stream, T[] arr, Comparator<T> c) throws Exception {
        BinaryHeap<T> b = new BinaryHeap<>(arr, c);

        // put first k elements into the heap
        int k = 0;
        while (stream.hasNext() && k < arr.length) {
            b.offer(stream.next());
            k++;
        }


        // we have less elements than k
        if (b.elements < arr.length) {
            return null;
        }

        // keep adding elements to heap
        while (stream.hasNext()) {
            T x = stream.next();
            b.replace(x);
        }

        Comparable[] res;
        res = Arrays.copyOf(b.pq, k);
        Arrays.sort(res);

        return res;
    }

    public static <T extends Comparable<? super T>> Comparable<? super T>[] kLargest(Iterator<T> stream, T[] arr) throws Exception {
        return kLargest(stream, arr, Comparator.naturalOrder());
    }

    public void add(T x) throws Exception { /* throw exception if pq is full */
        if (elements == pq.length) {
            throw new Exception("Priority queue is full, can't add more elements");
        }

        pq[elements] = x;
        percolateUp(elements);
        elements++;
    }

    public boolean offer(T x) { /* return false if pq is full */
        try {
            add(x);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public T remove() throws Exception { /* throw exception if pq is empty */
        if (elements == 0) {
            throw new Exception("No elements in the priority queue, can't perform remove");
        }

        T min = pq[0];
        if (elements > 1) {
            pq[0] = pq[elements - 1];
            percolateDown(0);
        }
        pq[elements - 1] = null;
        elements--;
        return min;
    }

    public T poll() { /* return false if pq is empty */
        try {
            return remove();
        } catch (Exception e) {
            return null;
        }
    }

    public T peek() { /* return null if pq is empty */
        if (elements > 0) {
            return pq[0];
        }

        return null;
    }

    /**
     * pq[i] may violate heap order with parent
     */
    void percolateUp(int i) { /* to be implemented */
        T x = pq[i];
        while ((i > 0) && (comp.compare(pq[parent(i)], x) > 0)) {
            pq[i] = pq[parent(i)];
            i = parent(i);
        }
        pq[i] = x;
    }

// end of functions for team project


// start of optional problem: heap sort (Q2)

    /**
     * pq[i] may violate heap order with children
     */
    void percolateDown(int i) { /* to be implemented */
        T x = pq[i];
        int c = leftChild(i);
        while (c <= elements - 1) {
            if ((c < elements - 1) && (comp.compare(pq[c], pq[c + 1]) > 0)) {
                c++;
            }
            if (comp.compare(x, pq[c]) < 0) {
                break;
            }
            pq[i] = pq[c];
            i = c;
            c = leftChild(i);
        }
        pq[i] = x;
    }

    // Assign x to pq[i].  Indexed heap will override this method
    void move(int i, T x) {
        pq[i] = x;
    }

    int parent(int i) {
        return (i - 1) / 2;
    }
// end of optional problem: heap sort (Q2)


// start of optional problem: kth largest element (Q3)

    int leftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * Create a heap.  Precondition: none.
     * Implement this ifsolving optional problem
     */
    void buildHeap() {
        // make unused elements null
        // this isn't required but useful for printing array and unit tests
        for (int i = elements - 1; i < pq.length - 1; i++) {
            pq[i] = null;
        }

        for (int i = parent(elements - 1); i >= 0; i--) {
            percolateDown(i);
        }
    }

    public void replace(T x) throws Exception {
	/* TO DO.  Replaces root of binary heap by x if x has higher priority
	     (smaller) than root, and restore heap order.  Otherwise do nothing.
	   This operation is used in finding largest k elements in a stream.
	   Implement this if solving optional problem.
	 */

        if (comp.compare(x, peek()) > 0) {
            remove();
            add(x);
        }
    }
// end of optional problem: kth largest element (Q3)

    // utility functions - mainly for testing
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < pq.length; i++) {
            if (pq[i] != null) {
                output.append(pq[i]).append(" ");
            }
        }

        return output.toString().trim();
    }
}