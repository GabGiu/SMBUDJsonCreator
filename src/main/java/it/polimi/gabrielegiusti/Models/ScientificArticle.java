package it.polimi.gabrielegiusti.Models;

import java.util.Date;
import java.util.List;

public class ScientificArticle {

    private String title;

    private String article_abstract;

    private Date year;

    private String type;

    private String DOI;

    private List<Author> authors;

    private PublicationDetails publicationDetails;

    private Section section;

    public ScientificArticle(String title, String article_abstract,
                             Date year, String type, String DOI, List<Author> authors,
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

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
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
}
