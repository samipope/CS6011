package assignment06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet<T> {

    private Node<T> root;

    /**
     * we have a class nested in our class that is a node
     * @param <T>
     */
    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;

        Node(T data) {
            this.data = data;
        }
    }

    /**
     * empty constructor with no params
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * adds something to the binary search tree.
     * if the root isn't defined, define it and then set it to the root
     * or just add it to the set
     * @throws NullPointerException if the item passed is null
     * @param item
     *          - the item whose presence is ensured in this set
     * @return
     */
    @Override
    public boolean add(T item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        if (root == null) {
            root = new Node<>(item);
            return true;
        }
        return addRecursive(root, item);
    }

    /**
     * lets us addAll list of items in a collection
     * go through everything in the items and then add them and return result
     * @param items
     *          - the collection of items whose presence is ensured in this set
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends T> items) {
        boolean result = false;
        for (T item : items) {
            result |= add(item); // |= is used to keep track of any changes
        }
        return result;
    }

    /**
     * clear the entire set by setting the root to null
     */
    @Override
    public void clear() {
        root = null;

    }

    /**
     * helper function for our contains method
     * goes through the data and recursively compares the items together until the entire set is compared
     * @param current
     * @param item
     * @return
     */
    private boolean containsRecursive(Node<T> current, T item) {
        if (current == null) {
            return false;
        }
        int compare = item.compareTo(current.data);
        if (compare == 0) {
            return true;
        } else if (compare < 0) {
            return containsRecursive(current.left, item);
        } else {
            return containsRecursive(current.right, item);
        }
    }

    /**
     *
     * @param item
     *          - the item sought in this set
     * @return boolean
     */
    @Override
    public boolean contains(T item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        return containsRecursive(root, item);
    }

    /**
     * goes through the entire list and uses contains to see if the item is ever present
     * @param items
     *          - the collection of items sought in this set
     * @return
     */
    @Override
    public boolean containsAll(Collection<? extends T> items) {
        for (T item : items) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * look for the left most item
     * @param current Node
     * @return minimum Node
     */
    private Node<T> findMin(Node<T> current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * returns the first element
     * @return Type T
     * @throws NoSuchElementException if the root is null
     */
    @Override
    public T first() throws NoSuchElementException {
        if (root == null) {
            throw new NoSuchElementException("The tree is empty");
        }
        return findMin(root).data;
    }


    /**
     *
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return root ==null;
    }

    /**
     * find the node that is the most to the right
     * @param current Node
     * @return maximum Node
     */
    private Node<T> findMax(Node<T> current) {
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    /**
     * Looks for the last element using the findMax function
     * @return T
     * @throws NoSuchElementException if the tree is empty
     */
    @Override
    public T last() throws NoSuchElementException {
        if (root == null) {
            throw new NoSuchElementException("The tree is empty");
        }
        return findMax(root).data;
    }

    /**
     * removes something from the set
     * @param item
     *          - the item whose absence is ensured in this set
     * @return boolean if was able to remove or not
     */
    @Override
    public boolean remove(T item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        int initialSize = size();
        root = removeRecursive(root, item);
        return size() != initialSize;
    }

    /**
     * this recursive is a helper function in my remove function. goes through the entire set recursively
     * @param current node
     * @param item that you want to find
     * @return smallest Node
     */
    private Node<T> removeRecursive(Node<T> current, T item) {
        if (current == null) {
            return null;
        }
        int compare = item.compareTo(current.data);
        if (compare < 0) {
            current.left = removeRecursive(current.left, item);
        } else if (compare > 0) {
            current.right = removeRecursive(current.right, item);
        } else {
            // found the node to delete!!!
            if (current.left == null && current.right == null) {
                return null;
            } else if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            } else {
                Node<T> smallestValue = findMin(current.right);
                current.data = smallestValue.data;
                current.right = removeRecursive(current.right, smallestValue.data);
            }
        }
        return current;
    }

    /**
     * removes everything in the set
     * @param items
     *          - the collection of items whose absence is ensured in this set
     * @return true if able to remove
     */
    @Override
    public boolean removeAll(Collection<? extends T> items) {
        boolean result = false;
        for (T item : items) {
            result |= remove(item); // |= is used to keep track of any changes
        }
        return result;
    }

    /**
     * recursive helper function that helps us recursively look at size
     * @param current Node
     * @return int size
     */
    private int sizeRecursive(Node<T> current) {
        if (current == null) {
            return 0;
        } else {
            return 1 + sizeRecursive(current.left) + sizeRecursive(current.right);
        }
    }

    /**
     *
     * @return size
     */
    @Override
    public int size() {
        return sizeRecursive(root);
    }

    /**
     * recursive helper adding function
     * @param current Node
     * @param item looking for
     * @return true if could add
     */
    private boolean addRecursive(Node<T> current, T item) {
        int compare = item.compareTo(current.data);
        if (compare == 0) {
            return false;
        } else if (compare < 0) {
            if (current.left == null) {
                current.left = new Node<>(item);
                return true;
            } else {
                return addRecursive(current.left, item);
            }
        } else {
            if (current.right == null) {
                current.right = new Node<>(item);
                return true;
            } else {
                return addRecursive(current.right, item);
            }
        }
    }

    /**
     *
     * @param node Node
     * @param acc ArrayList of type T
     */
    private void inOrderTraversal(Node<T> node, ArrayList<T> acc) {
        if (node != null) { //if current node is not null
            inOrderTraversal(node.left, acc); //goes down to left most
            acc.add(node.data); //once it reaches a node without a child, it adds it
            inOrderTraversal(node.right, acc); //goes down to the right most
        }
    }

    /**
     *
     * @return arrayList made
     */
    @Override
    public ArrayList<T> toArrayList() {
        ArrayList<T> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

}
