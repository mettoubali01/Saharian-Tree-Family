package com.example.repository;

import com.example.beans.NodeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface INodeDetailsRepository extends JpaRepository<NodeDetails, Integer> {
    NodeDetails findNodeDetailsByName(String name);
    @Query("select p.name from Node_detail p where p.birthPlace = ?1")
    List<String> findNodeDetailsByBirthPlace(String birthPlace);

    @Query("select n.image from Node_detail n where n.id = ?1")
    byte[] findImageById(int id);

    @Query("select n.name, n.image from Node_detail n")
    List<String> findAllImageWName();

    @Query("select n.name from Node_detail n where n.id = ?1")
    String getNodeNameById(int id);
}
