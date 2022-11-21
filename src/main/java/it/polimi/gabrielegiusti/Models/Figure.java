package it.polimi.gabrielegiusti.Models;

import java.util.Map;

public class Figure {

    private int figureNumber;

    private String imageURL;

    private String caption;

    private String ref_text;

    private Map<String, String> type;

    public Figure(){}
    public Figure(int figureNumber, String imageURL, String caption, String ref_text, Map<String, String> type) {
        this.figureNumber = figureNumber;
        this.imageURL = imageURL;
        this.caption = caption;
        this.ref_text = ref_text;
        this.type = type;
    }


    public int getFigureNumber() {
        return figureNumber;
    }

    public void setFigureNumber(int figureNumber) {
        this.figureNumber = figureNumber;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Map<String, String> getType() {
        return type;
    }

    public void setType(Map<String, String> type) {
        this.type = type;
    }

    public String getRef_text() {
        return ref_text;
    }

    public void setRef_text(String ref_text) {
        this.ref_text = ref_text;
    }
}
