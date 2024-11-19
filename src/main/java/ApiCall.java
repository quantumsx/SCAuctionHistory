
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import static io.restassured.RestAssured.given;
import java.io.File;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class ApiCall {


    public static void ApiDescription(String ItemID,int CurrentOffset,String token) throws IOException, InterruptedException {

        RestAssured.baseURI = "https://eapi.stalcraft.net/ru/auction/";


        AuctionList auctionList = given()
                .contentType(ContentType.JSON)
                .header("HostName", "eapi.stalcraft.net")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(ItemID + "/history?additional=true&limit=200&offset=" + CurrentOffset)
                .then()
                .extract().as(AuctionList.class);





            for (AuctionList.AuctionItem i : auctionList.getPrices()) {
                synchronized (Main.itemList) {
                    if((i.getAdditional().getQlt() != 0) & (i.getAdditional().getQlt() != 1)){
                    Main.itemList.add(new Item(ItemID, i.getPrice(), qltIntoString(i.getAdditional().getQlt()), i.getAdditional().getPtn(), i.getTime()));
                    }
                }
            }

    }

    public static String qltIntoString(int qlt){

        switch(qlt) {
            case(0):
               return "Обычный";

            case(1):
                return "Необычный";

            case(2):
                return "Особый";

            case(3):
                return "Редкий";

            case(4):
                return "Искл";

            case(5):
                return "Лег";

            default:
                return "Не определено";

        }

    }

    }
