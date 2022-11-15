package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.Paragraph;

import java.io.IOException;

public class ParagraphAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;
    private final Class<? super T> rawClass;

    private ParagraphAdapter(Class<? super T> rawClass, Gson gson) {
        this.gson = gson;
        this.rawClass = rawClass;
    }

    public static <T> TypeAdapter<T> get(Class<? super  T> rawClass, Gson gson){
        return new ParagraphAdapter<T>(rawClass, gson).nullSafe();
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {

        Paragraph record = new Paragraph();

        jsonWriter.beginObject();

        jsonWriter.name("paragraphTitle");
        gson.getAdapter(String.class).write(jsonWriter, record.getParagraphTitle());

        jsonWriter.name("paragraphText");
        gson.getAdapter(String.class).write(jsonWriter, record.getParagraphText());

        jsonWriter.endObject();

    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        String fieldName;

        Paragraph record = new Paragraph();

        jsonReader.beginObject();

        while (jsonReader.hasNext()){
            fieldName = jsonReader.nextName();
            switch (fieldName) {
                case "paragraphTitle" -> record.setParagraphTitle(gson.getAdapter(String.class).read(jsonReader));
                case "paragraphText" -> record.setParagraphText(gson.getAdapter(String.class).read(jsonReader));
                default -> jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return (T) record;
    }
}
