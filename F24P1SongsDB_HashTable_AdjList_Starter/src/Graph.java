import java.lang.reflect.Array;

/**
 * Graph class
 *
 * @author Christian Calvo
 * @version 1.0
 */
public class Graph {
    
    //AdjList of DLLNodes representing vertices
    private DoubleLL[] vertex;
    //List of freed DLLNodes
    private DoubleLL freed;
    //Number of Edges in vertex list
    private int numEdge;
    //Number of nodes in vertex list
    private int numberOfNodes;
    //The size of the vertex list
    private int size;
    //The parents of each vertex
    private int[] parents;
    //The number of elements connected to each parent
    private int[] uniqueRoots;
    //The number of freed DLLNodes
    private int numFreed;
  
    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     *  Create a new graph object
     */
    Graph() { }

    /**
     * Initialize the graph with n vertices
     * 
     * @param n
     *       The initial size of the graph
     */
    public void init(int n) 
    {
        vertex = new DoubleLL[n];
        //Setup list headers
        for (int i = 0; i < n; i++) { vertex[i] = new DoubleLL(); }
        freed = new DoubleLL();
        numFreed = 0;
        numEdge = 0;
        numberOfNodes = 0;
        size = n;
    }
  
    // Return the number of vertices
    public int nodeCount() { return numberOfNodes; }

    // Return the current number of edges
    public int edgeCount() { return numEdge; }
    
    // Return the current size of the list
    public int getSize() { return size; }
    
    // Return the current number of freed nodes
    public int freedCount() { return numFreed; }
  
    /**
     * Return the link in v's list that proceeds the
     * one with w (or where it would be)
     * 
     * @param v
     *      The list we are looking in
     * @param w
     *      The vertex we are looking for
     * @return
     *      The node the proceeds w
     */
    private DoubleLL.DLLNode find(int v, int w) 
    {
        DoubleLL.DLLNode curr = vertex[v].getHead();
        //Find the node proceeding w
        while ((curr.getNext() != null) && (curr.getNext().getData() < w)) {
            curr = curr.getNext();
        }
        return curr;
    }
    
    /**
     * Create a new node and add it to a free vertex
     * Expand the AdjList if the number of nodes is
     * over half the size of the list
     * 
     * @return
     *      The vertex(index) where the node was added 
     */
    public int newNode()
    {
        int index;
        //Check if there are any freed nodes
        if (freed.getHead().getNext() != null)
        {
            //Get the first freed node
            DoubleLL.DLLNode free = freed.getHead().getNext();
            index = free.getData();
            //Go to the freed vertex
            DoubleLL.DLLNode curr = vertex[index].getHead();
            //Add the new node at the freed vertex
            curr.setData(index);
            //Remove the freed node from the freed list
            if (free.getNext() != null)
            {
                freed.getHead().setNext(free.getNext());
                free.getNext().setPrev(freed.getHead());
                numFreed--;
            }
            else
            {
                freed.getHead().setNext(null);
                numFreed--;
            }
            numberOfNodes++;
        }
        else
        {
            //Go to the first available vertex
            index = numberOfNodes;
            DoubleLL.DLLNode curr = vertex[numberOfNodes].getHead();
            //Add the new node at the vertex
            curr.setData(numberOfNodes);
            numberOfNodes++;
        }
        //If the list is full expand it
        if (numberOfNodes == size)
        {
            int oldSize = size;
            size = size * 2;
            //create a temp list to store our vertices
            DoubleLL[] temp = vertex;
            //create a new list twice as large as the original
            vertex = new DoubleLL[size];
            for (int i = 0; i < size; i++) { vertex[i] = new DoubleLL(); }
            //loop through the temp list and place each vertex in the new list
            for (int i = 0; i < oldSize; i++)
            {
                vertex[i].setHead(temp[i].getHead());
            }
        }
        return index;
    }

    /**
     * Adds a new edge from node v to node w
     * 
     * @param v
     *      The vertex we want to add a link to
     * @param w
     *      The vertex of the link we are adding
     */
    public void addEdge(int v, int w) 
    {
        //Cannot add edges to the same vertex
        if (v == w) { return; }
        DoubleLL.DLLNode curr = find(v, w);
        if ((curr.getNext() != null) && (curr.getNext().getData() == w)) 
        {
            //Edge already exists
        }
        else 
        {
            //Add the edge to the list and connect it to following nodes
            curr.setNext(new DoubleLL.DLLNode(w, curr, curr.getNext()));
            numEdge++;
            if (curr.getNext().getNext() != null) 
            { 
                curr.getNext().getNext().setPrev(curr.getNext()); 
            }
        }
    }

