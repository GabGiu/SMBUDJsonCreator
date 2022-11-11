package it.polimi.gabrielegiusti.Models;

import java.awt.*;
import java.util.Map;

public class Section {

    private String sectionTitle;

    private String[] paragraph;

    private String[] subsection;

    private Map<String, Image> figures;

    private String bibliography;

    public Section(String sectionTitle, String[] paragraph,
                   String[] subsection, Map<String, Image> figures,
                   String bibliography) {
        this.sectionTitle = sectionTitle;
        this.paragraph = paragraph;
        this.subsection = subsection;
        this.figures = figures;
        this.bibliography = bibliography;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String[] getParagraph() {
        return paragraph;
    }

    public void setParagraph(String[] paragraph) {
        this.paragraph = paragraph;
    }

    public String[] getSubsection() {
        return subsection;
    }

    public void setSubsection(String[] subsection) {
        this.subsection = subsection;
    }

    public Map<String, Image> getFigures() {
        return figures;
    }

    public void setFigures(Map<String, Image> figures) {
        this.figures = figures;
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }
}
