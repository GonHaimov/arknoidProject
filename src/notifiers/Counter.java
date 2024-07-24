package notifiers;

public class Counter {
    private int count;

    public Counter() {
        this.count = 0;
    }

    public void increase(int number) {
        this.count += number;
    }

    public void decrease(int number) {
        this.count -= number;
    }

    public int getValue() {
        return this.count;
    }

    public void setCount(int number) {
        this.count = number;
    }
}
