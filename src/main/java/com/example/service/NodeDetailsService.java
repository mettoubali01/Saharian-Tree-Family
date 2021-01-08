package com.example.service;

import com.example.beans.NodeDetails;
import com.example.repository.INodeDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;

@Service
public class NodeDetailsService implements INodeDetailsService {

    @Autowired
    private INodeDetailsRepository iNodeDetailsRepository;


    public void getAllImages(){
        List<String> images = iNodeDetailsRepository.findAllImageWName();
        System.out.println("consulta a ala imagen " + images);
    }

    public String getImageById(int id){
        byte[] image = iNodeDetailsRepository.findImageById(id);
        if (image != null) {

            System.out.println(image);
            String contentType = null;
            try {
                contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(image));
            } catch (IOException e) {
                e.getMessage();
            }
            System.out.println("to say " + contentType);

            String path = "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(image);
            if (path != null)
                return path;
            else
                return "";
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
    public NodeDetails updateNodeDatails(NodeDetails nodeDetails) {
        iNodeDetailsRepository.save(nodeDetails);
        return nodeDetails;
    }

    @Override
    public List<NodeDetails> getNodeDetails() {
        return iNodeDetailsRepository.findAll();
    }

    @Override
    public NodeDetails getNodeDetailsbyName(String name) {
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
