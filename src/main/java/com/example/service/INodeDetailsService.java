package com.example.service;

import com.example.beans.NodeDetails;

import java.util.List;

public interface INodeDetailsService {
    NodeDetails addNodeDetails(NodeDetails person);
    NodeDetails updateNodeDetails(NodeDetails person);
    List<NodeDetails> getNodeDetails();
    NodeDetails getNodeDetailsByName(String name);
    NodeDetails findById(long id);
    void deleteNodeDetails(long id);
    List<String> getNodeDetailsByBirthPlace(String birthPlace);
}