    /**
     * Removes the edge from the graph.
     * 
     * @param v
     *      The vertex we are removing a link from
     * @param w
     *      The vertex of the link we want to remove
     */
    public void removeEdge(int v, int w) 
    {
        //Find the link proceeding the edge we want to remove
        DoubleLL.DLLNode curr = find(v, w);
        //Check if the edge exists
        if ((curr.getNext() == null) || curr.getNext().getData() != w) 
        { 
            return; 
        }
        else 
        {
            //Remove the edge
            curr.setNext(curr.getNext().getNext());
            if (curr.getNext() != null) { curr.getNext().setPrev(curr); }
        }
        numEdge--;
    }
    
    /**
     * Remove a node from the graph.
     * 
     * @param v
     *      Vertex of the node we want removed
     */
    public void removeNode(int v)
    {
        //Get the head of the node we want to remove
        DoubleLL.DLLNode curr = vertex[v].getHead();
        //Remove all edges to this node
        while (curr.getNext() != null)
        {
            removeEdge(curr.getNext().getData(), v);
            removeEdge(v, curr.getNext().getData());
        }
        //Get the list of free nodes
        DoubleLL.DLLNode free = freed.getHead();
        //Find the node proceeding where this node should go
        while ((free.getNext() != null) && (free.getNext().getData() < v))
        {
            free = free.getNext();
        }
        //Add this removed node to the freed list
        free.setNext(new DoubleLL.DLLNode(v, free, free.getNext()));
        numFreed++;
        //If there is a node following the node we just added connect them
        if (free.getNext().getNext() != null) 
        { 
            free.getNext().getNext().setPrev(free.getNext()); 
        }
        //Set the node in the graph to empty
        curr.setNext(null);
        curr.setData(-1);
        numberOfNodes--;
    }

    /**
     * Returns true iff the graph has the edge
     * 
     * @param v
     *      The vertex we are looking in
     * @param w
     *      The vertex of the link we are looking for
     * @return
     *      true if there is an edge between the vertices
     */
    public boolean hasEdge(int v, int w) 
    { 
        DoubleLL.DLLNode curr = find(v, w);
        //Check that w is linked to v
        return ((curr.getNext() != null) && (curr.getNext().getData() == w)); 
    }
    
    /**
     * Print the graph
     */
    public void print()
    {
        //Number of positions in the graph
        int numP = numberOfNodes + numFreed;
        //The largest number of connected elements
        int largest = 0;
        //The total number of connected components
        int numConnections = 0;
        //The parents of each vertex
        parents = new int[numP];
        //Initialize the parent array
        for (int s = 0; s < numP; s++) { parents[s] = -1; }
        //The current outer vertex
        DoubleLL.DLLNode curr1;
        //The current inner vertex
        DoubleLL.DLLNode curr2;
        //Loop through each vertex in the graph
        for (int i = 0; i < numP; i++)
        {
            curr1 = vertex[i].getHead();
            //Check that the outer vertex is not empty
            if (curr1.getData() != -1)
            {
                //Loop through each vertex at or above the outer vertex
                for (int j = i; j < numP; j++)
                {
                    curr2 = vertex[j].getHead();
                    //Check if the vertices have an edge between them
                    if ((curr2.getData() != -1) && 
                        hasEdge(curr1.getData(), curr2.getData()))
                    {
                        //Merge the two
                        union(curr1.getData(), curr2.getData());
                    }
                }
            }
        }
        //Calculate the number elements pointing to each root
        connectedComponents();
        //Determine the number of connected components and the largest one
        for (int r = 0; r < numP; r++)
        {
            if (uniqueRoots[r] != 0)
            {
                numConnections++;
                if (uniqueRoots[r] > largest)
                {
                    largest = uniqueRoots[r];
                }
            }
        }
        System.out.println("There are " + numConnections 
            + " connected components\r\n"
            + "The largest connected component has " 
            + largest + " elements");
    }
    
    /**
     * Merge two subtrees together
     * 
     * @param a
     *      vertex of node we want to merge
     * @param b
     *      vertex of node we want to merge
     */
    public void union(int a, int b)
    {
        int root1 = uFind(a); //find the root of node a
        int root2 = uFind(b); //find the root of node b
        if (root1 != root2)
        {
            parents[root2] = root1;
        }
    }
  
    /**
     * Find the root of a node
     * 
     * @param curr
     *      Vertex of the node we want to find the root of
     * @return
     *      The root of the node
     */
    public int uFind(int curr)
    {
        DoubleLL.DLLNode currNode = vertex[curr].getHead();
        while (currNode.getPrev() != null)
        {
            currNode = currNode.getPrev();
        }
        return currNode.getData();
    }
    
    /**
     * Compute the number elements pointing to each unique root
     */
    public void connectedComponents()
    {
        int numR = numberOfNodes + numFreed;
        int curr;
        int count;
        uniqueRoots = new int[numR];
        for (int i = 0; i < numR; i++)
        {
            if (vertex[i].getHead().getData() != -1)
            {
                curr = i;
                while (parents[curr] != -1)
                {
                    curr = parents[curr];
                }
                uniqueRoots[curr]++;
            }
        }
    }
}