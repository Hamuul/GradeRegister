import java.io.Serializable;

public class Tuple<T, U, V> implements Serializable {
    public T getVal1() {
        return val1;
    }

    public void setVal1(T val1) {
        this.val1 = val1;
    }

    public U getVal2() {
        return val2;
    }

    public void setVal2(U val2) {
        this.val2 = val2;
    }

    public V getVal3() {
        return val3;
    }

    public void setVal3(V val3) {
        this.val3 = val3;
    }

    private T val1;
    private U val2;
    private V val3;
    public Tuple(T val1, U val2, V val3){
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
    }
    public static <T, U, V> Tuple of(T val1, U val2, V val3){
        return new Tuple(val1, val2, val3);
    }
    @Override
    public String toString() {
        return "Tuple{" +
                "val1=" + val1 +
                ", val2=" + val2 +
                ", val3=" + val3 +
                '}';
    }
}