import Jumbl.Runner;

import java.io.File;
import java.sql.Time;
import Jumbl.*;

public class Tester {
    public static void main(String[] args){
       System.out.println("Hello" + "World");
       System.out.println("This is a number: " + 12);
       int var = 12;
       System.out.println("This is the same number: " + var);
       System.out.println("Is this the same number?" + 6 + 6);
    }
    public static void getFile(String fileName){
        System.out.print("\u000c"); // clear screen
        try{
            File input = new File(fileName);}
        catch(Exception e){System.out.print("Couldn't find file");}
        //System.out.println("Found File!");
        Runner.runInterpreter(fileName);
    }
}
