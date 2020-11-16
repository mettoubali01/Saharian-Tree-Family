package model;

import javax.persistence.*;

@Entity
@Table(name = "trees")
public class Trees {

    @Id
    private String name;

    public Trees() {
    }

    public Trees(String name) {
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
        return "Trees{" +
                "name='" + name + '\'' +
                '}';
    }
}
