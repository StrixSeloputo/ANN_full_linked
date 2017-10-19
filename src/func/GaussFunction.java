package func;

public class GaussFunction implements Function {
    @Override
    public double value(double x) {
        return Math.exp(-x*x/2);
    }

    @Override
    public double derivative(double x) {
        return -x*Math.exp(-x*x/2);
    }
}
