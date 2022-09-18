package bearmaps;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private class pack {
        private T item;
        private double priority;

        pack() {}
        pack(T t, double p) {
            this.item = t;
            this.priority = p;
        }

        public T get_item() {
            return this.item;
        }

        public double get_priority() {
            return this.priority;
        }

        public void update_priority(double new_priority) {
            this.priority = new_priority;
        }

        public double compare(pack other) {
            return this.get_priority() - other.get_priority();
        }
    }

    private ArrayList<pack> ls;
    private HashMap<T, Integer> map;
    private int size;

    public ArrayHeapMinPQ() {
        ls = new ArrayList<>();
        map = new HashMap<>();
        size = 0;
        //need to fill in an arbitrarily starting pack
        ls.add(new pack());
    }

    @Override
    public boolean contains(T t) {
        return map.containsKey(t);
    }

    @Override
    public void add(T t, double priority) {
        if (contains(t)) {
            throw new IllegalArgumentException();
        }
        size += 1;
        ls.add(new pack(t, priority));
        //climb operation to be added
        promote(size);
        map.put(t, size);
    }

    @Override
    public T getSmallest() {
        if (size < 1) {
            throw new NoSuchElementException();
        }
        return ls.get(1).get_item();
    }

    @Override
    public T removeSmallest() {
        if (size < 1) {
            throw new NoSuchElementException();
        }

        pack smallest_pack = ls.get(1);
        if (size == 1) {
            ls.remove(1);
        } else {
            ls.set(1, ls.get(size));
            ls.remove(size);
            size -= 1;
            demote(1);
        }
        return smallest_pack.get_item();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T t, double priority) {
        if (!contains(t)) {
            throw new NoSuchElementException();
        }
        int cur_ind = map.get(t);
        ls.get(cur_ind).update_priority(priority);
        promote(cur_ind);
        demote(cur_ind);
    }

    private void promote(int ind) {
        if (ind == 1) {
            return;
        }
        int parent_ind = ind/2;
        pack child_pack = ls.get(ind);
        pack parent_pack = ls.get(parent_ind);
        //compare two packs and swap if cur ind is smaller than its parent
        if (child_pack.compare(parent_pack) < 0) {
            //swap values at these two indices
            ls.set(ind, parent_pack);
            ls.set(parent_ind, child_pack);
            map.put(parent_pack.get_item(), ind);
            map.put(child_pack.get_item(), parent_ind);
            promote(parent_ind);
        }
    }

    private void demote(int ind) {
        if (ind >= size || size < ind*2) {
            return;
        }

        pack parent_pack = ls.get(ind);
        if (size == ind*2) { //only has left child
            int left_ind = ind*2;
            pack left_pack = ls.get(left_ind);
            if (parent_pack.compare(left_pack) > 0) {
                ls.set(ind, left_pack);
                ls.set(left_ind, parent_pack);
                map.put(left_pack.get_item(), ind);
                map.put(parent_pack.get_item(), left_ind);
            }
        } else {
            int left_ind = ind*2;
            int right_ind = ind*2+1;
            pack left_pack = ls.get(left_ind);
            pack right_pack = ls.get(right_ind);
            if (left_pack.compare(right_pack) <= 0) {
                if (parent_pack.compare(left_pack) > 0) {
                    ls.set(ind, left_pack);
                    ls.set(left_ind, parent_pack);
                    map.put(left_pack.get_item(), ind);
                    map.put(parent_pack.get_item(), left_ind);
                    demote(left_ind);
                }
            } else {
                if (parent_pack.compare(right_pack) > 0) {
                    ls.set(ind, right_pack);
                    ls.set(right_ind, parent_pack);
                    map.put(right_pack.get_item(), ind);
                    map.put(parent_pack.get_item(), right_ind);
                    demote(right_ind);
                }
            }
        }
    }

}
