package com.example.service;

import com.example.beans.NodeDetails;
import com.example.repository.INodeDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class NodeDetailsService implements INodeDetailsService {

    @Autowired
    private INodeDetailsRepository iNodeDetailsRepository;


    public void getAllImages(){
        List<String> images = iNodeDetailsRepository.findAllImageWName();
        System.out.println("consulta las imagenes " + images);
    }

    public String getImageById(int id){
        byte[] image = iNodeDetailsRepository.findImageById(id);
        if (image != null) {

            System.out.println(Arrays.toString(image));
            String contentType = null;
            try {
                contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(image));
            } catch (IOException e) {
                e.getMessage();
            }

            String path = "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(image);

            return path;
        }else
            return null;
    }

    public String getNameById(int id){
        String name = iNodeDetailsRepository.getNodeNameById(id);
        if (name != null) {
            return name;
        }else
            return null;
    }

    @Override
    public NodeDetails addNodeDetails(NodeDetails nodeDetails) {
        iNodeDetailsRepository.save(nodeDetails);
        return nodeDetails;
    }

    @Override
    public NodeDetails updateNodeDetails(NodeDetails nodeDetails) {
        iNodeDetailsRepository.save(nodeDetails);
        return nodeDetails;
    }

    @Override
    public List<NodeDetails> getNodeDetails() {
        return iNodeDetailsRepository.findAll();
    }

    @Override
    public NodeDetails getNodeDetailsByName(String name) {
        return iNodeDetailsRepository.findNodeDetailsByName(name);
    }

    @Override
    public NodeDetails findById(long id) {

        NodeDetails nodeDetails = iNodeDetailsRepository.findById((int)id).orElse(null);
        System.out.println("NodeDetails " + nodeDetails);

        return nodeDetails;
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
