package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.Subsection;

import java.io.IOException;
import java.util.List;

public class SubsectionAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;
    private final Class<? super T> rawClass;

    private SubsectionAdapter(Class<? super T> rawClass, Gson gson) {
        this.gson = gson;
        this.rawClass = rawClass;
    }

    public static <T> TypeAdapter<T> get(Class<? super  T> rawClass, Gson gson){
        return new SubsectionAdapter<T>(rawClass, gson).nullSafe();
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {

        Subsection record = new Subsection();

        jsonWriter.beginObject();

        jsonWriter.name("subsecNumber");
        gson.getAdapter(Integer.class).write(jsonWriter, record.getSubsecNumber());

        jsonWriter.name("subsecTitle");
        gson.getAdapter(String.class).write(jsonWriter, record.getSubsecTitle());

        jsonWriter.name("subsecText");
        gson.getAdapter(String.class).write(jsonWriter, record.getSubsecText());

        jsonWriter.name("paragraphs");
        gson.getAdapter(List.class).write(jsonWriter, record.getParagraphs());

        jsonWriter.endObject();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        String fieldName;

        Subsection record = new Subsection();

        jsonReader.beginObject();

        while (jsonReader.hasNext()){
            fieldName = jsonReader.nextName();
            switch (fieldName) {
                case "subsecTitle" -> record.setSubsecTitle(gson.getAdapter(String.class).read(jsonReader));
                case "subsecNumber" -> record.setSubsecNumber(gson.getAdapter(Integer.class).read(jsonReader));
                case "subsecText" -> record.setSubsecText(gson.getAdapter(String.class).read(jsonReader));
                case "paragraphs" -> record.setParagraphs(gson.getAdapter(List.class).read(jsonReader));
                default -> jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return (T) record;
    }
}
