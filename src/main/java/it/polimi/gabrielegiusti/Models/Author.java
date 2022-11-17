package it.polimi.gabrielegiusti.Models;

import java.util.Date;

public class Author {

    private Affiliation affiliation;

    private String email;

    private String bio;

    private String name;

    private String surname;

    private String dateOfBirth;

    public Author(){}

    public Author(Affiliation affiliation, String email, String bio,
                  String name, String surname, String dateOfBirth) {
        this.affiliation = affiliation;
        this.email = email;
        this.bio = bio;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


}
