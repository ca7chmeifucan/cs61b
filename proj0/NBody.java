public class NBody {
    public static double readRadius(String file) {
        In in = new In(file);
        in.readInt();
        return in.readDouble();
    }

    public static Body[] readBodies(String file) {
        In in = new In(file);
        int count = in.readInt();
        in.readDouble();

        Body[] res = new Body[count];
        for (int i = 0; i < count; i++) {
            res[i] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return res;
    }

    public static void draw_background(double radius, String background) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, background);
    }

    public static void draw_bodies(Body[] bodies) {
        //draw the bodies
        for (Body b : bodies) {
            b.draw();
        }
    }

    public static void main(String[] args) {
        double T = Double.valueOf(args[0]);
        double dt = Double.valueOf(args[1]);
        String filename = args[2];
        Body[] bodies = readBodies(filename);
        Double radius = readRadius(filename);
        String background = "./images/starfield.jpg";

        draw_background(radius, background);
        draw_bodies(bodies);
        StdDraw.show();
        StdDraw.pause(200);

        double time = 0;
        while (time <= T) {
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];
            for (int i = 0; i < bodies.length; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].update(dt, xForces[i], yForces[i]);
            }
            draw_background(radius, background);
            draw_bodies(bodies);
            StdDraw.show();
            StdDraw.pause(200);
            time += dt;
        }
    }
}
