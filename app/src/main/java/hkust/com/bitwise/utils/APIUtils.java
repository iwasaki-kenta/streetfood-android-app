package hkust.com.bitwise.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class APIUtils {
    private static final String BASE_URL = "http://bwhacks.prashcr.xyz/api/v1/";
    private static final int ELEMENTS_PER_PAGE = 5;

    public static final String items() {
        return BASE_URL + "items";
    }

    public static final String selections() {
        return BASE_URL + "selections/";
    }

    public static final String selections(String foodChoice, int pageNum, JsonObject params) {
        String url = BASE_URL + "selections/" + foodChoice + "/?limit=" + Integer.toString(ELEMENTS_PER_PAGE) + "&skip="
                + Integer.toString(ELEMENTS_PER_PAGE * pageNum);
        if (params != null) {
            System.out.println(params.toString());
            url += "&query=" + params.toString();
        }
        return url;
    }

    public static final String popular(int pageNum) {
        String url = BASE_URL + "vendors?limit=" + Integer.toString(ELEMENTS_PER_PAGE) + "&skip="
                + Integer.toString(ELEMENTS_PER_PAGE * pageNum) + "&sort=-ordersCompleted";
        return url;
    }

    public static final String menuItems(List<String> menuIds) {
        String url = BASE_URL + "items?query=";
        JsonObject params = new JsonObject();
        params.add("_id", new Gson().toJsonTree(menuIds, new TypeToken<List<String>>() {
        }.getType()).getAsJsonArray());
        System.out.println(params.toString());
        url += params.toString();
        return url;
    }
}
