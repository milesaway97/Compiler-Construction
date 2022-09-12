import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class ParseTreeInfo
{
    // Use this classes to store information into parse tree node (subclasses of ParseTree.Node)
    // You should not modify ParseTree.java
    public static class TypeSpecInfo
    {
        public String type;
        public int lineno;
        public int column;
    }
    public static class ProgramInfo
    {
        public String type;
        public int lineno;
        public int column;
    }
    public static class FuncDefnInfo
    {
        public String type;
        public int lineno;
        public int column;
    }
    public static class ParamInfo
    {
        public String type;
        public int lineno;
        public int column;
    }
    public static class LocalDeclInfo
    {
        public String type;
        public int lineno;
        public int column;
    }
    public static class StmtStmtInfo
    {
        public String rettype = "";
        public boolean isReturn = false;
    }
    public static class ArgInfo
    {
        public String type;
        public int lineno;
        public int column;
    }
    public static class ExprInfo
    {
        public String type;
        public int lineno;
        public int column;
    }
}
