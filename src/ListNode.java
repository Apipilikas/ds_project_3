public class ListNode<T>{

    private T data;
    private ListNode<T> nextNode;

    public ListNode(T ndata, ListNode<T> node){
        data = ndata;
        nextNode = node;
    }

    public ListNode(T ndata){
        this(ndata, null);
    }

    public ListNode(){
    }

    public T getData(){
        return data;
    }

    public ListNode<T> getNext(){
        return nextNode;
    }

    public void setNext(ListNode<T> newNextNode){
        nextNode = newNextNode;
    }
}