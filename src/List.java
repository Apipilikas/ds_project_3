import java.io.*;
import java.util.*;

public class List<T>{
    static int numOfNodes = 0;
    private ListNode<T> firstListNode;
    private ListNode<T> lastListNode;

    public List(){
        firstListNode = lastListNode = null;
    }


    //Returns true if List does not consist of any ListNode's.
    public boolean isEmpty(){
        if (firstListNode == null){
            return true;
        }
        else {
            return false;
        }
    }

    /*
    /Creates a new ListNode that contains ndata,
    / at which firstListNode pointer points and which
    / will point to previously first firstListNode, node.
    */
    public void insertAtFront(T ndata){
        if (isEmpty()){
            firstListNode = lastListNode = new ListNode<T>(ndata);
        }
        else {
            firstListNode = new ListNode<T>(ndata, firstListNode);
        }
        numOfNodes++;
    }

    /*
    /Creates a new ListNode that contains ndata.
    / Then we make the previously last lastListNode point to the new ListNode
    / and finally we set the lastListNode pointer point to the new ListNode.  
    */
    public void insertAtBack(T ndata){
        if (isEmpty()){
            firstListNode = lastListNode = new ListNode<T>(ndata);
        }
        else {
            ListNode<T> nListNode = new ListNode<T>(ndata);
            lastListNode.setNext(nListNode);
            lastListNode = nListNode;
        }
        numOfNodes++ ;
    }

    /*
    /Sets a T type reference to the firstListNode's data and returns it when removeFromFront() method is called.
    /Then checks if List consist of 1 ListNode and if so, sets both lastListNode and firstListNode as null. 
    /Otherwise sets firstListNode as the "second" listNode using getNext() method.
    */ 
    public T removeFromFront(){
        
        T removedNode = firstListNode.getData();

        if (firstListNode == lastListNode){
            firstListNode = lastListNode = null;
        }
        else {
            firstListNode = firstListNode.getNext();
        }
        numOfNodes-- ;
        return removedNode;
    }

    /*
    /Sets a T type reference to the firstListNode's data and returns it when removeFromFront() method is called.
    /Then checks if List consist of 1 node and if so, sets both lastListNode and firstListNode as null.
    /Otherwise prLastListNode iterates through the List using getNext() method until it reaches second from last listNode.
    /Then sets the second from last listNode as lastListNode and makes it point to null so that there is no reference to the previously lastListNode and garbage collector deletes it. 
    */ 
    public T removeFromBack(){

        T removedNode = lastListNode.getData();

        if (firstListNode == lastListNode){
            firstListNode = lastListNode = null;
        }
        else {
            
            ListNode<T> prLastListNode = firstListNode;

            while (prLastListNode.getNext() != lastListNode){
                prLastListNode = prLastListNode.getNext();
            }
            lastListNode = prLastListNode;
            prLastListNode.setNext(null);
        }
        numOfNodes-- ;
        return removedNode;
    }

    public void printList(){
        if (isEmpty())
            System.out.print("List is empty.");
        else{

            ListNode<T> current = getFirstListNode();

            while (current != null){
                System.out.print(current.getData()+" ");
                current = current.getNext();
            }
        }
    }

    public T getFirstListNodeData(){
        return firstListNode.getData();
    }

    public ListNode<T> getFirstListNode(){
        return firstListNode;
    }

    public ListNode<T> getLastListNode(){
        return lastListNode;
    }

    public int size(){
        return numOfNodes;
    }
}