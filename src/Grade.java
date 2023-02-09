public class Grade implements Comparable, Cloneable{
    private Double partialScore = 0.0, examScore = 0.0;
    private Student student;
    private String course;
    public Grade(){};
    public Grade(Double partialScore, Double examScore, Student student, String course){
        this.partialScore = partialScore;
        this.examScore = examScore;
        this.student = student;
        this.course = course;
    }
    public Double getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    public Double getTotal(){
        return partialScore + examScore;
    }
    public String toString(){
        return getStudent() + " | " + getCourse() + " | " + getPartialScore() + " | " + getExamScore();
    }
    @Override
    public int compareTo(Object o) {
        Grade obj = (Grade) o;
        return Double.compare(((Grade) o).getTotal(), this.getTotal());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Double num1 = Double.valueOf(partialScore);
        Double num2 = Double.valueOf(examScore);
        String[] nm = student.toString().split(" ");
        Student s = new Student(nm[0], nm[1]);
        String deepCopyCourse = new String(course);
        return new Grade(num1, num2, s, deepCopyCourse);
    }
}
