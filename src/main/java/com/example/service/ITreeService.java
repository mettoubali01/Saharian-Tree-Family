package com.example.service;

import com.example.beans.Tree;

import java.util.List;

public interface ITreeService {
    Tree saveTree(Tree tree);
    List<Tree> getTrees();
    Tree getTreeByName(String name);
    void rmByID(String name);
    void rmTree(Tree tree);
}
