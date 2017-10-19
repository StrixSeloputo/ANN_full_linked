package func;

public class Sigmoid implements Function {

    @Override
    public String toString() {
        return "Sigmoid: 1/(1+exp(-x))";
    }

    @Override
    public double value(double x) {
        return 1/(1+Math.exp(-x));
    }

    @Override
    public double derivative(double x) {
        return Math.exp(-x)/((1+Math.exp(-x))*(1+Math.exp(-x)));
    }
}
