package func;

public class LogarithmicFunction implements Function {
    @Override
    public double value(double x) {
        return Math.log(x + Math.hypot(x,1));
    }

    @Override
    public double derivative(double x) {
        return 1/Math.hypot(x, 1);
    }
}
