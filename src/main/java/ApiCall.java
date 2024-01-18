import lombok.extern.slf4j.Slf4j;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.bson.Document;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class ApiCall {
    static String connectionString = "mongodb://localhost:27017";
    static MongoClient mongoClient = MongoClients.create(connectionString);
    static MongoDatabase database = mongoClient.getDatabase("ItemList");

    public static void ApiDescription(String ItemID,int CurrentOffset, String itemName) throws IOException, InterruptedException {

        long startTime2 = System.currentTimeMillis();

        RestAssured.baseURI = "https://eapi.stalcraft.net/ru/auction/";


        AuctionList auctionList = given()
                .contentType(ContentType.JSON)
                .header("HostName", "eapi.stalcraft.net")
                .header("Authorization", "Bearer " + Main.token)
                .when()
                .get(ItemID + "/history?additional=true&limit=200&offset=" + CurrentOffset)
                .then()
                .extract().as(AuctionList.class);


        if (auctionList.getPrices().size() != 0) {

            MongoCollection<Document> collection = database.getCollection(itemName + "_" + "History");

            for (AuctionList.AuctionItem i : auctionList.getPrices()) {
                // if((i.getAdditional().getQlt() != 0)) {
                Document document = new Document()
                        .append("ItemName", itemName)
                        .append("price", i.getPrice())
                        .append("time", i.getTime())
                        .append("qlt", i.getAdditional().getQlt())
                        .append("ptn", i.getAdditional().getPtn());

                collection.insertOne(document);
                //    }
            }

        }
        else{System.out.println("ПИЗДЕЦ СЛОМАЛОСЬ" + " " + itemName);}

        long endTime2 = System.currentTimeMillis();
        long duration2 = endTime2 - startTime2;
        System.out.println("Время выполнения 1 предмета: " + duration2 + " миллисекунд");

    }
}
