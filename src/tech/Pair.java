package tech;

import org.jetbrains.annotations.NotNull;

public class Pair<S extends Comparable<S>, T extends Comparable<T>>
        implements Comparable<Pair<S, T>> {
    private S _1;
    private T _2;

    public Pair() {}
    public Pair(S s, T t) {
        _1 = s;
        _2 = t;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
    public boolean equals(Pair<S, T> other) {
        System.out.println("Pair equals");
        return _1.equals(other._1) && _2.equals(other._2);
    }


    public S get_1() {
        return _1;
    }
    public T get_2() {
        return _2;
    }

    @Override
    public int compareTo(@NotNull Pair<S, T> other) {
        if (_1.compareTo(other._1)==0)
            return _2.compareTo(other._2);
        return _1.compareTo(other._1);
    }
}
