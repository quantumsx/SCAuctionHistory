
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.bson.Document;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import static io.restassured.RestAssured.given;
import java.io.File;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class ApiCall {
    static String connectionString = "mongodb://localhost:27017";
    static MongoClient mongoClient = MongoClients.create(connectionString);
    static MongoDatabase database = mongoClient.getDatabase("ItemList");
    static MongoCollection<Document> collection = database.getCollection(Main.itemToFind + "_" + "History");
    private static final Object databaseLock = new Object();


    public static void ApiDescription(String ItemID,int CurrentOffset) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();

        RestAssured.baseURI = "https://eapi.stalcraft.net/ru/auction/";


        AuctionList auctionList = given()
                .contentType(ContentType.JSON)
                .header("HostName", "eapi.stalcraft.net")
                .header("Authorization", "Bearer " + Main.token)
                .when()
                .get(ItemID + "/history?additional=true&limit=200&offset=" + CurrentOffset)
                .then()
                .extract().as(AuctionList.class);

        int indexCounter = CurrentOffset / 200;

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Время выполнения: " + duration + " миллисекунд");

            for (AuctionList.AuctionItem i : auctionList.getPrices()) {
                    if((i.getAdditional().getQlt() != 0) & (i.getAdditional().getQlt() != 1)) {
                        Main.writeToArray(indexCounter, new Item(Main.itemToFind, i.getPrice(), i.getAdditional().getQlt(), i.getAdditional().getPtn(),  i.getTime()));
                    }
            }

    }
}
