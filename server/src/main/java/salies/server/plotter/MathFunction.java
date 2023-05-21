package salies.server.plotter;

import salies.server.Task;

import java.awt.Image;
import java.io.Serializable;

abstract class MathFunction implements Task<Image>, Serializable {
    abstract Image plot();

    public Image execute() {
        return plot();
    }
}