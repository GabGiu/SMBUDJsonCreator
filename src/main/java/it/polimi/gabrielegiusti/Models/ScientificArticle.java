package it.polimi.gabrielegiusti.Models;

import it.polimi.gabrielegiusti.Handlers.JsonHandler;

import java.util.*;

public class ScientificArticle {

    private String title;

    private String article_abstract;

    private List<String> metadata;

    private int year;

    private String type;

    private String DOI;

    private List<Author> authors;

    private PublicationDetails publicationDetails;

    private List<Section> sections;

    private List<Reference> bibliography;

    private List<Figure> figures;

    public ScientificArticle(){}

    public ScientificArticle(String title, String article_abstract,
                             List<String> metadata, int year, String type, String DOI, List<Author> authors,
                             PublicationDetails publicationDetails, List<Section> sections, List<Reference> bibliography, List<Figure> figures) {
        this.title = title;
        this.article_abstract = article_abstract;
        this.metadata = metadata;
        this.year = year;
        this.type = type;
        this.DOI = DOI;
        this.authors = authors;
        this.publicationDetails = publicationDetails;
        this.sections = sections;
        this.bibliography = bibliography;
        this.figures = figures;
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

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<Reference> getBibliography() {
        return bibliography;
    }

    public void setBibliography(List<Reference> bibliography) {
        this.bibliography = bibliography;
    }

    @Override
    public String toString() {
        return "ScientificArticle{" +
                "title='" + title + '\'' +
                ", article_abstract='" + article_abstract + '\'' +
                ", metadata=" + metadata +
                ", year=" + year +
                ", type='" + type + '\'' +
                ", DOI='" + DOI + '\'' +
                ", authors=" + authors +
                ", publicationDetails=" + publicationDetails +
                ", sections=" + sections +
                ", bibliography=" + bibliography +
                ", image=" + figures +
                '}';
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }

    public List<String> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<String> metadata) {
        this.metadata = metadata;
    }

    public ScientificArticle deepCopy(JsonHandler jsonHandler){
        return (ScientificArticle) jsonHandler.jsonToObject(jsonHandler.objectToJson(this), ScientificArticle.class);
    }

}
