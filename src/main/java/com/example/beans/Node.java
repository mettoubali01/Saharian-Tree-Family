package com.example.beans;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Node")
public class Node implements Serializable {
    @Id
    @Column(name="node_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    private Tree tree;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "node_details_id")
    private NodeDetails nodeDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Node parent;

    @OneToMany(mappedBy = "parent" ,cascade ={ CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonManagedReference
   // @JsonIgnoreProperties("childs")
    private List<Node> childs = new ArrayList<>();
    private boolean isRoot;

    public Node() {}

    public Node(NodeDetails nodeDetails, boolean isRoot) {
        this.nodeDetails = nodeDetails;
        this.isRoot = isRoot;
    }

    public Node(NodeDetails nodeDetails, boolean isRoot, Node parent) {
        this.nodeDetails = nodeDetails;
        this.isRoot = isRoot;
        if (isRoot){
            this.parent = parent;
        }
    }

    public Node(NodeDetails nodeDetails) {
       this.nodeDetails = nodeDetails;
    }

    public Node(NodeDetails nodeDetails, List<Node> childs) {
        this.nodeDetails = nodeDetails;
        this.childs = childs;
    }

    public Node( NodeDetails nodeDetails, Node parent, List<Node> childs) {
        this.nodeDetails = nodeDetails;
        this.parent = parent;
        this.childs = childs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public NodeDetails getNodeDetails() {
        return nodeDetails;
    }

    public void setNodeDetails(NodeDetails nodeDetails) {
        this.nodeDetails = nodeDetails;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChilds() {
        return childs;
    }

    public void setChilds(List<Node> childs) {
        this.childs = childs;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }


}
