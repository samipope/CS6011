package assignment06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet<T> {

    private Node<T> root;

    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;

        Node(T data) {
            this.data = data;
        }
    }

    public BinarySearchTree() {
        root = null;
    }

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

    @Override
    public boolean addAll(Collection<? extends T> items) {
        boolean result = false;
        for (T item : items) {
            result |= add(item); // |= is used to keep track of any changes
        }
        return result;
    }

    @Override
    public void clear() {
        root = null;

    }

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

    @Override
    public boolean contains(T item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        return containsRecursive(root, item);
    }

    @Override
    public boolean containsAll(Collection<? extends T> items) {
        for (T item : items) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    private Node<T> findMin(Node<T> current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    @Override
    public T first() throws NoSuchElementException {
        if (root == null) {
            throw new NoSuchElementException("The tree is empty");
        }
        return findMin(root).data;
    }

    @Override
    public boolean isEmpty() {
        return root ==null;
    }

    private Node<T> findMax(Node<T> current) {
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }
    @Override
    public T last() throws NoSuchElementException {
        if (root == null) {
            throw new NoSuchElementException("The tree is empty");
        }
        return findMax(root).data;
    }

    @Override
    public boolean remove(T item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        int initialSize = size();
        root = removeRecursive(root, item);
        return size() != initialSize;
    }
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
            // Node to delete found
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

    @Override
    public boolean removeAll(Collection<? extends T> items) {
        boolean result = false;
        for (T item : items) {
            result |= remove(item); // |= is used to keep track of any changes
        }
        return result;
    }

    private int sizeRecursive(Node<T> current) {
        if (current == null) {
            return 0;
        } else {
            return 1 + sizeRecursive(current.left) + sizeRecursive(current.right);
        }
    }

    @Override
    public int size() {
        return sizeRecursive(root);
    }

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

    // ... Similar implementations for other methods like addAll, clear, contains, etc.

    // Helper method for in-order traversal
    private void inOrderTraversal(Node<T> node, ArrayList<T> acc) {
        if (node != null) {
            inOrderTraversal(node.left, acc);
            acc.add(node.data);
            inOrderTraversal(node.right, acc);
        }
    }

    @Override
    public ArrayList<T> toArrayList() {
        ArrayList<T> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    // ... Implement other methods here
}
