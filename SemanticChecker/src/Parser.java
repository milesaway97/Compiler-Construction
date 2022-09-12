//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 15 "Parser.y"
import java.io.*;
//#line 19 "Parser.java"




public class Parser
             extends ParserImpl
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ASSIGN=257;
public final static short OR=258;
public final static short AND=259;
public final static short NOT=260;
public final static short EQ=261;
public final static short NE=262;
public final static short LE=263;
public final static short LT=264;
public final static short GE=265;
public final static short GT=266;
public final static short ADD=267;
public final static short SUB=268;
public final static short MUL=269;
public final static short DIV=270;
public final static short MOD=271;
public final static short IDENT=272;
public final static short INT_LIT=273;
public final static short BOOL_LIT=274;
public final static short BOOL=275;
public final static short INT=276;
public final static short FUNC=277;
public final static short IF=278;
public final static short THEN=279;
public final static short ELSE=280;
public final static short WHILE=281;
public final static short PRINT=282;
public final static short RETURN=283;
public final static short BEGIN=284;
public final static short END=285;
public final static short LPAREN=286;
public final static short RPAREN=287;
public final static short TYPEOF=288;
public final static short VAR=289;
public final static short SEMI=290;
public final static short COMMA=291;
public final static short FUNCRET=292;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,   22,    3,    8,    8,    9,    9,
   10,    6,    7,    7,    4,    4,    5,   13,   13,   14,
   14,   14,   14,   14,   14,   15,   16,   17,   18,   19,
   23,   20,   11,   11,   12,   12,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   21,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    0,   13,    1,    0,    3,    1,
    3,    1,    1,    1,    2,    0,    5,    2,    0,    1,
    1,    1,    1,    1,    1,    4,    3,    3,    7,    3,
    0,    5,    1,    0,    3,    1,    3,    3,    2,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    1,    1,    1,    4,
};
final static short yydefred[] = {                         3,
    0,    0,    0,    2,    4,    0,    0,    0,    0,    0,
    0,   10,    0,    0,    0,   14,   13,   11,   12,    0,
    9,    0,   16,    0,    0,   15,   19,    0,    0,    0,
    0,    0,    0,    0,    0,   16,    6,   18,   20,   21,
   22,   23,   24,   25,    0,    0,    0,    0,   54,   53,
    0,    0,    0,    0,    0,    0,   17,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   19,   30,   27,   28,   19,   26,
    0,    0,    0,   51,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   48,   49,   50,    0,    0,   55,
    0,   19,   32,    0,    0,   29,
};
final static short yydgoto[] = {                          1,
    2,    4,    5,   24,   26,   18,   19,   10,   11,   12,
   81,   82,   29,   38,   39,   40,   41,   42,   43,   44,
   52,   27,   79,
};
final static short yysindex[] = {                         0,
    0, -252, -256,    0,    0, -262, -258, -227, -226, -224,
 -225,    0, -266, -222, -227,    0,    0,    0,    0, -266,
    0, -219,    0, -218, -199,    0,    0, -214, -261, -266,
 -178,   94,   94,   94,   94,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -201,   94,   94, -196,    0,    0,
   94,  120,   81,   10,   24, -218,    0,   40,  208,   94,
   54,   94,   94,   94,   94,   94,   94,   94,   94,   94,
   94,   94,   94,   94,    0,    0,    0,    0,    0,    0,
 -195, -200,  139,    0,  183,  208,  217,  217, -264, -264,
 -264, -264, -211, -211,    0,    0,    0,  184,   91,    0,
   94,    0,    0,  139,  140,    0,
};
final static short yyrindex[] = {                         0,
    0,   95,    0,    0,    0,    0,    0, -193,    0,    0,
 -190,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  148,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -215,    0,    0,
    0,    0,    0,    0,    0,  156,    0,    0,  -34, -180,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -179, -279,    0, -191,  -24, -157,  -51, -113, -104,
  -70,  -61, -181, -147,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -230,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   77,    0,   93,  104,    0,    0,  110,
    0,    0,  -60,    0,    0,    0,    0,    0,    0,   73,
  -33,    0,    0,
};
final static int YYTABLESIZE=488;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         53,
   54,   55,   70,   71,   72,   73,   74,   36,   16,   17,
   31,   36,   58,   59,   98,    6,   32,   61,   99,   33,
   34,   35,   36,   37,    3,    7,   83,    8,   85,   86,
   87,   88,   89,   90,   91,   92,   93,   94,   95,   96,
   97,  105,   52,   52,    9,   52,   52,   52,   52,   52,
   52,   52,   52,   52,   52,   52,   35,   72,   73,   74,
   35,   13,   14,   52,   23,   15,   38,  104,   52,   20,
   25,   52,   28,   30,   52,   52,   46,   46,   46,   46,
   46,   46,   46,   46,   46,   46,   46,   38,   57,   60,
  101,  100,   38,    8,    1,   38,    7,   46,   38,   38,
   40,   40,   46,   40,   40,   46,   34,   33,   46,   46,
   47,   47,   56,   47,   47,   47,   47,   47,   47,   47,
   47,   40,   45,   22,   21,   76,   40,    0,    0,   40,
    0,   47,   40,   40,    0,    0,   47,    0,    0,   47,
    0,    0,   47,   47,   42,   42,    0,   42,   42,   42,
   42,   42,   42,   43,   43,    0,   43,   43,   43,   43,
   43,   43,    0,    0,    0,   42,    0,    0,    0,    0,
   42,    0,    0,   42,   43,    0,   42,   42,    0,   43,
    0,    0,   43,    0,    0,   43,   43,   44,   44,    0,
   44,   44,   44,   44,   44,   44,   45,   45,    0,   45,
   45,   45,   45,   45,   45,    0,   41,   41,   44,   41,
   41,    0,    0,   44,    0,    0,   44,   45,    0,   44,
   44,    0,   45,   39,   39,   45,    0,   41,   45,   45,
    0,    0,   41,   37,   37,   41,    0,    0,   41,   41,
    0,    0,    0,    0,   39,    0,    0,    0,    0,   39,
    0,    0,   39,    0,   37,   39,   39,    0,    0,   37,
    0,    0,   37,    0,    0,   37,   37,   62,   63,    0,
   64,   65,   66,   67,   68,   69,   70,   71,   72,   73,
   74,   62,   63,    0,   64,   65,   66,   67,   68,   69,
   70,   71,   72,   73,   74,    0,    0,   62,   63,   77,
   64,   65,   66,   67,   68,   69,   70,   71,   72,   73,
   74,   62,   63,   78,   64,   65,   66,   67,   68,   69,
   70,   71,   72,   73,   74,    0,    0,    0,    0,   80,
    0,    0,    0,    0,    0,    0,    0,    0,   62,   63,
   84,   64,   65,   66,   67,   68,   69,   70,   71,   72,
   73,   74,    0,   47,    0,    0,    0,    0,    0,    0,
    0,    0,   31,    0,   36,   48,   49,   50,   32,    0,
    0,   33,   34,   35,   36,  103,    0,   62,   63,   51,
   64,   65,   66,   67,   68,   69,   70,   71,   72,   73,
   74,    0,    0,    0,    0,    0,   62,   63,   75,   64,
   65,   66,   67,   68,   69,   70,   71,   72,   73,   74,
    0,   31,    0,    0,    0,    0,    0,   32,    0,    5,
   33,   34,   35,   36,  106,    5,    0,   31,    5,    5,
    5,    5,    5,   31,    0,    0,   31,   31,   31,   31,
   31,   63,    0,   64,   65,   66,   67,   68,   69,   70,
   71,   72,   73,   74,    0,   31,    0,    0,    0,    0,
    0,   32,    0,  102,   33,   34,   35,   36,   64,   65,
   66,   67,   68,   69,   70,   71,   72,   73,   74,   66,
   67,   68,   69,   70,   71,   72,   73,   74,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   34,   35,  267,  268,  269,  270,  271,  287,  275,  276,
  272,  291,   46,   47,   75,  272,  278,   51,   79,  281,
  282,  283,  284,  285,  277,  288,   60,  286,   62,   63,
   64,   65,   66,   67,   68,   69,   70,   71,   72,   73,
   74,  102,  258,  259,  272,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  287,  269,  270,  271,
  291,  288,  287,  279,  284,  291,  258,  101,  284,  292,
  289,  287,  272,  288,  290,  291,  258,  259,  257,  261,
  262,  263,  264,  265,  266,  267,  268,  279,  290,  286,
  291,  287,  284,  287,    0,  287,  287,  279,  290,  291,
  258,  259,  284,  261,  262,  287,  287,  287,  290,  291,
  258,  259,   36,  261,  262,  263,  264,  265,  266,  267,
  268,  279,   30,   20,   15,   53,  284,   -1,   -1,  287,
   -1,  279,  290,  291,   -1,   -1,  284,   -1,   -1,  287,
   -1,   -1,  290,  291,  258,  259,   -1,  261,  262,  263,
  264,  265,  266,  258,  259,   -1,  261,  262,  263,  264,
  265,  266,   -1,   -1,   -1,  279,   -1,   -1,   -1,   -1,
  284,   -1,   -1,  287,  279,   -1,  290,  291,   -1,  284,
   -1,   -1,  287,   -1,   -1,  290,  291,  258,  259,   -1,
  261,  262,  263,  264,  265,  266,  258,  259,   -1,  261,
  262,  263,  264,  265,  266,   -1,  258,  259,  279,  261,
  262,   -1,   -1,  284,   -1,   -1,  287,  279,   -1,  290,
  291,   -1,  284,  258,  259,  287,   -1,  279,  290,  291,
   -1,   -1,  284,  258,  259,  287,   -1,   -1,  290,  291,
   -1,   -1,   -1,   -1,  279,   -1,   -1,   -1,   -1,  284,
   -1,   -1,  287,   -1,  279,  290,  291,   -1,   -1,  284,
   -1,   -1,  287,   -1,   -1,  290,  291,  258,  259,   -1,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  258,  259,   -1,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   -1,   -1,  258,  259,  290,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  271,  258,  259,  290,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  271,   -1,   -1,   -1,   -1,  290,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  258,  259,
  287,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,   -1,  260,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  272,   -1,  284,  272,  273,  274,  278,   -1,
   -1,  281,  282,  283,  284,  285,   -1,  258,  259,  286,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,   -1,   -1,   -1,   -1,  258,  259,  279,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
   -1,  272,   -1,   -1,   -1,   -1,   -1,  278,   -1,  272,
  281,  282,  283,  284,  285,  278,   -1,  272,  281,  282,
  283,  284,  285,  278,   -1,   -1,  281,  282,  283,  284,
  285,  259,   -1,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,   -1,  272,   -1,   -1,   -1,   -1,
   -1,  278,   -1,  280,  281,  282,  283,  284,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  270,  271,  263,
  264,  265,  266,  267,  268,  269,  270,  271,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=292;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"ASSIGN","OR","AND","NOT","EQ","NE","LE","LT","GE","GT","ADD",
