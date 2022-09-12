/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%%

%class Lexer
%byaccj

%{

  public Parser   parser;
  public int      lineno;
  public int      column;
  public int      col_add;

  public Lexer(java.io.Reader r, Parser parser) {
    this(r);
    this.parser = parser;
    this.lineno = 1;
    this.column = 1;
    this.col_add = 0;
  }

%}

int         = [0-9]+
identifier  = [a-zA-Z_][a-zA-Z0-9_]*
newline     = \n
whitespace  = [ \t\r]+
comment     = "//".*
blkcomment1 = "<!--"[^]*"-->"
blkcomment2 = "["[^]*"]"

%%

"func"                 { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.FUNC      ; }
"return"               { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.RETURN    ; }
"var"                  { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.VAR       ; }
"if"                   { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.IF        ; }
"then"                 { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.THEN      ; }
"else"                 { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.ELSE      ; }
"begin"                { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.BEGIN     ; }
"end"                  { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.END       ; }
"while"                { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.WHILE     ; }
"("                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.LPAREN    ; }
")"                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.RPAREN    ; }
"int"                  { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.INT       ; }
"bool"                 { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.BOOL      ; }
"print"                { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.PRINT     ; }
"<-"                   { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.ASSIGN    ; }
"->"                   { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.FUNCRET   ; }
"::"                   { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.TYPEOF    ; }
"+"                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.ADD       ; }
"-"                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.SUB       ; }
"*"                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.MUL       ; }
"/"                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.DIV       ; }
"%"                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.MOD       ; }
"and"                  { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.AND       ; }
"or"                   { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.OR        ; }
"not"                  { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.NOT       ; }
"<"                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.LT        ; }
">"                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.GT        ; }
"<="                   { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.LE        ; }
">="                   { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.GE        ; }
"="                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.EQ        ; }
"!="                   { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.NE        ; }
";"                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.SEMI      ; }
","                    { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.COMMA     ; }
"true"|"false"         { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.BOOL_LIT  ; }
{int}                  { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.INT_LIT   ; }
{identifier}           { column += col_add; parser.yylval = new ParserVal(new Token(yytext(), lineno, column)); col_add = yytext().length(); return Parser.IDENT     ; }
{comment}              { column = 1; col_add = 0; }
{newline}              { lineno++; column = 1; col_add = 0; }
{whitespace}           { column += col_add; col_add = yytext().length(); }
{blkcomment1}          { /* skip */ }
{blkcomment2}          { /* skip */ }


\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
