import java.util.*;

public class MyHashMap<K extends Comparable<K>, V> implements Map61B<K,V>{
    private double load_factor;
    private int m; //hashmap size
    private int n; //number of key-value pairs
    private ArrayList<pair>[] ls;
    private HashSet<K> key_set = new HashSet<K>();

    private class pair {
        private K key;
        private V value;

        private pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public MyHashMap() {
        this.load_factor = 0.75;
        this.m = 16;
        this.n = 0;
        this.ls = new ArrayList[16];
        for (int i = 0; i < 16; i++) {
            ls[i] = new ArrayList<pair>();
        }
    }

    public MyHashMap(int initialSize) {
        this.load_factor = 0.75;
        this.m = initialSize;
        this.n = 0;
        this.ls = new ArrayList[initialSize];
        for (int i = 0; i < initialSize; i++) {
            ls[i] = new ArrayList<pair>();
        }
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.load_factor = loadFactor;
        this.m = initialSize;
        this.n = 0;
        this.ls = new ArrayList[initialSize];
        for (int i = 0; i < initialSize; i++) {
            ls[i] = new ArrayList<pair>();
        }
    }

    public int hash(K key) {
        return Math.abs(key.hashCode());
    }

    @Override
    public void clear() {
        int cur_length = this.ls.length;
        this.ls = new ArrayList[cur_length];
        this.m = this.ls.length;
        this.n = 0;
        for (int i = 0; i < cur_length; i++) {
            ls[i] = new ArrayList<pair>();
        }
        this.key_set.clear();
    }

    @Override
    public boolean containsKey(K key) {
        return this.key_set.contains(key);
    }

    @Override
    public V get(K key) {
        int index = hash(key) % m;
        for (pair p : ls[index]) {
            if (p.key.equals(key)) {
                return p.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public void put(K key, V value) {
        int index = hash(key) % m;
        if (containsKey(key)) {
            for (int i = 0; i < ls[index].size(); i++) {
                if (ls[index].get(i).key.equals(key)) {
                    ls[index].remove(i);
                    ls[index].add(new pair(key, value));
                }
            }
        } else {
            ls[index].add(new pair(key, value));
            key_set.add(key);
            n += 1;
        }

        if (n/m > load_factor) {
            m *= 2;
            ArrayList<pair>[] new_ls = new ArrayList[m]; //resize
            for (int i = 0; i < m; i++) {
                new_ls[i] = new ArrayList<pair>();
            }
            //reset pair list, key list
            for (int i = 0; i < ls.length; i++) {
                for (pair p : ls[i]) {
                    int temp_index = hash(p.key) % m;
                    new_ls[temp_index].add(new pair(p.key, p.value));
                }
            }
            ls = new_ls;
        }
    }

    @Override
    public Set<K> keySet() {
        return key_set;
    }

    @Override
    public V remove(K key) {
        V value = null;
        if (!containsKey(key)) {
            return value;
        }

        n -= 1;
        int index = hash(key) % m;
        for (int i = 0; i < ls[index].size(); i++) {
            if (ls[index].get(i).key.equals(key)) {
                value = ls[index].get(i).value;
                ls[index].remove(i);
            }
        }
        return value;
    }

    @Override
    public V remove(K key, V value) {
        if (!containsKey(key)) {
            return null;
        } else {
            boolean find = false;
            n -= 1;
            int index = hash(key) % m;
            for (int i = 0; i < ls[index].size(); i++) {
                if (ls[index].get(i).key.equals(key) && ls[index].get(i).value.equals(value)) {
                    find = true;
                    ls[index].remove(i);
                }
            }
            return find == true ? value : null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return key_set.iterator();
    }


}
