import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TokenArray {

    private static List<String> tokenList;
    private static int currentIndex;

    public static void createTokenArray(String first_token, String second_token) throws FileNotFoundException {
        tokenList = new ArrayList<>();

        tokenList.add(first_token);
        tokenList.add(second_token);


        currentIndex = 0;
    }

    public static String getNewToken() {
        String currentToken = tokenList.get(currentIndex);

        currentIndex = (currentIndex + 1) % tokenList.size();
        return currentToken;
    }
}
