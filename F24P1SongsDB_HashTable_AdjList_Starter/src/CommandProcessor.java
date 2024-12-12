import java.io.File;
import java.util.Scanner;
/**
 * Command Processor class
 *
 * @author Christian Calvo
 * @version 1.0
 */


public class CommandProcessor 
{
    //The file containing the instructions
    private String fileName;
    //The controller used to execute instructions
    private Controller controller;
    
    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Create a new CommandProcessor
     * 
     * @param fileName
     *          name of the file containing instructions
     * @param controller
     *          controller being used to execute instructions
     */
    public CommandProcessor(String fileName, Controller controller)
    {
        this.fileName = fileName;
        this.controller = controller;
    }
    
    /**
     * Parse all the lines in the file and run the commands
     * 
     * @throws
     *      FileNotFoundException
     */
    public void parseAllLines()
    {
        try 
        {   
            //Create our new scanner
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) //Loop through all lines in the file
            {
                //Get the command
                String cmd = sc.next();
                //Get the inputs for this command
                String line = sc.nextLine().trim();
                //Check if the line is empty
                if (!line.isEmpty())
                {
                    //parse the inputs for this command
                    parseLine(new Scanner(line), cmd.trim());
                }
                else
                {
                    System.out.println("Invalid input " + cmd.trim());
                }
            }
            sc.close();
        } 
        catch (Exception e)
        {
            e.printStackTrace(); //catch FileNotFoundException
        }
    }
    
    /**
     * Parse one command line
     * 
     * @param oneLine
     *          The line we are scanning for inputs
     * @param cmd
     *          The command for this line
     */
    public void parseLine(Scanner oneLine, String cmd)
    {
        //Check if the scanner has information to read
        if (oneLine.hasNext()) 
        {
            String type;
            switch(cmd) {
                case "print" : //Found a print command
                    //get type of list to print
                    type = oneLine.next();
                    controller.print(type.trim());
                    break;
                case "insert" : //Found a delete command
                    //Change delimiter to match command format
                    oneLine.useDelimiter("<SEP>");
                    //get artist to insert
                    String artist = oneLine.next();
                    //check if the command format is valid
                    if (!oneLine.hasNext())
                    {
                        System.out.println("Invalid input " + cmd);
                        break;
                    }
                    //get the song to insert
                    String song = oneLine.next();
                    controller.insert(artist.trim(), song.trim());
                    break;
                case "remove" : //Found a search command
                    //get the type of list to remove from
                    type = oneLine.next();
                    //Change delimiter to match command format
                    oneLine.useDelimiter("\n");
                    //check if the command format is valid
                    if (!oneLine.hasNext()) 
                    {
                        System.out.println("Invalid input " + cmd);
                        break;
                    }
                    //get the input we want to remove
                    String input = oneLine.next();
                    controller.remove(type.trim(), input.trim());
                    break;
                default : //Found an unrecognized command
                    System.out.println("Invalid input " + cmd);
                    break;
            }
        }
    }
}