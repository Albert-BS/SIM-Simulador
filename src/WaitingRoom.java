import java.util.Iterator;
import java.util.Vector;

public class WaitingRoom {
    private final Vector<Client> clients = new Vector<>();

    private Chair[] destination;

    public void createConnection(Chair[] chairs) {
        destination = chairs;
    }

    public void addClient(Client client) {
        if(clients.size() < 4) {
            clients.add(client);
            client.setArrivalTime();
            StatisticsCollector.getInstance().notifyNewArrival();
        }
        else {
            StatisticsCollector.getInstance().notifyClientCouldNotEnter();
        }
    }

    public void checkWaitingRoom(){
        sortClientsByTime();
        for(Chair c : destination){
            Iterator<Client> i = clients.iterator();
            while (i.hasNext()){
                Client client = i.next();
                if(c.isEmpty()){
                    StatisticsCollector.getInstance().notifyClientInCuttingChair(Scheduler.getInstance().getCurrentTime() - client.getArrivalTime());
                    c.addClient(client);
                    i.remove();
                }
            }
        }
        int previousSize = clients.size();
        clients.removeIf(Client::checkLeaveTime);
        if(clients.size() != previousSize) {
            StatisticsCollector.getInstance().notifyClientLeft();
        }
    }

    private void sortClientsByTime() {
        clients.sort((o1, o2) -> {
            Float leaveTime1 = o1.getLeaveTime();
            Float leaveTime2 = o2.getLeaveTime();
            return leaveTime1.compareTo(leaveTime2);
        });
    }
}
