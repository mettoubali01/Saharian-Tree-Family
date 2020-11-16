package model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Persons")
public class Person {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String Surname;
    private Date birthDate;
    private String birthPlace;
    private String Description;
    private boolean isDead;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, String surname, String birthPlace, boolean isDead) {
        this.name = name;
        Surname = surname;
        this.birthPlace = birthPlace;
        this.isDead = isDead;
    }

    public Person(String name, String surname, Date birthDate, String birthPlace, String description, boolean isDead) {
        this.name = name;
        Surname = surname;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        Description = description;
        this.isDead = isDead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String placeDate) {
        this.birthPlace = placeDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", Surname='" + Surname + '\'' +
                ", birthDate=" + birthDate +
                ", placeDate='" + birthPlace + '\'' +
                ", Description='" + Description + '\'' +
                ", isDead=" + isDead +
                '}';
    }
}
