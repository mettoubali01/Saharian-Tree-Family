package com.example.service;

import com.example.beans.Tree;
import com.example.repository.ITreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeService implements ITreeService {

    @Autowired
    private ITreeRepository iTreeRepository;

    @Override
    public Tree saveTree(Tree tree) {
        iTreeRepository.save(tree);
        return tree;
    }

    @Override
    public List<Tree> getTrees() {
        return iTreeRepository.findAll();
    }

    @Override
    public void rmByID(String name) {
        iTreeRepository.deleteById(name);
    }

    @Override
    public void rmTree(Tree tree) {
        iTreeRepository.delete(tree);
    }

    @Override
    public Tree getTreeByName(String name) {
        return iTreeRepository.findById(name).orElse(null);
    }
}
