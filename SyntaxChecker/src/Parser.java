import java.util.List;
import java.util.ArrayList;

public class Parser
{
    public static final int ENDMARKER   =  0;
    public static final int LEXERROR    =  1;

    public static final int FUNC        = 2;
    public static final int RETURN      = 3;
    public static final int VAR         = 4;
    public static final int IF          = 5;
    public static final int THEN        = 6;
    public static final int ELSE        = 7;
    public static final int BEGIN       = 8;
    public static final int END         = 9;
    public static final int WHILE       = 10;
    public static final int LPAREN      = 11;
    public static final int RPAREN      = 12;
    public static final int LBRACKET    = 13;
    public static final int RBRACKET    = 14;
    public static final int VOID        = 15;
    public static final int INT         = 16;
    public static final int BOOL        = 17;
    public static final int NEW         = 18;
    public static final int SIZE        = 19;
    public static final int PRINT       = 20;
    public static final int ASSIGN      = 21;
    public static final int FUNCRET     = 22;
    public static final int TYPEOF      = 23;
    public static final int RELOP       = 24;
    public static final int EXPROP      = 25;
    public static final int TERMOP      = 26;
    public static final int SEMI        = 27;
    public static final int COMMA       = 28;
    public static final int DOT         = 29;
    public static final int BOOL_LIT    = 30;
    public static final int INT_LIT     = 31;
    public static final int IDENT       = 32;
    public static final int COMMENT     = 33;
    public static final int NEWLINE     = 34;
    public static final int WHITESPACE  = 35;
    public static final int BLKCOMMENT  = 36;

    public class Token
    {
        public int       type;
        public ParserVal attr;
        public Token(int type, ParserVal attr) {
            this.type   = type;
            this.attr   = attr;
        }
    }

    public ParserVal yylval;
    Token _token;
    Lexer _lexer;
    public Parser(java.io.Reader r) throws Exception
    {
        _lexer = new Lexer(r, this);
        _token = null;
        Advance();
    }

    public void Advance() throws Exception
    {
        int token_type = _lexer.yylex();
        if(token_type ==  0)      _token = new Token(ENDMARKER , null  );
        else if(token_type == -1) _token = new Token(LEXERROR  , yylval);
        else                      _token = new Token(token_type, yylval);
    }

    public String Match(int token_type) throws Exception
    {
        boolean match = (token_type == _token.type);
        String lexeme = "";

        if(match == false)
            throw new Exception();

        if(_token.type != ENDMARKER)
            Advance();

        return lexeme;
    }

    public int yyparse() throws Exception
    {
        try
        {
            parse();
            System.out.println("Success: no syntax error is found.");
        }
        catch(Exception e)
        {
            System.out.println("Error: there exists syntax error(s).");
            //System.out.println(e.getMessage());
        }
        return 0;
    }

    public List<String> parse() throws Exception
    {
        return program();
    }

