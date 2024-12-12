/**
 * Node class
 *
 * @author Christian Calvo
 * @version 1.0
 */
public class Node
{

    private int index; //Store the position of the node in the graph
    
    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Create a new node object
     * 
     * @param i
     *         The index of this Node
     */
    public Node(int i)
    {
        index = i;
    }
    
    /**
     * Return the index of this node
     * 
     * @return
     *         the index of this node
     */
    public int get()
    {
        return index;
    }
    
    /**
     * Set the index of this node
     * 
     * @param i
     *          the int we want to set index to
     */
    public void set(int i)
    {
        index = i;
    }
    
}