public class Source {

    public WaitingRoom waitingRoom;

    public void newArrival() {
        waitingRoom.addClient(new Client());
        Scheduler.getInstance().addEvent(EventType.newArrival);
    }

    public void createConnection(WaitingRoom w) {
        waitingRoom = w;
    }
}
