public class Parser
{
    // Names are tokenname
    public static final int OP         = 10;    // "+", "-", "*", "/"
    public static final int RELOP      = 11;    // "<", ">", "=", "!=", "<=", ">="
    public static final int TYPEOF     = 12;    // "::"
    public static final int ASSIGN     = 13;    // "<-"
    public static final int LPAREN     = 14;    // "("
    public static final int RPAREN     = 15;    // ")"
    public static final int SEMI       = 16;    // ";"
    public static final int COMMA      = 17;    // ","
    public static final int FUNCRET    = 18;    // "->"
    public static final int NUM        = 19;    // number
    public static final int ID         = 20;    // identifier
    public static final int BEGIN      = 21;    // "begin"
    public static final int END        = 22;    // "end"
    public static final int INT        = 23;    // "int"
    public static final int PRINT      = 24;    // "print"
    public static final int VAR        = 25;    // "var"
    public static final int FUNC       = 26;    // "func"
    public static final int IF         = 27;    // "if"
    public static final int THEN       = 28;    // "then"
    public static final int ELSE       = 29;    // "else"
    public static final int WHILE      = 30;    // "while"
    public static final int VOID       = 31;    // "void"

    Compiler         compiler;
    Lexer            lexer;     // lexer.yylex() returns token-name
    public ParserVal yylval;    // yylval contains token-attribute

    public Parser(java.io.Reader r, Compiler compiler) throws Exception
    {
        this.compiler = compiler;
        this.lexer    = new Lexer(r, this);
    }

    public int yyparse() throws Exception
    {
        while ( true )
        {
            int token = lexer.yylex();  // get next token-name
            Object attr = yylval.obj;   // get      token-attribute
            String tokenname = "";
            switch (token) {
                case 0:
                    // EOF is reached
                    System.out.println("Success!");
                    return 0;
                case -1:
                    // lexical error is found
                    System.out.println("Error! There is a lexical error at line " + lexer.lineno + " and column " + lexer.column + ".");
                    return -1;
                case 10:
                    tokenname = "OP"; break;
                case 11:
                    tokenname = "RELOP"; break;
                case 12:
                    tokenname = "TYPEOF"; break;
                case 13:
                    tokenname = "ASSIGN"; break;
                case 14:
                    tokenname = "LPAREN"; break;
                case 15:
                    tokenname = "RPAREN"; break;
                case 16:
                    tokenname = "SEMI"; break;
                case 17:
                    tokenname = "COMMA"; break;
                case 18:
                    tokenname = "FUNCRET"; break;
                case 19:
                    tokenname = "NUM"; break;
                case 20:
                    tokenname = "ID"; break;
                case 21:
                    tokenname = "BEGIN"; break;
                case 22:
                    tokenname = "END"; break;
                case 23:
                    tokenname = "INT"; break;
                case 24:
                    tokenname = "PRINT"; break;
                case 25:
                    tokenname = "VAR"; break;
                case 26:
                    tokenname = "FUNC"; break;
                case 27:
                    tokenname = "IF"; break;
                case 28:
                    tokenname = "THEN"; break;
                case 29:
                    tokenname = "ELSE"; break;
                case 30:
                    tokenname = "WHILE"; break;
                case 31:
                    tokenname = "VOID"; break;
            }

            System.out.println("<" + tokenname + ", token-attr:\"" + attr + "\", lineno:" + lexer.lineno + ", column:" + lexer.column + ">");

            //            // TEMP---------------------------------------------------------------------
//            int token = lexer.yylex();  // get next token-name
//            String tokenname = "SEMI";
//
//            if(token == 0)
//            {
//                // EOF is reached
//                System.out.println("Success!");
//                return 0;
//            }
//            if(token == -1)
//            {
//                // lexical error is found
//                System.out.println("Error! There is a lexical error at line " + lexer.lineno + " and column " + lexer.column + ".");
//                return -1;
//            }
//            Object attr = yylval.obj;   // get      token-attribute
//
//            System.out.println("<" + tokenname + ", token-attr:\"" + attr + "\", lineno:" + lexer.lineno + ", column:" + lexer.column + ">");
//            //TEMP--------------------------------------------------------------------------------------------------------------------
        }
    }
}
