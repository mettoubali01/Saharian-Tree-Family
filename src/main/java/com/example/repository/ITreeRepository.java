package com.example.repository;

import com.example.beans.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ITreeRepository extends JpaRepository<Tree, String> {
}
