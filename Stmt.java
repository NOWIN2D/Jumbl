package Jumbl;

import java.util.*;
/**
 * Used for making statements like write, functions, or variable instantiations
 * among other things of course
 *
 * Don't question the insanely inconsistent naming
 */
abstract class Stmt {
    public String type;
    public Expr expr;
    public String name;

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
        public String toString(){return type + " " + expr;}
    }

        static abstract class BlockStmt extends Stmt {
            public Stack<Stmt> stmts;
            public HashMap<String, Expr> vars;
          	public BlockStmt(String type, Stack<Stmt> stmts){
                  this.type = type;
                  this.vars=new HashMap<String, Expr>();
                  this.stmts = stmts;
          	}
          	static class IfStmt extends BlockStmt{
                public IfStmt daughter;
          		public IfStmt(String type,Stack<Stmt> stmts){ // this is really only necessary for else statements
          			super(type,stmts);
               }
          		public IfStmt(String type,Stack<Stmt> stmts, Expr condition, IfStmt daughter){
          			super(type,stmts);this.expr=condition;this.daughter=daughter;
               }
           }
           static class LoopStmt extends BlockStmt{
               public LoopStmt(Stack<Stmt> stmts){super("LOOP",stmts);}
               public LoopStmt(String type, Stack<Stmt> stmts){super(type,stmts);}
               static class WhileStmt extends LoopStmt{public WhileStmt(Stack<Stmt> stmts, Expr condition){super("WHILE",stmts);this.expr=condition;}}
               static class ForStmt extends LoopStmt{
                   private BinaryStmt.Dec from;
                   private Expr to;
                   private BinaryStmt.VarOp as;
                   public ForStmt(Stack<Stmt> stmts, BinaryStmt.Dec from, Expr to){super("FOR",stmts);this.from=from;this.to=to;this.as=null;}
                   public ForStmt(Stack<Stmt> stmts, BinaryStmt.Dec from, Expr to, BinaryStmt.VarOp as){super("FOR",stmts);this.from=from;this.to=to;this.as=as;}
                   public BinaryStmt.Dec getFrom(){return this.from;}
                   public Expr getTo(){return this.to;}
                   public BinaryStmt.VarOp getAs(){return this.as;}
               }
           }


              //public String toString(){return type + " " + expr + " " + Parser.reverse(stmts) + " " + daughter;}

        //public String toString(){return type + " " + expr + " " + Parser.reverse(stmts) + " " + daughter;}
    }
}
