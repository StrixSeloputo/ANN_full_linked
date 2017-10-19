package func;

public class QuadraticDifference implements Function2 {
    @Override
    public double value(double x1, double x2) {
        return (x1-x2)*(x1-x2);
    }

    @Override
    public double derivative_x1(double x1, double x2) {
        return 2*(x1-x2);
    }
}
