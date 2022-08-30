package hw2;

import java.util.Random;

public class PercolationStats {
    int[] record;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        record = new int[T];
        for (int t = 0; t < T; t++) {
            Percolation p = pf.make(N);
            record[t] = experiment(p, N);
        }
    }

    public int experiment(Percolation p, int dim) {
        int steps = 0;
        int[] checked = new int[dim*dim];
        int rand_row = new Random().nextInt(dim), rand_col = new Random().nextInt(dim);
        while (!p.percolates()) {
            steps += 1;
            while (checked[rand_row*dim+rand_col] == 1) {
                rand_row = new Random().nextInt(dim);
                rand_col = new Random().nextInt(dim);
            }
            checked[rand_row*dim+rand_col] = 1;
            p.open(rand_row, rand_col);
        }
        return steps;
    }

    public double mean() {
        double sum = 0;
        for (int r : record) {
            sum += r;
        }
        return sum/record.length;
    }

    public double stddev() {
        double avg = mean();
        double sum = 0;
        for (int r : record) {
            sum += Math.pow(r-avg, 2);
        }
        return Math.sqrt(sum/(record.length-1));
    }

    public double confidenceLow() {
        return mean() - 1.96*stddev()/Math.sqrt(record.length);
    }

    public double confidenceHigh() {
        return mean() + 1.96*stddev()/Math.sqrt(record.length);
    }
}
