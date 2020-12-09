package com.example.service;

import com.example.beans.NodeDetails;
import com.example.repository.INodeDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeDetailsService implements INodeDetailsService {

    @Autowired
    private INodeDetailsRepository iNodeDetailsRepository;

    @Override
    public NodeDetails addNodeDetails(NodeDetails nodeDetails) {
        iNodeDetailsRepository.save(nodeDetails);
        return nodeDetails;
    }

    @Override
    public NodeDetails updateNodeDatails(NodeDetails nodeDetails) {
        iNodeDetailsRepository.save(nodeDetails);
        return nodeDetails;
    }

    @Override
    public List<NodeDetails> getNodeDetails() {
        return iNodeDetailsRepository.findAll();
    }

    @Override
    public List<NodeDetails> getNodeDetailsbyName(String name) {
        return iNodeDetailsRepository.findNodeDetailsByName(name);
    }

    @Override
    public NodeDetails findById(long id) {
        return iNodeDetailsRepository.findById((int)id).orElse(null);
    }

    @Override
    public void deleteNodeDetails(long id) {
        iNodeDetailsRepository.deleteById((int) id);
    }

    @Override
    public List<String> getNodeDetailsByBirthPlace(String birthPlace) {
        return iNodeDetailsRepository.findNodeDetailsByBirthPlace(birthPlace);

    }
}
