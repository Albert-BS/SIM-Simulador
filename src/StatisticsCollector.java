import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StatisticsCollector {
    private static StatisticsCollector instance;
    private int clients = 0;
    private int clientsCouldNotEnter = 0;
    private int clientsLeft = 0;
    private int clientsCut = 0;
    private int clientsRinsed = 0;
    private float meanWaitingTime = 0;
    private float meanCuttingTime = 0;
    private float meanRinsingTime = 0;

    public static StatisticsCollector getInstance(){
        if(instance == null){
            instance = new StatisticsCollector();
        }
        return instance;
    }

    public void notifyNewArrival(){
        ++clients;
    }

    public void notifyClientCouldNotEnter(){
        ++clientsCouldNotEnter;
    }

    public void notifyClientLeft(){
        ++clientsLeft;
    }

    public void notifyClientInCuttingChair(float waitingTime){
        int clientsDidNotLeave = clients - clientsLeft;
        float totalWaitingTime = meanWaitingTime * (clientsDidNotLeave - 1); //no contem aquest client
        totalWaitingTime += waitingTime;
        meanWaitingTime = totalWaitingTime/clientsDidNotLeave;
    }

    public void notifyFinishCut(float cuttingTime){
        float totalCuttingTime = meanCuttingTime * clientsCut;
        totalCuttingTime += cuttingTime;
        ++clientsCut;
        meanCuttingTime = totalCuttingTime/clientsCut;
    }

    public void notifyFinishRinse(float rinsingTime){
        float totalRinsingTime = meanRinsingTime * clientsRinsed;
        totalRinsingTime += rinsingTime;
        ++clientsRinsed;
        meanRinsingTime = totalRinsingTime/clientsRinsed;
    }

    public void writeStatistics(){
        try {
            File resDir = new File("results");
            Files.createDirectories(Path.of(resDir.getAbsolutePath()));
            int numRuns = resDir.list().length;
            File results = new File("results\\run" + numRuns + ".txt");

            FileWriter fileWriter = new FileWriter(results.getAbsolutePath());

            fileWriter.write("Nombre de clients que han entrat a la barberia: " + clients);
            fileWriter.write("\n");

            fileWriter.write("Nombre de clients no han pogut entrar perquè la sala d'espera era plena: " + clientsCouldNotEnter);
            fileWriter.write("\n");

            fileWriter.write("Nombre de clients que se n'han anat: " + clientsLeft);
            fileWriter.write("\n");

            fileWriter.write("Nombre de clients als que s'ha tallat el pèl: " + clientsCut);
            fileWriter.write("\n");

            fileWriter.write("Nombre de clients als que s'ha rentat el pèl: " + clientsRinsed);
            fileWriter.write("\n");

            fileWriter.write("Temps mitjà que han estat a la sala d'espera els que s'han quedat: " + meanWaitingTime);
            fileWriter.write("\n");

            fileWriter.write("Temps mitjà que ha trigat el barber a tallar-los-hi els cabells: " + meanCuttingTime);
            fileWriter.write("\n");

            fileWriter.write("Temps mitjà que ha trigat el barber a rentar-los-hi els cabells: " + meanRinsingTime);

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
