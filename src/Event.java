public class Event implements Comparable<Event> {
    private final EventType type;
    private float time;

    public Event (EventType n, float currentSimTime){
        type = n;
        switch (type) {
            case newArrival -> time = RandomGenerator.getInstance().createArrivalTime() + currentSimTime;
            case finishCut -> time = RandomGenerator.getInstance().createCuttingTime() + currentSimTime;
            case finishRinse -> time = RandomGenerator.getInstance().createRinsingTime() + currentSimTime;
        }
    }

    @Override
    public int compareTo(Event e) {
        return Float.compare(this.time, e.time);
    }

    public float getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }

}
