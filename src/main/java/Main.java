import io.github.cdimascio.dotenv.Dotenv;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class Main {

    public static int maxOffset = 20000;
    static int numberOfThreads = 20;

    public static List<Item> itemList = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {

        ItemDB.addItemsToList();

        Dotenv dotenv = Dotenv.load();
        String discordToken = dotenv.get("DISCORD_TOKEN");
        String scFirstToken = dotenv.get("STALCRAFT_TOKEN_FIRST");
        String scSecondToken = dotenv.get("STALCRAFT_TOKEN_SECOND");

        TokenArray.createTokenArray(scFirstToken,scSecondToken);



        JDA jda = JDABuilder.createDefault(discordToken).build();

        jda.addEventListener(new DiscordBot());


    }
}