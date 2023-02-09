import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PartialCourse extends Course {
    private PartialCourse(PartialCourseBuilder builder){
        super(builder);
    }
    public ArrayList<Student> getGraduatedStudents(){
        ArrayList<Student> rez = new ArrayList<Student>();
        for(Map.Entry<Student, Grade> ent: super.getAllStudentGrades().entrySet()){
            if(ent.getValue().getTotal() >= 5.0){
                rez.add(ent.getKey());
            }
        }
        return rez;
    }
    public static class PartialCourseBuilder extends CourseBuilder{
        public PartialCourse build(){
            return new PartialCourse(this);
        }
    }
}
