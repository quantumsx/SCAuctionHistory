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
    public static String itemID = null;
    public static String itemToFind = "Призма";

    private static ArrayList<ArrayList<Item>> arraysList = new ArrayList<>();

    static int numberOfThreads = 10;
    public static int offsetMaxCount = 10000;

    static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    public static void main(String[] args) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();

        File myObjToken = new File("C:\\Users\\Artem\\IdeaProjects\\ScMonitorGit\\out\\artifacts\\ScMonitor_jar\\token.txt");
        Scanner sc = new Scanner(myObjToken);
        token = sc.nextLine();




        getItemID(itemToFind);
        createArrays(50);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        int baseOffset = 0;
        int offsetMax = offsetMaxCount / 200;

        List<CompletableFuture<Void>> futures = new CopyOnWriteArrayList<>();

        for (int i = 0; i < offsetMax; i++) {
            int currentOffset = baseOffset + i * 200;

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    ApiCall.ApiDescription(itemID, currentOffset);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, executorService).thenRunAsync(() -> {

            }, executorService);

            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        sendToDB(arraysList);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Время выполнения итоговое: " + duration + " миллисекунд");

    }


    public static void getItemID(String itemNameToFind) {

        String[] itemTypeList = new String[]{"Armor","Artifacts","Attachments","Containers","Misc","Other","Weapon"};

        MongoDatabase database = mongoClient.getDatabase("ItemList");

        for (String collectionName : itemTypeList) {
            MongoCollection<Document> collection = database.getCollection(collectionName);

            Document filter = new Document("ItemName", itemNameToFind);

            FindIterable<Document> result = collection.find(filter);

            for (Document document : result) {
                itemID = document.getString("ItemID");
                if (itemID != null) {
                    break;
                }
            }
            if (itemID != null) {
                break;
            }

        }
    }

    public static void sendToDB(ArrayList<ArrayList<Item>> arraysList){
        String connectionString = "mongodb://localhost:27017";

        MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase("ItemList");
            MongoCollection<Document> collection = database.getCollection(Main.itemToFind + "_" + "History");

        for (ArrayList<Item> currentArray : arraysList) {

            for (Item currentItem : currentArray) {
                Document document = new Document()
                        .append("ItemName", Main.itemToFind)
                        .append("price", currentItem.getPrice())
                        .append("time", currentItem.getTime())
                        .append("qlt", currentItem.getQlt())
                        .append("ptn", currentItem.getPtn());

                collection.insertOne(document);
            }
        }
    }
    public static void createArrays(int count) {
        for (int i = 0; i < count; i++) {
            arraysList.add(new ArrayList<>());
        }
    }

    public static ArrayList<Item> getArray(int index) {
        return arraysList.get(index);
    }

    public static void writeToArray(int index, Item item) {
        ArrayList<Item> arrayList = getArray(index);
        arrayList.add(item);
    }

}