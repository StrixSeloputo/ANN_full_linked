package ann;

import java.util.ArrayList;

public class Layer extends ArrayList<Neuron> {
/*
    Layer() {}
    Layer(int count) {
        for (int i = 0; i < count; i++) {
            add(new Neuron());
        }
    }
*/
    Layer(Network network, String []neuronLabels) {
        N = network;
        for (String neuronLabel : neuronLabels) {
            add(new Neuron(neuronLabel).setNetwork(N));
        }
    }
    Layer(Network network, String []neuronLabels, Layer before, Layer after) {
        this(network, neuronLabels);
        //System.out.println("Layer init:"+super.toString()+";\n size: "+size());
        if (before != null) {
            before.forEach(Neuron::removeAllOutSynapse);
            forEach(
                    curr -> before.forEach(
                                beforeNeuron -> beforeNeuron.addSynapseTo(curr)
                        )
            );
        } else {
            N.input.removeAllOutSynapse();
            forEach(
                    curr -> curr.addSynapseFrom(N.input)
            );
        }
        if (after != null) {
            after.forEach(Neuron::removeAllOutSynapse);
            forEach(
                    curr -> after.forEach(
                            afterNeuron -> afterNeuron.addSynapseFrom(curr)
                    )
            );
        } else {
            N.output.removeAllInSynapse();
            forEach(
                    curr -> curr.addSynapseTo(N.output)
            );
        }
    }

    @Override
    public String toString() {
        StringBuffer strBuf = new StringBuffer(256);
        if (size() < 1) return "";
        // align neurons
        strBuf.append("\t{ rank=same");
        forEach(
                neuron ->
                        strBuf.append("; \"")
                            .append(neuron).append("\"")
        );
        strBuf.append(" }\n");
        // out synapse
        forEach(
                neuron -> strBuf.append(neuron.outSynapseToString())
        );
        return strBuf.toString();
    }

    double [] calculate() {
        forEach(Neuron::calculate);
        double []res = new double[size()];
        int i = 0;
        for (Neuron neuron : this) {
            res[i] = neuron.out;
            i++;
        }
        return res;
    }

    void getDelta() {
        forEach(Neuron::getDelta);
    }

    private Network N;
}
