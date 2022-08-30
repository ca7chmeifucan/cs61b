import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    Comparator<Flight> start_comp = Comparator.comparingInt(i -> i.startTime);
    Comparator<Flight> end_comp = Comparator.comparingInt(i -> i.endTime);
    PriorityQueue<Flight> start_flights;
    PriorityQueue<Flight> end_flights;


    public FlightSolver(ArrayList<Flight> flights) {
        start_flights = new PriorityQueue<>(start_comp);
        end_flights = new PriorityQueue<>(end_comp);

        for (Flight flight : flights) {
            start_flights.add(flight);
            end_flights.add(flight);
        }
    }

    public int solve() {
        /* FIX ME */
        int global_max = 0;
        int curr_max = 0;
        while(!start_flights.isEmpty() && !end_flights.isEmpty()) {
            Flight start = start_flights.peek();
            Flight end = end_flights.peek();
            if (end.endTime >= start.startTime) {
                start_flights.poll();
                curr_max += start.passengers;
            } else {
                end_flights.poll();
                curr_max -= end.passengers;
            }
            global_max = Math.max(curr_max, global_max);
        }
        return global_max;
    }

}
