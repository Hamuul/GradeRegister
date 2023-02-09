import java.util.Comparator;
import java.util.TreeSet;

public class Group extends TreeSet<Student> {
    public Assistant getAst() {
        return ast;
    }

    public void setAst(Assistant ast) {
        this.ast = ast;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private Assistant ast;
    private String ID;
    public Group(String ID, Assistant ast){
        super();
        this.ID = ID;
        this.ast = ast;
    }
    public Group(String ID, Assistant ast, Comparator<Student> comp){
        super(comp);
        this.ID = ID;
        this.ast = ast;
    }
}
