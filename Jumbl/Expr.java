package Jumbl;

/**
 * Stores an expression tree to be executed by the interpreter
 * 
 * @author Jeremiah Francis
 * @version 0.0.1 ?
 */
abstract class Expr
{   
    boolean isLiteral;
    boolean isGrouping;
    boolean isUnary;
    boolean isVar;
    boolean isString; // concatenation
    Expr left;
    Expr right;
    Token op;
    Value val;
    Expr expr;
    public Expr(){}
    public Expr(Expr other){ // for copying expressions
        this.isLiteral = other.isLiteral;
        this.isGrouping = other.isGrouping;
        this.isUnary = other.isUnary;
        this.isVar = other.isVar;
        this.left = other.left;
        this.right = other.right;
        this.op = other.op;
        this.val = other.val;
        this.expr = other.expr;
    }
    static class Binary extends Expr{
        Binary (Expr left, Token op, Expr right){
            this.left=left;this.op=op;this.right=right;
        }
        Binary (Expr other){ // copying
            this.left=other.left;this.op=other.op;this.right=other.right;
        }
        public String toString(){return "{"+left+", "+op+", "+right+"}";}
        public boolean isEquality(){return op.getType().equals("EE")||op.getType().equals("NOTE");}
    }
    static class Unary extends Expr{
        Unary (Token op, Expr right){this.op=op;this.right=right;isUnary=true;}
        Unary (Expr other){this.op=other.op;this.right=right;isUnary=true;} // copying
        public String toString(){return "{"+op+", "+right+"}";}
    }
    static class Literal extends Expr{
        public Literal(Value v){this.val = v;isLiteral=true;}
        public Literal(Value.Int i){this.val=i;isLiteral=true;}
        public Literal(Value.Doub d){this.val=d; isLiteral=true;}
        public Literal(Value.Bool b){this.val=b; isLiteral=true;}
        public Literal(Value.Str s){this.val=s; isLiteral=true;}
        public Literal(Value.Var v){this.val=v; isLiteral=true; isVar = true;}
        public Literal(Expr other){this.val=other.val;isLiteral=true;this.isVar=other.isVar;}
        
        public String toString (){return val.toString();}
    }
    static class Grouping extends Expr{
        public Grouping(Expr expr){this.expr=expr;isGrouping=true;}// also acts as copying constructor, neat
        public String toString (){return "("+expr.toString()+")";}
    }
    static class VarOp extends Expr{
        public VarOp(Value val, Token op, Expr right){this.val=val;this.op=op;this.right=right;}
        public VarOp(Expr other){this.val=other.val;this.op=other.op;this.right=other.right;} // copying
        public String toString(){return "{"+val+", "+op+", "+right+"}";}
    }
}
