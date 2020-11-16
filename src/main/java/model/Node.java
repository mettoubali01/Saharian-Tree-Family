package model;

import javax.persistence.*;

@Entity
@Table(name = "nodes")
public class Node {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    private Trees tree_fk;
    @OneToOne
    private Person person_fk;
    @OneToOne
    private Person child_person_fk;
    @OneToOne
    private Person parent_person_fk;

    private boolean isRoot;

    public Node() {}

    public Node(Person person, boolean isRoot) {
        this.person_fk = person;
        this.isRoot = isRoot;
        if (this.isRoot)
            this.parent_person_fk = person;

    }

    public Node(Person person) {
        this.person_fk = person;
    }

    public Node(Person person, Person child) {
        this.person_fk = person;
        this.child_person_fk = child;
    }

    public Node(Person person, Person parent, Person child) {
        this.person_fk = person;
        this.parent_person_fk = parent;
        this.child_person_fk = child;
    }

    //adding child
    public void associateChild(Person child) {
        this.child_person_fk = child;
    }

    //remove children
    public void removeChildRelation() {
        this.child_person_fk = null;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public Trees getTree_fk() {
        return tree_fk;
    }

    public void setTree_fk(Trees tree_fk) {
        this.tree_fk = tree_fk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getChild_person_fk() {
        return child_person_fk;
    }

    public void setChild_person_fk(Person child) {
        this.child_person_fk = child;
    }

    public Person getParent_person_fk() {
        return parent_person_fk;
    }

    public void setParent_person_fk(Person parent) {

        this.parent_person_fk = parent;
    }


    public Person getPerson_fk() {
        return person_fk;
    }

    public void setPerson_fk(Person person_fk) {
        this.person_fk = person_fk;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", tree_fk='" + tree_fk + '\'' +
                ", child_id=" + child_person_fk +
                ", parent_id=" + parent_person_fk +
                ", isRoot=" + isRoot +
                '}';
    }
}
