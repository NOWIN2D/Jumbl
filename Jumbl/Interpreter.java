package Jumbl;

import java.util.*;
/**
 * Reads the abstract syntax tree built by the parser and executes it
 *
 * @author Jeremiah Francis
 * @version 0.0.1 ?
 */
public class Interpreter
{
    public Interpreter(){}

    public void interpret(){
        Memory.scope = Memory.exec.execStmt;
        visitExec();
    }

    public void visitExec(){
        Stack<Stmt> stmts = Memory.exec.stmts;
        while(!stmts.empty()){visitStmt(stmts.pop());}
    }

    public void visitStmt(Stmt s){
        //Stack<Stmt> stmts = s.stmts;
        //while(!stmts.empty()){
        //if(s.vars != null )Memory.scope = s;
        if(s.type.equals("WRITE"))System.out.print(visitExpr(s.expr).val);
        if(s.type.equals("WRITELN"))System.out.println(visitExpr(s.expr).val);
        if(s.type.equals("DEC"))Memory.scope.vars.put(s.name, visitExpr(s.expr));
        if(s.type.equals("IF")){
            Memory.scope = s;
            if(visitExpr(s.expr).val.b){
                while(!s.stmts.empty()){visitStmt(s.stmts.pop());}
            }
            else if(s.daughter != null){
                visitDaughter(s);
            }
            Memory.scope = s.parent != null ? s.parent: Memory.exec.execStmt;
        }
        if(s.type.equals("LOOP")){
            Memory.scope = s;
            while(visitExpr(s.expr).val.b){
                Stack<Stmt> looped = new Stack<>();
                while(!s.stmts.empty()){
                    Stmt used = s.stmts.pop();
                    looped.push(used);
                    visitStmt(used);
                }
                s.stmts = Parser.reverse(looped);
            }
            Memory.scope = s.parent != null ? s.parent: Memory.exec.execStmt;
        }
        if(s.type.equals("VAROP"))varSet(s.expr);
        //}
    }

    private void visitDaughter(Stmt s){
        if(s.daughter.type.equals("ELSE"))while(!s.daughter.stmts.empty()){visitStmt(s.daughter.stmts.pop());}
        if(s.daughter.type.equals("ELIF")) {
            if (visitExpr(s.daughter.expr).val.b) {
                while(!s.daughter.stmts.empty()){visitStmt(s.daughter.stmts.pop());}
            }
            else if(s.daughter.daughter != null){
                visitDaughter(s.daughter);
            }
        }
    }

    private Expr visitExpr(Expr e){
        Expr rtnr = e.isGrouping ? new Expr.Grouping(e): e.isLiteral ? new Expr.Literal(e): e.isUnary ? new Expr.Unary(e): e.isVar ? new Expr.Literal(e):new Expr.Binary(e);
        if(rtnr.isVar) rtnr = findMem(rtnr.val.s, Memory.scope).get(rtnr.val.s);
        if(rtnr.isUnary){
            if(e.op.getType().equals("NOT"))rtnr = negateBool(rtnr.right);
            if(e.right.isLiteral){
                if(e.op.getType().equals("MINUS"))rtnr = negateNum(rtnr.right);
                return rtnr;
            }
        }
        if(rtnr.isGrouping) {
            rtnr = visitExpr(rtnr.expr);
        }
        if(rtnr.isLiteral){return rtnr;}
        if(rtnr.right.isVar)rtnr.right = findMem(rtnr.right.val.s, Memory.scope).get(rtnr.right.val.s);
        if(rtnr.left.isVar)rtnr.left = findMem(rtnr.left.val.s, Memory.scope).get(rtnr.left.val.s);
        if (rtnr.op!=null) {
            //if (rtnr.op.getType().equals("PLUS") && ())
            if (rtnr.op.getType().equals("PLUS") || rtnr.op.getType().equals("MINUS") || rtnr.op.getType().equals("MUL") || rtnr.op.getType().equals("DIV")) {
                if (!rtnr.left.isLiteral) rtnr.left = visitExpr(rtnr.left);
                if (!rtnr.right.isLiteral) rtnr.right = visitExpr(rtnr.right);
                if (rtnr.right.isLiteral && rtnr.left.isLiteral) {
                    if (rtnr.right.val.type.equals("INT") && rtnr.left.val.type.equals("INT")) rtnr = combineInt(rtnr);
                    else if (rtnr.right.val.type.equals("DOUB") && rtnr.left.val.type.equals("DOUB"))
                        rtnr = combineDoub(rtnr);
                    else {
                        rtnr = combineBoth(rtnr);
                    }
                }
            }
            if (rtnr.op.getType().equals("EE") || rtnr.op.getType().equals("NOTE") || rtnr.op.getType().equals("GREAT") || rtnr.op.getType().equals("LESS") || rtnr.op.getType().equals("GREATE") || rtnr.op.getType().equals("LESSE")) {
                if (!rtnr.left.isLiteral) rtnr.left = visitExpr(rtnr.left);
                if (!rtnr.right.isLiteral) rtnr.right = visitExpr(rtnr.right);
                if (rtnr.right.isLiteral && rtnr.left.isLiteral) {
                    rtnr = check(rtnr);
                }
            }
        }
        return rtnr;
    }

