import java.lang.reflect.Array;
import java.util.*;

public abstract class Course {
    private String courseName;
    private Teacher titular;

    private HashSet<Assistant> astTree;

    public void setGradeTree(TreeSet<Grade> gradeTree) {
        this.gradeTree = gradeTree;
    }
    private TreeSet<Grade> gradeTree;

    public HashMap<String, Group> getGrupe() {
        return grupe;
    }

    private HashMap<String, Group> grupe;
    private int credit;

    public Course(CourseBuilder builder){
        this.courseName = builder.courseName;
        this.titular = builder.titular;
        this.astTree = builder.astTree;
        this.gradeTree = builder.gradeTree;
        this.grupe = builder.grupe;
        this.credit = builder.credit;
    }
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Teacher getTitular() {
        return titular;
    }

    public void setTitular(Teacher titular) {
        this.titular = titular;
    }

    public HashSet<Assistant> getAstTree() {
        return astTree;
    }

    public void setAstTree(HashSet<Assistant> astTree) {
        this.astTree = astTree;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
    public TreeSet<Grade> getGradeTree() {
        return gradeTree;
    }
    public void modifyGradeTree(Grade g){
        for(Grade aux: gradeTree) {
            if(aux.getStudent().toString().equals(g.getStudent().toString())){
                aux.setPartialScore(g.getPartialScore());
            }
        }
    }
    public abstract static class CourseBuilder{
        private String courseName;
        private Teacher titular;
        private HashSet<Assistant> astTree;
        private TreeSet<Grade> gradeTree;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public Teacher getTitular() {
            return titular;
        }

        public void setTitular(Teacher titular) {
            this.titular = titular;
        }

        public HashSet<Assistant> getAstTree() {
            return astTree;
        }

        public void setAstTree(HashSet<Assistant> astTree) {
            this.astTree = astTree;
        }

        public TreeSet<Grade> getGradeTree() {
            return gradeTree;
        }

        public void setGradeTree(TreeSet<Grade> gradeTree) {
            this.gradeTree = gradeTree;
        }

        public HashMap<String, Group> getGrupe() {
            return grupe;
        }

        public void setGrupe(HashMap<String, Group> grupe) {
            this.grupe = grupe;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        private HashMap<String, Group> grupe;
        private int credit;

        public CourseBuilder(){};

        public CourseBuilder courseName(String courseName){
            this.courseName = courseName;
            return this;
        }
        public CourseBuilder titular(Teacher titular){
            this.titular = titular;
            return this;
        }
        public CourseBuilder astTree(HashSet<Assistant> astTree){
            this.astTree = astTree;
            return this;
        }
        public CourseBuilder gradeTree(TreeSet<Grade> gradeTree){
            this.gradeTree = gradeTree;
            return this;
        }
        public CourseBuilder grupe(HashMap<String, Group> grupe){
            this.grupe = grupe;
            return this;
        }
        public CourseBuilder credit(int credit){
            this.credit = credit;
            return this;
        }
        public CourseBuilder strategy(Strategy strat){
            titular.setScoreStrat(strat);
            return this;
        }
    }
    public void addAssistant(String ID, Assistant ast){
        if(!astTree.contains(ast))
            astTree.add(ast);
        grupe.get(ID).setAst(ast);

    }
    public void addStudent(String ID, Student student){
            grupe.get(ID).add(student);
    }
    public void addGroup(Group group){
        if(!grupe.containsKey(group.getID())){
            grupe.put(group.getID(), group);
        }
    }
    public void addGroup(String ID, Assistant ast){
        Group group = new Group(ID, ast);
        if(!grupe.containsKey(group.getID())){
            grupe.put(group.getID(), group);
        }
    }
    public void addGroup(String ID, Assistant ast, Comparator<Student> comp){
        Group group = new Group(ID, ast, comp);
        if(!grupe.containsKey(group.getID())){
            grupe.put(group.getID(), group);
        }
    }
    public Grade getGrade(Student student){
        for(Grade g: gradeTree){
            if(g.getStudent().equals(student)){
                return g;
            }
        }
        return null;
    }
    public void addGrade(Grade grade){
        gradeTree.add(grade);
    }
    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> rez = new ArrayList<Student>();
        for(Map.Entry<String, Group> ent: grupe.entrySet()){
            Group g = ent.getValue();
            for(Student s : g){
                rez.add(s);
            }
        }
        return rez;
    }
    public HashMap<Student, Grade> getAllStudentGrades(){
        HashMap<Student, Grade> situatie = new HashMap<Student, Grade>();
        for(Grade g: gradeTree){
            situatie.put(g.getStudent(), g);
        }
        return situatie;
    }
    public abstract ArrayList<Student> getGraduatedStudents();
    public Student getBestStudent(){
        return getTitular().getScoreStrat().calculateScore(this.getGradeTree());
    }
    private Snapshot snapshot = new Snapshot();
    private class Snapshot{
        public TreeSet<Grade> getBackup() {
            return backup = (TreeSet<Grade>)getGradeTree().clone();
        }

        public void setBackup(TreeSet<Grade> backup) {
            this.backup = backup;
        }

        private TreeSet<Grade> backup = new TreeSet<>();
    }
    public void makeBackup(){
        TreeSet<Grade> ts = snapshot.getBackup();
        ts = (TreeSet<Grade>)getGradeTree().clone();
    }
    public void undo(){
        setGradeTree(snapshot.getBackup());
    }
}

