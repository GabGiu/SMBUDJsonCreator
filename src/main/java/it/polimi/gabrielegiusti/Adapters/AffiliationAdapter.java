package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.Affiliation;
import it.polimi.gabrielegiusti.Models.Author;
import it.polimi.gabrielegiusti.Models.Location;

import java.io.IOException;
import java.util.Date;

public class AffiliationAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;
    private final Class<? super T> rawClass;

    private AffiliationAdapter(Class<? super T> rawClass, Gson gson) {
        this.gson = gson;
        this.rawClass = rawClass;
    }

    public static <T> TypeAdapter<T> get(Class<? super  T> rawClass, Gson gson){
        return new AffiliationAdapter<T>(rawClass, gson).nullSafe();
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {

        Affiliation record = (Affiliation) t;

        jsonWriter.beginObject();

        jsonWriter.name("affiliationName");
        gson.getAdapter(String.class).write(jsonWriter, record.getAffiliationName());

        jsonWriter.name("affiliationDepartment");
        gson.getAdapter(String.class).write(jsonWriter, record.getAffiliationDepartment());

        jsonWriter.name("location");
        gson.getAdapter(Location.class).write(jsonWriter, record.getLocation());

        jsonWriter.endObject();

    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
