import org.json.JSONObject;

import java.io.IOException;

public class questions {

    static String question;
    private static String getQuestion = helpers.mainUrl;

    public static String getQuestion(String question) throws IOException {
        String data =helpers.executePost(getQuestion, "");
        JSONObject json = helpers.readJsonFromUrl("https://trakyaunichatbot.firebaseio.com/WorkHard/first.json");
        question = (String) json.get("message");
        return question;

    }
}
