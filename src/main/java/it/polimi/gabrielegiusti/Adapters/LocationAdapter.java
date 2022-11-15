package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.Location;

import java.io.IOException;

public class LocationAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;
    private final Class<? super T> rawClass;

    private LocationAdapter(Class<? super T> rawClass, Gson gson) {
        this.gson = gson;
        this.rawClass = rawClass;
    }

    public static <T> TypeAdapter<T> get(Class<? super  T> rawClass, Gson gson){
        return new LocationAdapter<T>(rawClass, gson).nullSafe();
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {

        Location record = (Location) t;

        jsonWriter.beginObject();

        jsonWriter.name("zipcode");
        gson.getAdapter(Integer.class).write(jsonWriter, record.getZipcode());

        jsonWriter.name("city");
        gson.getAdapter(String.class).write(jsonWriter, record.getCity());

        jsonWriter.name("country");
        gson.getAdapter(String.class).write(jsonWriter, record.getCountry());

        jsonWriter.endObject();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
