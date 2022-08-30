package es.datastructur.synthesizer;
import javax.management.RuntimeErrorException;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T>{
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = rb.length-1 == last ? 0 : last+1;
        fillCount += 1;
        return;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T ans = rb[first];
        first = rb.length-1 == first ? 0 : first+1;
        fillCount -= 1;
        return ans;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        T[] buffer;
        int head;
        int len;
        int iterated;

        public ArrayRingBufferIterator(T[] buffer, int head, int tail) {
            this.buffer = buffer;
            this.head = head;
            this.len = tail >= head ? tail - head + 1 : buffer.length - (head - tail - 1);
            this.iterated = 0;
        }
        public boolean hasNext() {
            return iterated < len;
        }

        public T next() {
            T ans = buffer[head];
            head = head == buffer.length-1 ? 0 : head+1;
            iterated += 1;
            return ans;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator(rb, first, last);
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArrayRingBuffer<?>)) {
            return false;
        }

        ArrayRingBuffer arr = (ArrayRingBuffer) o;
        if (rb.length != arr.rb.length || fillCount != arr.fillCount) {
            return false;
        }

        for (int i = 0; i < rb.length; i++) {
            if (!rb[i].equals(arr.rb[i])) {return false;}
        }
        return true;
    }
}