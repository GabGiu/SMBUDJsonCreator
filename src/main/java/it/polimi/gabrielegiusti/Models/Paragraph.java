package it.polimi.gabrielegiusti.Models;

public class Paragraph {

    private String paragraphTitle;

    private String paragraphText;

    public Paragraph(){}

    public Paragraph(String paragraphTitle, String paragraphText) {
        this.paragraphTitle = paragraphTitle;
        this.paragraphText = paragraphText;
    }

    public String getParagraphTitle() {
        return paragraphTitle;
    }

    public void setParagraphTitle(String paragraphTitle) {
        this.paragraphTitle = paragraphTitle;
    }

    public String getParagraphText() {
        return paragraphText;
    }

    public void setParagraphText(String paragraphText) {
        this.paragraphText = paragraphText;
    }
}
