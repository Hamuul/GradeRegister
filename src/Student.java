public class Student extends User implements Comparable{
    private Parent mother, father;
    public Student(String firstName, String lastName){
        super(firstName, lastName);
    }
    public void setMother(Parent mother){
        this.mother = mother;
    }
    public void setFather(Parent father){
        this.father = father;
    }

    @Override
    public int compareTo(Object o) {
        Student s = (Student) o;
        return String.CASE_INSENSITIVE_ORDER.compare(s.toString(), this.toString());
    }
}
