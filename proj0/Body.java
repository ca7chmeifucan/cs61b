public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public double G = 6.67e-11;

    /** First constructor */
    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    /** Second constructor */
    public Body(Body b) {
        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        return Math.pow(Math.pow((b.yyPos - this.yyPos), 2) + Math.pow((b.xxPos - this.xxPos), 2), 0.5);
    }

    public double calcForceExertedBy(Body b) {
        return (this.G * this.mass * b.mass) / (Math.pow(this.calcDistance(b), 2));
    }

    public double calcForceExertedByX(Body b) {
        double force = this.calcForceExertedBy(b);
        double distance = this.calcDistance(b);
        return force*(b.xxPos - this.xxPos)/distance;
    }

    public double calcForceExertedByY(Body b) {
        double force = this.calcForceExertedBy(b);
        double distance = this.calcDistance(b);
        return force*(b.yyPos - this.yyPos)/distance;
    }

    public double calcNetForceExertedByX(Body[] allBodys) {
        double res = 0;
        for (Body b : allBodys) {
            if (this.equals(b)) {
                continue;
            }
            res += this.calcForceExertedByX(b);
        }
        return res;
    }

    public double calcNetForceExertedByY(Body[] allBodys) {
        double res = 0;
        for (Body b : allBodys) {
            if (this.equals(b)) {
                continue;
            }
            res += this.calcForceExertedByY(b);
        }
        return res;
    }

    public void update(double dt, double fx, double fy) {
        double ax = fx/this.mass;
        double ay = fy/this.mass;
        this.xxVel = this.xxVel + dt*ax;
        this.yyVel = this.yyVel + dt*ay;

        this.xxPos = this.xxPos + dt*this.xxVel;
        this.yyPos = this.yyPos + dt*this.yyVel;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "./images/"+this.imgFileName);
    }
}
