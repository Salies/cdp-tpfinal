package salies.server.plotter;

public class Line extends MathFunction {
    private Double a;
    private Double b;
    public Line(double a, double b, double x0, double xF, double step, String name) {
        super(x0, xF, step, name);
        this.a = a;
        this.b = b;
    }

    protected Double y(double x) {
        return a * x + b;
    }
}
