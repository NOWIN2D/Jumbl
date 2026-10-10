package Jumbl;


public class Position
{
    public int idx;
    public int ln;
    public int col;
    public String fn;
    public String ftxt;
    
    public Position (int idx, int ln, int col, String fn, String ftxt){
        this.idx=idx; this.ln = ln; this.col=col;
        this.fn = fn; this.ftxt = ftxt;
    }
    
    public void advance(char currentChar){
        idx++; col++;
        if(currentChar == '\n'){ln++; col = 0;}
    }
    
    public Position copy(){return  new Position(idx, ln, col, fn, ftxt);}
}
