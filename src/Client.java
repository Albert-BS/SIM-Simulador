public class Client {
    private float arrivalTime;
    private float leaveTime;
    private float cuttingStartTime;
    private float rinsingStartTime;

    public void setArrivalTime(){
        arrivalTime = Scheduler.getInstance().getCurrentTime();
        leaveTime = arrivalTime + RandomGenerator.getInstance().createWaitingTime();
    }

    public float getArrivalTime() {
        return arrivalTime;
    }

    public float getLeaveTime() {
        return leaveTime;
    }

    public boolean checkLeaveTime(){
        return Scheduler.getInstance().getCurrentTime() >= leaveTime;
    }

    public void setCuttingStartTime() {
        this.cuttingStartTime = Scheduler.getInstance().getCurrentTime();
    }

    public void setRinsingStartTime() {
        this.rinsingStartTime = Scheduler.getInstance().getCurrentTime();
    }

    public float getCuttingStartTime() {
        return cuttingStartTime;
    }

    public float getRinsingStartTime() {
        return rinsingStartTime;
    }
}
