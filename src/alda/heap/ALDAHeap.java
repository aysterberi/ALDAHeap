/**
 * Billy G. J. Beltran(bibe1744) & Joakim Berglund(jobe7147)
 * Contact details: billy@caudimordax.org, joakimberglund@live.se
 */

package alda.heap;
//
// CONSTRUCTION: with optional capacity (that defaults to 100)
//               or an array containing initial items
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements a d-ary heap
 * Index for elements begins at 1, not 0.
 * Originally based on Mark Allen Weiss' binary heap implementation
 * Completely rewritten.
 */
public class ALDAHeap<AnyType extends Comparable<? super AnyType>> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int BINARY_HEAP = 2;

    private int d; // Number of children for each node
    private int currentSize; // Number of elements in heap
    private AnyType[] array; // The heap array

    /**
     * Construct the binary heap by sending 2 (binary) to the second constructor.
     */
    public ALDAHeap() {
        this(BINARY_HEAP);
    }

    /**
     * Construct the binary heap.
     *
     * @param d the specific amount of children per node in the heap.
     */
    public ALDAHeap(int d) {
        if (d < 2) {
            throw new IllegalArgumentException();
        }
        currentSize = 0;
        this.d = d;
        array = (AnyType[]) new Comparable[DEFAULT_CAPACITY * d + 1];
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     *
     * @param x the item to insert.
     */
    public void insert(AnyType x) {
        if (size() == array.length - 1) {
            enlargeArray((array.length * d) + 1);
        }

        int hole = ++currentSize;
        array[hole] = x;
        bubbleUp(hole);
    }

    /**
     * Remove the smallest item from the priority queue.
     *
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin() {
        if (isEmpty())
            throw new UnderflowException();

        AnyType minItem = findMin();
        array[1] = array[currentSize--];
        siftDown(1);

        return minItem;
    }

    /**
     * Find the smallest item in the priority queue.
     *
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin() {
        if (isEmpty())
            throw new UnderflowException();
        return array[1];
    }

    public int size() {
        return currentSize;
    }

    AnyType get(int index) {
        return array[index];
    }

    public int parentIndex(int child) {
        if (child < 2) {
            throw new IllegalArgumentException();
        }
        return (child - 2) / d + 1;
    }

    public int firstChildIndex(int parent) {
        if (parent < 1) {
            throw new IllegalArgumentException();
        }
        return d * (parent - 1) + 2;
    }


    private void enlargeArray(int newSize) {
        AnyType[] old = array;
        array = (AnyType[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++)
            array[i] = old[i];
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap() {
        for (int i = currentSize / d; i > 0; i--)
            siftDown(i);
    }

    /**
     * Test if the priority queue is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty() {
        currentSize = 0;
    }

    /**
     * Internal method to sift down in the heap.
     *
     * @param hole the index at which the sift down begins.
     */
    private void siftDown(int hole) {
        int child;
        int tmpChild;
        AnyType tmp = array[hole];

        for (; firstChildIndex(hole) <= size(); hole = tmpChild) {
            child = firstChildIndex(hole);
            tmpChild = child;

            for (int i = 1; i < d; i++) {
                if ((child != size()) &&
                        (child + i < size() + 1) &&
                        (array[child + i].compareTo(array[tmpChild]) < 0)) {
                    tmpChild = child + i;
                }
            }
            if (array[tmpChild].compareTo(tmp) < 0) {
                array[hole] = array[tmpChild];
            } else {
                break;
            }
        }
        array[hole] = tmp;
    }

    /**
     * Internal method to bubble up in the heap.
     *
     * @param hole the index at which the bubble up begins.
     */
    private void bubbleUp(int hole) {
        AnyType tmp = array[hole];

        while (hole > 1) {
            int parIndex = parentIndex(hole);
            AnyType parent = array[parIndex];
            if (tmp.compareTo(parent) >= 0) {
                break;
            }
            array[parIndex] = tmp;
            array[hole] = parent;
            hole = parIndex;
        }
    }

    // Test program
    public static void main(String[] args) {
        int numItems = 10000;
        ALDAHeap<Integer> h = new ALDAHeap<>();
        int i = 37;

        for (i = 37; i != 0; i = (i + 37) % numItems)
            h.insert(i);
        for (i = 1; i < numItems; i++)
            if (h.deleteMin() != i)
                System.out.println("Oops! " + i);
    }
}