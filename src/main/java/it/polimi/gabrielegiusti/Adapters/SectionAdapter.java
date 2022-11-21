package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.Section;

import java.io.IOException;
import java.util.List;

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

        jsonWriter.name("sectionNumber");
        gson.getAdapter(Integer.class).write(jsonWriter, record.getSectionNumber());

        jsonWriter.name("sectionText");
        gson.getAdapter(String.class).write(jsonWriter, record.getSectionText());

        jsonWriter.name("subsections");
        gson.getAdapter(List.class).write(jsonWriter, record.getSubsection());

        jsonWriter.endObject();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        String fieldName;

        Section record = new Section();

        jsonReader.beginObject();

        while (jsonReader.hasNext()){
            fieldName = jsonReader.nextName();
            switch (fieldName) {
                case "sectionTitle" -> record.setSectionTitle(gson.getAdapter(String.class).read(jsonReader));
                case "sectionNumber" -> record.setSectionNumber(gson.getAdapter(Integer.class).read(jsonReader));
                case "sectionText" -> record.setSectionText(gson.getAdapter(String.class).read(jsonReader));
                case "subsection" -> record.setSubsection(gson.getAdapter(List.class).read(jsonReader));
                default -> jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return (T) record;
    }
}
