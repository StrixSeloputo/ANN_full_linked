package ann;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Synapse {
    public Synapse() {
        initWeights();
    }

    public Synapse(Network network, Neuron from, Neuron to) {
        N = network;
        A = from;
        A.outSynapse.add(this);
        B = to;
        B.inSynapse.add(this);
        initWeights();
    }

    @Override
    public String toString() {
        return String.format("\t\"%s\" -> \"%s\" [ label = \"%.5f\" ];\n", A, B, weight);
    }

//    public double getGrad() {
//        return grad = B.delta*A.out;
//    }
    public double getDWeight() {
        double dws = 0;
        for (double dw : dweights) {
            dws += dw;
        }
        double dw_i = N.epsilon * grad + N.alpha * dws;
        dweights.add(dw_i);
        return dw_i;
    }

    public void correctWeight() {
        if (B.equals(N.output))
            return;
        if (A.equals(N.input)) {
            B.correctWeights();
            return;
        }
        weight += getDWeight();
        B.correctWeights();
    }

    void setWeight(double w) {
        weight = w;
    }

    double getDelta() {
        if (B.equals(N.output)) {
            return N.output.getDelta(A);
        } else {
            grad = B.delta*A.out;
            return B.delta;
        }
    }

    private void initWeights() {
        weight = (A==N.input || B==N.output) ? 0. : new Random().nextDouble();
        dweights = new ArrayList<>();
    }

    double weight;
    double grad;
    Neuron A;
    Neuron B;
    private Network N;
    private List<Double> dweights;
}
