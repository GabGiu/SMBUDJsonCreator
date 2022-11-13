package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.Affiliation;
import it.polimi.gabrielegiusti.Models.Author;

import java.io.IOException;
import java.util.Date;

public class AuthorAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;
    private final Class<? super T> rawClass;

    private AuthorAdapter(Class<? super T> rawClass, Gson gson) {
        this.gson = gson;
        this.rawClass = rawClass;
    }

    public static <T> TypeAdapter<T> get(Class<? super  T> rawClass, Gson gson){
        return new AuthorAdapter<T>(rawClass, gson).nullSafe();
    }


    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {

        Author record = (Author) t;

        jsonWriter.beginObject();

        jsonWriter.name("name");
        gson.getAdapter(String.class).write(jsonWriter, record.getName());

        jsonWriter.name("surname");
        gson.getAdapter(String.class).write(jsonWriter, record.getSurname());

        jsonWriter.name("email");
        gson.getAdapter(String.class).write(jsonWriter, record.getEmail());

        jsonWriter.name("bio");
        gson.getAdapter(String.class).write(jsonWriter, record.getBio());

        jsonWriter.name("dateOfBirth");
        gson.getAdapter(Date.class).write(jsonWriter, record.getDateOfBirth());

        jsonWriter.name("affiliation");
        gson.getAdapter(Affiliation.class).write(jsonWriter, record.getAffiliation());

        jsonWriter.endObject();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
