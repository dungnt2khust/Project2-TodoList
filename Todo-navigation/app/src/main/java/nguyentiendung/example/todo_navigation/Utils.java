package nguyentiendung.example.todo_navigation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {
    public static Gson gson;

    public static Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }
}