    public List<String> program() throws Exception
    {
        switch(_token.type)
        {
            // program -> decl_list
            case FUNC:
                decl_list();
                return null;
            case ENDMARKER:
                decl_list();
                return null;
        }
        throw new Exception();
    }
    public List<String> decl_list() throws Exception
    {
        switch(_token.type)
        {
            // decl_list -> decl_list'
            case FUNC:
                decl_list_();
                return null;
            case ENDMARKER:
                decl_list_();
                return null;
        }
        throw new Exception();
    }
    public List<String> decl_list_() throws Exception
    {
        switch(_token.type)
        {
            // decl_list' -> fun_decl decl_list'
            case FUNC:
                fun_decl();
                decl_list_();
                return null;
            // decl_list' -> ϵ
            case ENDMARKER:
                return null;
        }
        throw new Exception();
    }
    public List<String> fun_decl() throws Exception
    {
        switch(_token.type)
        {
            // fun_decl -> FUNC IDENT TYPEOF LPAREN params RPAREN FUNCRET prim_type BEGIN local_decls stmt_list END
            case FUNC:
                Match(FUNC);
                Match(IDENT);
                Match(TYPEOF);
                Match(LPAREN);
                params();
                Match(RPAREN);
                Match(FUNCRET);
                prim_type();
                Match(BEGIN);
                local_decls();
                stmt_list();
                Match(END);
                return null;
        }
        throw new Exception();
    }
    public List<String> params() throws Exception
    {
        switch(_token.type)
        {
            // params -> param_list
            //         | ϵ
            case RPAREN:
                return null;
            case IDENT:
                param_list();
                return null;
        }
        throw new Exception();
    }
    public List<String> param_list() throws Exception
    {
        switch(_token.type)
        {
            // param_list -> param param_list'
            case IDENT:
                param();
                param_list_();
                return null;
        }
        throw new Exception();
    }
    public List<String> param_list_() throws Exception
    {
        switch(_token.type)
        {
            // param_list' -> COMMA param param_list'
            //              | ϵ
            case RPAREN:
                return null;
            case COMMA:
                Match(COMMA);
                param();
                param_list_();
                return null;
        }
        throw new Exception();
    }
    public List<String> param() throws Exception
    {
        switch(_token.type)
        {
            // param -> IDENT TYPEOF type_spec
            case IDENT:
                Match(IDENT);
                Match(TYPEOF);
                type_spec();
                return null;
        }
        throw new Exception();
    }
    public List<String> type_spec() throws Exception
    {
        switch(_token.type)
        {
            // type_spec -> prim_type type_spec'
            case INT:
                prim_type();
                type_spec_();
                return null;
            case BOOL:
                prim_type();
                type_spec_();
                return null;
        }
        throw new Exception();
    }
    public List<String> type_spec_() throws Exception
    {
        switch(_token.type)
        {
            // type_spec' -> LBRACKET RBRACKET
            //             | ϵ
            case RPAREN:
                return null;
            case LBRACKET:
                Match(LBRACKET);
                Match(RBRACKET);
                return null;
            case SEMI:
                return null;
            case COMMA:
                return null;
        }
        throw new Exception();
    }
    public List<String> prim_type() throws Exception
    {
        switch(_token.type)
        {
            // prim_type -> INT
            //            | BOOL
            case INT:
                Match(INT);
                return null;
            case BOOL:
                Match(BOOL);
                return null;
        }
        throw new Exception();
    }
    public List<String> local_decls() throws Exception
    {
        switch(_token.type)
        {
            // local_decls -> local_decls'
            case RETURN:
                local_decls_();
                return null;
            case VAR:
                local_decls_();
                return null;
            case IF:
                local_decls_();
                return null;
            case BEGIN:
                local_decls_();
                return null;
            case END:
                local_decls_();
                return null;
            case WHILE:
                local_decls_();
                return null;
            case PRINT:
                local_decls_();
                return null;
            case IDENT:
                local_decls_();
                return null;
        }
        throw new Exception();
    }
    public List<String> local_decls_() throws Exception
    {
        switch(_token.type)
        {
            // local_decls' -> local_decl local_decls'
            //               | ϵ
            case RETURN:
                return null;
            case VAR:
                local_decl();
                local_decls_()
                return null;
            case IF:
                return null;
            case BEGIN:
                return null;
            case END:
                return null;
            case WHILE:
                return null;
            case PRINT:
                return null;
            case IDENT:
                return null;
        }
        throw new Exception();
    }
    public List<String> local_decl() throws Exception
    {
        switch(_token.type)
        {
            // local_decl -> VAR IDENT TYPEOF type_spec SEMI
            case VAR:
                Match(VAR);
                Match(IDENT);
                Match(TYPEOF);
                type_spec();
                Match(SEMI);
                return null;
        }
        throw new Exception();
    }
    public List<String> stmt_list() throws Exception
    {
        switch(_token.type)
        {
            // stmt_list -> stmt_list'
            case RETURN:
                stmt_list_();
                return null;
            case IF:
                stmt_list_();
                return null;
            case ELSE:
                stmt_list_();
                return null;
            case BEGIN:
                stmt_list_();
                return null;
            case END:
                stmt_list_();
                return null;
            case WHILE:
                stmt_list_();
                return null;
            case PRINT:
                stmt_list_();
                return null;
            case IDENT:
                stmt_list_();
                return null;
        }
        throw new Exception();
    }
    public List<String> stmt_list_() throws Exception
    {
        switch(_token.type)
        {
            // stmt_list' -> stmt stmt_list'
            //             | ϵ
            case RETURN:
                stmt();
                stmt_list_();
                return null;
            case IF:
                stmt();
                stmt_list_();
                return null;
            case ELSE:
                return null;
            case BEGIN:
                stmt();
                stmt_list_();
                return null;
            case END:
                return null;
            case WHILE:
                stmt();
                stmt_list_();
                return null;
            case PRINT:
                stmt();
                stmt_list_();
                return null;
            case IDENT:
                stmt();
                stmt_list_();
                return null;
        }
        throw new Exception();
    }
    public List<String> stmt() throws Exception
    {
        switch(_token.type)
        {
            // stmt -> expr_stmt
            //               | print_stmt
            //               | return_stmt
            //               | if_stmt
            //               | while_stmt
            //               | compound_stmt
            case RETURN:
                return_stmt();
                return null;
            case IF:
                if_stmt();
                return null;
            case BEGIN:
                compound_stmt();
                return null;
            case WHILE:
                while_stmt();
                return null;
            case PRINT:
                print_stmt();
                return null;
            case IDENT:
                expr_stmt();
                return null;
        }
        throw new Exception();
    }
    public List<String> expr_stmt() throws Exception
    {
        switch(_token.type)
        {
            // expr_stmt -> IDENT ASSIGN expr SEMI
            case IDENT:
                Match(IDENT);
                Match(ASSIGN);
                expr();
                Match(SEMI);
                return null;
        }
        throw new Exception();
    }
    public List<String> print_stmt() throws Exception
    {
        switch(_token.type)
        {
            // print_stmt -> PRINT expr SEMI
            case PRINT:
                Match(PRINT);
                expr();
                Match(SEMI);
                return null;
        }
        throw new Exception();
    }
    public List<String> return_stmt() throws Exception
    {
        switch(_token.type)
        {
            // return_stmt -> RETURN expr SEMI
            case RETURN:
                Match(RETURN);
                expr();
                Match(SEMI);
                return null;
        }
        throw new Exception();
    }
    public List<String> if_stmt() throws Exception
    {
        switch(_token.type)
        {
            // if_stmt -> IF expr THEN stmt_list ELSE stmt_list END
            case IF:
                Match(IF);
                expr();
                Match(THEN);
                stmt_list();
                Match(ELSE);
                stmt_list();
                Match(END);
                return null;
        }
        throw new Exception();
    }
    public List<String> while_stmt() throws Exception
    {
        switch(_token.type)
        {
            // while_stmt -> WHILE expr BEGIN stmt_list END
            case WHILE:
                Match(WHILE);
                expr();
                Match(BEGIN);
                stmt_list();
                Match(END);
                return null;
        }
        throw new Exception();
    }
    public List<String> compound_stmt() throws Exception
    {
        switch(_token.type)
        {
            // compound_stmt -> BEGIN local_decls stmt_list END
            case BEGIN:
                Match(BEGIN);
                local_decls();
                stmt_list();
                Match(END);
                return null;
        }
        throw new Exception();
    }
    public List<String> args() throws Exception
    {
        switch(_token.type)
        {
            // args -> arg_list
            //       | ϵ
            case LPAREN:
                arg_list();
                return null;
            case RPAREN:
                return null;
            case NEW:
                arg_list();
                return null;
            case BOOL_LIT:
                arg_list();
                return null;
            case INT_LIT:
                arg_list();
                return null;
            case IDENT:
                arg_list();
                return null;
        }
        throw new Exception();
    }
    public List<String> arg_list() throws Exception
    {
        switch(_token.type)
        {
            // arg_list -> expr arg_list'
            case LPAREN:
                expr();
                arg_list_();
                return null;
            case NEW:
                expr();
                arg_list_();
                return null;
            case BOOL_LIT:
                expr();
                arg_list_();
                return null;
            case INT_LIT:
                expr();
                arg_list_();
                return null;
            case IDENT:
                expr();
                arg_list_();
                return null;
        }
        throw new Exception();
    }
    public List<String> arg_list_() throws Exception
    {
        switch(_token.type)
        {
            // arg_list' -> COMMA expr arg_list'
            //            | ϵ
            case RPAREN:
                return null;
            case COMMA:
                Match(COMMA);
                expr();
                arg_list_();
                return null;
        }
        throw new Exception();
    }
    public List<String> expr() throws Exception
    {
        switch(_token.type)
        {
            // expr -> term expr'
            case LPAREN:
                term();
                expr_();
                return null;
            case NEW:
                term();
                expr_();
                return null;
            case BOOL_LIT:
                term();
                expr_();
                return null;
            case INT_LIT:
                term();
                expr_();
                return null;
            case IDENT:
                term();
                expr_();
                return null;
        }
        throw new Exception();
    }
    public List<String> expr_() throws Exception
    {
        switch(_token.type)
        {
            // expr' -> EXPROP term expr'
            //        | RELOP term expr'
            //        | ϵ
            case THEN:
                return null;
            case BEGIN:
                return null;
            case RPAREN:
                return null;
            case RBRACKET:
                return null;
            case RELOP:
                Match(RELOP);
                term();
                expr_();
                return null;
            case EXPROP:
                Match(EXPROP);
                term();
                expr_();
                return null;
            case SEMI:
                return null;
            case COMMA:
                return null;
        }
        throw new Exception();
    }
    public List<String> term() throws Exception
    {
        switch(_token.type)
        {
            // term -> factor term'
            case LPAREN:
                factor();
                term_();
                return null;
            case NEW:
                factor();
                term_();
                return null;
            case BOOL_LIT:
                factor();
                term_();
                return null;
            case INT_LIT:
                factor();
                term_();
                return null;
            case IDENT:
                factor();
                term_();
                return null;
        }
        throw new Exception();
    }
    public List<String> term_() throws Exception
    {
        switch(_token.type)
        {
            // term' -> TERMOP factor term'
            //        | ϵ
            case THEN:
                return null;
            case BEGIN:
                return null;
            case RPAREN:
                return null;
            case RBRACKET:
                return null;
            case RELOP:
                return null;
            case EXPROP:
                return null;
            case TERMOP:
                Match(TERMOP);
                factor();
                term_();
                return null;
            case SEMI:
                return null;
            case COMMA:
                return null;
        }
        throw new Exception();
    }
    public List<String> factor() throws Exception
    {
        switch(_token.type)
        {
            // factor -> IDENT factor'
            //               | LPAREN expr RPAREN
            //               | INT_LIT
            //               | BOOL_LIT
            //               | NEW prim_type LBRACKET expr RBRACKET
            case LPAREN:
                Match(LPAREN);
                expr();
                Match(RPAREN);
                return null;
            case NEW:
                Match(NEW);
                prim_type();
                Match(LBRACKET);
                expr();
                Match(RBRACKET);
                return null;
            case BOOL_LIT:
                Match(BOOL_LIT);
                return null;
            case INT_LIT:
                Match(INT_LIT);
                return null;
            case IDENT:
                Match(IDENT);
                factor_();
                return null;
        }
        throw new Exception();
    }
    public List<String> factor_() throws Exception
    {
        switch(_token.type)
        {
            // factor' -> LPAREN args RPAREN
            //               | LBRACKET expr RBRACKET
            //               | DOT SIZE
            //               | ϵ
            case THEN:
                return null;
            case BEGIN:
                return null;
            case LPAREN:
                Match(LPAREN);
                args();
                Match(RPAREN);
                return null;
            case RPAREN:
                return null;
            case LBRACKET:
                Match(LBRACKET);
                expr();
                Match(RBRACKET);
                return null;
            case RBRACKET:
                return null;
            case RELOP:
                return null;
            case EXPROP:
                return null;
            case TERMOP:
                return null;
            case SEMI:
                return null;
            case COMMA:
                return null;
            case DOT:
                Match(DOT);
                Match(SIZE);
                return null;
        }
        throw new Exception();
    }

}
