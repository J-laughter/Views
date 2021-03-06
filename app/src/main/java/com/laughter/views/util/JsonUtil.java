package com.laughter.views.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.laughter.views.model.Article;

import java.util.List;

public class JsonUtil {

    public static int getErrorCode(String response) {
        int errorCode = -1;
        try{
            JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
            errorCode = jsonObj.get("errorCode").getAsInt();
        }catch (Exception e){
            e.printStackTrace();
        }
        return errorCode;
    }

    public static List<Article> getArticles(String response) {
        List<Article> articles = null;
        try {
            JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
            JsonObject data = jsonObj.get("data").getAsJsonObject();
            if (data.get("datas").getClass() == JsonArray.class){
                JsonArray datas = data.get("datas").getAsJsonArray();
                articles = new Gson().fromJson(datas, new TypeToken<List<Article>>(){}.getType());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return articles;
    }
}
