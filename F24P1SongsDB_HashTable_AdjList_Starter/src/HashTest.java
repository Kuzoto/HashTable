import student.TestCase;

/**
 * @author Christian Calvo
 * @version 1.0
 */
public class HashTest extends TestCase 
{
    //table being used for testing
    private Hash table;
    private Hash smallTable;
    
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        table = new Hash(10, "Song");
        smallTable = new Hash(2, "Song");
    }


    /**
     * Check out the sfold method
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testSfold() throws Exception {
        assertTrue(Hash.h("a", 10000) == 97);
        assertTrue(Hash.h("b", 10000) == 98);
        assertTrue(Hash.h("aaaa", 10000) == 1873);
        assertTrue(Hash.h("aaab", 10000) == 9089);
        assertTrue(Hash.h("baaa", 10000) == 1874);
        assertTrue(Hash.h("aaaaaaa", 10000) == 3794);
        assertTrue(Hash.h("Long Lonesome Blues", 10000) == 4635);
        assertTrue(Hash.h("Long   Lonesome Blues", 10000) == 4159);
        assertTrue(Hash.h("long Lonesome Blues", 10000) == 4667);
    }
    
    /**
     * Check out the getNumRecords method
     */
    public void testGetNumRecords()
    {
        assertTrue(table.getNumRecords() == 0);
    }
    
    /**
     * Check out the getSize method
     */
    public void testGetSize()
    {
        assertTrue(table.getSize() == 10);
    }
    
    /**
     * Check out the insert method
     */
    public void testInsert()
    {
        //Make sure that the table expands correctly
        smallTable.insert("Long Lonesome Blues", 0);
        assertTrue(smallTable.getSize() == 2);
        smallTable.insert("Long Lonesome Blues", 1);
        assertTrue(smallTable.getNumRecords() == 1);
        assertTrue(smallTable.getSize() == 4);
        //Make sure new Records are inserted correctly
        table.insert("Long Lonesome Blues", 0);
        assertTrue(table.getNumRecords() == 1);
        //Make sure no duplicate Records are inserted
        table.insert("Long Lonesome Blues", 1);
        assertTrue(table.getNumRecords() == 1);
        //Make sure insert is case sensitive
        table.insert("Long   Lonesome Blues", 2);
        assertTrue(table.getNumRecords() == 2);
        table.insert("long Lonesome Blues", 3);
        assertTrue(table.getNumRecords() == 3);
        table.insert("Ma Rainey's Black Bottom", 4);
        assertTrue(table.getNumRecords() == 4);
        table.insert("Mississippi Boweavil Blues", 5);
        assertTrue(table.getNumRecords() == 5);
        assertTrue(table.getSize() == 10);
        //Make sure that the table expands when half full
        table.insert("Fixin' To Die Blues", 6);
        assertTrue(table.getSize() == 20);
        assertTrue(table.getNumRecords() == 6);
        table.insert("baaa", 7);
        assertTrue(table.getNumRecords() == 7);
        table.insert("aaaaaaa", 8);
        assertTrue(table.getNumRecords() == 8);
        //Make sure the Records are inserted correctly after removes
        table.remove("aaaaaaa");
        table.insert("aaaaaaa", 8);
        assertTrue(table.getNumRecords() == 8);
        table.remove("aaaaaaa");
        table.insert("When Summer's Through", 8);
        assertTrue(table.getNumRecords() == 8);
        table.insert("aaaa", 9);
        assertTrue(table.getNumRecords() == 9);
        table.insert("Sleepy", 10);
        assertTrue(table.getNumRecords() == 10);
        table.insert("Sleepy Boy", 11);
        assertTrue(table.getSize() == 40);
        assertTrue(table.getNumRecords() == 11);
    }
    
    /**
     * Check out the print method
     */
    public void testPrint()
    {
        //Make sure print handles empty tables correctly
        assertTrue(table.print() == 0);
        table.insert("Long Lonesome Blues", 0);
        table.insert("Long   Lonesome Blues", 1);
        table.insert("long Lonesome Blues", 2);
        table.insert("aaaaaaa", 3);
        table.insert("baaa", 4);
        systemOut().clearHistory();
        //Make sure the table is printed correctly
        assertTrue(table.print() == 5);
        assertFuzzyEquals("4: |aaaaaaa|\r\n"
            + "5: |Long Lonesome Blues|\r\n"
            + "7: |long Lonesome Blues|\r\n"
            + "8: |baaa|\r\n"
            + "9: |Long   Lonesome Blues|", systemOut().getHistory());
        table.remove("aaaaaaa");
        systemOut().clearHistory();
        //Make sure the table is printed correctly after a remove
        assertTrue(table.print() == 4);
        assertFuzzyEquals("4: TOMBSTONE\r\n"
            + "5: |Long Lonesome Blues|\r\n"
            + "7: |long Lonesome Blues|\r\n"
            + "8: |baaa|\r\n"
            + "9: |Long   Lonesome Blues|", systemOut().getHistory());
    }
    
    /**
     * Check out the remove method
     */
    public void testRemove()
    {
        systemOut().clearHistory();
        //Make sure that invalid removes are handled correctly
        table.remove("Sleepy");
        assertFuzzyEquals("|Sleepy| does not exist in the Song database.", 
            systemOut().getHistory());
        table.insert("Long Lonesome Blues", 0);
        table.insert("Long   Lonesome Blues", 1);
        table.insert("long Lonesome Blues", 2);
        table.insert("aaaaaaa", 3);
        table.insert("baaa", 4);
        assertTrue(table.getNumRecords() == 5);
        systemOut().clearHistory();
        //Make sure that a successful remove gives the correct output
        table.remove("baaa");
        assertFuzzyEquals("|baaa| is removed from the Song database.", 
            systemOut().getHistory());
        assertTrue(table.getNumRecords() == 4);
    }
    
    /**
     * Check out the find method
     */
    public void testFind()
    {
        table.insert("Long Lonesome Blues", 0);
        table.insert("Long   Lonesome Blues", 1);
        table.insert("long Lonesome Blues", 2);
        table.insert("aaaaaaa", 3);
        table.insert("baaa", 4);
        //Make sure find handles not found Records correctly
        assertNull(table.find("Sleepy"));
        //Make sure find returns the correct Record
        assertEquals(table.find("baaa").key(), "baaa");
        table.remove("aaaaaaa");
        //Make sure find handles removed Records correctly
        assertEquals(table.find("baaa").key(), "baaa");
    }
}
