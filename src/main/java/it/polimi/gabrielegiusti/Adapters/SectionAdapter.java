package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.Section;

import java.io.IOException;
import java.util.Map;

public class SectionAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;
    private final Class<? super T> rawClass;

    private SectionAdapter(Class<? super T> rawClass, Gson gson) {
        this.gson = gson;
        this.rawClass = rawClass;
    }

    public static <T> TypeAdapter<T> get(Class<? super  T> rawClass, Gson gson){
        return new SectionAdapter<T>(rawClass, gson).nullSafe();
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {

        Section record = (Section) t;

        jsonWriter.beginObject();

        jsonWriter.name("sectionTitle");
        gson.getAdapter(String.class).write(jsonWriter, record.getSectionTitle());

        jsonWriter.name("paragraph");
        gson.getAdapter(String[].class).write(jsonWriter, record.getParagraph());

        jsonWriter.name("subsection");
        gson.getAdapter(String[].class).write(jsonWriter, record.getSubsection());

        jsonWriter.name("figures");
        gson.getAdapter(Map.class).write(jsonWriter, record.getFigures());

        jsonWriter.name("bibliography");
        gson.getAdapter(String.class).write(jsonWriter, record.getBibliography());

        jsonWriter.endObject();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
