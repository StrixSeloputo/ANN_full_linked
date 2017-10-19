package ann;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Neuron {
    Neuron() {
        label = UUID.randomUUID().toString().substring(0, 8);
    }
    Neuron(String l) {
        label = l;
    }
/*
    Neuron(Layer from, Layer to) {
        this();
        initSynapses(from, to);
    }
    Neuron(String l, Layer from, Layer to) {
        this(l);
        initSynapses(from, to);
    }
*/

    @Override
    public String toString() {
        String str = label;
        if (N.calculatedResult) {
            str += String.format("\\n(%.5f-->%.5f)",in,out);
        }
        if (N.calculatedDelta) {
            str += String.format("\\nd=%.5f", delta);
        }
        return str;
    }
    String outSynapseToString() {
        StringBuffer strBuf = new StringBuffer(128);
//        strBuf.append("//"+label+"::"+outSynapse.size()+"\n");
        for (Synapse synapse : outSynapse) {
//            String labelB = synapse.B == null ? "output" : synapse.B.toString();
            strBuf.append(synapse.toString());
        }
        strBuf.append("\n");
        return strBuf.toString();
    }
    String inSynapseToString() {
        StringBuffer strBuf = new StringBuffer(128);
        for (Synapse synapse : inSynapse) {
//            String labelA = synapse.A == null ? "input" : synapse.A.toString();
            strBuf.append(synapse.toString());
        }
/*        for (Synapse synapse : outSynapse) {
//            String labelA = synapse.A == null ? "input" : synapse.A.toString();
            strBuf.append(
                    "\t\""+synapse.A+"\" -> \""+synapse.B+"\" [ label = \""+synapse+"*\" ];\n"
            );
        }*/
        strBuf.append("\n");
        return strBuf.toString();
    }
    Network getNetwork() {
        return N;
    }
    Neuron setNetwork(Network network) {
        N = network;
        return this;
    }

    void addSynapseTo(Neuron to) {
//        System.out.println("("+outSynapse.size()+")"+label+".addSynapseTo("+to.label+")");
//        outSynapse.add(
                new Synapse(N, this, to);
//        );
    }
    void addSynapseFrom(Neuron from) {
//        System.out.println("("+inSynapse.size()+")"+label+".addSynapseFrom("+from.label+")");
//        inSynapse.add(
                new Synapse(N, from, this);
//        );
    }

    void removeAllInSynapse() {
//        System.out.println("("+inSynapse.size()+")"+label+".removeAllInSynapse()");
        inSynapse.clear();
    }
    void removeAllOutSynapse() {
//        System.out.println("("+outSynapse.size()+")"+label+".removeAllOutSynapse()");
        outSynapse.clear();
    }
/*
    public void addInSynapse(Synapse s) {
        s.setToNeuron(this);
        inSynapse.add(s);
    }
    private void addInSynapseFrom(Neuron A) {
        Synapse s = new Synapse();
        s.setNetwork(N);
        s.setFromNeuron(A);
        s.setToNeuron(this);
        inSynapse.add(s);
    }
    public void addOutSynapse(Synapse s) {
        s.setFromNeuron(this);
        inSynapse.add(s);
    }
    private void addOutSynapseTo(Neuron B) {
        Synapse s = new Synapse();
        s.setNetwork(N);
        s.setFromNeuron(this);
        s.setToNeuron(B);
        inSynapse.add(s);
    }
*/

    public double getIn() {
        double input = 0;
        for (Synapse inSyn : inSynapse) {
            input += inSyn.A.getOut()*inSyn.weight;
        }
        return in = input;
    }
    public double getOut() {
        return out = N.f.value(getIn());
    }

    public double getDelta() {
        double dOut = 0;
        for (Synapse outSyn : outSynapse) {
//            System.out.println(String.format("%s: %.5f", label, dOut));
            dOut += outSyn.weight * outSyn.getDelta();
//            System.out.println(String.format("%s: %.5f", label, dOut));
        }
        return delta = N.f.derivative(in)*dOut;
    }

    public double calculate() {
        return getOut();
    }

    void correctWeights() {
        outSynapse.forEach(Synapse::correctWeight);
    }

    private void initSynapses(Layer from, Layer to) {
        for (Neuron A : from) {
            Synapse A_this = new Synapse(N, A, this);
            A.outSynapse.add(A_this);
            inSynapse.add(A_this);
        }
        for (Neuron B : to) {
            Synapse this_B = new Synapse(N, this, B);
            outSynapse.add(this_B);
            B.inSynapse.add(this_B);
        }
    }

    double delta = 0.;
    double in;
    double out;
    String label;
    Network N;
    List<Synapse> inSynapse = new ArrayList<>();
    List<Synapse> outSynapse = new ArrayList<>();
}
