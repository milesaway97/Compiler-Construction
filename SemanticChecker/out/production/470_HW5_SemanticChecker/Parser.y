/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2001 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * This is a modified version of the example from                          *
 *   http://www.lincom-asg.com/~rjamison/byacc/                            *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%{
import java.io.*;
%}

%right  ASSIGN
%left   OR
%left   AND
%right  NOT
%left   EQ      NE
%left   LE      LT      GE      GT
%left   ADD     SUB
%left   MUL     DIV     MOD

%token <obj>    EQ   NE   LE   LT   GE   GT
%token <obj>    ADD  SUB  MUL  DIV  MOD
%token <obj>    OR   AND  NOT

%token <obj>    IDENT     INT_LIT   BOOL_LIT

%token <obj> BOOL  INT
%token <obj> FUNC  IF  THEN  ELSE  WHILE  PRINT  RETURN
%token <obj> BEGIN  END  LPAREN  RPAREN
%token <obj> ASSIGN  TYPEOF  VAR  SEMI  COMMA  FUNCRET

%type <obj> program   decl_list  decl
%type <obj> fun_defn  local_decls  local_decl  type_spec  prim_type
%type <obj> params  param_list  param  args  arg_list
%type <obj> stmt_list  stmt  assign_stmt  print_stmt  return_stmt  if_stmt  while_stmt  compound_stmt     
%type <obj> expr

%%


program         : decl_list                                     { /*Debug("program -> decl_list"                  ); $$ = program____decllist($1); */}
                ;

decl_list       : decl_list  decl                                { /*Debug("decl_list -> decl_list decl"           ); $$ = decllist____decllist_decl($1,$2); */}
                |                                               { /*Debug("decl_list -> eps"                      ); $$ = decllist____eps          (     ); */}
                ;

decl            : fun_defn                                      { /*Debug("decl -> fun_defn"                      ); $$ = decl____fundefn($1); */}
                ;

fun_defn        : FUNC  IDENT  TYPEOF  LPAREN  params  RPAREN  FUNCRET  prim_type  BEGIN  local_decls{ /*Debug("fun_defn -> FUNC ID::( params )->type BEGIN local_decls"); $<obj>$ =    fundefn____FUNC_IDENT_TYPEOF_LPAREN_params_RPAREN_FUNCRET_primtype_BEGIN_localdecls_11x_stmtlist_END($2, $5, $8, $10          ); */}
                                                                               stmt_list  END{ /*Debug("                                         stmt_list END "); $$ =         fundefn____FUNC_IDENT_TYPEOF_LPAREN_params_RPAREN_FUNCRET_primtype_BEGIN_localdecls_x11_stmtlist_END($2, $5, $8, $10, $12, $13); */}
                ;

params          : param_list                                    { /*Debug("params -> param_list"                  ); $$ = params____param_list($1); */}
                |                                               { /*Debug("params -> eps"                         ); $$ = params____eps(); */}
                ;

param_list      : param_list  COMMA  param                      { }
                | param                                         { }
                ;

param           : IDENT  TYPEOF  type_spec                      { }
                ;

type_spec       : prim_type                                     { }
                ;

prim_type       : INT                                           { /*Debug("prim_type -> INT"                      ); $$ = primtype____INT (); */}
                | BOOL                                          { /*Debug("prim_type -> INT"                      ); $$ = primtype____INT (); */}
                ;

local_decls     : local_decls  local_decl                       { /*Debug("local_decls -> eps"                    ); $$ = localdecls____eps(); */}
                |                                               { }
                ;

local_decl      : VAR  IDENT  TYPEOF  type_spec  SEMI           { }
                ;

stmt_list       : stmt_list  stmt                                { /*Debug("stmt_list -> stmt_list stmt"           ); $$ = stmtlist____stmtlist_stmt($1, $2); */}
                |                                               { /*Debug("stmt_list -> eps"                      ); $$ = stmtlist____eps          (      ); */}
                ;

