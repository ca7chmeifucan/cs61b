import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver{
    private class TreeNode<Item> {
        Item item;
        TreeNode left;
        TreeNode right;

        public TreeNode() {}
        public TreeNode(Item item) {
            item = item;
            left = null;
            right = null;
        }

        public TreeNode(Item item, TreeNode left, TreeNode right) {
            item = item;
            left = left;
            right = right;
        }
    }

    List<Bear> bears;
    List<Bed> beds;
    int length;

    public BnBSolver(List<Bear> bear, List<Bed> bed) {
        bears = bear;
        beds = bed;
        length = bears.size();
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        // TODO: Fix me.
        TreeNode<Integer> tree = null; //stores the index of the bear
        for (Bed b : beds) {
            int[] bound = pointerbound(tree, (Comparable) b, bears, 0, length-1);
            int l_pointer = bound[0], r_pointer = bound[1];
            int self_index = inplacepartitionbear(bears, l_pointer, r_pointer, b);
            updatetree(tree, b, bears, self_index);
        }
        return bears;
    }

    public int[] pointerbound(TreeNode tree, Comparable comp, List<? extends HiddenComparable> ls, int min, int max) {
        if (tree == null) {
            return new int[] {min, max};
        }
        if (comp.compareTo(ls.get((int) tree.item)) > 0) {
            return pointerbound(tree.right, comp, ls, (int) tree.item + 1, max);
        } else {
            return pointerbound(tree.left, comp, ls, min, (int) tree.item - 1);
        }
    }

    public int inplacepartitionbear(List<Bear> ls, int l, int r, Bed bed) {
        int itself = 0;
        int smallest = l;
        int biggest = r;
        while (l <= r) {
            if (ls.get(l).compareTo(bed) == 0) {
                itself = l;
                l += 1;
            } else if (ls.get(r).compareTo(bed) == 0) {
                itself = r;
                r -= 1;
            } else if (ls.get(l).compareTo(bed) > 0) {
                if (ls.get(r).compareTo(bed) < 0) {
                    //swap and move both
                    inplaceswapbear(ls, l, r);
                    l += 1;
                    r -= 1;
                } else {
                    r -= 1;
                }
            } else {
                if (ls.get(r).compareTo(bed) > 0) {
                    //move right one
                    r -= 1;
                }
                l += 1;
            }
        }
        //swap l/r with itself
        if (r < smallest) { r = smallest; }
        if (l > biggest) { l = biggest; }
        return partitionswitchbear(ls, itself, l, r, bed);
    }

    public int inplacepartitionbed(List<Bed> ls, int l, int r, Bear bear) {
        int itself = 0;
        int smallest = l;
        int biggest = r;
        while (l <= r) {
            if (ls.get(l).compareTo(bear) == 0) {
                itself = l;
                l += 1;
            } else if (ls.get(r).compareTo(bear) == 0) {
                itself = r;
                r -= 1;
            } else if (ls.get(l).compareTo(bear) > 0) {
                if (ls.get(r).compareTo(bear) < 0) {
                    //swap and move both
                    inplaceswapbed(ls, l, r);
                    l += 1;
                    r -= 1;
                } else {
                    r -= 1;
                }
            } else {
                if (ls.get(r).compareTo(bear) > 0) {
                    //move right one
                    r -= 1;
                }
                l += 1;
            }
        }
        //swap l/r with itself
        if (r < smallest) { r = smallest; }
        if (l > biggest) { l = biggest; }
        return partitionswitchbed(ls, itself, l, r, bear);
    }

    public void inplaceswapbear(List<Bear> ls, int ind1, int ind2) {
        Bear b1 = ls.get(ind1);
        Bear b2 = ls.get(ind2);
        ls.set(ind1, b2);
        ls.set(ind2, b1);
    }

    public int partitionswitchbear(List<Bear> ls, int ind, int l, int r, Bed bed) {
        if (ind < l) {
            if (bed.compareTo(ls.get(l))>0) {
                inplaceswapbear(ls, l, ind);
                return l;
            } else {
                inplaceswapbear(ls, r, ind);
                return r;
            }
        } else {
            if (bed.compareTo(ls.get(r))<0) {
                inplaceswapbear(ls, r, ind);
                return r;
            } else {
                inplaceswapbear(ls, l, ind);
                return l;
            }
        }
    }

    public int partitionswitchbed(List<Bed> ls, int ind, int l, int r, Bear bear) {
        if (ind < l) {
            if (bear.compareTo(ls.get(l))>0) {
                inplaceswapbed(ls, l, ind);
                return l;
            } else {
                inplaceswapbed(ls, r, ind);
                return r;
            }
        } else {
            if (bear.compareTo(ls.get(r))<0) {
                inplaceswapbed(ls, r, ind);
                return r;
            } else {
                inplaceswapbed(ls, l, ind);
                return l;
            }
        }
    }

    public void inplaceswapbed(List<Bed> ls, int ind1, int ind2) {
        Bed b1 = ls.get(ind1);
        Bed b2 = ls.get(ind2);
        ls.set(ind1, b2);
        ls.set(ind2, b1);
    }

    public TreeNode updatetree(TreeNode tree, Comparable comp, List<? extends HiddenComparable> ls, int ind) {
        if (tree == null) {
            return new TreeNode(ind);
        }
        if (comp.compareTo(ls.get((int) tree.item)) > 0) {
            tree.right = updatetree(tree.right, comp, ls, ind);
        } else {
            tree.left = updatetree(tree.left, comp, ls, ind);
        }
        return tree;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        // TODO: Fix me.
        TreeNode<Integer> tree = null; //stores the index of the bear
        for (Bear b : bears) {
            int[] bound = pointerbound(tree, (Comparable) b, beds, 0, length-1);
            int l_pointer = bound[0], r_pointer = bound[1];
            int self_index = inplacepartitionbed(beds, l_pointer, r_pointer, b);
            updatetree(tree, b, bears, self_index);
        }
        return beds;
    }
}
