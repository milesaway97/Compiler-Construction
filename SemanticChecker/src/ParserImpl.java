import java.util.*;

@SuppressWarnings("ALL")
public class ParserImpl
{
    public static Boolean _debug = true;
    void Debug(String message)
    {
        if(_debug)
            System.out.println(message);
    }

    void throwSemanticException(String e) throws Exception
    {
        throw new Exception(e);
    }

    // this stores the root of parse tree, which will be used to print parse tree and run the parse tree
    ParseTree.Program parsetree_program = null;
    Env env = new Env(null);
    HashMap<String, String> funcList = new HashMap<>();
    HashMap<String, ArrayList<ParseTree.Param>> funcParamList = new HashMap<>();
    int retCount = 0;

    Object program____decllist(Object s1) throws Exception
    {
        ArrayList<ParseTree.FuncDefn> decllist = (ArrayList<ParseTree.FuncDefn>)s1;
        int mainFuncCount = 0;
        for (ParseTree.FuncDefn decl : decllist) {
            if (decl.ident.equals("main") && decl.rettype.typename.equals("int") && decl.params.size() == 0) {
                mainFuncCount++;
            }
        }
        if (!(mainFuncCount == 1)) {
            String e = "The program must have one main function that returns int type and has no parameters.";
            throwSemanticException(e);
        }
        parsetree_program = new ParseTree.Program(decllist);
        return parsetree_program;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object decllist____decllist_decl(Object s1, Object s2) throws Exception
    {
        ArrayList<ParseTree.FuncDefn> decllist = (ArrayList<ParseTree.FuncDefn>)s1;
        ParseTree.FuncDefn                decl = (ParseTree.FuncDefn           )s2;
        decllist.add(decl);
        return decllist;
    }
    Object decllist____eps() throws Exception
    {
        return new ArrayList<ParseTree.FuncDefn>();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object decl____fundefn(Object s1) throws Exception
    {
        return s1;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object fundefn____FUNC_IDENT_TYPEOF_LPAREN_params_RPAREN_FUNCRET_primtype_BEGIN_localdecls_11x_stmtlist_END(Object s2, Object s5, Object s8, Object s10) throws Exception
    {
        Token                           id         = (Token                           )s2;
        ArrayList<ParseTree.Param>      params     = (ArrayList<ParseTree.Param>      )s5;
        ParseTree.TypeSpec              rettype    = (ParseTree.TypeSpec              )s8;
        ArrayList<ParseTree.LocalDecl>  localdecls = (ArrayList<ParseTree.LocalDecl>  )s10;
        //create new local env, add localdecls and function name and return type into the env
        env = new Env(env);
        env.PutParams(params);
        for (ParseTree.LocalDecl localdecl : localdecls) {
            if (env.Get(localdecl.ident) != null) {
                String e = "The identifier " + localdecl.ident + " is already defined.\n"
                        + "Error location is " + localdecl.info.lineno + ":" + localdecl.info.column + ".";
                throwSemanticException(e);
            }
            env.Put(localdecl.ident, localdecl.typespec);
        }
        env.setFuncName(id.lexeme);
        env.setFuncRetType(rettype.typename);
        // check that pair of function name and return type don't already exist
        if (funcList.containsKey(id.lexeme)) {
            String e = "The function " + id.lexeme + "() is already defined.\n"
                    + "Error location is " + id.lineno + ":" + id.column + ".";
            throwSemanticException(e);
        }
        funcList.put(id.lexeme, rettype.typename);
        funcParamList.put(id.lexeme, params);
        ParseTree.FuncDefn funcdefn = new ParseTree.FuncDefn(id.lexeme, rettype, params, localdecls, null);
        return funcdefn;
    }
    Object fundefn____FUNC_IDENT_TYPEOF_LPAREN_params_RPAREN_FUNCRET_primtype_BEGIN_localdecls_x11_stmtlist_END(Object s2, Object s5, Object s8, Object s10, Object s12, Object s13) throws Exception
    {
        Token                           id         = (Token                           )s2;
        ArrayList<ParseTree.Param>      params     = (ArrayList<ParseTree.Param>      )s5;
        ParseTree.TypeSpec              rettype    = (ParseTree.TypeSpec              )s8;
        ArrayList<ParseTree.LocalDecl>  localdecls = (ArrayList<ParseTree.LocalDecl>  )s10;
        ArrayList<ParseTree.Stmt>       stmtlist   = (ArrayList<ParseTree.Stmt>       )s12;
        Token                           end        = (Token                           )s13;
        // check function returns at least one value
        if (retCount == 0) {
            String e = "The function " + id.lexeme + "() should return at least one " + rettype.typename + " value.\n"
                    + "Error location is " + end.lineno + ":" + end.column + ".";
            throwSemanticException(e);
        }
        retCount = 0;
        //delete the env
        env = env.prev;
        ParseTree.FuncDefn funcdefn = new ParseTree.FuncDefn(id.lexeme, rettype, params, localdecls, stmtlist);
        return funcdefn;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object params____paramlist(Object s1) throws Exception
    {
        ArrayList<ParseTree.Param>      params     = (ArrayList<ParseTree.Param>      )s1;
        return s1;
    }
    Object params____eps() throws Exception
    {
        return new ArrayList<ParseTree.Param>();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object paramlist____paramlist_COMMA_param(Object s1, Object s3) throws Exception
    {
        ArrayList<ParseTree.Param>      paramlist  = (ArrayList<ParseTree.Param>      )s1;
        ParseTree.Param                 param      = (ParseTree.Param                 )s3;
        paramlist.add(param);
        return paramlist;
    }
    Object paramlist____param(Object s1) throws Exception
    {
        ParseTree.Param                 param      = (ParseTree.Param                 )s1;
        ArrayList<ParseTree.Param>      paramlist  = new ArrayList<ParseTree.Param>();
        paramlist.add(param);
        return paramlist;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object param____IDENT_TYPEOF_typespec(Object s1, Object s3) throws Exception
    {
        Token                           id         = (Token                           )s1;
        ParseTree.TypeSpec              typespec   = (ParseTree.TypeSpec              )s3;
        ParseTree.Param param = new ParseTree.Param(id.lexeme, typespec);
        param.info.type = typespec.typename;
        param.info.lineno = typespec.info.lineno;
        param.info.column = typespec.info.column;
        return param;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object typespec____primtype(Object s1) throws Exception
    {
        ParseTree.TypeSpec              typespec   = (ParseTree.TypeSpec)s1;
        return s1;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object primtype____INT() throws Exception
    {
        ParseTree.TypeSpec typespec = new ParseTree.TypeSpec("int");
        return typespec;
    }
    Object primtype____BOOL() throws Exception
    {
        ParseTree.TypeSpec typespec = new ParseTree.TypeSpec("bool");
        return typespec;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object localdecls____localdecls_localdecl(Object s1, Object s2) throws Exception
    {
        ArrayList<ParseTree.LocalDecl>  localdecls = (ArrayList<ParseTree.LocalDecl>  )s1;
        ParseTree.LocalDecl             localdecl  = (ParseTree.LocalDecl             )s2;
        localdecls.add(localdecl);
        return localdecls;
    }
    Object localdecls____eps() throws Exception
    {
        return new ArrayList<ParseTree.LocalDecl>();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object localdecl____VAR_IDENT_TYPEOF_typespec_SEMI(Object s2, Object s4) throws Exception
    {
        Token                           id         = (Token                           )s2;
        ParseTree.TypeSpec              typespec   = (ParseTree.TypeSpec              )s4;
        ParseTree.LocalDecl localdecl = new ParseTree.LocalDecl(id.lexeme, typespec);
        localdecl.info.lineno = id.lineno;
        localdecl.info.column = id.column;
        return localdecl;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object stmtlist____stmtlist_stmt(Object s1, Object s2) throws Exception
    {
        ArrayList<ParseTree.Stmt>       stmtlist   = (ArrayList<ParseTree.Stmt>)s1;
        ParseTree.Stmt                  stmt       = (ParseTree.Stmt           )s2;
        stmtlist.add(stmt);
        return stmtlist;
    }
    Object stmtlist____eps() throws Exception
    {
        return new ArrayList<ParseTree.Stmt>();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object stmt____assignstmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.AssignStmt);
        return s1;
    }
    Object stmt____printstmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.PrintStmt);
        return s1;
    }
    Object stmt____returnstmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.ReturnStmt);
        retCount++;
        return s1;
    }
    Object stmt____ifstmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.IfStmt);
        return s1;
    }
    Object stmt____whilestmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.WhileStmt);
        return s1;
    }
    Object stmt____compoundstmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.CompoundStmt);
        return s1;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object assignstmt____IDENT_ASSIGN_expr_SEMI(Object s1, Object s2, Object s3) throws Exception
    {
        Token          id     = (Token         )s1;
        Token          assign = (Token         )s2;
        ParseTree.Expr expr   = (ParseTree.Expr)s3;
        // check that IDENT was declared in local_decls
        if (env.Get(id.lexeme) == null) {
            String e = "Cannot use an undefined variable " + id.lexeme + ".\n"
                    + "Error location is " + id.lineno + ":" + id.column + ".";
            throwSemanticException(e);
        }
        // check that IDENT is assigned to an expr of the same type
        ParseTree.TypeSpec idType = (ParseTree.TypeSpec) env.Get(id.lexeme);
        if (!idType.ToString(0).equals(expr.info.type)) {
            String e = "Cannot assign " + expr.info.type + " value to " + idType.ToString(0) + " variable " + id.lexeme + ".\n"
                    + "Error location is " + assign.lineno + ":" + assign.column + ".";
            throwSemanticException(e);
        }
        return new ParseTree.AssignStmt(id.lexeme, expr);
    }
    Object printstmt____PRINT_expr_SEMI(Object s2) throws Exception
    {
        ParseTree.Expr expr = (ParseTree.Expr)s2;
        return new ParseTree.PrintStmt(expr);
    }
    Object returnstmt____RETURN_expr_SEMI(Object s2) throws Exception
    {
        ParseTree.Expr expr = (ParseTree.Expr)s2;
        if (!env.getFuncRetType().equals(expr.info.type)) {
            String e = "The type of returning value (" + expr.info.type + ") should match with the return type (" + env.getFuncRetType() + ") of the function " + env.getFuncName() + "().\n"
                    + "Error location is " + expr.info.lineno + ":" + expr.info.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ReturnStmt returnStmt = new ParseTree.ReturnStmt(expr);
        returnStmt.info.isReturn = true;
        returnStmt.info.rettype = expr.info.type;
        return returnStmt;
    }
    Object ifstmt____IF_expr_THEN_stmtlist_ELSE_stmtlist_END(Object s2, Object s4, Object s6) throws Exception
    {
        ParseTree.Expr                  cond       = (ParseTree.Expr)s2;
        ArrayList<ParseTree.Stmt>       thenstmts  = (ArrayList<ParseTree.Stmt>)s4;
        ArrayList<ParseTree.Stmt>       elsestmts  = (ArrayList<ParseTree.Stmt>)s6;
        ParseTree.IfStmt ifstmt = new ParseTree.IfStmt(cond, thenstmts, elsestmts);
        if (!cond.info.type.equals("bool")) {
            String e = "Use bool value to the check condition in if statement.\n"
                    + "Error location is " + cond.info.lineno + ":" + cond.info.column + ".";
            throwSemanticException(e);
        }
        return ifstmt;
    }
    Object whilestmt____WHILE_expr_compoundstmt(Object s2, Object s3) throws Exception
    {
        ParseTree.Expr                  cond       = (ParseTree.Expr)s2;
        ParseTree.CompoundStmt          compoundstmt = (ParseTree.CompoundStmt)s3;
        ParseTree.WhileStmt whilestmt = new ParseTree.WhileStmt(cond, compoundstmt);
        if (!cond.info.type.equals("bool")) {
            String e = "Use bool value to the check condition in while statement.\n"
                    + "Error location is " + cond.info.lineno + ":" + cond.info.column + ".";
            throwSemanticException(e);
        }
        return whilestmt;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object compoundstmt____BEGIN_localdecls_3x_stmtlist_END(Object s2) throws Exception
    {
        ArrayList<ParseTree.LocalDecl>  localdecls = (ArrayList<ParseTree.LocalDecl>  )s2;
        //create new local env, add localdecls into the env
        env = new Env(env);
        env.PutLocalDecls(localdecls);
        ParseTree.CompoundStmt compoundstmt = new ParseTree.CompoundStmt(localdecls, null);
        return compoundstmt;
    }
    Object compoundstmt____BEGIN_localdecls_x3_stmtlist_END(Object s2, Object s4) throws Exception
    {
        ArrayList<ParseTree.LocalDecl>  localdecls = (ArrayList<ParseTree.LocalDecl>  )s2;
        ArrayList<ParseTree.Stmt>       stmtlist   = (ArrayList<ParseTree.Stmt>       )s4;
        //delete the env
        env = env.prev;
        ParseTree.CompoundStmt compoundstmt = new ParseTree.CompoundStmt(localdecls, stmtlist);
        return compoundstmt;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object args____arglist(Object s1) throws Exception
    {
        ArrayList<ParseTree.Arg>       args       = (ArrayList<ParseTree.Arg>      )s1;
        return s1;
    }
    Object args____eps() throws Exception
    {
        return new ArrayList<ParseTree.Arg>();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object arglist____arglist_COMMA_expr(Object s1, Object s3) throws Exception
    {
        ArrayList<ParseTree.Arg>       arglist    = (ArrayList<ParseTree.Arg>)s1;
        ParseTree.Expr                  expr      = (ParseTree.Expr          )s3;
        ParseTree.Arg arg = new ParseTree.Arg(expr);
        arg.info.type = expr.info.type;
        arg.info.lineno = expr.info.lineno;
        arg.info.column = expr.info.column;
        arglist.add(arg);
        return arglist;
    }
    Object arglist____expr(Object s1) throws Exception
    {
        ParseTree.Expr                  expr      = (ParseTree.Expr          )s1;
        ParseTree.Arg arg = new ParseTree.Arg(expr);
        arg.info.type = expr.info.type;
        arg.info.lineno = expr.info.lineno;
        arg.info.column = expr.info.column;
        ArrayList<ParseTree.Arg> arglist = new ArrayList<>();
        arglist.add(arg);
        return arglist;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object expr____expr_AND_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        if (!expr1.info.type.equals("bool") || !expr2.info.type.equals("bool")) {
            String e = "Cannot perform " + expr1.info.type + " and " + expr2.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("and",expr1,expr2);
        exproper.info.type = "bool";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____expr_OR_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        if (!expr1.info.type.equals("bool") || !expr2.info.type.equals("bool")) {
            String e = "Cannot perform " + expr1.info.type + " or " + expr2.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("or",expr1,expr2);
        exproper.info.type = "bool";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____NOT_expr(Object s1, Object s2) throws Exception
    {
        Token          oper  = (Token         )s1;
        ParseTree.Expr expr1 = (ParseTree.Expr)s2;
        if (!expr1.info.type.equals("bool")) {
            String e = "Cannot perform not " + expr1.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("not",expr1,null);
        exproper.info.type = "bool";
        exproper.info.lineno = oper.lineno;
        exproper.info.column = oper.column;
        return exproper;
    }
    Object expr____expr_EQ_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
//        if (!expr1.info.type.equals(expr2.info.type)) {
//            String e = "Cannot perform " + expr1.info.type + " = " + expr2.info.type + ".\n"
//                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
//            throwSemanticException(e);
//        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("=",expr1,expr2);
        exproper.info.type = "bool";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____expr_NE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
//        if (!expr1.info.type.equals(expr2.info.type)) {
//            String e = "Cannot perform " + expr1.info.type + " != " + expr2.info.type + ".\n"
//                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
//            throwSemanticException(e);
//        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("!=",expr1,expr2);
        exproper.info.type = "bool";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____expr_LE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        if (!expr1.info.type.equals("int") || !expr2.info.type.equals("int")) {
            String e = "Cannot perform " + expr1.info.type + " <= " + expr2.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("<=",expr1,expr2);
        exproper.info.type = "bool";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____expr_LT_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        if (!expr1.info.type.equals("int") || !expr2.info.type.equals("int")) {
            String e = "Cannot perform " + expr1.info.type + " < " + expr2.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("<",expr1,expr2);
        exproper.info.type = "bool";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____expr_GE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        if (!expr1.info.type.equals("int") || !expr2.info.type.equals("int")) {
            String e = "Cannot perform " + expr1.info.type + " >= " + expr2.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper(">=",expr1,expr2);
        exproper.info.type = "bool";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____expr_GT_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        if (!expr1.info.type.equals("int") || !expr2.info.type.equals("int")) {
            String e = "Cannot perform " + expr1.info.type + " > " + expr2.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper(">",expr1,expr2);
        exproper.info.type = "bool";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____expr_ADD_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        if (!expr1.info.type.equals("int") || !expr2.info.type.equals("int")) {
            String e = "Cannot perform " + expr1.info.type + " + " + expr2.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("+",expr1,expr2);
        exproper.info.type = "int";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____expr_SUB_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        if (!expr1.info.type.equals("int") || !expr2.info.type.equals("int")) {
            String e = "Cannot perform " + expr1.info.type + " - " + expr2.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("-",expr1,expr2);
        exproper.info.type = "int";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____expr_MUL_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        if (!expr1.info.type.equals("int") || !expr2.info.type.equals("int")) {
            String e = "Cannot perform " + expr1.info.type + " * " + expr2.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("*",expr1,expr2);
        exproper.info.type = "int";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____expr_DIV_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        if (!expr1.info.type.equals("int") || !expr2.info.type.equals("int")) {
            String e = "Cannot perform " + expr1.info.type + " / " + expr2.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("/",expr1,expr2);
        exproper.info.type = "int";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____expr_MOD_expr(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        if (!expr1.info.type.equals("int") || !expr2.info.type.equals("int")) {
            String e = "Cannot perform " + expr1.info.type + " % " + expr2.info.type + ".\n"
                    + "Error location is " + oper.lineno + ":" + oper.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("%",expr1,expr2);
        exproper.info.type = "int";
        exproper.info.lineno = expr1.info.lineno;
        exproper.info.column = expr1.info.column;
        return exproper;
    }
    Object expr____LPAREN_expr_RPAREN(Object s1, Object s2, Object s3) throws Exception
    {
        Token          lparen = (Token         )s1;
        ParseTree.Expr expr   = (ParseTree.Expr)s2;
        Token          rparen = (Token         )s3;
        ParseTree.ExprOper exproper = new ParseTree.ExprOper("()",expr,null);
        exproper.info.type = expr.info.type;
        exproper.info.lineno = lparen.lineno;
        exproper.info.column = lparen.column;
        return exproper;
    }
    Object expr____IDENT(Object s1) throws Exception
    {
        Token id = (Token)s1;
        if (funcList.containsKey(id.lexeme)) {
            String e = "Cannot use the function " + id.lexeme + "() as a variable.\n"
                    + "Error location is " + id.lineno + ":" + id.column + ".";
            throwSemanticException(e);
        }
        // check that IDENT was declared in local_decls
        if (env.Get(id.lexeme) == null) {
            String e = "Cannot use an undefined variable " + id.lexeme + ".\n"
                    + "Error location is " + id.lineno + ":" + id.column + ".";
            throwSemanticException(e);
        }
        ParseTree.ExprIdent exprident = new ParseTree.ExprIdent(id.lexeme);
        ParseTree.TypeSpec identtype = (ParseTree.TypeSpec) env.Get(id.lexeme);
        exprident.info.type = identtype.ToString(0);
        exprident.info.lineno = id.lineno;
        exprident.info.column = id.column;
        return exprident;
    }
    Object expr____BOOLLIT(Object s1) throws Exception
    {
        Token token = (Token)s1;
        //value is true if token.lexeme is equal to string "true"
        boolean value = Boolean.parseBoolean(token.lexeme);
        ParseTree.ExprBoolLit exprboollit = new ParseTree.ExprBoolLit(value);
        exprboollit.info.type = "bool";
        exprboollit.info.lineno = token.lineno;
        exprboollit.info.column = token.column;
        return exprboollit;
    }
    Object expr____INTLIT(Object s1) throws Exception
    {
        Token token = (Token)s1;
        int value = Integer.parseInt(token.lexeme);
        ParseTree.ExprIntLit exprintlit = new ParseTree.ExprIntLit(value);
        exprintlit.info.type = "int";
        exprintlit.info.lineno = token.lineno;
        exprintlit.info.column = token.column;
        return exprintlit;
    }
    Object expr____IDENT_LPAREN_args_RPAREN(Object s1, Object s3) throws Exception
    {
        Token                    id   = (Token                   )s1;
        ArrayList<ParseTree.Arg> args = (ArrayList<ParseTree.Arg>)s3;
        if (env.Get(id.lexeme) != null) {
            String e = "Cannot use a variable " + id.lexeme + " as a function.\n"
                    + "Error location is " + id.lineno + ":" + id.column + ".";
            throwSemanticException(e);
        }
        if (!funcList.containsKey(id.lexeme)) {
            String e = "Cannot use an undefined function " + id.lexeme + "().\n"
                    + "Error location is " + id.lineno + ":" + id.column + ".";
            throwSemanticException(e);
        }
        ArrayList<ParseTree.Param> params = funcParamList.get(id.lexeme);
        if (args.size() != params.size()) {
            String e = "Cannot pass the incorrect number of arguments to " + id.lexeme + "().\n"
                    + "Error location is " + id.lineno + ":" + id.column + ".";
            throwSemanticException(e);
        }
        for (int i = 0; i < args.size(); i++) {
            if (!args.get(i).info.type.equals(params.get(i).info.type)) {
                String e = "The " + (i+1) + "rd argument of the function " + id.lexeme + "() should be " + params.get(i).info.type + " type.\n"
                        + "Error location is " + args.get(i).info.lineno + ":" + args.get(i).info.column + ".";
                throwSemanticException(e);
            }
        }
        ParseTree.ExprCall exprCall = new ParseTree.ExprCall(id.lexeme, args);
        exprCall.info.type = funcList.get(id.lexeme);
        exprCall.info.lineno = id.lineno;
        exprCall.info.column = id.column;
        return exprCall;
    }

}
