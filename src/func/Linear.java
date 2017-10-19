package func;

public class Linear implements Function {
    double a = 1.;
    double b = 0.;

    public Linear() {}
    public Linear(double slope, double intercept) {
        a = slope;
        b = intercept;
    }

    @Override
    public String toString() {
        return String.format("Linear: %.5f*x+%.5f", a, b);
    }

    @Override
    public double value(double x) {
        return a*x + b;
    }

    @Override
    public double derivative(double x) {
        return a;
    }
}
