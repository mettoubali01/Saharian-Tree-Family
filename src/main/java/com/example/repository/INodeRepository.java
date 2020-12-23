package com.example.repository;

import com.example.beans.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INodeRepository extends JpaRepository<Node, Integer> {

    @Query("select count(n.isRoot) from Node n where n.isRoot = 1")
    int countRootNodes();

}
