package com.example.service;

import com.example.beans.NodeDetails;

import java.util.List;

public interface INodeDetailsService {
    NodeDetails addNodeDetails(NodeDetails person);
    NodeDetails updateNodeDatails(NodeDetails person);
    List<NodeDetails> getNodeDetails();
    List<NodeDetails> getNodeDetailsbyName(String name);
    NodeDetails findById(long id);
    void deleteNodeDetails(long id);
    List<String> getNodeDetailsByBirthPlace(String birthPlace);
}
