import java.util.ArrayList;
import java.util.List;

public class MyTrieSet implements TrieSet61B{
    private static final int R = 128;
    private Node root;

    private static class Node {
        private boolean isKey;
        private Node[] next;

        private Node() {
            isKey = false;
            next = new Node[R];
        }
    }

    public MyTrieSet() {
        root = new Node();
    }

    @Override
    public void clear() {
        root = new Node();
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node node = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (node.next[(int) c] == null) {
                node.next[(int) c] = new Node();
            }
            node = node.next[(int) c];
        }
        node.isKey = true;
    }

    @Override
    public boolean contains(String key) {
        Node node = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            node = node.next[(int) c];
            if (node == null) {
                return false;
            }
        }
        return node.isKey == true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> res = new ArrayList<String>();
        if (prefix == null || prefix.length() < 1) {
            return res;
        }

        Node node = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            node = node.next[(int) c];
            if (node == null) {
                return res;
            }
        }

        res = iterationhelper(prefix, node);
        return res;
    }

    public List<String> iterationhelper(String prefix, Node root) {
        List<String> res = new ArrayList<String>();
        if (root.isKey) { res.add(prefix); }

        for (int i = 0; i < R; i++) {
            Node node = root.next[i];
            char c = (char) i;
            if (node != null) {
                List<String> child_res = iterationhelper(prefix+c, node);
                res.addAll(child_res);
            }
        }
        return res;
    }

    @Override
    public String longestPrefixOf(String prefix) {
        if (prefix == null || prefix.length() < 1) {
            return "";
        }

        Node node = root;
        String res = "";
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (node.next[(int) c] == null) {
                return res;
            } else {
                res += c;
                node = node.next[(int) c];
            }
        }
        return res;
    }

}
