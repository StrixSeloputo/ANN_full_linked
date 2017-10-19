package ann;

import func.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Network extends ArrayList<Layer> {
    public  Network() {
        this("Network");
    }
    public Network(String str) {
        this(str, FuncCreator.getDefaultFuncEnum());
    }
    public Network(String  str, FuncEnum funcEnum) {
//        this(str, funcEnum, FuncCreator.getDefaultFuncEnum2());
        name = str;
        f = FuncCreator.newFunction(funcEnum);
    }
/*
    public Network(String  str, FuncEnum funcEnum, FuncEnum2 funcEnum2) {
        name = str;
        f = FuncCreator.newFunction(funcEnum);
        L = FuncCreator.newFunction2(funcEnum2);
    }
*/
    @Override
    public String toString() {
        StringBuffer strBuf = new StringBuffer(256);
        strBuf.append("\\\\=====================================================\n")
                .append("digraph ").append(name).append(" {\n")
                .append("//").append(f).append("\n")
                .append("\trankdir=LR;\n")
                .append(inputSynapseToString());
        forEach(
                layer -> strBuf.append(layer)
        );
        strBuf.append("}\n")
                .append("\\\\=====================================================\n");
        return strBuf.toString();
    }

    /*
    * добавление в конец слоя с несколькими именованными нейронами
    */
    public Layer addLayer(String []neuronLabels) {
        Layer added;
        if (size()>0) {
            added = new Layer(this, neuronLabels, get(size()-1), null);
        } else {
            added = new Layer(this, neuronLabels, null, null);
        }
        add(added);
        return added;
    }
//    неиспользуемые методы
/*
    public Network addNeuronAtLayer(int layerIndex) {
        return addNeuronAtLayer(layerIndex, true);
    }
    public Network addNeuronAtLayer(int layerIndex, boolean needSynapses) {
        if (layerIndex > size()) {
            throw new IllegalArgumentException("layerIndex > layerCount");
        }
        if (needSynapses) {
            int neuronIndexInLayer = get(layerIndex).size();
            get(layerIndex).add(new Neuron());
            synapseMatrices.get(layerIndex - 1)
                    .initFromSynapseNeuronAtLayer(neuronIndexInLayer);
            synapseMatrices.get(layerIndex)
                    .initToSynapseNeuronAtLayer(neuronIndexInLayer);
        }
        return this;
    }
    public Network addNeuronAtLayer(Neuron T, int layerIndex) {
        return addNeuronAtLayer(T, layerIndex, true);
    }
    public Network addNeuronAtLayer(Neuron T, int layerIndex, boolean needSynapses) {
        this.get(layerIndex).add(T);
        if (needSynapses) {
            int neuronIndexInLayer = get(layerIndex).size();
            synapseMatrices.get(layerIndex-1)
                    .initFromSynapseNeuronAtLayer(neuronIndexInLayer);
            synapseMatrices.get(layerIndex)
                    .initToSynapseNeuronAtLayer(neuronIndexInLayer);
        }
        return this;
    }
*/

    public double []calculate() {
        return calculate(false);
    }

    public double []calculate(boolean needPrint) {
        forEach(Layer::calculate);
        calculatedResult = true;
        calculatedDelta = false;
        double []result = output.getOutput();
        printResult(result);
        return result;
    }

    public void print() {
        System.out.println(toString());
    }

/*
*   methods for 1 train of ANN with 'teacher'
*   gets input values and right output
*   for comparing result before and after train
*   method twice calculates output with specified input
*/
    public void train(double[] input, double []output) {
        setInput(input);
        calculate();
//        print();
        setIdealOutput(output);
        getDelta();
        this.input.correctWeights();
        calculate();
//        print();
    }

/*
*   sets specified input on Synapses from synthetic input of Network
*   to input Layer that added by user
*/
    private void setInput(double[]inputData) {
        if (inputData.length != get(0).size()) {
            System.out.println("[WARN]: Input size -- "+inputData.length+", but input layer size -- "+get(0).size());
        }
        input.setInput(inputData);
    }
/*
*   sets ideal output that specifies by user
*/
    private void setIdealOutput(double []idealOutputData) {
        if (idealOutputData.length != get(size()-1).size()) {
            System.out.println("[WARN]: Input size -- "+idealOutputData.length+", but input layer size -- "+get(size()-1).size());
        }
        output.setIdealOutput(idealOutputData);
    }
/*
*
*/
    private void getDelta() {
        if (output.outIdeal == null) {
            System.out.println("[SYSERR]: It was not setted ideal output to calculate delta");
            return;
        }
        int size = size();
        for (int i = size-1; i >= 0; i--) {
            get(i).getDelta();
        }
        calculatedDelta = true;
    }

    @NotNull
    private String inputSynapseToString() {
        StringBuffer strBuf = new StringBuffer();
        get(0).forEach(
                intputNeuron -> strBuf.append(intputNeuron.inSynapseToString())
        );
        return strBuf.toString();
    }

    private void printResult(double []result) {
        System.out.print(name+" calculated: [");
        if (result.length>0) {
            System.out.print(result[0]);
        } else {
            System.out.print("null");
        }
        for (int i = 1; i < result.length; i++) {
            System.out.print(", "+result[i]);
        }
        System.out.println("]");
    }

//    boolean hasBias = true;
    // isn't taken into account
    boolean calculatedResult = false;
    boolean calculatedDelta = false;
    double epsilon = 0.7;               // learning rate
//  0.7 - heuristics
    double alpha = 0.3;                 // momentum
//  0.3 - too heuristics
    Function f;                         // activation function
//    Function2 L;                        // loss function
    TechNeuron input = new TechNeuron("input");
    TechNeuron output = new TechNeuron("output");
    private String name;
}
