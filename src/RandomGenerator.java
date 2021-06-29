public class RandomGenerator {
    private static RandomGenerator instance;

    public static RandomGenerator getInstance() {
        if(instance == null){
            instance = new RandomGenerator();
        }
        return instance;
    }

    public float createWaitingTime(){
        float min = 40;
        float max = 60;
        return (float) (min + Math.random()*(max - min));
    }

    public float createArrivalTime(){
        float min = 15;
        float max = 30;
        return (float) (min + Math.random()*(max - min));
    }

    public float createCuttingTime(){
        float min = 25;
        float max = 45;
        return (float) (min + Math.random()*(max - min));
    }

    public float createRinsingTime(){
        float min = 2;
        float max = 15;
        return (float) (min + Math.random()*(max - min));
    }
}
