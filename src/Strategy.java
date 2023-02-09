import java.util.TreeSet;

public interface Strategy {
    Student calculateScore(TreeSet<Grade> gradeTree);
}