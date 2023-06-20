package server;

public class Sin extends MathFunction {
    public Sin(double x0, double xF, double step, String name) {
        super(x0, xF, step, name);
    }

    Double y(double x) {
        return Math.sin(Math.toRadians(x));
    }
}
