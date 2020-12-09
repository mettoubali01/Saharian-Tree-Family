package com.example.repository;

import com.example.beans.NodeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface INodeDetailsRepository extends JpaRepository<NodeDetails, Integer> {
    List<NodeDetails> findNodeDetailsByName(String name);
    @Query("select p.name from Node_detail p where p.birthPlace = ?1")
    List<String> findNodeDetailsByBirthPlace(String birthPlace);
}
