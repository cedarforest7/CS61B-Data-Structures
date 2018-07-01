public class LinkedListDeque<T> implements Deque<T> {
    private class LNode{
        public T item;
        public LNode previous;
        public LNode next;

        public LNode(T i, LNode p, LNode n) {
            item = i;
            previous = p;
            next = n;
        }
    }
    private LNode front;
    private LNode back;
    private int size;
    /** Create an empty LinekedListDeque. */
    public LinkedListDeque(){
        front = new LNode(null,null, null);
        back = new LNode(null, front, front);
        front.next = back;
        front.previous = back;
        size = 0;
    }
    /** Add x to the front of the list. */
    @Override
    public void addFirst(T x){
        front.next = new LNode(x, front, front.next);
        front.next.next.previous = front.next;
        size++;
    }
    /** Add x to the end of the list. */
    @Override
    public void addLast(T x){
        back.previous = new LNode(x, back.previous, back);
        back.previous.previous.next =back.previous;
        size++;
    }
    /** get the first item of the list. */
    public T getFirst(){
        return front.next.item;
    }
    /** get the last item of the list. */
    public T getLast(){
        return back.previous.item;
    }
    @Override
    public boolean isEmpty(){
        if(front.next == back){
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
        LNode scan = front;
        while(scan != back){
            scan = scan.next;
            System.out.print(scan.item + " ");
        }
    }
    @Override
    public T removeFirst(){
        T item = front.next.item;
        front.next = front.next.next;
        front.next.previous =front;
        size--;
        return item;
    }
    @Override
    public T removeLast(){
        T item = back.previous.item;
        back.previous = back.previous.previous;
        back.previous.next = back;
        size--;
        return item;
    }
    /** get the (index+1)th item of the list. */
    @Override
    public T get(int index){
        LNode scan = front;
        if (front.next == back){
            return null;
        }else {
            for (int k = 0; k <= index; k++) {
                scan = scan.next;
            }
            return scan.item;
        }
    }
    //necessary or not??

    /** Get using recursion. ?????*/
    /**public T getRecursive(int index){
        LNode scan = front;
        if (front.next == back){
            return null;
        }else {
            if(index == 0){
                return scan.next.item;
            }
            else{
                getRecursive(index-1);
                scan = scan.next;
            }
        }


    } */
}
