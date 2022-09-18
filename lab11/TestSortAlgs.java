import edu.princeton.cs.algs4.Queue;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(2);
        queue.enqueue(1);
        queue.enqueue(10);
        queue.enqueue(1);

        assertTrue(isSorted(QuickSort.quickSort(queue)));
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> queue = new Queue<>();
        for (int i = 10; i > 0; i-=2) {
            queue.enqueue(i);
        }

        assertTrue(isSorted(MergeSort.mergeSort(queue)));

    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
