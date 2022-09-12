import java.util.ArrayList;
import java.util.HashMap;

public class Env
{
    public final Env prev;
    public HashMap<String, Object> table;
    public String funcName;
    public String funcRetType;
    public Env(Env prev)
    {
        this.prev = prev;
        table = new HashMap<>();
    }
    public void Put(String name, Object value)
    {
        table.put(name, value);
    }
    public void PutLocalDecls(ArrayList<ParseTree.LocalDecl> list) throws Exception
    {
        if (list != null && list.size() > 0) {
            for (ParseTree.LocalDecl decl : list) {
                Put(decl.ident, decl.typespec);
            }
        }
    }
    public void PutParams(ArrayList<ParseTree.Param> list) throws Exception
    {
        if (list != null && list.size() > 0) {
            for (ParseTree.Param param : list) {
                Put(param.ident, param.typespec);
            }
        }
    }
    public Object Get(String name)
    {
        //search local table
        if (table.containsKey(name)) {
            return table.get(name);
        }
        //search for key iteratively from most recent table to the previous until null
        Env temp = prev;
        while (temp != null) {
            if (temp.table.containsKey(name)) {
                return temp.table.get(name);
            }
            temp = temp.prev;
        }
        //key is not found, return null
        return null;
    }
    public void setFuncName(String name) {
        funcName = name;
    }
    public String getFuncName() {
        return funcName;
    }
    public void setFuncRetType(String name) {
        funcRetType = name;
    }
    public String getFuncRetType() {
        return funcRetType;
    }
}
