package func;

/*
 * default - Sigmoid
 */

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FuncCreator {

    @Contract(pure = true)
    public static FuncEnum getDefaultFuncEnum() {
        return FuncEnum.Sigmoid;
    }
    @Contract(pure = true)
    public static FuncEnum2 getDefaultFuncEnum2() {
        return FuncEnum2.Difference;
    }

    @NotNull
    public static Function newFunction(FuncEnum funcType) {
        switch (funcType) {
            case HeavisideStepFunction:
                return new HeavisideStepFunction();
            case Sigmoid:
                return new Sigmoid();
            case HyperbolicTangent:
                return new HyperbolicTangent();
            case LogarithmicFunction:
                return new LogarithmicFunction();
            case GaussFunction:
                return new GaussFunction();
            case Linear:
                return new Linear();
            default: return new Sigmoid();
        }
    }
    @NotNull
    public static Function2 newFunction2(FuncEnum2 funcType) {
        switch (funcType) {
            case Linear2:
                return new Linear2();
            case Difference:
                return new Difference();
            case QuadratisDifference:
                return new QuadraticDifference();
            default:
                return new Difference();
        }
    }
}
