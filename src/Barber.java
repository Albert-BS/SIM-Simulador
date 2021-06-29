public class Barber {
    private Chair assigned;

    public Barber(){
        assigned = null;
    }

    public void setAssigned(Chair chair){
        assigned = chair;
    }

    public boolean isNotAssigned(){
        return assigned == null;
    }
}
