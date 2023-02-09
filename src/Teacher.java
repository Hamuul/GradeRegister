public class Teacher extends User implements Element{
    public Strategy getScoreStrat() {
        return scoreStrat;
    }

    public void setScoreStrat(Strategy scoreStrat) {
        this.scoreStrat = scoreStrat;
    }

    private Strategy scoreStrat;
    public Teacher(String firstName, String lastName){
        super(firstName, lastName);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
