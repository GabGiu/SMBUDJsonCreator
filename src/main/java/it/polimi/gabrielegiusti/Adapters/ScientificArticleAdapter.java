package it.polimi.gabrielegiusti.Adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.gabrielegiusti.Models.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ScientificArticleAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;
    private final Class<? super T> rawClass;

    private ScientificArticleAdapter(Class<? super T> rawClass, Gson gson) {
        this.gson = gson;
        this.rawClass = rawClass;
    }

    public static <T> TypeAdapter<T> get(Class<? super  T> rawClass, Gson gson){
        return new ScientificArticleAdapter<T>(rawClass, gson).nullSafe();
    }


    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {

        ScientificArticle record = (ScientificArticle) t;

        jsonWriter.beginObject();

        jsonWriter.name("title");
        gson.getAdapter(String.class).write(jsonWriter, record.getTitle());

        jsonWriter.name("article_abstract");
        gson.getAdapter(String.class).write(jsonWriter, record.getArticle_abstract());

        jsonWriter.name("metadata");
        gson.getAdapter(Map.class).write(jsonWriter, record.getMetadata());

        jsonWriter.name("year");
        gson.getAdapter(Integer.class).write(jsonWriter, record.getYear());

        jsonWriter.name("type");
        gson.getAdapter(String.class).write(jsonWriter, record.getType());

        jsonWriter.name("DOI");
        gson.getAdapter(String.class).write(jsonWriter, record.getDOI());

        jsonWriter.name("authors");
        gson.getAdapter(List.class).write(jsonWriter, record.getAuthors());

        jsonWriter.name("publicationDetails");
        gson.getAdapter(PublicationDetails.class).write(jsonWriter, record.getPublicationDetails());

        jsonWriter.name("sections");
        gson.getAdapter(List.class).write(jsonWriter, record.getSections());

        jsonWriter.name("bibliography");
        gson.getAdapter(Bibliography.class).write(jsonWriter, record.getBibliography());

        jsonWriter.endObject();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        String fieldName;

        ScientificArticle record = new ScientificArticle();

        jsonReader.beginObject();

        while (jsonReader.hasNext()){
            fieldName = jsonReader.nextName();
            switch (fieldName){
                case "title" -> record.setTitle(gson.getAdapter(String.class).read(jsonReader));
                case "article_abstract" -> record.setArticle_abstract(gson.getAdapter(String.class).read(jsonReader));
                case "metadata" -> record.setMetadata(gson.getAdapter(Map.class).read(jsonReader));
                case "year" -> record.setYear(gson.getAdapter(Integer.class).read(jsonReader));
                case "type" -> record.setType(gson.getAdapter(String.class).read(jsonReader));
                case "DOI" -> record.setDOI(gson.getAdapter(String.class).read(jsonReader));
                case "authors" -> record.setAuthors(gson.getAdapter(List.class).read(jsonReader));
                case "publicationDetails" ->record.setPublicationDetails(gson.getAdapter(PublicationDetails.class).read(jsonReader));
                case "sections" -> record.setSections(gson.getAdapter(List.class).read(jsonReader));
                case "bibliography" -> record.setBibliography(gson.getAdapter(Bibliography.class).read(jsonReader));
                case "image" -> record.setImage(gson.getAdapter(Map.class).read(jsonReader));
            }
        }

        jsonReader.endObject();

        return (T) record;
    }
}
