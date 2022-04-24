package com.pavlovich.list;

import com.google.common.base.Preconditions;

public class List<T> {
    private int size;
    private ListNode<T> first;
    private ListNode<T> last;

    public int getSize(){
        return size;
    }

    public void add(T element) {
        ListNode<T> newNode = new ListNode<T>(element);
        newNode.setPrevious(last);
        if (last != null)
            last.setNext(newNode);
        last = newNode;
        if (size == 0){
            first = newNode;
        }
        size++;
    }

    public T getFirstElement() {
        if (first != null)
            return first.getValue();
        else
            throw new IllegalStateException("List is empty!");
    }

    public T getLastElement() {
        if (last != null)
            return last.getValue();
        else
            throw new IllegalStateException("List is empty!");
    }

    public T get(int index) {
        Preconditions.checkArgument(index < size && index >= 0,
                "Index is out of range! Index is " + index + ".");

        if (index == 0) return first.getValue();
        if (index == size - 1) return last.getValue();

        return getNodeFromMiddle(index).getValue();
    }
    
    public void removeAt(int index){
        Preconditions.checkArgument(index < size && index >= 0,
                "Index is out of range! Index is " + index + ".");

        if (index == 0) {
            first = first.getNext();
            if (first != null)
                first.setPrevious(null);
            --size;
            return;
        }
        if (index == size - 1) {
            last = last.getPrevious();
            last.setNext(null);
            --size;
            return;
        }

        ListNode<T> deleteNode = getNodeFromMiddle(index);
        ListNode<T> before = deleteNode.getPrevious();
        ListNode<T> after = deleteNode.getNext();
        before.setNext(after);
        after.setPrevious(before);
        --size;
    }

    public void clear(){
        ListNode<T> processNode;
        ListNode<T> nextNode = first;
        final int startSize = size;
        for (int i = 0; i < startSize; i++) {
            processNode = nextNode;
            nextNode = nextNode.getNext();
            processNode.setPrevious(null);
            processNode.setNext(null);
            --size;
        }
        first = null;
        last = null;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private ListNode<T> getNodeFromMiddle(int index) {
        ListNode<T> node;
        if (index < size / 2){
            node = first;
            for(int i = 0; i < index; i++){
                node = node.getNext();
            }
        }
        else {
            node = last;
            for (int i = 0; i < size - index - 1; i++) {
                node = node.getPrevious();
            }
        }
        return node;
    }

    public boolean contains(T value){
        ListNode<T> node = first;
        for (int i = 0; i < size; i++) {
            T nodeValue = node.getValue();
            if (nodeValue == value)
                return true;
            else
                node = node.getNext();
        }
        return false;
    }

    public static class ListNode<T> {
        private final T value;
        private ListNode<T> next;
        private ListNode<T> previous;

        public ListNode(T value){
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public ListNode<T> getNext() {
            return next;
        }

        public void setNext(ListNode<T> next) {
            this.next = next;
        }

        public ListNode<T> getPrevious() {
            return previous;
        }

        public void setPrevious(ListNode<T> previous) {
            this.previous = previous;
        }
    }
}