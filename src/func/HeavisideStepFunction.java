package func;

public class HeavisideStepFunction implements Function {
    @Override
    public double value(double x) {
        if (x==0) {
            return 0.5;
        }
        if (x>0) {
            return 1;
        }
        return 0;
    }

    @Override
    public double derivative(double x) {
        if (x==0) {
            return Double.POSITIVE_INFINITY;
        }
        return 0;
    }
}
