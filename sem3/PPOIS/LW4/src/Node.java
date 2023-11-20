public class Node<T>{
    private int index;
    private final T info;

    public int getIndex() {
        return index;
    }

    public Node(T info) {
        this.info = info;
    }

    public T getInfo() {
        return info;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
