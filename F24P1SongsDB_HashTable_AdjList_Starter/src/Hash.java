/**
 * Hash table class
 *
 * @author Christian Calvo
 * @version 1.0
 */

public class Hash {
    
    //Table containing all records for this hash table
    private Record[] allrecords;
    //String that tells us which hash table this is
    private String whichtable;
    //Stores the number of records in this hash table
    private int numberOfRecords;
    private Record tombstone;
    //Stores the size of the Table
    private int size;
    
    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Create a new Hash object
     * 
     * @param initSize
     *          The initial size of the hash table 
     * @param table
     *          String telling us which hash table this is
     */
    public Hash(int initSize, String table)
    {
        allrecords = new Record[initSize];
        numberOfRecords = 0;
        size = initSize;
        whichtable = table;
        tombstone = new Record("TOMBSTONE", null);
    }
    
    /**
     * Compute the hash function
     * 
     * @param s
     *            The string that we are hashing
     * @param length
     *            Length of the hash table (needed because this method is
     *            static)
     * @return
     *         The hash function value (the home slot in the table for this key)
     */
    public static int h(String s, int length) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % length);
    }
    
    /**
     * Insert song or artist into the hash table
     * 
     * @param input
     *            The string that we are inserting
     * @param index
     *            The vertex of record
     * 
     */
    public void insert(String input, int index) {
        //check if the numberOfRecords is greater than half the size of the list
        if (numberOfRecords == size / 2)
        {
            int oldSize = size;
            size = size * 2;
            //create a temp table to store our records 
            Record[] temp = allrecords;
            //create a new table twice as large as the original
            allrecords = new Record[size]; 
            numberOfRecords = 0;
            //loop through the temp table and rehash the records
            for (int i = 0; i < oldSize; i++)
            {
                //skip over null spaces and tombstones
                if (!(temp[i] == null) && !(temp[i] == tombstone))
                {
                    insert(temp[i].key(), temp[i].node().get());
                }
            }
            System.out.println(whichtable + " hash table size doubled.");
        }
        
        int home = h(input, size); // Home position for e
        int pos = home; // Init probe sequence
        
        //probe until we find a null spot or tombstone
        for (int i = 1; (!(allrecords[pos] == null) && 
            !(tombstone == allrecords[pos])); i++) 
        {
            //Double Check for duplicates
            if (input.equals(allrecords[pos].key())) 
            {
                return;
            }
            pos = (home + (i * i)) % size; // probe
        }
        //create a new record and add it to the table in the current pos
         allrecords[pos] = new Record(input, new Node(index));
         numberOfRecords++;
        
    }
    
    /**
     * Find a song or artist in the hash table
     * 
     * @param input
     *            The string that we are searching for
     * @return
     *          The record with given key
     */
    public Record find(String input)
    {
        int home = h(input, size); // Home position for e
        int pos = home; // Init probe sequence
        //probe until a null spot or a matching key is found
        for (int i = 1; !(allrecords[pos] == null) && 
            (!(input.equals((allrecords[pos]).key()))); i++)
        {
            pos = (home + (i * i)) % size;
        }
        /**check that the current pos is not null and 
         * that its key equals what we are searching for
         * this check may be redundant but I kept it for safety
        */
        if (!(allrecords[pos] == null) && input.equals(allrecords[pos].key()))
        {
            return allrecords[pos]; //The record with key we are searching for
        }
        else
        {
            return null; //Returned if no matching record is found
        }
    }
    
    /**
     * Remove a song or artist from the hash table
     * 
     * @param input
     *            The string that we are removing
     */
    public void remove(String input)
    {
        int home = h(input, size); // Home position for e
        int pos = home; // Init probe sequence
        //probe until a null spot or a matching key is found
        for (int i = 1; (!(allrecords[pos] == null) && 
            (!(input.equals((allrecords[pos]).key())))); i++)
        {
            pos = (home + (i * i)) % size;
        }
        /**check that the current pos is not null and 
         * that its key equals what we are searching for
         * this check may be redundant but I kept it for safety
        */
        if (!(allrecords[pos] == null) && input.equals(allrecords[pos].key()))
        {
            //set the matching to a tombstone record
            allrecords[pos] = tombstone;
            numberOfRecords--; //remove the record from the count
            System.out.println("|" + input + "| "
                + "is removed from the " + whichtable + " database.");
        }
        else
        {
            System.out.println("|" + input + "| "
                + "does not exist in the " + whichtable + " database.");
        }
    }
    
    /**
     * Print the contents of the hash table
     * 
     * @return
     *          The number of records printed
     */
    public int print()
    {
        int count = 0; //Records printed
        for (int i = 0; (i < size); i++)
        {
            //Check if a record is a tombstone
            if (!(allrecords[i] == null) && (tombstone == allrecords[i]))
            {
                System.out.println(i + ": " + allrecords[i].key());
                //do not include in count of printed records
            }
            else if (!(allrecords[i] == null)) //check if a record is not null
            {
                //Print the record and increment count
                System.out.println(i + ": |" + allrecords[i].key() + "|");
                count++;
            }
        }
        return count;
    }
    
    /**
     * Return the number of records in the hash table
     * 
     * @return
     *          The number of records in the table
     */
    public int getNumRecords()
    {
        return numberOfRecords;
    }
    
    /**
     * Return the size of the hash table
     * 
     * @return
     *          The size of the table
     */
    public int getSize()
    {
        return size;
    }
}
