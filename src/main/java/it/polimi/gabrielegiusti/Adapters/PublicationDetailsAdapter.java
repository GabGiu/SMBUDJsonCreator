package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.PublicationDetails;
import it.polimi.gabrielegiusti.Models.Section;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class PublicationDetailsAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;
    private final Class<? super T> rawClass;

    private PublicationDetailsAdapter(Class<? super T> rawClass, Gson gson) {
        this.gson = gson;
        this.rawClass = rawClass;
    }

    public static <T> TypeAdapter<T> get(Class<? super  T> rawClass, Gson gson){
        return new PublicationDetailsAdapter<T>(rawClass, gson).nullSafe();
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {

        PublicationDetails record = (PublicationDetails) t;

        jsonWriter.beginObject();

        jsonWriter.name("JournalName");
        gson.getAdapter(String.class).write(jsonWriter, record.getJournalName());

        jsonWriter.name("volume");
        gson.getAdapter(String.class).write(jsonWriter, record.getVolume());

        jsonWriter.name("number");
        gson.getAdapter(Integer.class).write(jsonWriter, record.getNumber());

        jsonWriter.name("date");
        gson.getAdapter(Date.class).write(jsonWriter, record.getDate());

        jsonWriter.name("pages");
        gson.getAdapter(Integer.class).write(jsonWriter, record.getPages());

        jsonWriter.name("editor");
        gson.getAdapter(String.class).write(jsonWriter, record.getEditor());

        jsonWriter.endObject();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
