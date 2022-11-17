package it.polimi.gabrielegiusti;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import it.polimi.gabrielegiusti.DBManager.Neo4jManager;
import it.polimi.gabrielegiusti.Handlers.JsonHandler;
import it.polimi.gabrielegiusti.Models.*;
import me.tongfei.progressbar.ProgressBar;
import org.neo4j.driver.Config;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.neo4j.driver.exceptions.value.Uncoercible;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        JsonHandler jsonHandler = new JsonHandler();
        Gson gson = new Gson();

        String uri = "neo4j+s://b535a102.databases.neo4j.io";
        String username = "neo4j";
        String password = "ggeeRNSFsyayFSp86dQqNpZ4wUt7vroXY_lZyMiH-OU";

        List<ScientificArticle> scientificArticles = new ArrayList<>();
        List<Author> authors = new ArrayList<>();

        ScientificArticle articleToStore;

        try (var app = new Neo4jManager(uri, username, password, Config.defaultConfig())){

            List<Record> allArticlesRecord = app.findAllArticles();
            List<Record> allAuthorsRecord = app.findAllAuthors();
            List<Record> allAffiliationsRecord = app.findAllAffiliations();
            List<Record> allLocationsRecord = app.findAllLocations();

            List<Object> generatedSectionsFromJson = jsonHandler.prepareReader("data/section.json").jsonToObject(List.class);
            List<Object> generatedPublicationsFromJson = jsonHandler.prepareReader("data/publication.json").jsonToObject(List.class);

            int sectionIndex = 0;
            int publicationIndex = 0;

            try (JsonReader jsonWriter = new JsonReader(new FileReader("data/authors.json"))) {
                List<Object> generatedAuthorFieldsFromJson = gson.fromJson(jsonWriter, List.class);

                try (ProgressBar progressBar = new ProgressBar("Test", 100)) {
                    progressBar.maxHint(allArticlesRecord.size());
                    for (Record record : allArticlesRecord) {
                        progressBar.step();
                        authors.clear();
                        articleToStore = new ScientificArticle();
                        articleToStore.setTitle(record.get("Article").get("title").asString());
                        articleToStore.setDOI(record.get("Article").get("DOI").asString());
                        articleToStore.setType(record.get("Article").get("type").asString());
                        if (!record.get("Article").get("year").isNull()) {
                            try {
                                articleToStore.setYear(record.get("Article").get("year").asInt());
                            } catch (Uncoercible e) {
                                articleToStore.setYear(Integer.parseInt(record.get("Article").get("year").asString()));
                            }
                        }

                        for (Record element : allAuthorsRecord) {

                            if (element.get("articleID").asList(Value::asInt).contains(record.get("Article").get("articleID").asInt())) {
                                for (Object authorFields : generatedAuthorFieldsFromJson) {
                                    LinkedTreeMap<String, Object> tmpAF = (LinkedTreeMap<String, Object>) authorFields;
                                    if (tmpAF.get("orcid").toString().equals(element.get("Author").get("authorOrcid").asString())) {
                                        authors.add(getAuthorWithAffiliationAndLocation(element, allAffiliationsRecord, allLocationsRecord, tmpAF));
                                    }
                                }
                            }
                        }
                        //peepopoopoo
                        articleToStore.setAuthors(authors);
                        articleToStore.setArticle_abstract("");
                        articleToStore.setSections((List<Section>) (generatedSectionsFromJson.get(sectionIndex)));
                        articleToStore.setPublicationDetails(getPublicationFromMap((LinkedTreeMap) generatedPublicationsFromJson.get(publicationIndex)));
                        articleToStore.setMetadata(null);
                        articleToStore.setBibliography(new Bibliography());
                        scientificArticles.add(articleToStore.deepCopy(jsonHandler));
                        sectionIndex++;
                        publicationIndex++;
                    }
                }
            }

            String postUrl = "http://localhost:8080/SMBUD-API/rest/provisioning/articles/createMultiple";
            URL url = new URL(postUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            String jsonInputString = jsonHandler.objectToJson(scientificArticles);

            try (OutputStream os = connection.getOutputStream()){
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))){

                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null){
                    response.append(responseLine.trim());
                }
                System.out.println(response);
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Author getAuthorWithAffiliationAndLocation(Record authorRecord, List<Record> affiliations
            , List<Record> locations, LinkedTreeMap<String, Object> authorFields){
        Author authorToStore = new Author();

        authorToStore.setName(authorRecord.get("Author").get("name").asString());
        authorToStore.setSurname(authorRecord.get("Author").get("surname").asString());
        authorToStore.setBio((String) authorFields.get("bio"));
        authorToStore.setDateOfBirth((String) authorFields.get("dateOfBirth"));
        authorToStore.setEmail((String) authorFields.get("email"));
        authorToStore.setAffiliation(getAffFromAuthorID(authorRecord, affiliations, locations));

        return authorToStore;
    }

    private static Author getAuthorWithAffiliationAndLocation(Record authorRecord, List<Record> affiliations
            , List<Record> locations){
        Author authorToStore = new Author();

        authorToStore.setName(authorRecord.get("Author").get("name").asString());
        authorToStore.setSurname(authorRecord.get("Author").get("surname").asString());
        authorToStore.setBio("");
        authorToStore.setDateOfBirth("");
        authorToStore.setEmail("");
        authorToStore.setAffiliation(getAffFromAuthorID(authorRecord, affiliations, locations));

        return authorToStore;
    }

    private static Affiliation getAffFromAuthorID(Record author, List<Record> affiliations, List<Record> locations){
        Affiliation affiliationToStore = new Affiliation();

        for (Record affiliation : affiliations) {
            if (author.get("Author").get("authorOrcid").asString().equals(affiliation.get("authorID").asString())) {
                affiliationToStore.setAffiliationName(affiliation.get("Affiliation").get("affiliationName").asString());
                affiliationToStore.setAffiliationDepartment(affiliation.get("Affiliation").get("Department").asString());
                affiliationToStore.setLocation(getLocFromAffiliationID(affiliation, locations));
                break;
            }
        }

        return affiliationToStore;
    }

    private static Location getLocFromAffiliationID(Record record, List<Record> locations) {
        Location locationToStore = new Location();

        for (Record location : locations) {
            if (location.get("affiliationID").asInt() == record.get("Affiliation").get("affiliationID").asInt()) {
                locationToStore.setZipcode(location.get("Location").get("zipcode").asInt());
                locationToStore.setCity(location.get("Location").get("city").asString());
                locationToStore.setCountry(location.get("Location").get("country").asString());
                break;
            }
        }
        return locationToStore;
    }

    private static PublicationDetails getPublicationFromMap(Map<String, Object> map) {
        return new PublicationDetails(
                (String) map.get("journalName"),
                (String) map.get("volume"),
                (int)Math.round((double)map.get("number")),
                (String) map.get("date"),
                (String) map.get("pages"),
                (String) map.get("editor"),
                (int) Math.round((double) map.get("numberOfCopiesSold"))
        );
    }
}
