package resources.classes;
import resources.exceptions.ElementNotFoundException;
import resources.interfaces.BinaryTreeADT;

import java.util.Iterator;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {
    protected int count;
    protected BinaryTreeNode<T> root;

    /*****************************************************************
     Creates an empty binary tree.
     *****************************************************************/
    public LinkedBinaryTree() {
        count = 0;
        root = null;
    }

    /*****************************************************************
     Creates a binary tree with the specified element as its root.
     *****************************************************************/
    public LinkedBinaryTree(T element) {
        count = 1;
        root = new BinaryTreeNode<T>(element);
    }

    /*****************************************************************
     Constructs a binary tree from the two specified binary trees.
     *****************************************************************/
    public LinkedBinaryTree(T element, LinkedBinaryTree<T> leftSubtree,
                            LinkedBinaryTree<T> rightSubtree) {
        root = new BinaryTreeNode<T>(element);
        count = 1;

        if (leftSubtree != null) {
            count = count + leftSubtree.size();
            root.left = leftSubtree.root;
        } else
            root.left = null;

        if (rightSubtree != null) {
            count = count + rightSubtree.size();
            root.right = rightSubtree.root;
        } else
            root.right = null;
    }

    /*****************************************************************
     Removes the left subtree of this binary tree.
     *****************************************************************/
    public void removeLeftSubtree() {
        if (root.left != null)
            count = count - root.left.numChildren() - 1;
        root.left = null;
    }

    /*****************************************************************
     Removes the right subtree of this binary tree.
     *****************************************************************/
    public void removeRightSubtree() {
        if (root.right != null)
            count = count - root.right.numChildren() - 1;

        root.right = null;
    }

    /*****************************************************************
     Deletes all nodes from this binary tree.
     *****************************************************************/
    public void removeAllElements() {
        count = 0;
        root = null;
    }

    /*****************************************************************
     Returns true if this binary tree is empty and false otherwise.
     *****************************************************************/
    public boolean isEmpty() {
        return (count == 0);
    }

    /*****************************************************************
     Returns true if this binary tree is empty and false otherwise.
     *****************************************************************/
    public int size() {
        return count;
    }

    /*****************************************************************
     Returns true if this tree contains an element that matches the
     specified target element and false otherwise.
     *****************************************************************/
    public boolean contains(T targetElement) {
        T temp;
        boolean found = false;

        try {
            temp = find(targetElement);
            found = true;
        } catch (Exception ElementNotFoundException) {
            found = false;
        }

        return found;
    }

    /*****************************************************************
     Returns a reference to the specified target element if it is
     found in this binary tree.  Throws a NoSuchElementException if
     the specified target element is not found in the binary tree.
     *****************************************************************/
    public T find(T targetElement) throws ElementNotFoundException {
        BinaryTreeNode<T> current = findAgain(targetElement, root);

        if (current == null)
            throw new ElementNotFoundException("binary tree");

        return (current.element);
    }

    /*****************************************************************
     Returns a reference to the specified target element if it is
     found in this binary tree.
     *****************************************************************/
    private BinaryTreeNode<T> findAgain(T targetElement,
                                        BinaryTreeNode<T> next) {
        if (next == null)
            return null;

        if (next.element.equals(targetElement))
            return next;

        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);

        if (temp == null)
            temp = findAgain(targetElement, next.right);

        return temp;
    }

    /*****************************************************************
     Returns a string representation of this binary tree.
     *****************************************************************/
    public String toString() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preorder(root, tempList);

        return tempList.toString();
    }

    /*****************************************************************
     Performs an inorder traversal on this binary tree by calling an
     overloaded, recursive inorder method that starts with
     the root.
     *****************************************************************/
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inorder(root, tempList);

        return tempList.iterator();
    }

    /*****************************************************************
     Performs a recursive inorder traversal.
     *****************************************************************/
    protected void inorder(BinaryTreeNode<T> node,
                           ArrayUnorderedList<T> tempList) {
        if (node != null) {
            inorder(node.left, tempList);
            tempList.addToRear(node.element);
            inorder(node.right, tempList);
        }
    }

    /*****************************************************************
     Performs an preorder traversal on this binary tree by calling
     an overloaded, recursive preorder method that starts with
     the root.
     ****************************************************************
     * @return*/
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preorder(root, tempList);

        return tempList.iterator();
    }

    /*****************************************************************
     Performs a recursive preorder traversal.
     *****************************************************************/
    protected void preorder(BinaryTreeNode<T> node,
                            ArrayUnorderedList<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.element);
            preorder(node.left, tempList);
            preorder(node.right, tempList);
        }
    }

    /*****************************************************************
     Performs an postorder traversal on this binary tree by calling
     an overloaded, recursive postorder method that starts
     with the root.
     *****************************************************************/
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postorder(root, tempList);

        return tempList.iterator();
    }

    /*****************************************************************
     Performs a recursive postorder traversal.
     *****************************************************************/
    protected void postorder(BinaryTreeNode<T> node,
                             ArrayUnorderedList<T> tempList) {
        if (node != null) {
            postorder(node.left, tempList);
            postorder(node.right, tempList);
            tempList.addToRear(node.element);
        }
    }

    /*****************************************************************
     Performs a levelorder traversal on this binary tree, using a
     templist.
     *****************************************************************/
    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<BinaryTreeNode<T>> nodes =
                new ArrayUnorderedList<BinaryTreeNode<T>>();
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        BinaryTreeNode<T> current;

        nodes.addToRear(root);

        while (!nodes.isEmpty()) {
            current = (BinaryTreeNode<T>) (nodes.removeFirst());

            if (current != null) {
                tempList.addToRear(current.element);
                if (current.left != null)
                    nodes.addToRear(current.left);
                if (current.right != null)
                    nodes.addToRear(current.right);
            } else
                tempList.addToRear(null);
        }

        return tempList.iterator();
    }
}