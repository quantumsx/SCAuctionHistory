import com.mongodb.client.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

import org.bson.Document;

public class Main {

    public static String token;
    // public static String itemID = null;
    // public static String itemToFind = "Призма";

    private static ArrayList<ArrayList<Item>> arraysList = new ArrayList<>();

    static int numberOfThreads = 50;
    public static int offsetMaxCount = 1000;

    static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    static MongoDatabase database = mongoClient.getDatabase("ItemList");

    private static final int THREAD_POOL_SIZE = 12;

    public static void main(String[] args) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();

        File myObjToken = new File("C:\\Users\\Artem\\IdeaProjects\\ScMonitorGit\\out\\artifacts\\ScMonitor_jar\\token.txt");
        Scanner sc = new Scanner(myObjToken);
        token = sc.nextLine();


        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        MongoCollection<Document> collection = database.getCollection("Artifacts");
        FindIterable<Document> documents = collection.find();

        for (Document document : documents) {
            String itemID = document.getString("ItemID");
            String itemName = document.getString("ItemName");

            executorService.execute(() -> {

                for (int i = 0; i < offsetMaxCount / 200; i++) {
                    int currentOffset = i * 200;
                    try {
                        ApiCall.ApiDescription(itemID, currentOffset, itemName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            });

        }

        executorService.shutdown();
        // Дождитесь завершения всех задач
        while (!executorService.isTerminated()) {
            // ожидание
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Время выполнения итоговое: " + duration + " миллисекунд");
    }


}