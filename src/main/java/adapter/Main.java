package adapter;

import model.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");

    public static void main(java.lang.String[] args) {

        savePerson();
        saveTree();
        saveNodes();
        
    }

    public static void savePerson(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        //examples
        Person bachir = new Person("Bachir","Ettoubali", "Western Sahara", true);
        Person ibrahim = new Person("Ibrahim", "Ettoubali", "Labyar", false);
        Person yusef = new Person("Yusef", "Ettoubali", "Laayoun", false);
        Person maryam = new Person("Maryam", "Ettoubali", "Labyar", false);

        //saving Persons
        entityManager.persist(bachir);
        entityManager.persist(ibrahim);
        entityManager.persist(yusef);
        entityManager.persist(maryam);

        entityManager.getTransaction().commit();
        entityManager.close();
       // entityManagerFactory.close();
    }

    public static List<Person> getPersons(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Person> persons = entityManager.createQuery("select p from Persons p", Person.class).getResultList();

        //System.out.println("Size : " + persons.size());
        entityManager.getTransaction().commit();
        entityManager.close();
        //entityManagerFactory.close();
        return persons;
    }

    public static Trees getTreeName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Trees tree = entityManager.find(Trees.class, name);
        //System.out.println(tree);

        entityManager.getTransaction().commit();
        entityManager.close();
        //entityManagerFactory.close();

        return tree;
    }

    public static void saveNodes(){
        List<Person> persons = getPersons();
        Trees tree = getTreeName("Ettoubali");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Person personRoot = persons.stream()
                .filter(person -> "Bachir".equals(person.getName()))
                .findAny()
                .orElse(null);

        Person ibrahim = persons.stream()
                .filter(person -> "Ibrahim".equals(person.getName()))
                .findAny()
                .orElse(null);

        Person yusef = persons.stream()
                .filter(person -> "Yusef".equals(person.getName()))
                .findAny()
                .orElse(null);

        Node root = new Node(personRoot, true);
        root.setTree_fk(tree);
        //System.out.println("Root " + root.getPerson_fk());
        Node brahimNode = new Node(ibrahim);
        brahimNode.setTree_fk(tree);
        //System.out.println("Ibrahim root " + brahimNode.getPerson_fk());
        brahimNode.setParent_person_fk(personRoot);
        brahimNode.setChild_person_fk(yusef);

        entityManager.persist(root);
        entityManager.persist(brahimNode);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
        //System.out.println(root);

    }

    public static void saveTree(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Trees ettoubaliTrees = new Trees("Ettoubali");

        entityManager.persist(ettoubaliTrees);
        entityManager.getTransaction().commit();
        entityManager.close();
        //entityManagerFactory.close();
    }
}