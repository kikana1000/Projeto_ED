/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change 
icense
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit 
emplate
 */
package resources.classes;
import resources.interfaces.*;

public class DoubleLinkedUnorderedList<T> extends DoubleLinkedList<T>
        implements UnorderedListADT<T> {

    /**
     * Adds an element to the front of the list.
     *
     * @param element the element to be added
     */
    @Override
    public void addToFront(T element) {
        if (head.getData() == null) {
            head.setData(element);

        } else {
            DllNode<T> tmp = head;
            head = new DllNode<>(element);
            head.setNext(tmp);
        }

        counter++;
        modCount++;
    }

    /**
     * Adds an element to the rear of the list.
     *
     * @param element the element to be added
     */
    @Override
    public void addToRear(T element) {
        if (head == null) {
            head.setData(element);

        } else {
            DllNode<T> newTail = new DllNode<>(element);
            tail.setNext(newTail);
            newTail.setPrev(tail);
            tail = tail.getNext();
        }

        counter++;
        modCount++;
    }

    /**
     * Adds an element after an element of the list.
     *
     * @param element the element to be added
     * @param previous the previous element of the new element.
     */
    @Override
    public void addAfter(T element, T previous) {
        if (!contains(previous)) {
            return;
        }

        DllNode<T> previousNode = returnsNodeByElement(previous);
        DllNode<T> nextNode = previousNode.getNext();
        DllNode<T> newNode = new DllNode<>(element);
        newNode.setPrev(previousNode);
        previousNode.setNext(newNode);

        if (nextNode != null) {
            newNode.setNext(nextNode);
            nextNode.setPrev(newNode);
        }

        counter++;
        modCount++;
    }

}
