import java.util.TreeSet;

public class BestExamScore implements Strategy{
    public Student calculateScore(TreeSet<Grade> gradeTree){
        Student bestStudent = null;
        Double bestExamScore = 0.0;
        for(Grade g: gradeTree){
            if(g.getExamScore() > bestExamScore){
                bestStudent = g.getStudent();
                bestExamScore = g.getExamScore();
            }
        }
        return bestStudent;
    }
}
