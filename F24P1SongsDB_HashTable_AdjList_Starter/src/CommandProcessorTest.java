import student.TestCase;
import java.util.Scanner;
/**
 * @author Christian Calvo
 * @version 1.0
 */
public class CommandProcessorTest extends TestCase 
{
    //CommandProcessor being used to test methods
    private CommandProcessor processor;
    //CommandProcessor being used to test exception catching
    private CommandProcessor exception;
    
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        processor = new CommandProcessor("solutionTestData/P1_testInput1.txt", 
            new Controller(10));
        exception = new CommandProcessor("solutionTestData/fake.txt", 
            new Controller(10));
    }
    
    /**
     * Check out the parseAllLines method
     * 
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testParseAllLines()
    {
        //Check that parseAllLines produces the correct output
        processor.parseAllLines();
        assertFuzzyEquals("|When Summer's Through| does not exist in the Song database.\r\n"
            + "total songs: 0\r\n"
            + "total artists: 0\r\n"
            + "There are 0 connected components\r\n"
            + "The largest connected component has 0 elements\r\n"
            + "Invalid input print\r\n"
            + "|ABC| is added to the Artist database.\r\n"
            + "|American| is added to the Song database.\r\n"
            + "|ABC<SEP>American| duplicates a record already in the database.\r\n"
            + "Invalid input insert\r\n"
            + "Invalid input insert\r\n"
            + "|Blind Lemon Jefferson| is added to the Artist database.\r\n"
            + "|Long   Lonesome Blues| is added to the Song database.\r\n"
            + "|long Lonesome Blues| is added to the Song database.\r\n"
            + "|Ma Rainey| is added to the Artist database.\r\n"
            + "|Ma Rainey's Black Bottom| is added to the Song database.\r\n"
            + "|Long Lonesome Blues| is added to the Song database.\r\n"
            + "Song hash table size doubled.\r\n"
            + "|Mississippi Boweavil Blues| is added to the Song database.\r\n"
            + "|Fixin' To Die Blues| is added to the Song database.\r\n"
            + "0: |Blind Lemon Jefferson|\r\n"
            + "3: |ABC|\r\n"
            + "7: |Ma Rainey|\r\n"
            + "total artists: 3\r\n"
            + "1: |Fixin' To Die Blues|\r\n"
            + "2: |Mississippi Boweavil Blues|\r\n"
            + "7: |long Lonesome Blues|\r\n"
            + "10: |American|\r\n"
            + "15: |Long Lonesome Blues|\r\n"
            + "16: |Ma Rainey's Black Bottom|\r\n"
            + "19: |Long   Lonesome Blues|\r\n"
            + "total songs: 7\r\n"
            + "There are 3 connected components\r\n"
            + "The largest connected component has 5 elements\r\n"
            + "|Sleepy| does not exist in the Song database.\r\n"
            + "Invalid input remove\r\n"
            + "Invalid input remove\r\n"
            + "|ma rainey| does not exist in the Artist database.\r\n"
            + "|Ma Rainey| is removed from the Artist database.\r\n"
            + "0: |Blind Lemon Jefferson|\r\n"
            + "3: |ABC|\r\n"
            + "7: TOMBSTONE\r\n"
            + "total artists: 2\r\n"
            + "Invalid input add\r\n"
            + "Invalid input print", systemOut().getHistory());
        systemOut().clearHistory();
        //Check that parseAllLines catches the FileNotFound exception
        exception.parseAllLines();
    }
    
    /**
     * Check out the parseLine method
     */
    public void testParseLine()
    {
        systemOut().clearHistory();
        //Check if parseLine correctly catches invalid inputs
        processor.parseLine(new Scanner("Blind Lemon Jefferson"
            + "<SEP>Long Lonesome Blues"), "add");
        assertFuzzyEquals("Invalid input add\r\n", systemOut().getHistory());
        systemOut().clearHistory();
        //Check that an empty input produces the correct result
        processor.parseLine(new Scanner(""), "insert");
        assertFuzzyEquals("", systemOut().getHistory());
    }
    
}