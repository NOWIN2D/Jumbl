package Jumbl;

import java.util.*;
/**
 * Stores stuff the interpreter needs to remember
 *
 * @author Jeremiah Francis
 * @version 0.0.1 ?
 */
public class Memory
{
    public static HashMap<String, String> lexerValues; // value, TYPE
    public static ArrayList<CodeUnit> globals;
    public static CodeUnit.Exec exec;
    public static Stmt scope;

}
