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

  public Lexer(java.io.Reader r, Parser parser) {
    this(r);
    this.parser = parser;
    this.lineno = 1;
    this.column = 1;
  }
%}

relop         =      "<"|">"|"<="|">="|"="|"!="
exprop        =      "+"|"-"|"or"
termop        =      "*"|"/"|"and"
bool_lit      =      "true"|"false"
int_lit       =      [0-9]+
ident         =      [a-zA-Z_][a-zA-Z0-9_]*
comment       =      "//".*
newline       =      \n
whitespace    =      [ \t\r]+
blkcomment    =      "<!--"[^]*"-->"

%%

"func"                              { parser.yylval = new ParserVal((Object)yytext()); return Parser.FUNC  ; }
"return"                            { parser.yylval = new ParserVal((Object)yytext()); return Parser.RETURN    ; }
"var"                               { parser.yylval = new ParserVal((Object)yytext()); return Parser.VAR    ; }
"if"                                { parser.yylval = new ParserVal((Object)yytext()); return Parser.IF    ; }
"then"                              { parser.yylval = new ParserVal((Object)yytext()); return Parser.THEN    ; }
"else"                              { parser.yylval = new ParserVal((Object)yytext()); return Parser.ELSE    ; }
"begin"                             { parser.yylval = new ParserVal((Object)yytext()); return Parser.BEGIN    ; }
"end"                               { parser.yylval = new ParserVal((Object)yytext()); return Parser.END    ; }
"while"                             { parser.yylval = new ParserVal((Object)yytext()); return Parser.WHILE    ; }
"("                                 { parser.yylval = new ParserVal((Object)yytext()); return Parser.LPAREN ; }
")"                                 { parser.yylval = new ParserVal((Object)yytext()); return Parser.RPAREN ; }
"["                                 { parser.yylval = new ParserVal((Object)yytext()); return Parser.LBRACKET ; }
"]"                                 { parser.yylval = new ParserVal((Object)yytext()); return Parser.RBRACKET ; }
"void"                              { parser.yylval = new ParserVal((Object)yytext()); return Parser.VOID ; }
"int"                               { parser.yylval = new ParserVal((Object)yytext()); return Parser.INT    ; }
"bool"                              { parser.yylval = new ParserVal((Object)yytext()); return Parser.BOOL ; }
"new"                               { parser.yylval = new ParserVal((Object)yytext()); return Parser.NEW ; }
"size"                              { parser.yylval = new ParserVal((Object)yytext()); return Parser.SIZE ; }
"print"                             { parser.yylval = new ParserVal((Object)yytext()); return Parser.PRINT ; }
"<-"                                { parser.yylval = new ParserVal((Object)yytext()); return Parser.ASSIGN ; }
"->"                                { parser.yylval = new ParserVal((Object)yytext()); return Parser.FUNCRET ; }
"::"                                { parser.yylval = new ParserVal((Object)yytext()); return Parser.TYPEOF ; }
";"                                 { parser.yylval = new ParserVal((Object)yytext()); return Parser.SEMI   ; }
","                                 { parser.yylval = new ParserVal((Object)yytext()); return Parser.COMMA   ; }
"."                                 { parser.yylval = new ParserVal((Object)yytext()); return Parser.DOT   ; }

{relop}                             { parser.yylval = new ParserVal((Object)yytext()); return Parser.RELOP  ; }
{exprop}                            { parser.yylval = new ParserVal((Object)yytext()); return Parser.EXPROP ; }
{termop}                            { parser.yylval = new ParserVal((Object)yytext()); return Parser.TERMOP ; }
{bool_lit}                          { parser.yylval = new ParserVal((Object)yytext()); return Parser.BOOL_LIT; }
{int_lit}                           { parser.yylval = new ParserVal((Object)yytext()); return Parser.INT_LIT; }
{ident}                             { parser.yylval = new ParserVal((Object)yytext()); return Parser.IDENT  ; }
{comment}                           { /* skip */ }
{newline}                           { /* skip */ }
{whitespace}                        { /* skip */ }
{blkcomment}                        { /* skip */ }


\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
