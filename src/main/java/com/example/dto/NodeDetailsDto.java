package com.example.dto;

import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.Date;

public class NodeDetailsDto {

    @NotEmpty(message = "First name field should not be empty")
    @Size(min = 3, message = "First name must be a minimum of 3 characters")
    private String name;

    @NotEmpty(message = "Surname field should not be empty")
    @Size(min = 3, message = "Surname must be a minimum of 3 characters")
    private String surname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadDate;

    @NotEmpty(message = "Surname field should not be empty")
    @Size(min = 3, message = "Birth place must be a minimum of 3 characters")
    private String birthPlace;

    private MultipartFile image;

    @NotEmpty(message = "Surname field should not be empty")
    @Size(min = 5, message = "Description must be a minimum of 5 characters")
    private String description;

    private boolean isDead;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public Date getDeadDate() {
        return deadDate;
    }

    public void setDeadDate(Date deadDate) {
        this.deadDate = deadDate;
    }
}
