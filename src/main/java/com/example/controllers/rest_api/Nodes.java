package com.example.controllers.rest_api;

import com.example.beans.Node;
import com.example.beans.NodeDetails;
import com.example.service.NodeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.example.service.NodeService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Nodes {

    @Autowired
    NodeService nodeService;

    @Autowired
    NodeDetailsService nodeDetailsService;

    @GetMapping("/node/{id}")
    @ResponseBody
    public Optional<Node> getNodeById(@PathVariable int id){
        return nodeService.getNodeById(id);
    }

    @GetMapping("/nodeDetails/{id}")
    @ResponseBody
    public NodeDetails getNodeDetailsById(@PathVariable int id){
        return nodeDetailsService.findById(id);
    }

    @GetMapping("/node/children/{id}")
    public List<Node> getChildrenNode(@PathVariable int id){
        Optional<Node> node = getNodeById(id);

        return node.get().getChilds();
    }

    @GetMapping("/nodes")
    @ResponseBody
    public com.example.beans.Node getNodes() {
        int totalNode = nodeService.getAllNodes().size();

        if (totalNode > 0)
            return nodeService.getAllNodes().get(0);
        else
            return null;
    }
}
