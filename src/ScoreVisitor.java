import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class ScoreVisitor implements Visitor{
    private HashMap<Teacher, Tuple<Student, String, Double>> examScore;
    private HashMap<Assistant, Tuple<Student, String, Double>> partialScore;
    public ScoreVisitor(){
        examScore = new HashMap<>();
        partialScore = new HashMap<>();
    }
    public HashMap<Teacher, Tuple<Student, String, Double>> getExamScore() {
        return examScore;
    }

    public void setExamScore(HashMap<Teacher, Tuple<Student, String, Double>> examScore) {
        this.examScore = examScore;
    }

    public HashMap<Assistant, Tuple<Student, String, Double>> getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(HashMap<Assistant, Tuple<Student, String, Double>> partialScore) {
        this.partialScore = partialScore;
    }
    @Override
    public void visit(Assistant assistant) {
        HashMap<Assistant, Tuple<Student, String, Double>> ps = getPartialScore();
        Catalog ctlg = Catalog.getInstance();
        for(Course crs : ctlg.courseList){
            for(Map.Entry<Assistant, Tuple<Student, String, Double>> ent : ps.entrySet()){
                if(ent.getValue().getVal2().equals(crs.getCourseName())){
                    Grade toAdd = new Grade();
                    toAdd.setCourse(crs.getCourseName());
                    toAdd.setStudent(ent.getValue().getVal1());
                    toAdd.setPartialScore(ent.getValue().getVal3());
                    crs.modifyGradeTree(toAdd);
                    ctlg.notifyObservers(toAdd);
                }
            }
        }
    }

    @Override
    public void visit(Teacher teacher) {
        HashMap<Teacher, Tuple<Student, String, Double>> ps = getExamScore();
        Catalog ctlg = Catalog.getInstance();
        for(Course crs : ctlg.courseList){
            for(Map.Entry<Teacher, Tuple<Student, String, Double>> ent : ps.entrySet()){
                if(ent.getValue().getVal2().equals(crs.getCourseName())){
                    Grade toAdd = new Grade();
                    toAdd.setCourse(crs.getCourseName());
                    toAdd.setStudent(ent.getValue().getVal1());
                    toAdd.setExamScore(ent.getValue().getVal3());
                    crs.getGradeTree().add(toAdd);
                    ctlg.notifyObservers(toAdd);
                }
            }
        }
    }

}
