import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

public class Parser
{
    public static final int PRINT       = 10; // "print"
    public static final int FUNC        = 11; // "func"
    public static final int VAR         = 12; // "var"
    public static final int VOID        = 13; // "void"
    public static final int BOOL        = 14; // "bool"
    public static final int INT         = 15; // "int"
    public static final int FLOAT       = 16; // "float"
    public static final int STRUCT      = 17; // "struct"
    public static final int SIZE        = 18; // "size"
    public static final int NEW         = 19; // "new"
    public static final int IF          = 20; // "if"
    public static final int THEN        = 21; // "then"
    public static final int ELSE        = 22; // "else"
    public static final int BEGIN       = 23; // "begin"
    public static final int END         = 24; // "end"
    public static final int WHILE       = 25; // "while"
    public static final int RETURN      = 26; // "return"
    public static final int BREAK       = 27; // "break"
    public static final int CONTINUE    = 28; // "continue"
    public static final int LPAREN      = 29; // "("
    public static final int RPAREN      = 30; // ")"
    public static final int LBRACKET    = 31; // "["
    public static final int RBRACKET    = 32; // "]"
    public static final int SEMI        = 33; // ";"
    public static final int COMMA       = 34; // ","
    public static final int DOT         = 35; // "."
    public static final int ADDR        = 36; // "&"
    public static final int TYPEOF      = 37; // "::"
    public static final int ASSIGN      = 38; // "<-"
    public static final int FUNCRET     = 39; // "->"
    public static final int OP          = 40; // "+", "-", "*", "/", "and", "or", "not"
    public static final int RELOP       = 41; // "=", "!=", "<", ">", "<=", ">="
    public static final int BOOL_VALUE  = 42; // "true", "false"
    public static final int INT_VALUE   = 43; //
    public static final int FLOAT_VALUE = 44; //
    public static final int ID          = 45; //

    public Parser(java.io.Reader r) throws java.io.IOException
    {
        this.lexer    = new Lexer(r, this);
    }

    Lexer   lexer;
    public ParserVal yylval;
    HashMap<Integer, String> symbol_table = new HashMap<>();
    int pos = -1;

    public int yyparse() throws java.io.IOException
    {
        while ( true )
        {
            int token = lexer.yylex();

            if(token == 0)
            {
                // EOF is reached
                return 0;
            }
            if(token == -1)
            {
                // error
                return -1;
            }

            switch(token)
            {
                case PRINT:
                    System.out.print("<PRINT :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case FUNC:
                    System.out.print("<FUNC :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case VAR:
                    System.out.print("<VAR :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case VOID:
                    System.out.print("<VOID :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case BOOL:
                    System.out.print("<BOOL :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case INT:
                    System.out.print("<INT :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case FLOAT:
                    System.out.print("<FLOAT :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case STRUCT:
                    System.out.print("<STRUCT :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case SIZE:
                    System.out.print("<SIZE :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case NEW:
                    System.out.print("<NEW :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case IF:
                    System.out.print("<IF :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case THEN:
                    System.out.print("<THEN :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case ELSE:
                    System.out.print("<ELSE :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case BEGIN:
                    System.out.print("<BEGIN :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case END:
                    System.out.print("<END :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case WHILE:
                    System.out.print("<WHILE :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case RETURN:
                    System.out.print("<RETURN :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case BREAK:
                    System.out.print("<BREAK :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case CONTINUE:
                    System.out.print("<CONTINUE :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case LPAREN:
                    System.out.print("<LPAREN :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case RPAREN:
                    System.out.print("<RPAREN :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case LBRACKET:
                    System.out.print("<LBRACKET :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case RBRACKET:
                    System.out.print("<RBRACKET :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case SEMI:
                    System.out.print("<SEMI :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case COMMA:
                    System.out.print("<COMMA :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case DOT:
                    System.out.print("<DOT :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case ADDR:
                    System.out.print("<ADDR :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case TYPEOF:
                    System.out.print("<TYPEOF :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case ASSIGN:
                    System.out.print("<ASSIGN :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case FUNCRET:
                    System.out.print("<FUNCRET :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case OP:
                    System.out.print("<OP, " + yylval.sval + " :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case RELOP:
                    System.out.print("<RELOP, " + yylval.sval + " :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case BOOL_VALUE:
                    System.out.print("<BOOL_VALUE, " + yylval.sval + " :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case INT_VALUE:
                    System.out.print("<INT_VALUE, " + yylval.sval + " :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case FLOAT_VALUE:
                    System.out.print("<FLOAT_VALUE, " + yylval.sval + " :" + lexer.lineno + ":" + lexer.column + ">"); break;
                case ID:
                    if(!symbol_table.containsValue(yylval.sval)) {
                        symbol_table.put(pos++, yylval.sval);
                        System.out.print("<<symbol table entity [" + pos + ", \"" + yylval.sval + "\"]>>");
                        System.out.print("<ID, " + pos + " :" + lexer.lineno + ":" + lexer.column + ">"); break;
                    } else {
                        for (Entry<Integer, String> entry : symbol_table.entrySet()) {
                            if (Objects.equals(yylval.sval, entry.getValue())) {
                                System.out.print("<ID, " + (entry.getKey() + 1) + " :" + lexer.lineno + ":" + lexer.column + ">");
                                break;
                            }
                        }
                    }
            }

            //Object attr = yylval.obj;
            //System.out.println("<token-id:" + token + ", token-attr:" + attr + ", lineno:" + lexer.lineno + ", col:" + lexer.column + ">");
        }
    }
}
