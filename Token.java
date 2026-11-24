package Jumbl;


public class Token
{

    String value;
    
    public Token(String value){
        this.value = value;
    }
    
    public Token(Token other){
        this.value = other.value;
    }
    
    public String getType(){ // this doesn't work with strings, or numbers either
        if(Memory.lexerValues.containsKey(value))return Memory.lexerValues.get(value);
        if(value.charAt(0)=='"'&&value.charAt(value.length()-1)=='"')return "STRING";
        if(Character.isDigit(value.charAt(0)))return value.contains(".") ? "DOUB":"INT";
        return "VARIABLE";
    }
    
    public String toString(){
        if(value!=null)return getType() +":"+ value;
        return getType();
    }
    
    public Value toValue(){
        if(getType().equals("INT"))return new Value.Int(Integer.parseInt(value));
        if(getType().equals("DOUB"))return new Value.Doub(Double.parseDouble(value));
        if(getType().equals("BOOL"))return new Value.Bool(Boolean.parseBoolean(value));
        if(getType().equals("STRING"))return new Value.Str(value);
        if(getType().equals("VARIABLE"))return new Value.Var(value);
        return null; // could have null error but rly just pointless
    }
}
