public class Program {
    public static void main(String[] args) throws Exception
    {
        //  java.io.Reader r = new java.io.StringReader
        //  ("func main::void->int\n"
        //  +"begin\n"
        //  +"    a <- 1;\n"
        //  +"    print a;\n"
        //  +"end\n"
        //  );

        //  args = new String[] { "D:\\cmpsc470\\proj2-minc-lexer-startup\\test\\test1.minc" };
        if(args.length <= 0)
            return;

        java.io.Reader r = new java.io.FileReader(args[0]);
        Parser p = new Parser(r);
        p.yyparse();
    }
}
