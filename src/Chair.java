public class Chair {

    private Client client;
    private Barber barber;
    private final Type type;
    private Chair[] destination;

    public Chair(Type type) {
        client = null;
        barber = null;
        this.type = type;
    }

    public void createConnection(Chair[] chairs) {
        destination = chairs;
    }

    public boolean isEmpty() {
        return client == null;
    }

    public boolean isNotAssigned() {
        return barber == null;
    }

    public void addClient(Client client) {
        this.client = client;
        checkAddEvent();
    }

    public void assignBarber(Barber barber){
        this.barber = barber;
        this.barber.setAssigned(this);
        checkAddEvent();
    }

    public void finishCut() {
        StatisticsCollector.getInstance().notifyFinishCut(Scheduler.getInstance().getCurrentTime() - client.getCuttingStartTime());
        for(Chair c : destination) {
            if(c.isEmpty()) {
                c.addClient(client);
                client = null;
                barber.setAssigned(null);
                barber = null;
                return;
            }
        }
    }

    public void finishRinse() {
        StatisticsCollector.getInstance().notifyFinishRinse(Scheduler.getInstance().getCurrentTime() - client.getRinsingStartTime());
        client = null;
        barber.setAssigned(null);
        barber = null;
    }

    private void checkAddEvent(){
        if(client != null && barber != null){
            switch (type) {
                case Cut ->{
                    Scheduler.getInstance().addEvent(EventType.finishCut);
                    client.setCuttingStartTime();
                }
                case Rinse ->{
                    Scheduler.getInstance().addEvent(EventType.finishRinse);
                    client.setRinsingStartTime();
                }
            }
        }
    }
}
