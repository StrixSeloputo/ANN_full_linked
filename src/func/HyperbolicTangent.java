package func;

public class HyperbolicTangent implements Function {
    @Override
    public double value(double x) {
        return Math.tanh(x);
    }

    @Override
    public double derivative(double x) {
        return 1/(Math.cosh(x)*Math.cosh(x));
    }
}
