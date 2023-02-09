public class UserFactory {
    public User getUser(String type, String firstName, String lastName){
        User u = null;
        switch(type) {
            case "Student":
                u = new Student(firstName, lastName);
                break;
            case "Parent":
                u = new Parent(firstName, lastName);
                break;
            case "Assistant":
                u = new Assistant(firstName, lastName);
                break;
            case "Teacher":
                u = new Teacher(firstName, lastName);
                break;
        }
        return u;
    }
}
