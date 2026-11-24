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
    public enum Type {EXEC, CLASS, FUN, COMPONENT}
    Type type;
    Stack<Stmt> stmts;
    HashMap<String, Expr> vars; // this feels like a poor implementation, wouldnt i want variables stored by a class to be values and not exprs?
    public static class Exec extends CodeUnit{
        /**
         * main method-esc statement
         * there can only be one in any given .jumbl file
         */
        public Exec(){
            this.type = Type.EXEC;
            this.vars = new HashMap<String, Expr>();
        }
        public void addStmts(Stack<Stmt> stmts){
            this.stmts = stmts;
        }
    }
}
