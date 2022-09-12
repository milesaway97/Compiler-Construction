/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>         *
 * All rights reserved.         *
 *                 *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                 *
 * License: BSD                 *
 *                 *
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
float       = [0-9]+"."[0-9]*(E[+-]?[0-9]+)?
identifier  = [a-zA-Z][a-zA-Z0-9_]*
newline     = \n
whitespace  = [ \t\r]+
linecomment = "//".*

%%

"print"            { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.PRINT     ; }
"func"             { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.FUNC      ; }
"var"              { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.VAR       ; }
"void"             { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.VOID      ; }
"bool"             { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.BOOL      ; }
"int"              { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.INT       ; }
"float"            { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.FLOAT     ; }
"struct"           { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.STRUCT    ; }
"size"             { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.SIZE      ; }
"new"              { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.NEW       ; }
"if"               { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.IF        ; }
"then"             { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.THEN      ; }
"else"             { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.ELSE      ; }
"begin"            { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.BEGIN     ; }
"end"              { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.END       ; }
"while"            { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.WHILE     ; }
"return"           { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.RETURN    ; }
"break"            { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.BREAK     ; }
"continue"         { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.CONTINUE  ; }
"("                { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.LPAREN    ; }
")"                { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.RPAREN    ; }
"["                { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.LBRACKET  ; }
"]"                { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.RBRACKET  ; }
";"                { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.SEMI      ; }
","                { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.COMMA     ; }
"."                { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.DOT       ; }
"&"                { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.ADDR      ; }
"::"               { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.TYPEOF    ; }
"<-"               { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.ASSIGN    ; }
"->"               { parser.yylval = new ParserVal(null); column += col_add; col_add = yytext().length(); return Parser.FUNCRET   ; }
"+"                { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.OP        ; }
"-"                { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.OP        ; }
"*"                { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.OP        ; }
"/"                { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.OP        ; }
"and"              { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.OP        ; }
"or"               { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.OP        ; }
"not"              { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.OP        ; }
"="                { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.RELOP     ; }
"!="               { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.RELOP     ; }
"<"                { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.RELOP     ; }
">"                { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.RELOP     ; }
"<="               { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.RELOP     ; }
">="               { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.RELOP     ; }
"true"             { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.BOOL_VALUE; }
"false"            { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.BOOL_VALUE; }

{int}                   { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.INT_VALUE   ; }
{float}                 { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.FLOAT_VALUE ; }
{identifier}            { parser.yylval = new ParserVal(yytext()); column += col_add; col_add = yytext().length(); return Parser.ID          ; }
{linecomment}           { System.out.print(yytext()); column = 1; col_add = 0; }
{newline}               { System.out.println(); lineno++; column = 1; col_add = 0; }
{whitespace}            { System.out.print(yytext()); column += col_add; col_add = yytext().length(); }
"<!--"(.|\n)*"-->"      { System.out.print(yytext());
                            int i = 1;
                            for (char ch: yytext().toCharArray()) {
                                if (ch == '\n') {
                                    lineno++; column = 1;
                                } else if (i != yytext().toCharArray().length){
                                    column++;
                                }
                                i++;
                            }
                        }

\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
