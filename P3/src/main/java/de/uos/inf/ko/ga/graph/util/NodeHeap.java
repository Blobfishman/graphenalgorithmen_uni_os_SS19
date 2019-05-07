package de.uos.inf.ko.ga.graph.util;
/**
 * Use of an old template i did in INF-B
 */

import java.util.Arrays;
import java.util.PriorityQueue;

public class NodeHeap extends PriorityQueue {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_SIZE = 10;
    private Node[] heap;
    private int size;

    /**
     * Constructs a new GenBinHeap.
     */
    public NodeHeap () {
        // Stupid Java doesn't allow init generic arrays
        heap = new Node[DEFAULT_SIZE];
        size = 0;
    }


    /**
     * Adds a value to the min-heap.
     * @return true if insert was succsessful
     */
    public boolean add(Node value) {
        //resize array
        if (size >= heap.length - 1) {
            heap = resize();
        }
        size++;
        int index = size;
        //place at the end
        heap[index] = value;

        shiftUp();
        return true;
    }

    /**
     * true if the heap has no elements; false otherwise.
     * @return true if size == 0 else false
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Returns the minimum element in the heap. Does not remove it
     * @return The min element
     */
    public Node peek() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }

        return heap[1];
    }

    /**
     * Removes and returns the minimum element in the heap.
     * @return The minimal element
     */
    public Node remove() {
        Node result = peek();

        //new root is last leaf
        heap[1] = heap[size];
        heap[size] = null;
        size--;

        //reconstruct heap
        shiftDown();

        return result;
    }


    /**
     * Genarates a javastring of the Heap
     * @return A String with all Data that coes from every Node.toString
     */
    @Override
    public String toString() {
        return Arrays.toString(heap);
    }

    /**
     * Print to std::cout
     */
    public void print()
    {
        for (int i = 1; i <= size / 2; i++) {
            System.out.print(" ROOT : " + heap[i] + " LEFT NODE : " + heap[2 * i] + " RIGHT NODE :"
                    + heap[2 * i + 1] + "\n");
        }
    }


    /**
     * Shifts node down till HeapStructure is restored
     */
    private void shiftDown() {
        int index = 1;

        while (hasLeftChild(index)) {
            //compare chield Nodes
            int smallerChild = leftIndex(index);

            // shft down with smaller Node if any
            if (hasRightChild(index) && heap[leftIndex(index)].compareTo(heap[rightIndex(index)]) > 0) {
                smallerChild = rightIndex(index);
            }

            if (heap[index].compareTo(heap[smallerChild]) > 0) {
                swap(index, smallerChild);
            } else {
                //Nothing to do
                break;
            }
            index = smallerChild;
        }
    }


    /**
     * Shifts node up till HeapStructure is restored
     */
    private void shiftUp() {
        int index = this.size;

        while (hasParent(index)  && (parent(index).compareTo(heap[index]) > 0)) {
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }
    }

    /**
     * Check for parent if any
     * @param i The Node-Index to check parent for
     * @return true if any. False if root
     */
    private boolean hasParent(int i) {
        return i > 1;
    }

    /**
     * Get left index of node i
     * @param i Current index Node
     * @return index of the left node of current
     */
    private int leftIndex(int i) {
        return i * 2;
    }

    /**
     * Get right index of node i
     * @param i Current index Node
     * @return index of the right node of current
     */
    private int rightIndex(int i) {
        return i * 2 + 1;
    }

    /**
     * Check for left child, if any
     * @param i Node-index to check left node for
     * @return true if threre is a left child
     */
    private boolean hasLeftChild(int i) {
        return leftIndex(i) <= size;
    }

    /**
     * Check for right child, if any
     * @param i Node-index to check right node for
     * @return true if threre is a right child
     */
    private boolean hasRightChild(int i) {
        return rightIndex(i) <= size;
    }

    /**
     * Get parent of index i
     * @param i The index to get Parent Node form
     * @return The parent-Node of i
     */
    private Node parent(int i) {
        return heap[parentIndex(i)];
    }

    /**
     * Get parent-index  of index i
     * @param i The index to get Parent Node form
     * @return The parent-Node-index of i
     */
    private int parentIndex(int i) {
        return i / 2;
    }

    /**
     * Resize Array
     * @return new rezized array
     */
    public Node[] resize() {
        return Arrays.copyOf(heap, heap.length * 2);
    }

    /**
     * Swap two Nodes by index
     * @param index1 Index1 to swap with index2
     * @param index2 Index2 to swap with index1
     */
    private void swap(int index1, int index2) {
        Node tmp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = tmp;
    }
}
