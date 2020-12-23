package com.example.service;

import com.example.beans.Node;

import java.util.List;
import java.util.Optional;

public interface INodeService {
    Node saveN(Node node);
    Node saveNode(Node node);
    void deleteNode(Node node);
    void deleteNodeById(int id);
    Node updateNode(Node node);
    Optional<Node> getNodeById(int id);
    Node getNodeByName(String name);
    List<Node> getAllNodes();
    int countRootNodes();
}
