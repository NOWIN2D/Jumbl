import java.io.*;
import Jumbl.*;
/**
 * Gets a file for the program
 * 
 * "main.jumbl"
 */
public class FileGet
{
    public static void main(String[] args){
        getFile("main.jumbl");
    }
    public static void getFile(String fileName){
        System.out.print("\u000c"); // clear screen
        try{File input = new File(fileName);}
        catch(Exception e){System.out.print("Couldn't find file");}
        //System.out.println("Found File!");
        Runner.runInterpreter(fileName);
    }
}