"SUB","MUL","DIV","MOD","IDENT","INT_LIT","BOOL_LIT","BOOL","INT","FUNC","IF",
"THEN","ELSE","WHILE","PRINT","RETURN","BEGIN","END","LPAREN","RPAREN","TYPEOF",
"VAR","SEMI","COMMA","FUNCRET",
};
final static String yyrule[] = {
"$accept : program",
"program : decl_list",
"decl_list : decl_list decl",
"decl_list :",
"decl : fun_defn",
"$$1 :",
"fun_defn : FUNC IDENT TYPEOF LPAREN params RPAREN FUNCRET prim_type BEGIN local_decls $$1 stmt_list END",
"params : param_list",
"params :",
"param_list : param_list COMMA param",
"param_list : param",
"param : IDENT TYPEOF type_spec",
"type_spec : prim_type",
"prim_type : INT",
"prim_type : BOOL",
"local_decls : local_decls local_decl",
"local_decls :",
"local_decl : VAR IDENT TYPEOF type_spec SEMI",
"stmt_list : stmt_list stmt",
"stmt_list :",
"stmt : assign_stmt",
"stmt : print_stmt",
"stmt : return_stmt",
"stmt : if_stmt",
"stmt : while_stmt",
"stmt : compound_stmt",
"assign_stmt : IDENT ASSIGN expr SEMI",
"print_stmt : PRINT expr SEMI",
"return_stmt : RETURN expr SEMI",
"if_stmt : IF expr THEN stmt_list ELSE stmt_list END",
"while_stmt : WHILE expr compound_stmt",
"$$2 :",
"compound_stmt : BEGIN local_decls $$2 stmt_list END",
"args : arg_list",
"args :",
"arg_list : arg_list COMMA expr",
"arg_list : expr",
"expr : expr AND expr",
"expr : expr OR expr",
"expr : NOT expr",
"expr : expr EQ expr",
"expr : expr NE expr",
"expr : expr LE expr",
"expr : expr LT expr",
"expr : expr GE expr",
"expr : expr GT expr",
"expr : expr ADD expr",
"expr : expr SUB expr",
"expr : expr MUL expr",
"expr : expr DIV expr",
"expr : expr MOD expr",
"expr : LPAREN expr RPAREN",
"expr : IDENT",
"expr : BOOL_LIT",
"expr : INT_LIT",
"expr : IDENT LPAREN args RPAREN",
};

