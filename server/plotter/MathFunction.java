package server.plotter;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;
import compute.Task;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/*
    Este serviço plota uma função dada função matemática, dentre as disponíveis:
    - Afim
    - Quadrática (parábola)
    - Cúbica
    - Seno
    O cliente passa o x inicial e o x final, bem como outros parâmetros, a depender da função.
    Ex.: para a parábola, o cliente passa, além dos x inicias e finais, os parâmetros a, b e c.
    Já para o seno, passa apenas o x inicial e o x final (em graus).

    Esta é uma classe abstrata geral. Cada função tem sua respectiva classe, herdeira desta.
 */
abstract class MathFunction implements Task<BufferedImage>, Serializable {
    private Double x0;
    private Double xF;
    private Double step;
    private String name;
    abstract Double y(double x);

    protected MathFunction(double x0, double xF, double step, String name) {
        this.x0 = x0;
        this.xF = xF;
        this.step = step;
        this.name = name;
    }

    private BufferedImage plot() {
        int arraySize = (int) ((xF - x0) / step);
        double[] x = new double[arraySize];
        double[] y = new double[arraySize];
        for (int i = 0; i < arraySize; i++) {
            x[i] = x0 + (i * step);
            y[i] = y(x[i]);
        }
        XYChart chart = QuickChart.getChart(name, "x", "y", "y(x)", x, y);
        return BitmapEncoder.getBufferedImage(chart);
    }

    public BufferedImage execute() {
        return plot();
    }
}