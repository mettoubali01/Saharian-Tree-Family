package com.example.beans;

import javax.persistence.*;

@Entity
@Table(name = "Tree")
public class Tree {

    @Id
    @Column(name = "name_id")
    private String name;

    public Tree() {
    }

    public Tree(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tres{" +
                "name='" + name + '\'' +
                '}';
    }
}
