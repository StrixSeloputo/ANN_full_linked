package ann;

class TechNeuron extends Neuron {
    TechNeuron(String l) {
        label = l;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public double getIn() {
        return in;
    }
    @Override
    public double getOut() {
        return out;
    }
    @Override
    public double getDelta() {
        return Double.NaN;
    }
    double getDelta(Neuron neuron) {
//        System.out.println("Neuron: "+neuron.label);
        for (int i = 0; i < inSynapse.size(); i++) {
//            System.out.println();
            if (inSynapse.get(i).A.equals(neuron)) {
                return (outIdeal[i] - inSynapse.get(i).weight)/inSynapse.get(i).weight;
            }
        }
        return Double.NaN;
    }
    @Override
    void correctWeights() {
        outSynapse.forEach(Synapse::correctWeight);
    }

    void setInput(double []input) {
        int limit = Math.min(input.length, outSynapse.size());
        for (int i = 0; i < limit; i++) {
            outSynapse.get(i).setWeight(input[i]);
        }
        for (int i = limit; i < outSynapse.size(); i++) {
            outSynapse.get(i).setWeight(0.);
        }
    }
    double []getOutput() {
        double []res = new double[inSynapse.size()];
        int i = 0;
        for (Synapse synapse : inSynapse) {
            res[i] = (synapse.weight = synapse.A.out);
            i++;
        }
        return res;
    }

    void setIdealOutput(double []idealOutput) {
        outIdeal = new double[inSynapse.size()];
        int limit = Math.min(idealOutput.length, outIdeal.length);
        System.arraycopy(idealOutput, 0, outIdeal, 0, limit);
/*
        for (int i = 0; i < limit; i++) {
            outIdeal[i] = idealOutput[i];
        }
        for (int i = limit; i < outSynapse.size(); i++) {
            outIdeal[i] = 0.;
        }
*/

    }

    final double in = 1.;
    final double out = 1.;
    final double delta = 1.;
    double []outIdeal;
}
