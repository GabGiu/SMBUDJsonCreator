package it.polimi.gabrielegiusti.Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import it.polimi.gabrielegiusti.Factories.ScientificArticleTypeAdapterFactory;
import it.polimi.gabrielegiusti.Models.ScientificArticle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JsonHandler {

    private Gson gson;

    private JsonReader jsonReader;

    public JsonHandler() {
        gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ScientificArticleTypeAdapterFactory())
                .create();

    }

    public JsonHandler prepareReader(String filename){
        try {
            jsonReader = new JsonReader(new FileReader(filename));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return this;
    }

    public String objectToJson(Object object){
        return gson.toJson(object);
    }

    public List<Object> jsonToObject(Type type){
        return gson.fromJson(jsonReader, type);
    }

    public Object jsonToObject(String json, Type type){
        return gson.fromJson(json, type);
    }

    public  <T extends Object> T deepCopy(Class<T> clazz, T object){

        return (T) jsonToObject(objectToJson(object), clazz);
    }

}
