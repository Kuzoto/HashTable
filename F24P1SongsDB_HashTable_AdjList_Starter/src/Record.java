/**
 * Record class
 *
 * @author Christian Calvo
 * @version 1.0
 */
public class Record
{
    private String key; //The name of the song/artist
    private Node entry; //The node representing this song/artist
    
    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Create a new record object
     * 
     * @param k
     *          The key for this record
     * @param e
     *          The Node for this record
     */
    public Record(String k, Node e)
    {
        key = k;
        entry = e;
    }
    
    /**
     * Return the key for this record
     * 
     * @return
     *      The key of this record
     */
    public String key()
    {
        //Check that the key for this record is not null
        if (!key.equals(null))
        {
            return key;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Return the node for this record
     * 
     * @return
     *      The node of this record
     */
    public Node node()
    {
        return entry;
    }
}