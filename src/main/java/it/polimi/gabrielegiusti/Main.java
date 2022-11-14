package it.polimi.gabrielegiusti;

import it.polimi.gabrielegiusti.DBManager.Neo4jManager;
import it.polimi.gabrielegiusti.Handlers.JsonHandler;
import it.polimi.gabrielegiusti.Models.*;
import org.neo4j.driver.Config;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.neo4j.driver.exceptions.value.Uncoercible;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        String uri = "neo4j+s://b535a102.databases.neo4j.io";
        String username = "neo4j";
        String password = "ggeeRNSFsyayFSp86dQqNpZ4wUt7vroXY_lZyMiH-OU";

        List<ScientificArticle> scientificArticles = new ArrayList<>();
        List<Author> authors = new ArrayList<>();

        ScientificArticle articleToStore = null;
        Author authorToStore = null;
        Affiliation affiliationToStore = null;
        Location locationToStore = null;

        try (var app = new Neo4jManager(uri, username, password, Config.defaultConfig())){

            List<Record> allArticlesRecord = app.findAllArticles();
            List<Record> allAuthorsRecord = app.getChunksOfAuthors(0);
            List<Record> allAffiliationsRecord = app.getChunksOfAffiliations(0);
            List<Record> allLocationsRecord = app.getChunksOfLocations(0);

            int totalNumberOfAuthors = app.getNumberOfNodes("Author");
            int totalNumberOfAffiliation = app.getNumberOfNodes("Affiliation");
            int totalNumberOfLocations = app.getNumberOfNodes("Location");
            int authorSkip = 0;
            int affiliationSkip = 0;
            int locationSkip = 0;

            double h = 0;
            double f = allArticlesRecord.size();
            for (Record record : allArticlesRecord){
                double perc = ((h/f)*100);
                System.out.println(Math.floor(perc)+"%");
                authors.clear();
                articleToStore = new ScientificArticle();
                articleToStore.setTitle(record.get("Article").get("title").asString());
                articleToStore.setDOI(record.get("Article").get("DOI").asString());
                articleToStore.setType(record.get("Article").get("type").asString());
                if (!record.get("Article").get("year").isNull()){
                    try {
                        articleToStore.setYear(record.get("Article").get("year").asInt());
                    } catch (Uncoercible e){
                        articleToStore.setYear(Integer.parseInt(record.get("Article").get("year").asString()));
                    }

                }

                for (int i = 0; i < allAuthorsRecord.size(); i++){
                    if (allAuthorsRecord.get(i).get("articleID").asList(Value::asInt).contains(record.get("Article").get("articleID").asInt())){
                        authorToStore = new Author();
                        authorToStore.setName(allAuthorsRecord.get(i).get("Author").get("name").asString());
                        authorToStore.setSurname(allAuthorsRecord.get(i).get("Author").get("surname").asString());
                        authorToStore.setBio("");
                        authorToStore.setDateOfBirth(null);
                        authorToStore.setEmail("");

                        for (int j = 0; j < allAffiliationsRecord.size(); j++){
                            if (Objects.equals(allAffiliationsRecord.get(j).get("authorID").asString(), allAuthorsRecord.get(i).get("Author").get("authorOrcid").asString())){

                                affiliationToStore = new Affiliation();
                                affiliationToStore.setAffiliationName(allAffiliationsRecord.get(j).get("Affiliation").get("affiliationName").asString());
                                affiliationToStore.setAffiliationDepartment(allAffiliationsRecord.get(j).get("Affiliation").get("department").asString());

                                for (int k = 0; k < allLocationsRecord.size(); k++){
                                    if (allLocationsRecord.get(k).get("affiliationID").asInt() == allAffiliationsRecord.get(j).get("Affiliation").get("affiliationID").asInt()){
                                        locationToStore = new Location();
                                        locationToStore.setZipcode(allLocationsRecord.get(k).get("Location").get("zipcode").asInt());
                                        locationToStore.setCity(allLocationsRecord.get(k).get("Location").get("city").asString());
                                        locationToStore.setCountry(allLocationsRecord.get(k).get("Location").get("country").asString());
                                        break;
                                    }
                                    if (k == allLocationsRecord.size() - 2 && totalNumberOfLocations > locationSkip){
                                        k = 0;
                                        locationSkip += 999;
                                        allLocationsRecord = Stream.concat(allLocationsRecord.stream(), app.getChunksOfLocations(locationSkip).stream()).toList();
                                    }
                                }
                                affiliationToStore.setLocation(locationToStore);
                                break;
                            }
                            if (j == allAffiliationsRecord.size() - 2 && totalNumberOfAffiliation > affiliationSkip){
                                j = 0;
                                affiliationSkip += 999;
                                allAffiliationsRecord = Stream.concat(allAffiliationsRecord.stream(), app.getChunksOfAffiliations(affiliationSkip).stream()).toList();
                            }
                        }
                        authorToStore.setAffiliation(affiliationToStore);
                        authors.add(authorToStore);
                    }
                    if (i == allAuthorsRecord.size() - 2 && totalNumberOfAuthors > authorSkip){
                        i = 0;
                        authorSkip += 999;
                        allAuthorsRecord = Stream.concat(allAuthorsRecord.stream(), app.getChunksOfAuthors(authorSkip).stream()).toList();
                    }


                }

                articleToStore.setAuthors(authors);
                articleToStore.setArticle_abstract("");
                articleToStore.setSection(new Section());
                articleToStore.setPublicationDetails(new PublicationDetails());
                scientificArticles.add(articleToStore);
                h++;
            }

            JsonHandler jsonHandler = new JsonHandler();

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
                String responseLine = null;
                while ((responseLine = br.readLine()) != null){
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
