package Jumbl;
import java.util.*;
import java.io.*;
public class Runner
{
    //runs the program
    public static void runInterpreter(String fn){
        FileReader rdr = null;
        String txt = "";
        try{
            File input = new File(fn);
            rdr = new FileReader(input);
            while(rdr.ready()){txt+=(char)rdr.read();}
            rdr.close();
        }
        catch(IOException e){System.out.print("Couldn't find file");}

        //make tokens from text
        Lexer lexer = new Lexer(fn, txt);
        ArrayList<Token> tokens = lexer.makeTokens();
        //System.out.println(tokens);

        //generates abstract syntax tree(AST)
        Parser parser = new Parser(tokens);
        parser.parse();
        //System.out.println(ast); // bruh this is the weirdest bug i've ever seen

        Interpreter interpreter = new Interpreter();
        //String result = interpreter.interpret().toString();

        interpreter.interpret();
    }
    public static long testInterpreter(String fn){
        FileReader rdr = null;
        String txt = "";
        try{
            File input = new File(fn);
            rdr = new FileReader(input);
            while(rdr.ready()){txt+=(char)rdr.read();}
            rdr.close();
        }
        catch(IOException e){System.out.print("Couldn't find file");}

        //make tokens from text
        Lexer lexer = new Lexer(fn, txt);
        ArrayList<Token> tokens = lexer.makeTokens();
        //System.out.println(tokens);

        //generates abstract syntax tree(AST)
        Parser parser = new Parser(tokens);
        parser.parse();
        //System.out.println(ast); // bruh this is the weirdest bug i've ever seen
        final long start = System.nanoTime();
        Interpreter interpreter = new Interpreter();
        //String result = interpreter.interpret().toString();

        interpreter.interpret();
        final long end = System.nanoTime()- start;
        return end;
    }

}
