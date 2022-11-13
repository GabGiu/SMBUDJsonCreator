package it.polimi.gabrielegiusti.Models;

public class Location {

    private int zipcode;

    private String city;

    private String country;

    public Location(){};

    public Location(int zipcode, String city, String country) {
        this.zipcode = zipcode;
        this.city = city;
        this.country = country;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
