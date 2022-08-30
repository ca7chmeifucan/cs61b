package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int[] grid;
    WeightedQuickUnionUF uf;
    int dim;
    int opened;

    public Percolation(int N) {
        //create N by N grid, with all sites initially blocked
        grid = new int[N*N];
        uf = new WeightedQuickUnionUF(N*N);
        dim = N;
        opened = 0;
    }


    public void validate(int row, int col) {
        if ((row < 0 || col < 0) || (row >= dim || col >= dim)) {
            throw new IllegalArgumentException();
        }
    }

    public boolean rangecheck(int row, int col) {
        if ((row < 0 || col < 0) || (row >= dim || col >= dim)) {
            return false;
        }
        return true;
    }
    public void open(int row, int col) {
        //open the site (row, col) if it is not open already
        validate(row, col);
        if (!isOpen(row, col)) {
            grid[row*dim+col] = 1;
            opened += 1;
            connectNeighbor(row, col);
        }
    }

    public void connectNeighbor(int row, int col) {
        //top
        if (rangecheck(row-1, col) && isOpen(row-1, col)) {
            uf.union(row*dim+col, (row-1)*dim+col);
        }

        //right
        if (rangecheck(row, col+1) && isOpen(row, col+1)) {
            uf.union(row*dim+col, row*dim+col+1);
        }

        //bottom
        if (rangecheck(row+1, col) && isOpen(row+1, col)) {
            uf.union(row*dim+col, (row+1)*dim+col);
        }

        //left
        if (rangecheck(row, col-1) && isOpen(row, col-1))  {
            uf.union(row*dim+col, row*dim+col-1);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        // is the site (row, col) open?
        return grid[row*dim+col] == 1;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            return false;
        }

        for (int i = 0; i < dim; i++) {
            if (uf.connected(row*dim+col, i)) {
                return true;
            }
        }
        return false;
    }

    public int numberofOpenSites() {
        return opened;
    }

    public boolean percolates() {
        for (int top = 0; top < dim; top++) {
            for (int bottom = 0; bottom < dim; bottom++) {
                if (uf.connected(top, dim*(dim-1)+bottom)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
