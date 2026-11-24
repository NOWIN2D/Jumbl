package Jumbl;

import java.util.*;
/**
 * Reads the abstract syntax tree built by the parser and executes it recursively
 *
 * @author Jeremiah Francis
 * @version 0.0.1 ?
 */
public class Interpreter
{
    private int scope = -1;
    public Interpreter(){}
    public void interpret(){
        visitExec();
    }
    public void visitExec(){
        Stack<Stmt> stmts = Memory.exec.stmts;
        visitStmts(stmts);
    }
    private void visitStmts(Stack<Stmt> stmts){
        while(!stmts.empty()){
            if(stmts.peek().type.equals("WRITE")){visitWrite((Stmt.BinaryStmt.Write)stmts.pop());continue;}
            if(stmts.peek().type.equals("WRITELN")){visitWriteln((Stmt.BinaryStmt.WriteLine)stmts.pop());continue;}
            if(stmts.peek().type.equals("DEC")){visitDec((Stmt.BinaryStmt.Dec)stmts.pop());continue;}
            if(stmts.peek().type.equals("VAROP")){visitVarSet((Stmt.BinaryStmt.VarOp)stmts.pop());continue;}
            if(stmts.peek().type.equals("IF")){visitIf((Stmt.BlockStmt.IfStmt)stmts.pop());continue;}
            if(stmts.peek().type.equals("LOOP")){visitLoop((Stmt.BlockStmt.LoopStmt)stmts.pop());continue;}
            if(stmts.peek().type.equals("WHILE")){visitWhile((Stmt.BlockStmt.LoopStmt.WhileStmt)stmts.pop());continue;}
            if(stmts.peek().type.equals("FOR")){visitFor((Stmt.BlockStmt.LoopStmt.ForStmt)stmts.pop());continue;}
        }
    }
    private void visitWrite(Stmt.BinaryStmt.Write s){System.out.print(visitExpr(s.expr).val);}
    private void visitWriteln(Stmt.BinaryStmt.WriteLine s){System.out.println(visitExpr(s.expr).val);}
    private void visitDec(Stmt.BinaryStmt.Dec s){if(scope == -1) Memory.exec.vars.put(s.name, visitExpr(s.expr)); else Memory.scope.get(scope).vars.put(s.name, visitExpr(s.expr));}
    private void visitIf(Stmt.BlockStmt.IfStmt s){
        scope++;
        Memory.scope.add(s);
        if(visitExpr(s.expr).val.b){
            visitStmts(s.stmts);
        }
        else if(s.daughter != null){
            visitDaughter(s.daughter);
        }
        Memory.scope.remove(scope);
        scope--;
    }
    private void visitVarSet(Stmt.BinaryStmt.VarOp s){varSet(s.expr);}
    private void visitLoop(Stmt.BlockStmt.LoopStmt s){
        scope++;
        Memory.scope.add(s);
        while(true){
            Stack<Stmt> looped = new Stack<>(); // again, this would work so much better with a queue
            boolean b = false;
            while(!s.stmts.empty()){
                Stmt used = s.stmts.pop();
                looped.push(used); // temporary fix until I replace stacks with queues, I just don't feel like doing all that right now
                if(used.type.equals("WRITE")){visitWrite((Stmt.BinaryStmt.Write)used);}
                if(used.type.equals("WRITELN")){visitWriteln((Stmt.BinaryStmt.WriteLine)used);}
                if(used.type.equals("DEC")){visitDec((Stmt.BinaryStmt.Dec)used);}
                if(used.type.equals("VAROP")){visitVarSet((Stmt.BinaryStmt.VarOp)used);}
                if(used.type.equals("IF")){visitIf((Stmt.BlockStmt.IfStmt)used);}
                if(used.type.equals("LOOP")){visitLoop((Stmt.BlockStmt.LoopStmt)used);}
                if(used.type.equals("WHILE")){visitWhile((Stmt.BlockStmt.LoopStmt.WhileStmt)used);}
                if(used.type.equals("FOR")){visitFor((Stmt.BlockStmt.LoopStmt.ForStmt)used);}
                if(used.type.equals("BREAK")){b = true;} // temporary fix too
            }
            if(b)break;
            s.stmts = Parser.reverse(looped);
        }
        Memory.scope.remove(scope);
        scope--;
    }
    private void visitWhile(Stmt.BlockStmt.LoopStmt.WhileStmt s){ // yes I know this doesn't have break support, I'll add that in the next update
        scope++;
        Memory.scope.add(s);
        while(visitExpr(s.expr).val.b){
            Stack<Stmt> looped = new Stack<>();
            while(!s.stmts.empty()){
                Stmt used = s.stmts.pop();
                looped.push(used);
                if(used.type.equals("WRITE")){visitWrite((Stmt.BinaryStmt.Write)used);}
                if(used.type.equals("WRITELN")){visitWriteln((Stmt.BinaryStmt.WriteLine)used);}
                if(used.type.equals("DEC")){visitDec((Stmt.BinaryStmt.Dec)used);}
                if(used.type.equals("VAROP")){visitVarSet((Stmt.BinaryStmt.VarOp)used);}
                if(used.type.equals("IF")){visitIf((Stmt.BlockStmt.IfStmt)used);}
                if(used.type.equals("LOOP")){visitLoop((Stmt.BlockStmt.LoopStmt)used);}
                if(used.type.equals("WHILE")){visitWhile((Stmt.BlockStmt.LoopStmt.WhileStmt)used);}
                if(used.type.equals("FOR")){visitFor((Stmt.BlockStmt.LoopStmt.ForStmt)used);}
            }
            s.stmts = Parser.reverse(looped);
        }
        Memory.scope.remove(scope);
        scope--;
    }
    private void visitFor(Stmt.BlockStmt.LoopStmt.ForStmt s){
        scope++;
        Memory.scope.add(s);
        visitDec(s.getFrom());
        //for (int i = findMem(s.getFrom().name, scope).get(s.getFrom().name).val.i; i < visitExpr(s.getTo()).val.i; i+=0){
        while (findMem(s.getFrom().name, scope).get(s.getFrom().name).val.i < visitExpr(s.getTo()).val.i) { // not sure if this should be < or <=
            Stack<Stmt> looped = new Stack<>();
            while(!s.stmts.empty()){
                Stmt used = s.stmts.pop();
                looped.push(used);
                if(used.type.equals("WRITE")){visitWrite((Stmt.BinaryStmt.Write)used);}
                if(used.type.equals("WRITELN")){visitWriteln((Stmt.BinaryStmt.WriteLine)used);}
                if(used.type.equals("DEC")){visitDec((Stmt.BinaryStmt.Dec)used);}
                if(used.type.equals("VAROP")){visitVarSet((Stmt.BinaryStmt.VarOp)used);}
                if(used.type.equals("IF")){visitIf((Stmt.BlockStmt.IfStmt)used);}
                if(used.type.equals("LOOP")){visitLoop((Stmt.BlockStmt.LoopStmt)used);}
                if(used.type.equals("WHILE")){visitWhile((Stmt.BlockStmt.LoopStmt.WhileStmt)used);}
                if(used.type.equals("FOR")){visitFor((Stmt.BlockStmt.LoopStmt.ForStmt)used);}
            }
            s.stmts = Parser.reverse(looped);
            if(s.getAs() != null) visitVarSet(s.getAs());
            else findMem(s.getFrom().name, scope).get(s.getFrom().name).val.i++;
        }
        Memory.scope.remove(scope);
        scope--;
    }
    private void visitDaughter(Stmt.BlockStmt.IfStmt s){
        if(s.type.equals("ELSE"))visitStmts(s.stmts);
        if(s.type.equals("ELIF")) {
            if (visitExpr(s.expr).val.b){visitStmts(s.stmts);}
            else if(s.daughter != null){
                visitDaughter(s.daughter);
            }
        }
    }
    private Expr visitExpr(Expr e){
        Expr rtnr = e.isGrouping ? new Expr.Grouping(e): e.isLiteral ? new Expr.Literal(e): e.isUnary ? new Expr.Unary(e): e.isVar ? new Expr.Literal(e):new Expr.Binary(e);
        if(rtnr.isVar) rtnr = findMem(rtnr.val.s, scope).get(rtnr.val.s);
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
        if(rtnr.right.isVar)rtnr.right = findMem(rtnr.right.val.s, scope).get(rtnr.right.val.s);
        if(rtnr.left.isVar)rtnr.left = findMem(rtnr.left.val.s, scope).get(rtnr.left.val.s);
        if (rtnr.op!=null) { // this should probably give an error if not

            if (rtnr.op.getType().equals("PLUS") || rtnr.op.getType().equals("MINUS") || rtnr.op.getType().equals("MUL") || rtnr.op.getType().equals("DIV") || rtnr.op.getType().equals("MOD")) {
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
            else if (rtnr.op.getType().equals("EE") || rtnr.op.getType().equals("NOTE") || rtnr.op.getType().equals("GREAT") || rtnr.op.getType().equals("LESS") || rtnr.op.getType().equals("GREATE") || rtnr.op.getType().equals("LESSE")) {
                if (!rtnr.left.isLiteral) rtnr.left = visitExpr(rtnr.left);
                if (!rtnr.right.isLiteral) rtnr.right = visitExpr(rtnr.right);
                if (rtnr.right.isLiteral && rtnr.left.isLiteral) {
                    rtnr = check(rtnr);
                }
            }
        }
        return rtnr;
    }

    //private HashMap<String, Expr> findMem(String s, Stmt scope){
    //    return scope.vars.containsKey(s) ? scope.vars: findMem(s, scope.parent != null ? scope.parent : Memory.exec.execStmt);
    //}



     private HashMap<String, Expr> findMem(String s, int fScope){
        return fScope == -1 ? Memory.exec.vars.containsKey(s) ? Memory.exec.vars : null : Memory.scope.get(fScope).vars.containsKey(s) ? Memory.scope.get(fScope).vars : findMem(s,fScope-1);
     }



    private Expr combineInt(Expr e){
        if(e.op.getType().equals("PLUS"))e = addInt(e.left, e.right);
        else if(e.op.getType().equals("MINUS"))e = subInt(e.left, e.right);
        else if(e.op.getType().equals("MUL"))e = mulInt(e.left, e.right);
        else if(e.op.getType().equals("DIV"))e = divInt(e.left, e.right);
        else if(e.op.getType().equals("MOD"))e = modInt(e.left, e.right);
        return e;
    }
    private Expr combineDoub(Expr e){
        if(e.op.getType().equals("PLUS"))e = addDoub(e.left, e.right);
        else if(e.op.getType().equals("MINUS"))e = subDoub(e.left, e.right);
        else if(e.op.getType().equals("MUL"))e = mulDoub(e.left, e.right);
        else if(e.op.getType().equals("DIV"))e = divDoub(e.left, e.right);
        else if(e.op.getType().equals("MOD"))e = modDoub(e.left, e.right);
        return e;
    }
    private Expr combineBoth(Expr e){
        if(e.op.getType().equals("PLUS"))e = addDoub(e.left, e.right);
        else if(e.op.getType().equals("MINUS"))e = subDoub(e.left, e.right);
        else if(e.op.getType().equals("MUL"))e = mulDoub(e.left, e.right);
        else if(e.op.getType().equals("DIV"))e = divDoub(e.left, e.right);
        else if(e.op.getType().equals("MOD"))e = modDoub(e.left, e.right);
        return e;
    }
    /**** Integer adding ****/
    private Expr addInt(Expr left, Expr right){return new Expr.Literal(new Value.Int(left.val.i+right.val.i));}
    private Expr subInt(Expr left, Expr right){return new Expr.Literal(new Value.Int(left.val.i-right.val.i));}
    private Expr mulInt(Expr left, Expr right){return new Expr.Literal(new Value.Int(left.val.i*right.val.i));}
    private Expr divInt(Expr left, Expr right){return new Expr.Literal(new Value.Int(left.val.i/right.val.i));}
    private Expr modInt(Expr left, Expr right){return new Expr.Literal(new Value.Int(left.val.i%right.val.i));}

    /**** Double adding ****/
    private Expr addDoub(Expr left, Expr right){return new Expr.Literal(new Value.Doub(left.val.toDoub().d+right.val.toDoub().d));}
    private Expr subDoub(Expr left, Expr right){return new Expr.Literal(new Value.Doub(left.val.toDoub().d-right.val.toDoub().d));}
    private Expr mulDoub(Expr left, Expr right){return new Expr.Literal(new Value.Doub(left.val.toDoub().d*right.val.toDoub().d));}
    private Expr divDoub(Expr left, Expr right){return new Expr.Literal(new Value.Doub(left.val.toDoub().d/right.val.toDoub().d));}
    private Expr modDoub(Expr left, Expr right){return new Expr.Literal(new Value.Doub(left.val.toDoub().d%right.val.toDoub().d));}

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
        HashMap<String, Expr> mem = findMem(e.val.s, scope);
        if(e.op.getType().equals("EQUAL"))mem.put(e.val.s, visitExpr(e.right));
        if(e.op.getType().equals("PLUSE"))mem.put(e.val.s, new Expr.Literal(mem.get(e.val.s).val.add(visitExpr(e.right).val)));
        if(e.op.getType().equals("MINUSE"))mem.put(e.val.s, new Expr.Literal(mem.get(e.val.s).val.sub(visitExpr(e.right).val)));
        if(e.op.getType().equals("MULE"))mem.put(e.val.s, new Expr.Literal(mem.get(e.val.s).val.mul(visitExpr(e.right).val)));
        if(e.op.getType().equals("DIVE"))mem.put(e.val.s, new Expr.Literal(mem.get(e.val.s).val.div(visitExpr(e.right).val)));
        if(e.op.getType().equals("MODE"))mem.put(e.val.s, new Expr.Literal(mem.get(e.val.s).val.div(visitExpr(e.right).val)));
    }   

}
