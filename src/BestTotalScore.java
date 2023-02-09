import java.util.TreeSet;

public class BestTotalScore implements Strategy{
    public Student calculateScore(TreeSet<Grade> gradeTree){
        Student bestStudent = null;
        Double bestTotalScore = 0.0;
        for(Grade g: gradeTree){
            if(g.getTotal() > bestTotalScore){
                bestStudent = g.getStudent();
                bestTotalScore = g.getTotal();
            }
        }
        return bestStudent;
    }
}
