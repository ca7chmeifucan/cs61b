public class LinkedListDeque<type> {
    public class Node {
        public type item;
        public Node prev;
        public Node next;

        public Node() {        }
        public Node(type item) {
            this.item = item;
        }
        public Node(type item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    public int size;
    public Node sentinel;

    public LinkedListDeque() {
        this.size = 0;
        this.sentinel = new Node();
        this.sentinel.prev = sentinel;
        this.sentinel.next = sentinel;
    }

    public LinkedListDeque(Node n) {
        this.sentinel = new Node();
        this.sentinel.prev = n;
        this.sentinel.next = n;
        n.next = sentinel;
        n.prev = sentinel;
        this.size = 0;
    }

    public void addFirst(type item) {
        Node nxt = this.sentinel.next;
        Node curr = new Node(item, sentinel, nxt);
        this.sentinel.next = curr;
        nxt.prev = curr;
        this.size += 1;
    }

    public void addLast(type item) {
        Node prv = this.sentinel.prev;
        Node curr = new Node(item, prv, sentinel);
        this.sentinel.prev = curr;
        prv.next = curr;
        this.size += 1;
    }

    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        Node curr = sentinel;
        if (this.size == 0) {
            System.out.print("");
            System.out.println();
            return;
        }
        curr = curr.next;
        while (curr != sentinel) {
            System.out.print(curr.item);
            System.out.print(" ");
            curr = curr.next;
        }
        System.out.println();
    }

    public type removeFirst() {
        if (this.size == 0) {
            return null;
        }
        Node fst = sentinel.next;
        sentinel.next = fst.next;
        fst.next.prev = sentinel;
        this.size -= 1;
        return fst.item;
    }

    public type removeLast() {
        if (this.size == 0) {
            return null;
        }
        Node lst = sentinel.prev;
        sentinel.prev = lst.prev;
        lst.prev.next = sentinel;
        this.size -= 1;
        return lst.item;
    }

    public type get(int index) {
        if (index < this.size - 1) {
            return null;
        }
        Node curr = sentinel;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.item;
    }
}
