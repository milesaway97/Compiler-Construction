import java.io.IOException;
import java.util.ArrayList;

public class Lexer
{
    private static final char EOF        =  0;

    private Parser         yyparser;        // parent parser object
    private java.io.Reader reader;          // input stream
    public int             lineno;          // line number
    public int             column;          // column
    private int            forward;         // forward pointer
    private int            colAdd;          // updates column at lexBegin
    private char[]         buffer1 = new char[10];
    private char[]         buffer2 = new char[10];
    private boolean        usingB1 = true;
    private ArrayList<String> symbolTable = new ArrayList<>();     // table of all identifiers

    public Lexer(java.io.Reader reader, Parser yyparser) throws Exception
    {
        this.reader   = reader;
        this.yyparser = yyparser;
        lineno = 1;
        column = 1;
        forward = -1;
        colAdd = 0;
        loadB1();
        installReserved();
    }

    public void loadB1() throws IOException {
        int data;
        for (int i = 0; i < 9; i++)
        {
            data = reader.read();
            if (data == -1) {
                buffer1[i] = EOF;
                break;
            }
            else
                buffer1[i] = (char)data;
        }
        buffer1[9] = EOF;
    }

    public void loadB2() throws IOException {
        int data;
        for (int i = 0; i < 9; i++)
        {
            data = reader.read();
            if (data == -1) {
                buffer2[i] = EOF;
                break;
            }
            else
                buffer2[i] = (char)data;
        }
        buffer2[9] = EOF;
    }

    public void installReserved()
    {
        symbolTable.add("begin");
        symbolTable.add("end");
        symbolTable.add("int");
        symbolTable.add("print");
        symbolTable.add("var");
        symbolTable.add("func");
        symbolTable.add("if");
        symbolTable.add("then");
        symbolTable.add("else");
        symbolTable.add("while");
        symbolTable.add("void");
    }

    public char nextChar()
    {
        forward += 1;
        colAdd += 1;
        if (usingB1) { return buffer1[forward];}
        else { return buffer2[forward];}
    }

    public void Retract()
    {
        forward -= 1;
        colAdd -= 1;
    }

    public int Fail()
    {
        return -1;
    }

    public int getSymbol() throws IOException {
        int state = 0;
        char c;
        if (usingB1) c = buffer1[forward];
        else c = buffer2[forward];

        while (true)
        {
            switch(state)
            {
                case 0:
                    if (c == '<')       { state = 1; continue; }
                    else if (c == '>')  { state = 2; continue; }
                    else if (c == '=')  { state = 3; continue; }
                    else if (c == '!')  { state = 4; continue; }
                    else if (c == '-')  { state = 5; continue; }
                    else if (c == EOF)  { state = 6; continue; }
                    else return Fail();
                case 1:
                    c = nextChar();
                    if (c == '=')
                    {
                        yyparser.yylval = new ParserVal((Object)"<=");   // set token-attribute to yyparser.yylval
                        return Parser.RELOP;
                    } else if (c == '-')
                    {
                        yyparser.yylval = new ParserVal((Object)"<-");
                        return Parser.ASSIGN;
                    } else {
                        Retract();
                        yyparser.yylval = new ParserVal((Object)"<");
                        return Parser.RELOP;
                    }
                case 2:
                    c = nextChar();
                    if (c == '=')
                    {
                        yyparser.yylval = new ParserVal((Object)">=");
                    } else {
                        Retract();
                        yyparser.yylval = new ParserVal((Object)">");
                    }
                    return Parser.RELOP;
                case 3:
                    yyparser.yylval = new ParserVal((Object)"=");
                    return Parser.RELOP;
                case 4:
                    c = nextChar();
                    if (c == '=')
                    {
                        yyparser.yylval = new ParserVal((Object)"!=");
                        return Parser.RELOP;
                    } else  return Fail();
                case 5:
                    c = nextChar();
                    if (c == '>')
                    {
                        yyparser.yylval = new ParserVal((Object)"->");
                        return Parser.FUNCRET;
                    } else {
                        Retract();
                        yyparser.yylval = new ParserVal((Object)"-");
                        return Parser.OP;
                    }
                case 6:
                    if ((forward == 9) && usingB1 ){
                        loadB2();
                        forward = -1;
                        usingB1 = false;
                    } else if ((forward == 9)){
                        loadB1();
                        forward = -1;
                        usingB1 = true;
                    } else if (forward != -1){
                        return EOF;    // return end-of-file symbol
                    }
                    state = 0;
                    c = nextChar();
                    continue;
            }
        }
    }

    public int getNUM() throws IOException {
        int state = 0;
        char c;
        if (usingB1) c = buffer1[forward];
        else c = buffer2[forward];
        StringBuilder sb = new StringBuilder();
        String num;

        while (true) {
            switch (state) {
                case 0: // starting state, no "."
                    // c is a number
                    if (c >= 48 && c <= 57) { state = 2; continue; }
                    else if (c == '.') { state = 3; continue; }
                    else if (c == ' ') { state = 4; continue;}
                    else if (c == EOF) { state = 6; continue;}
                    else { state = 5; continue;}
                case 1: // starting state, have "."
                    if (c >= 48 && c <= 57) { state = 3; continue; }
                    else if (c == ' ') { state = 4; continue;}
                    else { state = 5; continue;}
                case 2: // c is number, no "." yet. Back to state 0
                    sb.append(c);
                    c = nextChar();
                    state = 0;
                    continue;
                case 3: // c is number or ".", already have ".", to state 1
                    sb.append(c);
                    c = nextChar();
                    state = 1;
                    continue;
                case 4:
                    num = sb.toString();
                    yyparser.yylval = new ParserVal((Object)num);
                    return Parser.NUM;
                case 5:
                    Retract();
                    num = sb.toString();
                    yyparser.yylval = new ParserVal((Object)num);
                    return Parser.NUM;
                case 6:
                    if ((forward == 9) && usingB1 ){
                        loadB2();
                        forward = -1;
                        usingB1 = false;
                    } else if ((forward == 9)){
                        loadB1();
                        forward = -1;
                        usingB1 = true;
                    } else if (forward != -1){
                        return EOF;    // return end-of-file symbol
                    }
                    state = 0;
                    c = nextChar();
                    continue;
            }
        }
    }

