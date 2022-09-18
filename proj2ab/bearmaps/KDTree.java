package bearmaps;

import java.util.List;

public class KDTree implements PointSet{
    public class Node {
        Point item;
        Node left;
        Node right;
        int level;

        public Node(Point p, int level) {
            this.item = p;
            this.level = level;
            this.left = null;
            this.right = null;
        }

        public Node(Point p, Node l, Node r, int level) {
            this.item = p;
            this.level = level;
            this.left = l;
            this.right = r;
        }

        public double distance(Point p) {
            return Point.distance(this.item, p);
        }

        public double shortest_x_distance(Point p) {
            return Math.abs(this.item.getX() - p.getX());
        }

        public double shortest_y_distance(Point p) {
            return Math.abs(this.item.getY() - p.getY());
        }


    }

    Node root;

    public KDTree(List<Point> points) {
        //Initialize the KDTree object
        this.root = null;
        for (Point point : points) {
            this.root = buildkdtree(point, this.root, 1);
        }
    }

    public Node buildkdtree(Point p, Node root, int level) {
        if (root == null) {
            return new Node(p, level);
        }
        if (level % 2 == 1) {
            //compare x
            if (root.item.getX() >= p.getX()) {
                root.left = buildkdtree(p, root.left, level+1);
            } else {
                root.right = buildkdtree(p, root.right, level+1);
            }
        } else {
            //comapre y
            if (root.item.getY() >= p.getY()) {
                root.left = buildkdtree(p, root.left, level+1);
            } else {
                root.right = buildkdtree(p, root.right, level+1);
            }
        }
        return root;
    }

    @Override
    public Point nearest(double x, double y) {
        Point target_point = new Point(x, y);
        return nearest_by_kdtree(this.root, target_point, this.root).item;
    }

    public Node nearest_by_kdtree(Node node, Point target_point, Node best) {
        if (node == null) {
            return best;
        }
        if (node.distance(target_point) < best.distance(target_point)) {
            best = node;
        }

        Node good_side = node.left;
        Node bad_side = node.right;
        if (compare(node, target_point) < 0) {
            good_side = node.right;
            bad_side = node.left;
        }

        best = nearest_by_kdtree(good_side, target_point, best);
        double potential_shortest_bad_side = node.level % 2 == 1 ? node.shortest_x_distance(target_point) : node.shortest_y_distance(target_point);

        if (potential_shortest_bad_side < node.distance(target_point)) {
            best = nearest_by_kdtree(bad_side, target_point, best);
        }
        return best;
    }

    public double compare(Node node, Point p) {
        //compare node and point using the rule determined by the node's level
        if (node.level % 2 == 1) {
            return node.item.getX() - p.getX();
        } else {
            return node.item.getY() - p.getY();
        }
    }
}
