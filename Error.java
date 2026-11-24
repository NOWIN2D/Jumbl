package Jumbl;


/**
 * Error Class for printing out errors when something unnacceptable happens
 * 
 * unnacceptable things include but are not limited to: 
 * characters I discriminate against, 
 * 
 */
public class Error
{
    private String name;
    private String details;
    private Position posStart;
    private Position posEnd;

    public Error(String name, String details, Position posStart, Position posEnd){
        this.name=name;this.details=details;
        this.posStart = posStart; this.posEnd = posEnd;
    }

    public String toString(){
        return name+":"+details+"\nFile "+posStart.fn+", line "+posStart.ln + 1;
    }
}