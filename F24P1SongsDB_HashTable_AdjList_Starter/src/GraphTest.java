import student.TestCase;

/**
 * @author Christian Calvo
 * @version 1.0
 */
public class GraphTest extends TestCase 
{
    //table being used for testing
    private Graph graph;
    
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        graph = new Graph();
        graph.init(10);
    }
    
    /**
     * Check out the nodeCount method
     */
    public void testNodeCount()
    {
        assertTrue(graph.nodeCount() == 0);
    }
    
    /**
     * Check out the edgeCount method
     */
    public void testEdgeCount()
    {
        assertTrue(graph.edgeCount() == 0);
    }
    
    /**
     * Check out the newNode method
     */
    public void testNewNode()
    {
        //Make sure we started with an empty graph
        assertTrue(graph.nodeCount() == 0);
        //Make sure we added the new node to the correct position
        assertEquals(graph.newNode(), 0);
        assertEquals(graph.newNode(), 1);
    }
    
    /**
     * Check out the addEdge method
     */
    public void testAddEdge()
    {
        //Make sure we started with an empty graph
        assertTrue(graph.edgeCount() == 0);
        graph.newNode();
        graph.newNode();
        graph.newNode();
        graph.newNode();
        //Make sure there are no edges despite the graph not being empty
        assertTrue(graph.edgeCount() == 0);
        graph.addEdge(0, 2);
        graph.addEdge(2, 0);
        //Make sure the edges were added
        assertTrue(graph.edgeCount() == 2);
        graph.addEdge(0, 2);
        graph.addEdge(2, 0);
        //Make sure no duplicate edges are added
        assertTrue(graph.edgeCount() == 2);
        graph.addEdge(0, 3);
        graph.addEdge(3, 0);
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);
        assertTrue(graph.edgeCount() == 6);
        graph.addEdge(3, 3);
        //Make sure no edges connect the same vertex
        assertTrue(graph.edgeCount() == 6);
    }
    
    /**
     * Check out the hasEdge method
     */
    public void testHasEdge()
    {
        graph.newNode();
        graph.newNode();
        //Make sure there are no edges despite the graph not being empty
        assertFalse(graph.hasEdge(0, 1));
        graph.addEdge(0, 1);
        //Make sure the edges only exist in the correct direction
        assertTrue(graph.hasEdge(0, 1));
        assertFalse(graph.hasEdge(1, 0));
        graph.addEdge(1, 0);
        assertTrue(graph.hasEdge(1, 0));
    }
    
    /**
     * Check out the print method
     */
    public void testPrint()
    {
        graph.newNode();
        graph.newNode();
        //Check that the nodes are connected correctly
        graph.print();
        assertFuzzyEquals("There are 2 connected components\r\n"
            + "The largest connected component has 1 elements", 
            systemOut().getHistory());
        systemOut().clearHistory();
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);
        //Check that the nodes are connected correctly
        graph.print();
        assertFuzzyEquals("There are 1 connected components\r\n"
            + "The largest connected component has 2 elements", 
            systemOut().getHistory());
        systemOut().clearHistory();
        graph.newNode();
        graph.newNode();
        graph.addEdge(0, 2);
        graph.addEdge(2, 0);
        graph.addEdge(0, 3);
        graph.addEdge(3, 0);
        graph.newNode();
        graph.addEdge(4, 3);
        graph.addEdge(3, 4);
        //Check that the graph is printed correctly
        graph.print();
        assertFuzzyEquals("There are 1 connected components\r\n"
            + "The largest connected component has 5 elements", 
            systemOut().getHistory());
        systemOut().clearHistory();
        graph.removeNode(2);
        //Check that the graph with a removed node is printed correctly
        graph.print();
        assertFuzzyEquals("There are 1 connected components\r\n"
            + "The largest connected component has 4 elements", 
            systemOut().getHistory());
        systemOut().clearHistory();
    }
    
    /**
     * Check out the removeEdge method
     */
    public void testRemoveEdge()
    {
        graph.newNode();
        graph.newNode();
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(1, 0));
        graph.newNode();
        graph.addEdge(0, 2);
        graph.addEdge(2, 0);
        assertTrue(graph.hasEdge(0, 2));
        assertTrue(graph.hasEdge(2, 0));
        graph.removeEdge(0, 2);
        //Check that the edge was removed
        assertFalse(graph.hasEdge(0, 2));
        graph.addEdge(0, 2);
        graph.removeEdge(1, 0);
        graph.removeEdge(1, 0);
        //Check that a removed edge can be added back
        assertTrue(graph.hasEdge(0, 2));
        //Make sure it doesn't try to remove edges that don't exist
        assertFalse(graph.hasEdge(1, 0));
        graph.newNode();
        graph.newNode();
        graph.addEdge(0, 4);
        graph.addEdge(0, 3);
        graph.removeEdge(0, 1);
        //Make sure new nodes and edges are still added correctly
        assertFalse(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(0, 3));
        assertTrue(graph.hasEdge(0, 4));
        //Check that the edgeCount is adjusted properly
        assertTrue(graph.edgeCount() == 4);
    }
    
    /**
     * Check out the removeNode method
     */
    public void testRemoveNode()
    {
        graph.newNode();
        graph.newNode();
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);
        //Make sure nodes were added correctly
        assertEquals(graph.newNode(), 2);
        graph.addEdge(0, 2);
        graph.addEdge(2, 0);
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(1, 0));
        assertEquals(graph.freedCount(), 0);
        graph.removeNode(1);
        //Make sure the freed list was adjusted correctly
        assertEquals(graph.freedCount(), 1);
        //Make sure the edges were removed correctly
        assertFalse(graph.hasEdge(0, 1));
        assertFalse(graph.hasEdge(1, 0));
        //Make sure the new node was added to the correct vertex
        assertEquals(graph.newNode(), 1);
        //Make sure the freed list was adjusted correctly
        assertEquals(graph.freedCount(), 0);
        graph.newNode();
        //Make sure that new nodes are added to the correct vertex
        assertEquals(graph.newNode(), 4);
        graph.removeNode(3);
        assertEquals(graph.freedCount(), 1);
        graph.removeNode(1);
        //Make sure multiple nodes can be removed
        assertEquals(graph.freedCount(), 2);
        //Make sure that new nodes are added to the first free vertex
        assertEquals(graph.newNode(), 1);
        assertEquals(graph.freedCount(), 1);
        assertEquals(graph.newNode(), 3);
        assertEquals(graph.freedCount(), 0);
        graph.newNode();
        graph.newNode();
        graph.addEdge(0, 3);
        graph.addEdge(3, 0);
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);
        graph.addEdge(1, 4);
        graph.addEdge(4, 1);
        graph.addEdge(4, 5);
        graph.addEdge(5, 4);
        graph.addEdge(4, 6);
        graph.addEdge(6, 4);
        //Make sure that the graph is adjusted correctly after remove
        graph.print();
        assertFuzzyEquals("There are 1 connected components\r\n"
            + "The largest connected component has 7 elements", 
            systemOut().getHistory());
        systemOut().clearHistory();
        graph.removeNode(4);
        //Make sure that the graph is adjusted correctly after remove
        graph.print();
        assertFuzzyEquals("There are 3 connected components\r\n"
            + "The largest connected component has 4 elements", 
            systemOut().getHistory());
        systemOut().clearHistory();
        graph.removeNode(0);
        //Make sure that the graph is adjusted correctly after remove
        graph.print();
        assertFuzzyEquals("There are 5 connected components\r\n"
            + "The largest connected component has 1 elements", 
            systemOut().getHistory());
        systemOut().clearHistory();
        
    }
    
    /**
     * Check out the expand method
     */
    public void testExpand()
    {
        graph.newNode();
        graph.newNode();
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);
        graph.newNode();
        graph.addEdge(0, 2);
        graph.addEdge(2, 0);
        graph.newNode();
        graph.addEdge(0, 3);
        graph.addEdge(3, 0);
        graph.newNode();
        graph.newNode();
        graph.newNode();
        graph.newNode();
        graph.newNode();
        graph.addEdge(4, 1);
        graph.addEdge(1, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 4);
        graph.addEdge(6, 4);
        graph.addEdge(4, 6);
        assertTrue(graph.getSize() == 10);
        graph.newNode();
        graph.print();
        //Make sure the graph size doubled when the graph was full
        assertTrue(graph.getSize() == 20);
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(1, 0));
        assertTrue(graph.hasEdge(0, 2));
        assertTrue(graph.hasEdge(2, 0));
        assertTrue(graph.hasEdge(0, 3));
        assertTrue(graph.hasEdge(3, 0));
    }
}