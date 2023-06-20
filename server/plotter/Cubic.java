package server.plotter;

public class Cubic extends MathFunction {
    private Double a;
    private Double b;
    private Double c;
    private Double d;

    public Cubic(double a, double b, double c, double d, double x0, double xF, double step, String name) {
        super(x0, xF, step, name);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    Double y(double x) {
        return a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
    }
}
