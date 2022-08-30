import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V>{
    public Node root;
    
    public BSTMap() {    }

    public class Node {
        K key;
        V value;
        Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return get(key) != null;
    }

    public V getKeyValue(K key, Node node) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) == 0) {
            return node.value;
        }
        return key.compareTo(node.key) > 0 ? getKeyValue(key, node.right) : getKeyValue(key, node.left);
    }

    public Node getlargestkeynode(Node node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public Node getsmallestkeynode(Node node) {
        if (node == null) {
            return null;
        }

        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public V get(K key) {
        return getKeyValue(key, root);
    }

    @Override
    public int size() {
        return getSize(root);
    }

    public int getSize(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + getSize(node.left) + getSize(node.right);
    }

    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        root = putHelper(key, value, root);
    }

    public Node putHelper(K key, V value, Node node) {
        if (node == null) {
            return new Node(key, value);
        }
        if (key.compareTo(node.key) > 0) {
            node.right = putHelper(key, value, node.right);
        } else if (key.compareTo(node.key) < 0) {
            node.left = putHelper(key, value, node.left);
        } else {
            node.key = key;
            node.value = value;
        }
        return node;
    }

    public void printInOrder() {
        printHelper(root);
    }

    public void printHelper(Node node) {
        if (node == null) {
            return;
        }
        printHelper(node.left);
        System.out.println(node.key);
        printHelper(node.right);
    }

    public Set<K> keySet() {
        Set<K> res = new TreeSet<>();
        return keySetHelper(root, res);
    }

    public Set<K> keySetHelper(Node node, Set<K> set) {
        if (node == null) {
            return set;
        }
        set = keySetHelper(node.left, set);
        set.add(node.key);
        set = keySetHelper(node.right, set);
        return set;
    }

    public V remove(K key) {
        V v_res = get(key);
        root = removeHelper(key, root);
        return v_res;
    }

    public Node removeHelper(K key, Node node) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) > 0) {
            node.right = removeHelper(key, node.right);
        } else if (key.compareTo(node.key) < 0) {
            node.left = removeHelper(key, node.left);
        } else {
            node = swap(node);
        }
        return node;
    }

    public Node swap(Node node) {
        Node return_node = node;
        if (node.left != null) {
            Node largest_node = getlargestkeynode(node.left);
            if (node.left == largest_node) {
                node.left = largest_node.left;
            } else {
                node = node.left;
                while (node.right != largest_node) {
                    node = node.right;
                }
                node.right = largest_node.left; //reconnect
            }
            return_node.key = largest_node.key; //reassign key
            return_node.value = largest_node.value; //reassign value
            return return_node;
        }
        if (node.right != null) {
            Node smallest_node = getsmallestkeynode(node.right);
            if (node.right == smallest_node) {
                node.right = smallest_node.right;
            } else {
                node = node.right;
                while (node.left != smallest_node) {
                    node = node.left;
                }
                node.left = smallest_node.right;
            }
            return_node.key = smallest_node.key;
            return_node.value = smallest_node.value;
            return return_node;
        }
        return null;
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
