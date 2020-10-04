package TI;

import java.math.BigInteger;

public class Pair {
    private BigInteger first;
    private BigInteger second;

    public Pair(BigInteger _first, BigInteger _second) {
        first = _first;
        second = _second;
    }

    public BigInteger getSecond() {
        return second;
    }

    public void setSecond(BigInteger second) {
        this.second = second;
    }

    public BigInteger getFirst() {
        return first;
    }

    public void setFirst(BigInteger first) {
        this.first = first;
    }
}
