package adapter;

import model.Data;
import model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeAdapter {
    private Node root;

    public TreeAdapter(Node root) {
        this.root = root;
    }

    //check if is  empty
    public boolean isEmpty() {
        return root == null;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    //searching the node in the tree or remove it depend of the order
    public boolean findNodeIntTheTree(Node tree, Node givingNode) {
        boolean flag = false;

        if (givingNode.getData().getName().equals(tree.getData().getName())) {
            return true;
        } else {
            for (Node node : tree.getChildren()) {
                findNodeIntTheTree(node, givingNode);
                flag = true;
            }
        }

        return flag;
    }

    public void removeNodeFromTree(Node tree, Node givingNode) {
        List<Node> result = new ArrayList();
        if (tree.getData().getName().equals(givingNode.getData().getName())){
            this.root.setData(new Data(null));
            this.root.getChildren().clear();
        }
        if (tree.getChildren().contains(givingNode)){
            tree.removeChild(givingNode);
        }else {
            for (Node node : tree.getChildren())
                removeNodeFromTree(node, givingNode);
        }
    }

    //count the number of descendants of the giving node
    public int getDescendants(Node node) {
        int num = node.getChildren().size();
        for (Node child : node.getChildren()) {
            num += getDescendants(child);
        }
        return num;
    }

    //print the tree in preorder way
    public void printInOrder(Node tree) {
        Stack<Node> stack = new Stack<Node>();
        ArrayList<String> list = new ArrayList();
        stack.push(tree);

        /*while (!stack.empty()) {

            Node node1 = stack.peek();
            System.out.print(node1);
            stack.pop();

            for (int i = 0; i < node1.getChildren().size(); i++) {
                stack.push(node1.getChildren().get(i));
            }


        }*/

        int total = tree.getChildren().size();

        if (tree == null)
            return;

        for (int i = 0; i < total; i++)
            printInOrder(tree.getChildren().get(i));

        System.out.print("" + tree.getData().getName() + " ");

        //printInOrder(tree.getChildren().get(total));
    }


}
