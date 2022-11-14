package it.polimi.gabrielegiusti.Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.gabrielegiusti.Factories.ScientificArticleTypeAdapterFactory;

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