    private HashMap<String, Expr> findMem(String s, Stmt scope){
        return scope.vars.containsKey(s) ? scope.vars: findMem(s, scope.parent != null ? scope.parent : Memory.exec.execStmt);
    }

    private Expr combineInt(Expr e){
        if(e.op.getType().equals("PLUS"))e = addInt(e.left, e.right);
        else if(e.op.getType().equals("MINUS"))e = subInt(e.left, e.right);
        else if(e.op.getType().equals("MUL"))e = mulInt(e.left, e.right);
        else if(e.op.getType().equals("DIV"))e = divInt(e.left, e.right);
        return e;
    }

    private Expr combineDoub(Expr e){
        if(e.op.getType().equals("PLUS"))e = addDoub(e.left, e.right);
        else if(e.op.getType().equals("MINUS"))e = subDoub(e.left, e.right);
        else if(e.op.getType().equals("MUL"))e = mulDoub(e.left, e.right);
        else if(e.op.getType().equals("DIV"))e = divDoub(e.left, e.right);
        return e;
    }

    private Expr combineBoth(Expr e){
        if(e.op.getType().equals("PLUS"))e = addDoub(e.left, e.right);
        else if(e.op.getType().equals("MINUS"))e = subDoub(e.left, e.right);
        else if(e.op.getType().equals("MUL"))e = mulDoub(e.left, e.right);
        else if(e.op.getType().equals("DIV"))e = divDoub(e.left, e.right);
        return e;
    }

    /**** Integer adding ****/
    private Expr addInt(Expr left, Expr right){return new Expr.Literal(new Value.Int(left.val.i+right.val.i));}
    private Expr subInt(Expr left, Expr right){return new Expr.Literal(new Value.Int(left.val.i-right.val.i));}
    private Expr mulInt(Expr left, Expr right){return new Expr.Literal(new Value.Int(left.val.i*right.val.i));}
    private Expr divInt(Expr left, Expr right){return new Expr.Literal(new Value.Int(left.val.i/right.val.i));}

    /**** Double adding ****/
    private Expr addDoub(Expr left, Expr right){return new Expr.Literal(new Value.Doub(left.val.toDoub().d+right.val.toDoub().d));}
    private Expr subDoub(Expr left, Expr right){return new Expr.Literal(new Value.Doub(left.val.toDoub().d-right.val.toDoub().d));}
    private Expr mulDoub(Expr left, Expr right){return new Expr.Literal(new Value.Doub(left.val.toDoub().d*right.val.toDoub().d));}
    private Expr divDoub(Expr left, Expr right){return new Expr.Literal(new Value.Doub(left.val.toDoub().d/right.val.toDoub().d));}

    private Expr check(Expr e){
        if(e.op.getType().equals("EE")) e = equals(e.left, e.right);
        else if(e.op.getType().equals("NOTE")) e = not(e.left, e.right);
        else if(e.op.getType().equals("GREAT")) e = great(e.left, e.right);
        else if(e.op.getType().equals("LESS")) e = less(e.left, e.right);
        else if(e.op.getType().equals("GREATE")) e = greate(e.left, e.right);
        else if(e.op.getType().equals("LESSE")) e = lesse(e.left, e.right);
        return e;
    }

    /**** Making Bools ****/
    private Expr equals(Expr left, Expr right){return new Expr.Literal(new Value.Bool(left.val.equals(right.val)));}
    private Expr not(Expr left, Expr right){return new Expr.Literal(new Value.Bool(left.val.note(right.val)));}
    private Expr great(Expr left, Expr right){return new Expr.Literal(new Value.Bool(left.val.great(right.val)));}
    private Expr less(Expr left, Expr right){return new Expr.Literal(new Value.Bool(left.val.less(right.val)));}
    private Expr greate(Expr left, Expr right){return new Expr.Literal(new Value.Bool(left.val.greate(right.val)));}
    private Expr lesse(Expr left, Expr right){return new Expr.Literal(new Value.Bool(left.val.lesse(right.val)));}

    private Expr negateBool(Expr e){
        e = visitExpr(e);
        return new Expr.Literal(new Value.Bool(e.val.b ? false:true));
    }

    private Expr negateNum(Expr e){
        if(e.val.type.equals("DOUB")) return new Expr.Literal(new Value.Doub(e.val.d*-1));
        return new Expr.Literal(new Value.Int(e.val.i*-1)); // defaults to an integer, there's really no other option
    }

    private void varSet(Expr e){
        HashMap<String, Expr> mem = findMem(e.val.s, Memory.scope);
        if(e.op.getType().equals("EQUAL"))mem.put(e.val.s, visitExpr(e.right));
        if(e.op.getType().equals("PLUSE"))mem.put(e.val.s, new Expr.Literal(mem.get(e.val.s).val.add(visitExpr(e.right).val)));
        if(e.op.getType().equals("MINUSE"))mem.put(e.val.s, new Expr.Literal(mem.get(e.val.s).val.sub(visitExpr(e.right).val)));
        if(e.op.getType().equals("MULE"))mem.put(e.val.s, new Expr.Literal(mem.get(e.val.s).val.mul(visitExpr(e.right).val)));
        if(e.op.getType().equals("DIVE"))mem.put(e.val.s, new Expr.Literal(mem.get(e.val.s).val.div(visitExpr(e.right).val)));
    }   

}
