package it.polimi.gabrielegiusti.Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.polimi.gabrielegiusti.Factories.ScientificArticleTypeAdapterFactory;

import java.lang.reflect.Type;

public class JsonHandler {

    Gson gson;

    public JsonHandler(){
        gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ScientificArticleTypeAdapterFactory())
                .create();
    }

    public String objectToJson(Object object){
        return gson.toJson(object);
    }

}