//#line 147 "Parser.y"
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
        System.out.println ("Error message for " + lexer.lineno+":"+lexer.column +" by Parser.yyerror(): " + error);
        //System.out.println ("Error message by Parser.yyerror(): " + error);
    }


    public Parser(Reader r, boolean yydebug) {
        this.lexer   = new Lexer(r, this);
        this.yydebug = yydebug;
    }
//#line 410 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
throws Exception
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 47 "Parser.y"
{ Debug("program -> decl_list"                  ); yyval.obj = program____decllist(val_peek(0).obj); }
break;
case 2:
//#line 50 "Parser.y"
{ Debug("decl_list -> decl_list decl"           ); yyval.obj = decllist____decllist_decl(val_peek(1).obj,val_peek(0).obj); }
break;
case 3:
//#line 51 "Parser.y"
{ Debug("decl_list -> eps"                      ); yyval.obj = decllist____eps(     ); }
break;
case 4:
//#line 54 "Parser.y"
{ Debug("decl -> fun_defn"                      ); yyval.obj = decl____fundefn(val_peek(0).obj); }
break;
case 5:
//#line 57 "Parser.y"
{ Debug("fun_defn -> FUNC ID::( params )->type BEGIN local_decls"); yyval.obj = fundefn____FUNC_IDENT_TYPEOF_LPAREN_params_RPAREN_FUNCRET_primtype_BEGIN_localdecls_11x_stmtlist_END(val_peek(8).obj, val_peek(5).obj, val_peek(2).obj, val_peek(0).obj); }
break;
case 6:
//#line 58 "Parser.y"
{ Debug("fun_defn -> ... stmt_list END "); yyval.obj = fundefn____FUNC_IDENT_TYPEOF_LPAREN_params_RPAREN_FUNCRET_primtype_BEGIN_localdecls_x11_stmtlist_END(val_peek(11).obj, val_peek(8).obj, val_peek(5).obj, val_peek(3).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 7:
//#line 61 "Parser.y"
{ Debug("params -> param_list"                  ); yyval.obj = params____paramlist(val_peek(0).obj); }
break;
case 8:
//#line 62 "Parser.y"
{ Debug("params -> eps"                         ); yyval.obj = params____eps(); }
break;
case 9:
//#line 65 "Parser.y"
{ Debug("param_list -> param_list COMMA param"  ); yyval.obj = paramlist____paramlist_COMMA_param(val_peek(2).obj,val_peek(0).obj);}
break;
case 10:
//#line 66 "Parser.y"
{ Debug("param_list -> param"                   ); yyval.obj = paramlist____param(val_peek(0).obj);}
break;
case 11:
//#line 69 "Parser.y"
{ Debug("param -> IDENT TYPEOF type_spec"       ); yyval.obj = param____IDENT_TYPEOF_typespec(val_peek(2).obj,val_peek(0).obj);}
break;
case 12:
//#line 72 "Parser.y"
{ Debug("type_spec -> prim_type"                ); yyval.obj = typespec____primtype(val_peek(0).obj);}
break;
case 13:
//#line 75 "Parser.y"
{ Debug("prim_type -> INT"                      ); yyval.obj = primtype____INT(); }
break;
case 14:
//#line 76 "Parser.y"
{ Debug("prim_type -> BOOL"                     ); yyval.obj = primtype____BOOL(); }
break;
case 15:
//#line 79 "Parser.y"
{ Debug("local_decls -> local_decls local_decl" ); yyval.obj = localdecls____localdecls_localdecl(val_peek(1).obj,val_peek(0).obj); }
break;
case 16:
//#line 80 "Parser.y"
{ Debug("local_decls -> eps"                    ); yyval.obj = localdecls____eps();}
break;
case 17:
//#line 83 "Parser.y"
{ Debug("local_decl -> VAR IDENT TYPEOF type_spec SEMI"); yyval.obj = localdecl____VAR_IDENT_TYPEOF_typespec_SEMI(val_peek(3).obj,val_peek(1).obj);}
break;
case 18:
//#line 86 "Parser.y"
{ Debug("stmt_list -> stmt_list stmt"           ); yyval.obj = stmtlist____stmtlist_stmt(val_peek(1).obj, val_peek(0).obj); }
break;
case 19:
//#line 87 "Parser.y"
{ Debug("stmt_list -> eps"                      ); yyval.obj = stmtlist____eps(); }
break;
case 20:
//#line 90 "Parser.y"
{ Debug("stmt -> assign_stmt"                   ); yyval.obj = stmt____assignstmt(val_peek(0).obj); }
break;
case 21:
//#line 91 "Parser.y"
{ Debug("stmt -> print_stmt"                    ); yyval.obj = stmt____printstmt(val_peek(0).obj); }
break;
case 22:
//#line 92 "Parser.y"
{ Debug("stmt -> return_stmt"                   ); yyval.obj = stmt____returnstmt(val_peek(0).obj); }
break;
case 23:
//#line 93 "Parser.y"
{ Debug("stmt -> if_stmt"                       ); yyval.obj = stmt____ifstmt(val_peek(0).obj); }
break;
case 24:
//#line 94 "Parser.y"
{ Debug("stmt -> while_stmt"                    ); yyval.obj = stmt____whilestmt(val_peek(0).obj); }
break;
case 25:
//#line 95 "Parser.y"
{ Debug("stmt -> compound_stmt"                 ); yyval.obj = stmt____compoundstmt(val_peek(0).obj); }
break;
case 26:
//#line 98 "Parser.y"
{ Debug("assign_stmt -> IDENT ASSIGN expr SEMI" ); yyval.obj = assignstmt____IDENT_ASSIGN_expr_SEMI(val_peek(3).obj,val_peek(2).obj,val_peek(1).obj); }
break;
case 27:
//#line 101 "Parser.y"
{ Debug("print_stmt -> PRINT expr SEMI"         ); yyval.obj = printstmt____PRINT_expr_SEMI(val_peek(1).obj); }
break;
case 28:
//#line 104 "Parser.y"
{ Debug("return_stmt -> RETURN expr SEMI"       ); yyval.obj = returnstmt____RETURN_expr_SEMI(val_peek(1).obj); }
break;
case 29:
//#line 107 "Parser.y"
{ Debug("if_stmt -> IF expr THEN stmt_list ELSE stmt_list END"); yyval.obj = ifstmt____IF_expr_THEN_stmtlist_ELSE_stmtlist_END(val_peek(5).obj,val_peek(3).obj,val_peek(1).obj); }
break;
case 30:
//#line 110 "Parser.y"
{ Debug("while_stmt -> WHILE expr compound_stmt"); yyval.obj = whilestmt____WHILE_expr_compoundstmt(val_peek(1).obj,val_peek(0).obj); }
break;
case 31:
//#line 113 "Parser.y"
{ Debug("compound_stmt -> BEGIN local_decls"                  ); yyval.obj = compoundstmt____BEGIN_localdecls_3x_stmtlist_END(val_peek(0).obj); }
break;
case 32:
//#line 114 "Parser.y"
{ Debug("compound_stmt -> ... stmt_list END"    ); yyval.obj = compoundstmt____BEGIN_localdecls_x3_stmtlist_END(val_peek(3).obj,val_peek(1).obj); }
break;
case 33:
//#line 117 "Parser.y"
{ Debug("args -> arg_list"                      ); yyval.obj = args____arglist(val_peek(0).obj); }
break;
case 34:
//#line 118 "Parser.y"
{ Debug("args -> eps"                           ); yyval.obj = args____eps(); }
break;
case 35:
//#line 121 "Parser.y"
{ Debug("arg_list -> arg_list COMMA expr"       ); yyval.obj = arglist____arglist_COMMA_expr(val_peek(2).obj,val_peek(0).obj); }
break;
case 36:
//#line 122 "Parser.y"
{ Debug("arg_list -> expr"                      ); yyval.obj = arglist____expr(val_peek(0).obj); }
break;
case 37:
//#line 125 "Parser.y"
{ Debug("expr -> expr AND expr"                 ); yyval.obj = expr____expr_AND_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 38:
//#line 126 "Parser.y"
{ Debug("expr -> expr OR expr"                  ); yyval.obj = expr____expr_OR_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 39:
//#line 127 "Parser.y"
{ Debug("expr -> NOT expr"                      ); yyval.obj = expr____NOT_expr(val_peek(1).obj,val_peek(0).obj); }
break;
case 40:
//#line 128 "Parser.y"
{ Debug("expr -> expr EQ expr"                  ); yyval.obj = expr____expr_EQ_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 41:
//#line 129 "Parser.y"
{ Debug("expr -> expr NE expr"                  ); yyval.obj = expr____expr_NE_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 42:
//#line 130 "Parser.y"
{ Debug("expr -> expr LE expr"                  ); yyval.obj = expr____expr_LE_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 43:
//#line 131 "Parser.y"
{ Debug("expr -> expr LT expr"                  ); yyval.obj = expr____expr_LT_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 44:
//#line 132 "Parser.y"
{ Debug("expr -> expr GE expr"                  ); yyval.obj = expr____expr_GE_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 45:
//#line 133 "Parser.y"
{ Debug("expr -> expr GT expr"                  ); yyval.obj = expr____expr_GT_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 46:
//#line 134 "Parser.y"
{ Debug("expr -> expr ADD expr"                 ); yyval.obj = expr____expr_ADD_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 47:
//#line 135 "Parser.y"
{ Debug("expr -> expr SUB expr"                 ); yyval.obj = expr____expr_SUB_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 48:
//#line 136 "Parser.y"
{ Debug("expr -> expr MUL expr"                 ); yyval.obj = expr____expr_MUL_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 49:
//#line 137 "Parser.y"
{ Debug("expr -> expr DIV expr"                 ); yyval.obj = expr____expr_DIV_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 50:
//#line 138 "Parser.y"
{ Debug("expr -> expr MOD expr"                 ); yyval.obj = expr____expr_MOD_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 51:
//#line 139 "Parser.y"
{ Debug("expr -> LPAREN expr RPAREN"            ); yyval.obj = expr____LPAREN_expr_RPAREN(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 52:
//#line 140 "Parser.y"
{ Debug("expr -> IDENT"                         ); yyval.obj = expr____IDENT(val_peek(0).obj); }
break;
case 53:
//#line 141 "Parser.y"
{ Debug("expr -> BOOL_LIT"                      ); yyval.obj = expr____BOOLLIT(val_peek(0).obj); }
break;
case 54:
//#line 142 "Parser.y"
{ Debug("expr -> INT_LIT"                       ); yyval.obj = expr____INTLIT(val_peek(0).obj); }
break;
case 55:
//#line 143 "Parser.y"
{ Debug("expr -> IDENT LPAREN args RPAREN"      ); yyval.obj = expr____IDENT_LPAREN_args_RPAREN(val_peek(3).obj,val_peek(1).obj); }
break;
//#line 779 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
