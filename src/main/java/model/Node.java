package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;


public class Node {

    private Data data;
    private Node parent;
    private List<Node> children;

    public Node() {
    }

    public Node(Data data) {
        this.data = data;
        this.children = new ArrayList();
    }

    //adding child
    public void addChild(Node node){
        node.setParent(this);
        this.children.add(node);
    }

    //add child at specific index
    public void addChildAt(Node node, int index){
        node.setParent(this);
        this.children.add(index, node);
    }

    //remove children
    public void removeChildren(){
        this.children.clear();
    }

    //remove child from by index
    public void removeChildAt(int index){
        this.children.remove(index);
    }

    //remove child by object node
    public boolean removeChild(Node node){
        return this.children.remove(node);
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    //get child by index
    public Node getChildAt(int index){
        return this.children.get(index);
    }

    public void setChildren(ArrayList<Node> children) {
        for (Node node: children)
            node.setParent(this);
        this.children = children;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", children=" + children +
                '}';
    }
}
