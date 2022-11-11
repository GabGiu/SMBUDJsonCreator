package it.polimi.gabrielegiusti.Models;

import it.polimi.gabrielegiusti.DBManager.Neo4jManager;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScientificArticle {

    private String title;

    private String article_abstract;

    private int year;

    private String type;

    private String DOI;

    private List<Author> authors;

    private PublicationDetails publicationDetails;

    private Section section;

    public ScientificArticle(){}

    public ScientificArticle(String title, String article_abstract,
                             int year, String type, String DOI, List<Author> authors,
                             PublicationDetails publicationDetails, Section section) {
        this.title = title;
        this.article_abstract = article_abstract;
        this.year = year;
        this.type = type;
        this.DOI = DOI;
        this.authors = authors;
        this.publicationDetails = publicationDetails;
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle_abstract() {
        return article_abstract;
    }

    public void setArticle_abstract(String article_abstract) {
        this.article_abstract = article_abstract;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDOI() {
        return DOI;
    }

    public void setDOI(String DOI) {
        this.DOI = DOI;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public PublicationDetails getPublicationDetails() {
        return publicationDetails;
    }

    public void setPublicationDetails(PublicationDetails publicationDetails) {
        this.publicationDetails = publicationDetails;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public void populateArticle(Neo4jManager neo4jManager, Record record){

        List<Author> authors1 = new ArrayList<>();

            this.title = record.get("Article").get("title").asString();
            this.DOI = record.get("Article").get("DOI").asString();
            this.type = record.get("Article").get("type").asString();
            if (!record.get("Article").get("year").isNull()){
                this.year = record.get("Article").get("year").asInt();
            }


            for (Record record1 : neo4jManager.findAuthorByArticle(String.valueOf(record.get("Article").get("articleID").asInt()))){
                Author author = new Author();
                Location location = new Location();
                Affiliation affiliation = new Affiliation();

                Record affiliationRecord = neo4jManager.findAffiliationByAuthorID(record1.get("Author").get("authorID").asString());
                Record locationRecord = neo4jManager.findLocationByAffiliationID(affiliationRecord.get("Affiliation").get("affiliationID").asString());

                author.setName(record1.get("Author").get("name").asString());
                author.setSurname(record1.get("Author").get("surname").asString());

                affiliation.setAffiliationDepartment(affiliationRecord.get("Affiliation").get("department").asString());
                affiliation.setAffiliationName(affiliationRecord.get("Affiliation").get("affiliationName").asString());

                location.setZipcode(locationRecord.get("Location").get("zipcode").asString());
                location.setCity(locationRecord.get("Location").get("city").asString());
                location.setCountry(locationRecord.get("Location").get("country").asString());

                affiliation.setLocation(location);

                author.setBio("");
                author.setEmail("");
                author.setAffiliation(affiliation);
                author.setDateOfBirth(null);

                authors1.add(author);
            }
            this.authors = authors1;
            this.article_abstract = "";

            PublicationDetails publicationDetails1 = new PublicationDetails();
            publicationDetails1.setDate(null);
            publicationDetails1.setEditor("");
            publicationDetails1.setNumber(0);
            publicationDetails1.setVolume("");
            publicationDetails1.setJournalName("pippo");
            publicationDetails1.setPages(3);
            this.publicationDetails = publicationDetails1;
            this.article_abstract = "pluto";
            this.section = null;
        }

    @Override
    public String toString() {
        return "ScientificArticle{" +
                "title='" + title + '\'' +
                ", article_abstract='" + article_abstract + '\'' +
                ", year=" + year +
                ", type='" + type + '\'' +
                ", DOI='" + DOI + '\'' +
                ", authors=" + authors +
                ", publicationDetails=" + publicationDetails +
                ", section=" + section +
                '}';
    }
}
