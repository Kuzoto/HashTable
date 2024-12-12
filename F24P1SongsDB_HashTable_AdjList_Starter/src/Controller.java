/**
 * Controller class
 *
 * @author Christian Calvo
 * @version 1.0
 */

public class Controller 
{
    //The artist Hash Table
    private Hash artists;
    //The song Hash Table
    private Hash songs;
    //The graph 
    private Graph graph;
    
    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Create a new controller.
     * 
     * @param initSize
     *          The initial size of the hash tables
     */
    public Controller(int initSize)
    {
        artists = new Hash(initSize, "Artist");
        songs = new Hash(initSize, "Song");
        graph = new Graph();
        graph.init(initSize);
    }
    
    /**
     * Insert song and artist into the hash tables
     * 
     * @param artist
     *            The artist that we want inserted
     * @param song
     *            The song that we want inserted
     */
    public void insert(String artist, String song)
    {
        //Find the artist or song
        Record a = artists.find(artist);
        Record s = songs.find(song);
        int aIndex;
        int sIndex;
        
        //Check if the artist is in the table already 
        if (a == null)
        {
            //Create a newNode in graph for this artist and save its index
            aIndex = graph.newNode();
            //Create a new Record in the table for this artist
            //and connect the record to the graph
            artists.insert(artist, aIndex);
            //Print success
            System.out.println("|" + artist + "| "
                + "is added to the Artist database.");
        }
        else
        {
            //Get the index of this artists node if it exists
            aIndex = a.node().get();
        }
        //Check if the song is in the table already
        if (s == null)
        {
            //Create a new node in graph for this song and save its index
            sIndex = graph.newNode();
            //Create a new Record in the table for this song
            //and connect the record to the graph
            songs.insert(song, sIndex);
            //Print success
            System.out.println("|" + song + "| "
                + "is added to the Song database.");
        }
        else
        {
            //Get the index of this songs node if it exists
            sIndex = s.node().get();
        }
        
        //Check if this edge already exists in the graph
        if (graph.hasEdge(aIndex, sIndex))
        {
            //Print duplicate message
            System.out.println("|" + artist + "<SEP>" + song + 
                "| duplicates a record already in the database.");
        }
        else
        {
            //Create the new edge in the graph
            graph.addEdge(aIndex, sIndex);
            graph.addEdge(sIndex, aIndex);
        }
        
        
    }
    
    /**
     * Removes a song or artist from the hash tables and graph
     * 
     * @param type
     *            The type we want to remove
     * @param name
     *            The name of what we want to remove
     */
    public void remove(String type, String name)
    {
        int index;
        Record r;
        //Check if we want to remove an artist
        if (type.equals("artist"))
        {
            //Find the artist in the table
            r = artists.find(name);
            //Check if the artist was found
            if (r != null)
            {
                //Get the index for this Record
                index = r.node().get();
                //Remove the node connected to this Record
                graph.removeNode(index);
            }
            //Remove the artist from the table
            artists.remove(name);
        }
        //Check if we want to remove a song
        else if (type.equals("song"))
        {
            //Find the song in the table
            r = songs.find(name);
            //Check if the song was found
            if (r != null)
            {
                //Get the index for this Record
                index = r.node().get();
                //Remove the node connected to this Record
                graph.removeNode(index);
            }
            //Remove the song from the table
            songs.remove(name);
        }
    }
    
    /**
     * Print the song or artist from the hash table or graph
     * 
     * @param type
     *            The type we want to print
     */
    public void print(String type)
    {
        //Check the type we want to print
        if (type.equals("artist"))
        {
            //Print the artists table and the number of artists
            int count = artists.print();
            
            System.out.println("total artists: " + count);
        }
        else if (type.equals("song"))
        {
            //Print the songs table and the number of songs
            int count = songs.print();
            System.out.println("total songs: " + count);
        }
        else if (type.equals("graph"))
        {
            //Print the graph
            graph.print();
        }
    }
    
}