    public int getToken(String attr)
    {
        switch(attr)
        {
            case "begin":
                return Parser.BEGIN;
            case "end":
                return Parser.END;
            case "int":
                return Parser.INT;
            case "print":
                return Parser.PRINT;
            case "var":
                return Parser.VAR;
            case "func":
                return Parser.FUNC;
            case "if":
                return Parser.IF;
            case "then":
                return Parser.THEN;
            case "else":
                return Parser.ELSE;
            case "while":
                return Parser.WHILE;
            case "void":
                return Parser.VOID;
            default:
                return Parser.ID;
        }
    }

    public void installID(String attr)
    {
        if (!symbolTable.contains(attr))
            symbolTable.add(attr);
    }

    public int getID() throws IOException {
        char c;
        if (usingB1) c = buffer1[forward];
        else c = buffer2[forward];
        StringBuilder sb = new StringBuilder();

        while (true)
        {
            //  Uppercase Letters        Lowercase letters        Numbers               Underline
            if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122) || (c >= 48 && c <= 57) || (c == '_')) {
                sb.append(c);
                c = nextChar();
            } else if (c == ' '){
                String attr = sb.toString();
                installID(attr);
                yyparser.yylval = new ParserVal((Object)attr);
                return getToken(attr);
            } else if (c == EOF) {
                if ((forward == 9) && usingB1 ){
                    loadB2();
                    forward = -1;
                    usingB1 = false;
                } else if ((forward == 9)){
                    loadB1();
                    forward = -1;
                    usingB1 = true;
                } else if (forward != -1){
                    return EOF;    // return end-of-file symbol
                }
                c = nextChar();
            } else {
                Retract();
                String attr = sb.toString();
                installID(attr);
                yyparser.yylval = new ParserVal((Object)attr);
                return getToken(attr);
            }
        }
    }

    // * If yylex reach to the end of file, return  0
    // * If there is an lexical error found, return -1
    // * If a proper lexeme is determined, return token <token-id, token-attribute> as follows:
    //   1. set token-attribute into yyparser.yylval
    //   2. return token-id defined in Parser
    //   token attribute can be lexeme, line number, colume, etc.
    public int yylex() throws Exception
    {
        int state = 0;
        column += colAdd;
        colAdd = 0;

        while(true)
        {
            char c;
            switch(state)
            {
                case 0:
                    c = nextChar();
                    if(c == ';') { state= 1; continue; }
                    if(c == EOF) { state=99; continue; }
                    if(c == '+') { state= 2; continue; }
                    if(c == '*') { state= 3; continue; }
                    if(c == '/') { state= 4; continue; }
                    if(c == ':') { state= 5; continue; }
                    if(c == '(') { state= 6; continue; }
                    if(c == ')') { state= 7; continue; }
                    if(c == ',') { state= 8; continue; }
                    if(c == '\n') { state= 9; continue; }
                    if(c == '\t') { state= 10; continue; }
                    if(c == ' ') { column += 1; colAdd = 0; continue; }
                    if (c == '<' | c == '>' | c == '=' | c == '!' | c == '-')   return getSymbol();
                    // if lexeme begins with a letter
                    if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122))      return getID();
                    // if lexeme begins with a number
                    if (c >= 48 && c <= 57)     return getNUM();
                    else return Fail();
                    // return Fail();
                case 1:
                    yyparser.yylval = new ParserVal((Object)";");   // set token-attribute to yyparser.yylval
                    return Parser.SEMI;                             // return token-name
                case 2:
                    yyparser.yylval = new ParserVal((Object)"+");
                    return Parser.OP;
                case 3:
                    yyparser.yylval = new ParserVal((Object)"*");
                    return Parser.OP;
                case 4:
                    yyparser.yylval = new ParserVal((Object)"/");
                    return Parser.OP;
                case 5:
                    c = nextChar();
                    if (c == ':')
                    {
                        colAdd -= 1;
                        yyparser.yylval = new ParserVal((Object)"::");
                        return Parser.TYPEOF;
                    } else {
                        return Fail();
                    }
                case 6:
                    yyparser.yylval = new ParserVal((Object)"(");
                    return Parser.LPAREN;
                case 7:
                    yyparser.yylval = new ParserVal((Object)")");
                    return Parser.RPAREN;
                case 8:
                    yyparser.yylval = new ParserVal((Object)",");
                    return Parser.COMMA;
                case 9: // \n
                    lineno += 1;
                    column = 1;
                    colAdd = 0;
                    state = 0;
                    continue;
                case 10: // \t
                    column += 4;
                    colAdd = 0;
                    state = 0;
                    continue;
                case 99:
                    if ((forward == 9) && usingB1 ){
                        loadB2();
                        forward = -1;
                        usingB1 = false;
                        //System.out.println("Check1 ");
                    } else if ((forward == 9)){
                        loadB1();
                        forward = -1;
                        usingB1 = true;
                        //System.out.println("Check2");
                    } else if (forward != -1){
                        return EOF;    // return end-of-file symbol
                    }
                    state = 0;
                    continue;
            }
        }
    }
}
