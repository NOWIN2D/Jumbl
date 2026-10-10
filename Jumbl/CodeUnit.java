package Jumbl;
import java.util.*;
/**
 * Large, blocks of code that go outside the exec 
 *
 * @author Jeremiah Francis
 * @version 0.0.1 ?
 */
public abstract class CodeUnit
{
    public enum type {EXEC, CLASS, FUN, COMPONENT;} // i dont think this actually gets used lol
    type type;
    Stack<Stmt> stmts;
    HashMap<String, Expr> vars; // this feels like a poor implementation, wouldnt i want variables stored by a class to be values and not exprs?
    public static class Exec extends CodeUnit{
        /** main method-esc statement 
         *  there can only be one in a .jumbl file
         */
        Stmt execStmt;
        public Exec(){
            this.type = type.EXEC; // oh there you go
            this.vars = new HashMap<String, Expr>();
            this.execStmt = new Stmt.BlockStmt(this.vars);
        }
        public void addStmts(Stack<Stmt> stmts){
            this.stmts = stmts;
        }
    }
}
