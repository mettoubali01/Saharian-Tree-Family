package com.example.service;

import com.example.beans.Node;
import com.example.repository.INodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class NodeService implements INodeService{

    @Autowired
    private INodeRepository iNodeRepository;

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
    public List<Node> getAllNodes() {
        return iNodeRepository.findAll();
    }

    public ArrayList<Integer> getIds(int id){
        Optional<Node> node = getNodeById(id);
        ArrayList<Integer> ids = new ArrayList<>();
        for (Node n: node.get().getChilds()) {
            ids.add(n.getId());
            if (n.getChilds().size()>0)
                ids.add(getIds(n.getId()).get(0));
        }
        return ids;
    }
}
