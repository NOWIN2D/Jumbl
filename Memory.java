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
    //public static Stmt scope;

    /**
     * The general scope of nested statements, the exec is -1 and anything is 0 and so on.
     *
     * For example:
     * exec {
     *     int i = 17
     *     if i == 17 {
     *         int r = 2
     *         loop {
     *             write i
     *             i -= r
     *         } for int n = 1 to 3
     *     }
     * }
     *
     * The variable i is stored in the exec variables at scope -1 and can be accessed by anything in the exec stmt
     * the variable r is stored at scope 0 and can therefore be accessed by any further scopes, but will be freed upon exiting the if stmt
     * The variable n is likewise stored at scope 1 and can be accessed within the for loop, but will be freed upon exiting the for loop
     */
    public static ArrayList<Stmt.BlockStmt> scope = new ArrayList<>();

}
