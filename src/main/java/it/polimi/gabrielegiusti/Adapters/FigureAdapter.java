package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.Figure;

import java.io.IOException;
import java.util.Map;

public class FigureAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;
    private final Class<? super T> rawClass;

    private FigureAdapter(Class<? super T> rawClass, Gson gson) {
        this.gson = gson;
        this.rawClass = rawClass;
    }

    public static <T> TypeAdapter<T> get(Class<? super  T> rawClass, Gson gson){
        return new FigureAdapter<T>(rawClass, gson).nullSafe();
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {

        Figure record = (Figure) t;

        jsonWriter.beginObject();

        jsonWriter.name("figure_number");
        gson.getAdapter(Integer.class).write(jsonWriter, record.getFigureNumber());

        jsonWriter.name("image_URL");
        gson.getAdapter(String.class).write(jsonWriter, record.getImageURL());

        jsonWriter.name("caption");
        gson.getAdapter(String.class).write(jsonWriter, record.getCaption());

        jsonWriter.name("ref_text");
        gson.getAdapter(String.class).write(jsonWriter, record.getRef_text());

        jsonWriter.name("type");
        gson.getAdapter(Map.class).write(jsonWriter, record.getType());

        jsonWriter.endObject();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        String fieldName;

        Figure record = new Figure();

        jsonReader.beginObject();

        while (jsonReader.hasNext()){
            fieldName = jsonReader.nextName();
            switch (fieldName){
                case "figure_number" -> record.setFigureNumber(gson.getAdapter(Integer.class).read(jsonReader));
                case "image_URL", "imageUrl" -> record.setImageURL(gson.getAdapter(String.class).read(jsonReader));
                case "caption" -> record.setCaption(gson.getAdapter(String.class).read(jsonReader));
                case "type" -> record.setType(gson.getAdapter(Map.class).read(jsonReader));
                case "ref_text" -> record.setRef_text(gson.getAdapter(String.class).read(jsonReader));
                default -> jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return (T) record;
    }
}
