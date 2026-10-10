package Jumbl;

/**
 * holds primitive values
 *
 * @author Jeremiah Francis
 * @version 0.0.1 ?
 */
abstract class Value
{
    String type;
    int i;
    double d;
    boolean b;
    String s;
    static class Int extends Value{
        public Int(int i){this.i=i;type="INT";}
        public String toString(){return ""+i;}
    }
    static class Doub extends Value{
        public Doub(double d){this.d=d;type="DOUB";}
        public String toString(){return ""+d;}
    }
    static class Bool extends Value{
        public Bool(boolean b){this.b=b;type="BOOL";}
        public String toString(){return ""+b;}
    }
    static class Str extends Value{
        public Str(String s){this.s=s;type="STRING";}
        public String toString(){return s.substring(1,s.length()-1);}
    }
    static class Var extends Value{
        public Var(String s){this.s=s;type="VARIABLE";}
        public String toString(){return s;}
    }
    
    public Doub toDoub(){return new Doub((double)i+d);}
    // add error checks to equality and arithmetic methods
    // add checks for ints and doubs
    public boolean equals(Value other){return (this.i == other.i && this.type.equals("INT")) || (this.d == other.d && this.type.equals("DOUB")) || (this.b == other.b && this.type.equals("BOOL"));}
    public boolean note(Value other){return (this.i != other.i && this.type.equals("INT")) || (this.d != other.d && this.type.equals("DOUB")) || (this.b != other.b && this.type.equals("BOOL"));}
    public boolean great(Value other){return (this.i > other.i && (this.type.equals("INT")&&other.type.equals("INT"))) || (this.d > other.d && (this.type.equals("DOUB")&&other.type.equals("DOUB"))) || (this.i > other.d && (this.type.equals("INT")&&other.type.equals("DOUB"))) || (this.d > other.i && (this.type.equals("DOUB")&&other.type.equals("INT")));}
    public boolean less(Value other){ return (this.i < other.i && (this.type.equals("INT")&&other.type.equals("INT"))) || (this.d < other.d && (this.type.equals("DOUB")&&other.type.equals("DOUB"))) || (this.i < other.d && (this.type.equals("INT")&&other.type.equals("DOUB"))) || (this.d < other.i && (this.type.equals("DOUB")&&other.type.equals("INT")));}
    public boolean greate(Value other){return (this.i >= other.i && (this.type.equals("INT")&&other.type.equals("INT"))) || (this.d >= other.d && (this.type.equals("DOUB")&&other.type.equals("DOUB"))) || (this.i >= other.d && (this.type.equals("INT")&&other.type.equals("DOUB"))) || (this.d >= other.i && (this.type.equals("DOUB")&&other.type.equals("INT")));}
    public boolean lesse(Value other){return (this.i <= other.i && (this.type.equals("INT")&&other.type.equals("INT"))) || (this.d <= other.d && (this.type.equals("DOUB")&&other.type.equals("DOUB"))) || (this.i <= other.d && (this.type.equals("INT")&&other.type.equals("DOUB"))) || (this.d <= other.i && (this.type.equals("DOUB")&&other.type.equals("INT")));}

    public Value add(Value other){return
            this.type.equals("INT") && other.type.equals("INT")? new Int(this.i + other.i):
                    this.type.equals("INT") && other.type.equals("DOUB")? new Int((int)(this.i + other.d)):
                            this.type.equals("DOUB") && other.type.equals("INT")? new Doub(this.d + other.i):
                                    new Doub(this.d+other.d);
    }
    public Value sub(Value other){return
            this.type.equals("INT") && other.type.equals("INT")? new Int(this.i - other.i):
                    this.type.equals("INT") && other.type.equals("DOUB")? new Int((int)(this.i - other.d)):
                            this.type.equals("DOUB") && other.type.equals("INT")? new Doub(this.d - other.i):
                                    new Doub(this.d - other.d);
    }
    public Value mul(Value other){return
            this.type.equals("INT") && other.type.equals("INT")? new Int(this.i * other.i):
                    this.type.equals("INT") && other.type.equals("DOUB")? new Int((int)(this.i * other.d)):
                            this.type.equals("DOUB") && other.type.equals("INT")? new Doub(this.d * other.i):
                                    new Doub(this.d * other.d);
    }
    public Value div(Value other){return
            this.type.equals("INT") && other.type.equals("INT")? new Int(this.i / other.i):
                    this.type.equals("INT") && other.type.equals("DOUB")? new Int((int)(this.i / other.d)):
                            this.type.equals("DOUB") && other.type.equals("INT")? new Doub(this.d / other.i):
                                    new Doub(this.d / other.d);
    }
}
