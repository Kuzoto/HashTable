import student.TestCase;

/**
 * @author Christian Calvo
 * @version 1.0
 */
public class ControllerTest extends TestCase 
{
    //Controller being used to test methods
    private Controller controller;
    
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        controller = new Controller(10);
    }
    
    /**
     * Check out the insert method
     */
    public void testInsert()
    {
        //Check the output when inserting a new Record into both tables
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        assertFuzzyEquals("|Blind Lemon Jefferson| "
            + "is added to the Artist database.\r\n"
            + "|Long Lonesome Blues| "
            + "is added to the Song database.", systemOut().getHistory());
        systemOut().clearHistory();
        //Check the output when inserting a duplicate edge
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        assertFuzzyEquals("|Blind Lemon Jefferson<SEP>Long Lonesome Blues| "
            + "duplicates a record "
            + "already in the database.", systemOut().getHistory());
        systemOut().clearHistory();
        //Check the output when inserting an existing song
        //with different spacing
        controller.insert("Blind Lemon Jefferson", "Long   Lonesome Blues");
        assertFuzzyEquals("|Long   Lonesome Blues| "
            + "is added to the Song database.", systemOut().getHistory());
        systemOut().clearHistory();
        //Check the output for inputting only one new Record
        controller.insert("Ma Rainey", "Long Lonesome Blues");
        assertFuzzyEquals("|Ma Rainey| "
            + "is added to the Artist database.", systemOut().getHistory());
    }
    
    /**
     * Check out the remove method
     */
    public void testRemove()
    {
        systemOut().clearHistory();
        //Check the output for trying to remove something not in the table
        controller.remove("artist", "Blind Lemon Jefferson");
        assertFuzzyEquals("|Blind Lemon Jefferson| "
            + "does not exist in the Artist database.", 
            systemOut().getHistory());
        systemOut().clearHistory();
        controller.remove("song", "Sleepy");
        assertFuzzyEquals("|Sleepy| "
            + "does not exist in the Song database.", systemOut().getHistory());
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        systemOut().clearHistory();
        //Check the output for removing something that exists
        controller.remove("artist", "Blind Lemon Jefferson");
        assertFuzzyEquals("|Blind Lemon Jefferson| "
            + "is removed from the Artist database.", systemOut().getHistory());
        systemOut().clearHistory();
        //Check the output for trying to remove from an invalid table
        controller.remove("music", "Long Lonesome Blues");
        assertFuzzyEquals("", systemOut().getHistory());
        systemOut().clearHistory();
        //Check the output for removing something that exists
        controller.remove("song", "Long Lonesome Blues");
        assertFuzzyEquals("|Long Lonesome Blues| "
            + "is removed from the Song database.", systemOut().getHistory());
    }
    
    /**
     * Check out the print method
     */
    public void testPrint()
    {
        systemOut().clearHistory();
        //Check the output for printing an empty table
        controller.print("artist");
        assertFuzzyEquals("total artists: 0", systemOut().getHistory());
        systemOut().clearHistory();
        controller.print("song");
        assertFuzzyEquals("total songs: 0", systemOut().getHistory());
        controller.insert("Blind Lemon Jefferson", "Long Lonesome Blues");
        controller.insert("Blind Lemon Jefferson", "Long   Lonesome Blues");
        controller.insert("Ma Rainey", "Long Lonesome Blues");
        systemOut().clearHistory();
        //Check the output for printing a table with 2 Records
        controller.print("artist");
        assertFuzzyEquals("0: |Blind Lemon Jefferson|\r\n"
            + "7: |Ma Rainey|\r\n"
            + "total artists: 2", systemOut().getHistory());
        systemOut().clearHistory();
        controller.print("song");
        assertFuzzyEquals("5: |Long Lonesome Blues|\r\n"
            + "9: |Long   Lonesome Blues|\r\n"
            + "total songs: 2", systemOut().getHistory());
        controller.remove("song", "Long Lonesome Blues");
        systemOut().clearHistory();
        //Check the output for printing a table with a removed Record
        controller.print("song");
        assertFuzzyEquals("5: TOMBSTONE\r\n"
            + "9: |Long   Lonesome Blues|\r\n"
            + "total songs: 1", systemOut().getHistory());
        systemOut().clearHistory();
        //Check the output of printing an invalid table
        controller.print("music");
        assertFuzzyEquals("", systemOut().getHistory());
        systemOut().clearHistory();
        //Check the output of printing a graph
        controller.print("graph");
        assertFuzzyEquals("There are 2 connected components\r\n"
            + "The largest connected component has 2 elements", 
            systemOut().getHistory());
    }
}