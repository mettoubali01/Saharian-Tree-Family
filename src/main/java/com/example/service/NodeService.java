package com.example.service;

import com.example.beans.Node;
import com.example.beans.NodeDetails;
import com.example.repository.INodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class NodeService implements INodeService {

    @Autowired
    private INodeRepository iNodeRepository;

    @Autowired
    private INodeDetailsService iNodeDetailsService;

    @Override
    public Node saveNode(Node node) {
        return iNodeRepository.save(node);
    }

    @Override
    public Node saveN(Node node) {
        System.out.println("Check " + iNodeRepository.existsById(node.getId()) + "\n" +
                node.getChilds().size());
        if (!iNodeRepository.exists(Example.of(node)))
            iNodeRepository.deleteAll();

        return node;
    }

    @Override
    public void deleteNode(Node node) {
        if (iNodeRepository.exists(Example.of(node)))
            iNodeRepository.delete(node);
    }

    @Transactional
    @Override
    public void deleteNodeById(int id) {
        iNodeRepository.deleteById(id);
    }

    @Override
    public Node updateNode(Node node) {
        if (iNodeRepository.exists(Example.of(node)))
            iNodeRepository.save(node);
        return node;
    }

    @Override
    public Optional<Node> getNodeById(int id) {

        return iNodeRepository.findById(id);
    }

    @Override
    public Node getNodeByName(String name) {
        List<Node> nodes = iNodeRepository.findAll();
        Node obj = null;
        for (Node node : nodes)
            if (node.getNodeDetails().getName().equals(name))
                obj = node;

        return obj;
    }

    @Override
    public List<Node> getAllNodes() {
        return iNodeRepository.findAll();
    }

    public HashMap<Integer, String> getNameNodeWNameParent(){
        List<Node> nodes = iNodeRepository.findAll();
        HashMap<Integer, String> results = new HashMap<>();

        for (Node node : nodes) {
            if (node.getParent() != null) {
                Node parent = iNodeRepository.findById(node.getParent().getId()).get();
                results.put(node.getId(), node.getNodeDetails().getName() + " Son of " + parent.getNodeDetails().getName());
            }else{
                results.put(node.getId(), node.getNodeDetails().getName());
            }
        }

        return results;
    }

    @Override
    public int countRootNodes() {
        return iNodeRepository.countRootNodes();
    }

    public ArrayList<Integer> getIds(int id) {
        Optional<Node> node = getNodeById(id);
        ArrayList<Integer> ids = new ArrayList<>();
        for (Node n : node.get().getChilds()) {
            ids.add(n.getId());
            if (n.getChilds().size() > 0)
                ids.add(getIds(n.getId()).get(0));
        }
        return ids;
    }
}
