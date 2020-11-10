package company;

import adapter.TreeAdapter;
import model.Node;
import model.Data;

public class Main {

    public static void main(String[] args) {

        Node root = new Node(new Data("Bachir"));
        Node ibrahim = new Node(new Data("Ibrahim"));
        Node maryam = new Node(new Data("Maryam"));
        Node yunes = new Node(new Data("Yunes"));
        Node hassana = new Node(new Data("Hassana"));
        Node najah = new Node(new Data("Najah"));
        Node yusef = new Node(new Data("Yusef"));
        Node abed = new Node(new Data("Abed"));
        Node zaynab = new Node(new Data("Zaynab"));

        root.addChild(ibrahim);
        root.addChild(maryam);
        maryam.addChild(yunes);
        maryam.addChild(hassana);
        hassana.addChild(najah);
        ibrahim.addChild(yusef);
        yusef.addChild(abed);
        yusef.addChild(zaynab);

        TreeAdapter tree = new TreeAdapter(root);

        System.out.println("How many nodes has the root: " + tree.getDescendants(root) + "nodes.");
        System.out.print("Checking if zaynab exist in the tree (true or false): " + tree.findNodeIntTheTree(root, zaynab) +
                " \nBefore removing zaynab: " );
        tree.printInOrder(root);
        System.out.println("\nRemoving Maryam...");

        tree.removeNodeFromTree(root, zaynab);
        System.out.print("After removing zaynab: ");
        tree.printInOrder(root);
    }
}
