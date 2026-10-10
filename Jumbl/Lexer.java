package Jumbl;

import java.util.*; // I'm lazy

/**
 * Reads the file and tokenizes it
 *
 * @author Jeremiah Francis
 * @version 0.0.1 ?
 */
public class Lexer
{
    private char currentChar;
    private Position pos;
    private String text;
    private String fn;

    public Lexer(String fn, String text){
        this.text = text; this.fn = fn;
        this.pos = new Position(-1 , 0, -1, fn, text);
        advance();
    }
    public void advance(){
        pos.advance(currentChar);
        if(pos.idx<text.length())currentChar = (text.charAt(pos.idx));
        else currentChar=0;
    }
    public Token makeNum(){
        String num = "";
        byte dotCnt = 0; // I could probably make this a boolean
        while(currentChar!=0 && Character.isDigit(currentChar)||currentChar=='.'){
            if(currentChar=='.'){
                if(dotCnt==1)break;// add error
                dotCnt++;
                num += ".";
            }
            else num += currentChar;
            advance();
        }
        return new Token(num);
    }
    public Token makeWord(){
        String worb = "";
        while(currentChar!=0 && Character.isLetter(currentChar)){
            worb += currentChar;
            advance();
        }
        if(Memory.lexerValues.containsKey(worb))return new Token(worb);
        return new Token(worb);
    }
    public Token makeString(){
        String str = "";
        advance();
        str += '"';
        while(currentChar!='"'){
            str += currentChar;
            advance();
        }
        str += '"';
        return new Token(str);
    }
    public ArrayList<Token> makeTokens(){
        // some nice boilerplate to make things more efficient
        Memory.lexerValues = new HashMap<String, String>();
        Memory.lexerValues.put("+=", "PLUSE");Memory.lexerValues.put("+", "PLUS");
        Memory.lexerValues.put("-=", "MINUSE");Memory.lexerValues.put("-", "MINUS");
        Memory.lexerValues.put("*=", "MULE");Memory.lexerValues.put("*", "MUL");
        Memory.lexerValues.put("/=", "DIVE");Memory.lexerValues.put("/", "DIV");
        Memory.lexerValues.put("(", "LPAREN");Memory.lexerValues.put(")", "RPAREN");
        Memory.lexerValues.put("{", "LSQUIG");Memory.lexerValues.put("}", "RSQUIG");
        Memory.lexerValues.put("!=", "NOTE");Memory.lexerValues.put("!", "NOT");
        Memory.lexerValues.put("==", "EE");Memory.lexerValues.put("=", "EQUAL");
        Memory.lexerValues.put(">=", "GREATE");Memory.lexerValues.put(">", "GREAT");
        Memory.lexerValues.put("<=", "LESSE");Memory.lexerValues.put("<", "LESS");
        Memory.lexerValues.put("&&", "AND");
        Memory.lexerValues.put(",", "COMMA");Memory.lexerValues.put(".", "DOT");
        Memory.lexerValues.put("?", "IF");Memory.lexerValues.put(":?", "ELIF");Memory.lexerValues.put(":", "ELSE");
        Memory.lexerValues.put("@", "LOOP");Memory.lexerValues.put("&", "FOR");

        Memory.lexerValues.put("loop", "LOOP");Memory.lexerValues.put("for", "FOR");Memory.lexerValues.put("while", "WHILE");
        Memory.lexerValues.put("if", "IF");Memory.lexerValues.put("elif", "ELIF");Memory.lexerValues.put("else", "ELSE");
        Memory.lexerValues.put("write", "WRITE");Memory.lexerValues.put("writeln", "WRITELN");
        Memory.lexerValues.put("true", "TRUE");Memory.lexerValues.put("false", "FALSE");
        Memory.lexerValues.put("int", "DEC");Memory.lexerValues.put("doub", "DEC");
        Memory.lexerValues.put("bool", "DEC");Memory.lexerValues.put("string", "DEC");
        Memory.lexerValues.put("exec", "EXEC");Memory.lexerValues.put("func", "FUNC");
        Memory.lexerValues.put("class", "CLASS");Memory.lexerValues.put("component", "COMP");



        ArrayList<Token> tokens = new ArrayList<Token>();
        while (currentChar!=0){
            switch (currentChar){
                case '\t':{advance();continue;}
                case '\n':{advance();continue;}
                case '\r':{advance();continue;}
                case ' ':{advance();continue;}
                case '|':{advance();continue;} // for seperating statements in one-liners like @ {write "Hello " | writeln "World"} & 0 to 5 // double comment, idk if I like this syntax, its so... python-y
                case '"':{tokens.add(makeString());advance();continue;}
                default:{
                    if(Memory.lexerValues.containsKey(text.substring(pos.idx,pos.idx+2))){
                        tokens.add(new Token(text.substring(pos.idx,pos.idx+2)));
                        advance();advance();continue;
                    }
                    else if(Memory.lexerValues.containsKey(Character.toString(currentChar))) {
                        tokens.add(new Token(Character.toString(currentChar)));
                        advance();continue;
                    } break;
                }
            }
            if(Character.isDigit(currentChar))tokens.add(new Token(makeNum()));
            else if(Character.isLetter(currentChar))tokens.add(new Token(makeWord()));
            else{
                Position start = pos.copy();
                Error e = new Error("IllegalCharError","'"+currentChar+"'", start, pos);
                System.out.print(e);
                return null;
            }
        }
        return tokens;
    }
}
