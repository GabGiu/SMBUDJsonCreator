package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.Bibliography;

import java.io.IOException;
import java.util.List;

public class BibliographyAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;
    private final Class<? super T> rawClass;

    private BibliographyAdapter(Class<? super T> rawClass, Gson gson) {
        this.gson = gson;
        this.rawClass = rawClass;
    }

    public static <T> TypeAdapter<T> get(Class<? super  T> rawClass, Gson gson){
        return new BibliographyAdapter<T>(rawClass, gson).nullSafe();
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {

        Bibliography record = new Bibliography();

        jsonWriter.beginObject();

        jsonWriter.name("references");
        gson.getAdapter(List.class).write(jsonWriter, record.getReferences());

        jsonWriter.endObject();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        String fieldName;

        Bibliography record = new Bibliography();

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            fieldName = jsonReader.nextName();
            switch (fieldName) {
                case "references" -> record.setReferences(gson.getAdapter(List.class).read(jsonReader));
            }
        }

        jsonReader.endObject();

        return (T) record;
    }
}
