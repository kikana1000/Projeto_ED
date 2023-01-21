/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources.classes;
import resources.interfaces.*;


public class DoubleLinkedOrderedList<T> extends DoubleLinkedList<T>
        implements OrderedListADT<T> {

    private void tailChecker() {
        if (tail.getNext() != null) {
            tail = tail.getNext();
        }
    }

    /**
     * Adds the specified element to this list at the proper location
     *
     * @param element the element to be added to this list
     */
    @Override
    public void add(T element) {
        if (!(element instanceof Comparable)) {
            return;
        }

        Comparable tmp = (Comparable) element;
        DllNode<T> newNode = new DllNode<>(element);

        if (head.getData() == null) {
            head.setData(element);

        } else if (tmp.compareTo(head.getData()) < 0) {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;

        } else {
            DllNode<T> n = head;
            DllNode<T> next;

            while (n.getNext() != null
                    && tmp.compareTo(n.getNext().getData()) > 0) {
                n = n.getNext();
            }

            next = n.getNext();
            newNode.setPrev(n);
            n.setNext(newNode);

            if (next != null) {
                newNode.setNext(next);
                next.setPrev(newNode);
            }

            this.tailChecker();
        }
        modCount++;
        counter++;
    }

}
