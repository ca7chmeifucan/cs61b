package bearmaps.proj2ab;

import java.util.List;

public class NaivePointSet implements PointSet {
    List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point res_point = this.points.get(0);
        Point target_point = new Point(x, y);
        double smallest_distance = Double.MAX_VALUE;
        for (Point curr_point : this.points) {
            double distance_to_target = Point.distance(curr_point, target_point);
            if (distance_to_target < smallest_distance) {
                smallest_distance = distance_to_target;
                res_point = curr_point;
            }
        }
        return res_point;
    }
}
