import java.util.TreeSet;

public class BestPartialScore implements Strategy{
    public Student calculateScore(TreeSet<Grade> gradeTree){
        Student bestStudent = null;
        Double bestPartialScore = 0.0;
        for(Grade g: gradeTree){
            if(g.getPartialScore() > bestPartialScore){
                bestStudent = g.getStudent();
                bestPartialScore = g.getPartialScore();
            }
        }
        return bestStudent;
    }
}
