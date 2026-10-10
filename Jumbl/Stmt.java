package Jumbl;

import java.util.*;
/**
 * Used for making statements like write, functions, or variable instantiations
 * among other things of course
 */
abstract class Stmt {
    public String type;
    public Expr expr;
    public String name;

    public Stack<Stmt> stmts;
    public HashMap<String, Expr> vars;
    public HashMap<Value, String> params;
    public ArrayList<Expr> inputs;
    public Stmt parent;
    public Stmt daughter;
    public Value v;
    static abstract class BinaryStmt extends Stmt {
        static class Write extends BinaryStmt{
            public Write(Expr expr){this.type="WRITE";this.expr=expr;}
        }
        static class WriteLine extends BinaryStmt{
            public WriteLine(Expr expr){this.type="WRITELN";this.expr=expr;}
        }
        static class Dec extends BinaryStmt{
            public Dec(String name, Expr expr){this.type ="DEC";this.expr=expr;this.name=name;}
        }
        static class VarOp extends BinaryStmt{
            public VarOp(Expr expr){this.expr = expr;this.type="VAROP";}
        }
        static class FuncOp extends BinaryStmt{
            public FuncOp(Value v, ArrayList<Expr> inputs){this.inputs=inputs;this.v=v;this.type="FUNCOP";}
            public String toString(){return type + " " + v + " " + inputs;}
        }
        public String toString(){return type + " " + expr;}
    }
    static class BlockStmt extends Stmt {

        public BlockStmt(String type, Stmt parent){
            this.type = type;
            this.vars=new HashMap<String, Expr>();
            this.parent = parent;
        }
        public BlockStmt(HashMap<String, Expr> vars){ // for execs
            this.type = "EXEC";
            this.vars = vars;
        }
        public void addDaughter(Stmt daughter){this.daughter = daughter;}
        public void addStmts(Stack<Stmt> stmts){this.stmts=stmts;}
        public void addExpr(Expr e){this.expr=e;}
        //public String toString(){return type + " " + expr + " " + Parser.reverse(stmts) + " " + daughter;}
    }
}
