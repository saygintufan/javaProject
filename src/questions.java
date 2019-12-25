import org.json.JSONObject;

import java.io.IOException;

public class questions {

    public static String question;
    private static String getQuestion = helpers.mainUrl;

    public static String getQuestion(String question) throws IOException {
        String data =helpers.executePost(getQuestion, "");
        JSONObject json = helpers.readJsonFromUrl("http://saygintufan.com/chatbot/que.json");
        question = (String) json.get("question");
        return question;
    }
}
