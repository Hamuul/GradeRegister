import java.util.ArrayList;
import java.util.Map;

public class FullCourse extends Course{
    private FullCourse(FullCourseBuilder builder){
        super(builder);
    }
    public ArrayList<Student> getGraduatedStudents(){
        ArrayList<Student> rez = new ArrayList<Student>();
        for(Map.Entry<Student, Grade> ent: super.getAllStudentGrades().entrySet()){
            if(ent.getValue().getPartialScore() >= 3.0 && ent.getValue().getExamScore() >= 2.0){
                rez.add(ent.getKey());
            }
        }
        return rez;
    }
    public static class FullCourseBuilder extends CourseBuilder{
        public FullCourse build(){
            return new FullCourse(this);
        }
    }
}
