package func;

public class Linear2 implements Function2 {
    double a1 = 1.;
    double a2 = 1.;
    double b = 0.;

    @Override
    public double value(double x1, double x2) {
        return a1*x1+a2*x2+b;
    }

    @Override
    public double derivative_x1(double x1, double x2) {
        return a1;
    }
}
