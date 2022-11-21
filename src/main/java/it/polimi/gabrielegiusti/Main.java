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

    public static void main(String[] args) throws FileNotFoundException{

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
            List<Object> generatedBibliographyFromJson = jsonHandler.prepareReader("data/bibliography.json").jsonToObject(List.class);
            List<Object> generatedFiguresFromJson =  jsonHandler.prepareReader("data/figure.json").jsonToObject(List.class);

            int sectionIndex = 0;
            int publicationIndex = 0;
            int bibliographyIndex = 0;
            int metaIndex = 0;
            int figureIndex = 0;

            JsonReader jsonReader = createJsonReader("data/authors.json");
            List<Object> generatedAuthorFieldsFromJson = gson.fromJson(jsonReader, List.class);
            jsonReader = createJsonReader("data/abstract_metadata_correzione.json");
            List<LinkedTreeMap<String, Object>> generatedAbstractAndMetaFromJson = gson.fromJson(jsonReader, List.class);

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

                    articleToStore.setAuthors(authors);
                    articleToStore.setArticle_abstract((String) generatedAbstractAndMetaFromJson.get(metaIndex).get("abstract"));
                    articleToStore.setSections((List<Section>) (generatedSectionsFromJson.get(sectionIndex)));
                    articleToStore.setPublicationDetails(getPublicationFromMap((LinkedTreeMap) generatedPublicationsFromJson.get(publicationIndex)));
                    articleToStore.setMetadata((List<String>) generatedAbstractAndMetaFromJson.get(metaIndex).get("metadata"));
                    articleToStore.setBibliography(getReferencesFromObjectBibliography((List<LinkedTreeMap>) (castObject(LinkedTreeMap.class, generatedBibliographyFromJson.get(bibliographyIndex)).get("bibliografia")), gson));

                    articleToStore.setFigures(generateFigureWithInfo((List<LinkedTreeMap>) castObject(LinkedTreeMap.class, generatedFiguresFromJson.get(figureIndex)).get("figure"), (List<LinkedTreeMap>) generatedSectionsFromJson.get(sectionIndex), gson));
                    scientificArticles.add(articleToStore.deepCopy(jsonHandler));

                    sectionIndex++;
                    publicationIndex++;
                    bibliographyIndex++;
                    metaIndex++;
                    figureIndex++;
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

    public static JsonReader createJsonReader(String fileName){
        try {
            return new JsonReader(new FileReader(fileName));
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    private static List<Figure> generateFigureWithInfo(List<LinkedTreeMap> figObjects, List<LinkedTreeMap> sections, Gson gson){
        List<Figure> result = new ArrayList<>();

        String[] sectionType = {"section", "subsection", "paragraph"};
        String randomSecType;

        Map<String, String> figType = new HashMap<>();

        Random random = new Random();
        boolean cont;
        for (LinkedTreeMap object: figObjects){

            randomSecType = sectionType[random.nextInt(sectionType.length)];

            cont = true;
            while (cont) {
                switch (randomSecType) {
                    case "section" -> {
                        figType.put("type", randomSecType);
                        figType.put("title", (String) sections.get(random.nextInt(sections.size())).get("sectionTitle"));
                        result.add(new Figure((int) Math.round((double) object.get("figure_number")),
                                (String) object.get("imageUrl"),
                                (String) object.get("caption"),
                                (String) object.get("ref_text"),
                                gson.fromJson(gson.toJson(figType), Map.class)));
                        cont = false;
                    }
                    case "subsection" -> {
                        figType.put("type", randomSecType);
                        List<LinkedTreeMap> subsection = (List<LinkedTreeMap>) sections.get(random.nextInt(sections.size())).get("subsections");
                        if (subsection.size() > 0) {
                            figType.put("title", (String) subsection.get(random.nextInt(subsection.size())).get("subsecTitle"));
                            result.add(new Figure((int) Math.round((double) object.get("figure_number")),
                                    (String) object.get("imageUrl"),
                                    (String) object.get("caption"),
                                    (String) object.get("ref_text"),
                                    gson.fromJson(gson.toJson(figType), Map.class)));
                            cont = false;
                        } else {
                            randomSecType = sectionType[random.nextInt(sectionType.length)];
                        }
                    }
                    case "paragraph" -> {
                        figType.put("type", randomSecType);
                        List<LinkedTreeMap> subsection = (List<LinkedTreeMap>) sections.get(random.nextInt(sections.size())).get("subsections");
                        if (subsection.size() > 0) {
                            List<LinkedTreeMap> paragraph = (List<LinkedTreeMap>) subsection.get(random.nextInt(subsection.size())).get("paragraphs");
                            if (paragraph.size() > 0) {
                                figType.put("title", (String) paragraph.get(random.nextInt(paragraph.size())).get("paragraphTitle"));
                                result.add(new Figure((int) Math.round((double) object.get("figure_number")),
                                        (String) object.get("imageUrl"),
                                        (String) object.get("caption"),
                                        (String) object.get("ref_text"),
                                        gson.fromJson(gson.toJson(figType), Map.class)));
                                cont = false;
                            } else {
                                randomSecType = sectionType[random.nextInt(sectionType.length)];
                            }
                        } else {
                            randomSecType = sectionType[random.nextInt(sectionType.length)];
                        }
                    }
                }
            }
        }
        return result;
    }

    private static List<Reference> getReferencesFromObjectBibliography(List<LinkedTreeMap> refObjects, Gson gson){
        List<Reference> result = new ArrayList<>();
        List<Object> author = new ArrayList<>();



        for (LinkedTreeMap object: refObjects){

            List<LinkedTreeMap> tmpList = (List<LinkedTreeMap>) object.get("author");
            tmpList.forEach(x -> {
                LinkedTreeMap tmpLTM = new LinkedTreeMap<>();
                tmpLTM.put("name", x.get("authorName"));
                tmpLTM.put("surname", x.get("authorSurname"));
                author.add(tmpLTM);
            });
            result.add(new Reference(gson.fromJson(gson.toJson(author), List.class),
                    (String) object.get("title"),
                    Integer.parseInt(object.get("year").toString()),
                    (String) object.get("DOI")));
            author.clear();
        }

        return result;
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

    private static <T extends Map> T castObject(Class<T> clazz, Object objToCast){
        return clazz.isInstance(objToCast) ? clazz.cast(objToCast) : null;
    }
}
