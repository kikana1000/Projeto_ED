/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources.classes;
import resources.interfaces.*;

import java.util.Iterator;


public class DoubleLinkedList<T> implements ListADT<T> {

    /**
     * The first element of the List.
     */
    protected DllNode<T> head;

    /**
     * The last element of the List.
     */
    protected DllNode<T> tail;

    /**
     * The number of elements.
     */
    protected int counter;

    /**
     * The number of operations made.
     */
    protected int modCount;

    /**
     * Default DoubleLinkedList class constructor.
     */
    public DoubleLinkedList() {
        this.counter = 0;
        this.modCount = 0;
        this.head = new DllNode<>();
        this.tail = new DllNode<>();
        this.head = this.tail;
    }

    private void removeTheLastElement() {
        this.head.setData(null);
        this.head.setNext(null);
        this.head.setPrev(null);
        this.tail.setData(null);
        this.tail.setNext(null);
        this.tail.setPrev(null);
        this.head = this.tail;
        this.counter--;
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     */
    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }

        if (this.counter == 1) {
            T element = this.head.getData();
            this.removeTheLastElement();
            return element;
        }

        T oldFirst = this.head.getData();
        this.head = this.head.getNext();
        this.head.setPrev(null);

        this.counter--;
        this.modCount++;

        return oldFirst;
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     */
    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }

        if (this.counter == 1) {
            T element = this.head.getData();
            this.removeTheLastElement();
            return element;
        }

        T oldLast = this.tail.getData();
        this.tail = this.tail.getPrev();
        this.tail.setNext(null);

        this.counter--;
        this.modCount++;

        return oldLast;
    }

    protected DllNode<T> returnsNodeByElement(T target) {
        DllNode n = this.head;

        while (n.getNext() != null) {
            if (n.getData().equals(target)) {
                return n;
            }
            n = n.getNext();
        }

        if (n.getData().equals(target)) {
            return n;
        }

        return null;
    }

    /**
     * Removes and returns the specified element from this list.
     *
     * @param element the element to be removed from the list
     * @return the element that was removed
     */
    @Override
    public T remove(T element) {
        if (this.isEmpty()) {
            return null;
        }

        if (!this.contains(element)) {
            return null;
        }

        if (this.counter == 1) {
            this.removeTheLastElement();
            return element;
        }

        if (element.equals(this.head.getData())) {
            return this.removeFirst();
        }

        if (element.equals(this.tail.getData())) {
            return this.removeLast();
        }

        DllNode toBeRemoved = this.returnsNodeByElement(element);
        DllNode previous = toBeRemoved.getPrev();
        DllNode next = toBeRemoved.getNext();

        previous.setNext(next);
        next.setPrev(previous);

        this.counter--;
        this.modCount++;

        return (T) toBeRemoved.getData();
    }

    /**
     * Returns a reference to the first element in this list.
     *
     * @return a reference to the first element in this list
     */
    @Override
    public T first() {
        return this.head.getData();
    }

    /**
     * Returns a reference to the last element in this list.
     *
     * @return a reference to the last element in this list
     */
    @Override
    public T last() {
        return this.tail.getData();
    }

    /**
     * Returns true if this list contains the specified target element.
     *
     * @param target the target that is being sought in the list
     * @return true if the list contains this element
     */
    @Override
    public boolean contains(T target) {
        if (this.isEmpty()) {
            return false;
        }

        DllNode n = this.head;

        while (n.getNext() != null) {
            if (n.getData().equals(target)) {
                return true;
            }
            n = n.getNext();
        }

        return n.getData().equals(target);
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return this.counter == 0;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the integer representation of number of elements in this list
     */
    @Override
    public int size() {
        return this.counter;
    }

    /**
     * Returns a string representation of this list.
     *
     * @return a string representation of this list
     */
    @Override
    public String toString() {
        if (this.isEmpty()) {
            return null;
        }

        String str = "";
        DllNode<T> n = this.head;

        while (n.getNext() != null) {
            str += n.getData() + "; ";
            n = n.getNext();
        }
        str += n.getData() + ";";

        return str;
    }

    /**
     * Returns an iterator for the elements in this list.
     *
     * @return an iterator over the elements in this list
     */
    @Override
    public Iterator<T> iterator() {
        return new DoubleLinkedListIterator(this.modCount);
    }

    private class DoubleLinkedListIterator<E> implements Iterator<E> {

        private int expectedModcount;
        private DllNode<E> current;
        private boolean okToRemove;

        private DoubleLinkedListIterator(int modCount) {
            this.expectedModcount = modCount;
            this.current = new DllNode<>();
            this.current.setNext((DllNode<E>) head);
            this.okToRemove = false;
        }

        @Override
        public boolean hasNext() {
            return this.current.getNext() != null;
        }

        @Override
        public E next() {
            if (this.expectedModcount != modCount) {
                throw new IllegalStateException();
            }

            if (!this.hasNext()) {
                return null;
            }

            this.okToRemove = true;
            this.current = this.current.getNext();

            return (E) this.current.getData();
        }

        @Override
        public void remove() {
            if (!this.okToRemove) {
                return;
            }

            DoubleLinkedList.this.remove((T) this.current.getData());
            this.expectedModcount++;
            this.okToRemove = false;
        }
    }

}
