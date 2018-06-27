public class ArrayDeque<T> implements Deque<T>{
    private T[] items;
    private int size;
    private int fL;  //frontLength
    private int bL; //backLength

    /** Create an emptry list. */
    public ArrayDeque(){
        items = (T[]) new Object[100];
        size = 0;
        fL = 0;
        bL = 0;
    }
    private void expandBack(int capacity){
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, fL + size + bL);
        items = a;
        bL = capacity - fL - size;
    }
    private void expandFront(int capacity){
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, capacity - fL -size - bL, fL + size + bL);
        items = a;
        fL = capacity - size - bL;
    }
    @Override
    public void addFirst(T x){
        int tL = fL + size + bL; //totalLength
        if (tL == items.length){
            expandFront(2*tL);
        }
        items[fL-1] = x;
        size++;
        fL--;
    }
    @Override
    public void addLast(T x){
        int tL = fL + size + bL; //totalLength
        if (tL == items.length){
            expandBack(2*tL);
        }
        items[fL + size] = x;
        size++;
        bL--;
    }
    @Override
    public boolean isEmpty(){
        if(size == 0){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque(){
        for(int i = fL; i < fL + size; i++ ) {
            System.out.print(items[i] + " ");
        }
    }
    public T getFirst(){
        return items[fL];
    }
    public T getLast(){
        return items[fL + size -1];
    }
    @Override
    public T removeFirst(){
        T x = getFirst();
        size--;
        fL++;
        return x;
    }
    @Override
    public T removeLast(){
        T x = getLast();
        size--;
        bL++;
        return x;
    }
    @Override
    public T get(int index){
        if(size == 0){
            return null;
        }else{
            return items[fL + index];
        }
    }
}
