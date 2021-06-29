import java.util.Vector;

public class Scheduler {
    private static Scheduler instance;
    private float currentTime = 0;
    private final Vector<Event> eventList = new Vector<>();
    private final Source source = new Source();
    private final Chair[] cuttingChairs = new Chair[3];
    private final Chair[] rinsingChairs = new Chair[2];
    private final WaitingRoom waitingRoom = new WaitingRoom();
    private final Vector<Barber> barbers = new Vector<>();

    public static Scheduler getInstance() {
        if(instance == null)
            instance = new Scheduler();
        return instance;
    }

    private Scheduler() {
        for(int i = 0; i < cuttingChairs.length; ++i) {
            cuttingChairs[i] = new Chair(Type.Cut);
        }
        for(int i = 0; i < rinsingChairs.length; ++i) {
            rinsingChairs[i] = new Chair(Type.Rinse);
        }

        source.createConnection(waitingRoom);
        waitingRoom.createConnection(cuttingChairs);

        for (Chair cuttingChair : cuttingChairs) {
            cuttingChair.createConnection(rinsingChairs);
        }
        for (Chair rinsingChair : rinsingChairs) {
            rinsingChair.createConnection(null);
        }

        barbers.add(new Barber());
    }

    public void run() {
        addEvent(EventType.newArrival);
        while (currentTime <= 720 && !eventList.isEmpty()) {
            eventList.sort(Event::compareTo);
            Event currentEvent = eventList.firstElement();
            currentTime = currentEvent.getTime();
            executeEvent(currentEvent);
            eventList.remove(currentEvent);
            waitingRoom.checkWaitingRoom();

            if(currentTime >= 180 && barbers.size() == 1){
                barbers.add(new Barber());
            }
            if(currentTime >= 420 && barbers.size() == 2){
                barbers.add(new Barber());
            }
            assignBarbers();
        }
        StatisticsCollector.getInstance().writeStatistics();
    }

    private void assignBarbers() {
        for(Chair c : rinsingChairs){
            for(Barber b : barbers){
                if(!c.isEmpty() && c.isNotAssigned() && b.isNotAssigned()){
                    c.assignBarber(b);
                }
            }
        }
        for(Chair c : cuttingChairs){
            for(Barber b : barbers){
                if(!c.isEmpty() && c.isNotAssigned() && b.isNotAssigned()){
                    c.assignBarber(b);
                }
            }
        }
        for(Chair c : cuttingChairs){
            for(Barber b : barbers){
                if(b.isNotAssigned()){
                    c.assignBarber(b);
                }
            }
        }
        for(Chair c : rinsingChairs){
            for(Barber b : barbers){
                if(b.isNotAssigned()){
                    c.assignBarber(b);
                }
            }
        }
    }

    public void addEvent(EventType eventType) {
        eventList.add(new Event(eventType, currentTime));
    }

    public void executeEvent(Event event) {
        switch (event.getType()) {
            case newArrival:
                source.newArrival();
                break;
            case finishCut:
                for(Chair cc : cuttingChairs) {
                    if(!cc.isEmpty() && !cc.isNotAssigned()) {
                        cc.finishCut();
                        break;
                    }
                }

                break;
            case finishRinse:
                for(Chair rc : rinsingChairs) {
                    if(!rc.isEmpty() && !rc.isNotAssigned()) {
                        rc.finishRinse();
                        break;
                    }
                }
                break;
            default:
                break;
        }
    }

    public float getCurrentTime() {
        return currentTime;
    }
}
