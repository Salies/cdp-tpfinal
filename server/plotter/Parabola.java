package server.plotter;

public class Parabola extends MathFunction {
    private Double a;
    private Double b;
    private Double c;
    public Parabola(double a, double b, double c, Double x0, Double xF, Double step, String name) {
        super(x0, xF, step, name);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    protected Double y(double x) {
        return (a * x * x) + (b * x) + c;
    }
}
