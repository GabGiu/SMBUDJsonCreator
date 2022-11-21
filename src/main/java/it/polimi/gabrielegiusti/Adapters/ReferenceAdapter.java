package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.Reference;

import java.io.IOException;
import java.util.List;

public class ReferenceAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;
    private final Class<? super T> rawClass;

    private ReferenceAdapter(Class<? super T> rawClass, Gson gson) {
        this.gson = gson;
        this.rawClass = rawClass;
    }

    public static <T> TypeAdapter<T> get(Class<? super  T> rawClass, Gson gson){
        return new ReferenceAdapter<T>(rawClass, gson).nullSafe();
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {

        Reference record = (Reference) t;

        jsonWriter.beginObject();

        jsonWriter.name("author");
        gson.getAdapter(List.class).write(jsonWriter, record.getAuthor());

        jsonWriter.name("title");
        gson.getAdapter(String.class).write(jsonWriter, record.getTitle());

        jsonWriter.name("year");
        gson.getAdapter(Integer.class).write(jsonWriter, record.getYear());

        jsonWriter.name("DOI");
        gson.getAdapter(String.class).write(jsonWriter, record.getDOI());

        jsonWriter.endObject();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        String fieldName;

        Reference record = new Reference();

        jsonReader.beginObject();

        while (jsonReader.hasNext()){
            fieldName = jsonReader.nextName();
            switch (fieldName){
                case "author" -> record.setAuthor(gson.getAdapter(List.class).read(jsonReader));
                case "title" -> record.setTitle(gson.getAdapter(String.class).read(jsonReader));
                case "year" -> record.setYear(gson.getAdapter(Integer.class).read(jsonReader));
                case "DOI" -> record.setDOI(gson.getAdapter(String.class).read(jsonReader));
                default -> jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return (T) record;
    }
}
