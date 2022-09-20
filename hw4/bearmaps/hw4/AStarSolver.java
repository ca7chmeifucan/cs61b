package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    SolverOutcome outcome;
    Vertex start;
    Vertex end;
    int dequeue_cnt;
    double time_elapsed;
    double time_bound;
    HashMap<Vertex, Vertex> outcome_map; // key is to, value is from
    HashMap<Vertex, Double> best_distance;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        this.outcome = SolverOutcome.UNSOLVABLE;
        this.start = start;
        this.end = end;
        this.dequeue_cnt = 0;
        this.time_elapsed = 0;
        this.time_bound = timeout;
        this.outcome_map = new HashMap<>();
        this.best_distance = new HashMap<>();
        this.best_distance.put(start, 0.0);
        solve(input, start, end);
    }

    public void solve(AStarGraph<Vertex> input, Vertex start, Vertex end) {
        //initialize the priority queue and add the start vertex into it
        DoubleMapPQ<Vertex> pq = new DoubleMapPQ<>();
        pq.add(start, input.estimatedDistanceToGoal(start, end));
        long starttime = System.nanoTime();
        while (pq.size() > 0) {
            dequeue_cnt += 1;
            Vertex curr_vertex = pq.removeSmallest();

            //time check
            long middletime = System.nanoTime();
            if ((middletime - starttime) / 1e9 > time_bound) {
                this.outcome = SolverOutcome.TIMEOUT;
                this.time_elapsed = time_bound;
                return;
            }

            //check if the popped one is end vertex
            if (curr_vertex.equals(end)) {
                break;
            }

            //relax all edges outgoing from curr_vertex
            relax(input, pq, curr_vertex);
        }
        long endtime = System.nanoTime();
        this.time_elapsed = (endtime - starttime) / 1e9;
        if (best_distance.containsKey(end)) {
            this.outcome = SolverOutcome.SOLVED;
        } else {
            this.outcome = SolverOutcome.UNSOLVABLE;
        }

    }

    public void relax(AStarGraph<Vertex> input, ExtrinsicMinPQ<Vertex> pq, Vertex curr) {
        for (WeightedEdge edge : input.neighbors(curr)) {
            Vertex neighbor = (Vertex) edge.to();
            double weight = edge.weight();
            double distance_start = this.best_distance.get(curr);
            double distance_end = distance_start + weight;
            double h_distance = input.estimatedDistanceToGoal(neighbor, this.end);
            if (distance_end < best_distance.getOrDefault(neighbor, Double.MAX_VALUE)) {
                if (pq.contains(neighbor)) {
                    pq.add(neighbor, distance_end+h_distance);
                } else {
                    pq.changePriority(neighbor, distance_end + h_distance);
                }
                //update outcome relation and best distance
                best_distance.put(neighbor, distance_end);
                outcome_map.put(neighbor, curr);
            }
        }
    }

    public SolverOutcome outcome() {
        return this.outcome;
    }

    public List<Vertex> solution() {
        //can be realized through distance_map
        List<Vertex> res = new ArrayList<>();
        Vertex curr_vertex = this.end;
        res.add(curr_vertex);
        if (this.outcome == SolverOutcome.SOLVED) {
            while (!curr_vertex.equals(this.start)) {
                Vertex source = this.outcome_map.get(curr_vertex);
                res.add(source);
                curr_vertex = source;
            }
            Collections.reverse(res);
        }
        return res;
    }

    public double solutionWeight() {
        //can be realized through distance_map
        if (this.outcome == SolverOutcome.SOLVED) {
            return this.best_distance.get(this.end);
        } else {
            return 0.0;
        }
    }

    public int numStatesExplored() {
        return this.dequeue_cnt;
    }

    public double explorationTime() {
        return this.time_elapsed;
    }
}