stmt            : assign_stmt                                   { /*Debug("stmt -> assign_stmt"                   ); $$ = stmt____assignstmt  ($1); */}
                | print_stmt                                   { /*Debug("stmt -> return_stmt"                   ); $$ = stmt____returnstmt  ($1); */}
                | return_stmt                                   { /*Debug("stmt -> return_stmt"                   ); $$ = stmt____returnstmt  ($1); */}
                | if_stmt                                   { /*Debug("stmt -> return_stmt"                   ); $$ = stmt____returnstmt  ($1); */}
                | while_stmt                                   { /*Debug("stmt -> return_stmt"                   ); $$ = stmt____returnstmt  ($1); */}
                | compound_stmt                                   { /*Debug("stmt -> return_stmt"                   ); $$ = stmt____returnstmt  ($1); */}
                ;

assign_stmt     : IDENT  ASSIGN  expr  SEMI                        { /*Debug("assign_stmt -> IDENT <- expr ;"        ); $$ = assignstmt____IDENT_ASSIGN_expr_SEMI($1,$2,$3); */}
                ;

print_stmt      : PRINT  expr  SEMI                             { }
                ;

return_stmt     : RETURN  expr  SEMI                              { /*Debug("return_stmt -> RETURN expr ;"          ); $$ = returnstmt____RETURN_expr_SEMI($2); */}
                ;

if_stmt         : IF  expr  THEN  stmt_list  ELSE  stmt_list  END   { }
                ;

while_stmt      : WHILE  expr  compound_stmt                    { }
                ;

compound_stmt   : BEGIN  local_decls  stmt_list  END            { }
                ;

args            : arg_list                                      { /*Debug("args -> eps"                           ); $$ = args____eps(); */}
                |                                               { }
                ;

arg_list        : arg_list  COMMA  expr                         { }
                | expr                                         { }
                ;

expr            : expr  AND  expr                              { }
                | expr  OR  expr                               { }
                | NOT  expr                                    { }
                | expr  EQ  expr                                 { /*Debug("expr -> expr EQ  expr"
                | expr  NE  expr                                 { /*Debug("expr -> expr EQ  expr"
                | expr  LE  expr                                 { /*Debug("expr -> expr EQ  expr"
                | expr  LT  expr                                 { /*Debug("expr -> expr EQ  expr"
                | expr  GE  expr                                 { /*Debug("expr -> expr EQ  expr"
                | expr  GT  expr                                 { /*Debug("expr -> expr EQ  expr"
                | expr  ADD  expr                                 { /*Debug("expr -> expr ADD expr"                 ); $$ = expr____expr_ADD_expr           ($1,$2,$3); */}
                | expr  SUB  expr                                 { /*Debug("expr -> expr EQ  expr"
                | expr  MUL  expr                                 { /*Debug("expr -> expr EQ  expr"
                | expr  DIV  expr                                 { /*Debug("expr -> expr EQ  expr"
                | expr  MOD  expr                                 { /*Debug("expr -> expr EQ  expr"
                | LPAREN  expr  RPAREN                            { /*Debug("expr -> LPAREN expr RPAREN"            ); $$ = expr____LPAREN_expr_RPAREN      ($1,$2,$3); */}
                | IDENT                                         { /*Debug("expr -> IDENT"                         ); $$ = expr____IDENT                   ($1      ); */}
                | BOOL_LIT                                       { /*Debug("expr -> INT_LIT"                       ); $$ = expr____INTLIT                  ($1      ); */}
                | INT_LIT                                       { /*Debug("expr -> INT_LIT"                       ); $$ = expr____INTLIT                  ($1      ); */}
                | IDENT  LPAREN  args  RPAREN                      { /*Debug("expr -> IDENT LPAREN args RPAREN"      ); $$ = expr____IDENT_LPAREN_args_RPAREN($1,   $3); */}
                ;

%%
    private Lexer lexer;

    private int yylex () {
        int yyl_return = -1;
        try {
            yylval = new ParserVal(0);
            yyl_return = lexer.yylex();
        }
        catch (IOException e) {
            System.out.println("IO error :"+e);
        }
        return yyl_return;
    }


    public void yyerror (String error) {
        //System.out.println ("Error message for " + lexer.lineno+":"+lexer.column +" by Parser.yyerror(): " + error);
        System.out.println ("Error message by Parser.yyerror(): " + error);
    }


    public Parser(Reader r, boolean yydebug) {
        this.lexer   = new Lexer(r, this);
        this.yydebug = yydebug;
    }
