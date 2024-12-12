

/**
 * Double Linked List class
 *
 * @author Christian Calvo
 * @version 1.0
 */
public class DoubleLL
{
    // This representation uses a pair of sentinel nodes at each end of
    // the chain to make it easier to insert at either end in constant time.
    private DLLNode head;

    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Create a new, empty list.
     */
    public DoubleLL() {
        this.clear();
    }

    // ~ Public Methods ........................................................

    
    // ----------------------------------------------------------
    /**
     * Resets this list to be empty by removing all its values.
     */
    public void clear() {
        // throws away any existing chain of nodes and replaces it with
        // a new empty chain containing just the sentinel nodes at start
        // and end.
        //this.tail = new DLLNode(-1, this.head, null);
        this.head = new DLLNode(-1, null, null);
    }
    
    /**
     * Return the head of the List
     * 
     * @return
     *      The head of the list
     */
    public DLLNode getHead()
    {
        return this.head;
    }
    
    /**
     * Replace the head node of the list
     * 
     * @param head
     *      The node we are replacing head with
     */
    public void setHead(DLLNode head)
    {
        this.head = head;
    }

    // Other operations, like various other remove() methods, or inserting
    // at a specified position, are left as an exercise for the reader.


    // ----------------------------------------------------------
    /**
     * The internal DLLNode class that represents nodes in the linked chain.
     */
    public static class DLLNode {
        private int data;
        private DLLNode next;
        private DLLNode prev;
        
        // ~ Constructor ............................................

        // ----------------------------------------------------------
        /**
         * Create a new DLLNode
         * 
         * @param data
         *      The vertex of the node
         * @param prev
         *      Pointer to the previous node
         * @param next
         *      Pointer to the following node 
         */
        public DLLNode(int data, DLLNode prev, DLLNode next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        /**
         * Return the vertex of the node
         * 
         * @return
         *      The vertex of the node
         */
        public int getData() {
            return this.data;
        }

        /**
         * Set the vertex of a node to value
         * 
         * @param value
         *      The new vertex value
         */
        public void setData(int value) {
            this.data = value;
        }

        /**
         * Return the pointer to the next node
         * 
         * @return
         *      The pointer to the next node
         */
        public DLLNode getNext() {
            return this.next;
        }

        /**
         * Change the pointer to the next node
         * 
         * @param next
         *      The pointer we want to change next to
         */
        public void setNext(DLLNode next) {
            this.next = next;
        }
        
        /**
         * Return the pointer to the previous node
         * 
         * @return
         *      The pointer to the previous node
         */
        public DLLNode getPrev() {
            return this.prev;
        }

        /**
         * Change the pointer to the previous node
         * 
         * @param prev
         *      The pointer we want to change prev to
         */
        public void setPrev(DLLNode prev) {
            this.prev = prev;
        }
    }
}