import java.util.ArrayList;

public class Catalog implements Subject{
    private static Catalog obj = null;
    public ArrayList<Course> courseList= new ArrayList<Course>();
    public ArrayList<Parent> subscribedParents = new ArrayList<Parent>();
    private Catalog(){}
    public static Catalog getInstance(){
        if(obj == null)
            obj = new Catalog();
        return obj;
    }
    public void addCourse(Course course){
        courseList.add(course);
    }
    public void removeCourse(Course course){
        if(courseList.contains(course))
            courseList.remove(course);
    }
    public void addObserver(Observer o){
        subscribedParents.add((Parent) o);
    }
    public void removeObserver(Observer o){
        subscribedParents.remove((Parent) o);
    }
    public void notifyObservers(Grade g){
        for(Observer o: subscribedParents){
            o.update(new Notification(g.toString()));
        }
    }
}
