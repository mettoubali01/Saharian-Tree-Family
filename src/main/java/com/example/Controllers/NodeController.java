package com.example.Controllers;

import com.example.beans.Node;
import com.example.beans.NodeDetails;
import com.example.repository.INodeRepository;
import com.example.service.NodeDetailsService;
import com.example.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class NodeController {

    @Autowired
    NodeService nodeService;

    @Autowired
    NodeDetailsService nodeDetailsService;

    @PostMapping("/Add")
    public Node saveNodeAsRoot(@RequestBody Node node){
       // System.out.println(node.getParent() + " " + node.getNodeDetails());
        nodeService.saveNode(node);
        return node;
    }

    @GetMapping("/node/children/{id}")
    public List<Node> childrenNode(@PathVariable int id){
        Optional<Node> node = getNodeById(id);

        return node.get().getChilds();
    }

    @PutMapping("/Node/{id}/AddChild")
    public Node addNodeAsChild(@RequestBody Node node, @PathVariable int id){
        Node parent = nodeService.getNodeById(id).get();
        node.setParent(parent);
        node.getNodeDetails().setNode(node);
        return nodeService.saveNode(node);
    }

    @PutMapping("/Update")
    public Node updatePerson(@RequestBody Node node){
        return nodeService.updateNode(node);
    }

    @GetMapping("/{id}")
    public Optional<Node> getNodeById(@PathVariable int id){

        return nodeService.getNodeById(id);
    }

    @GetMapping("/nodes")
    public List<Node> getNodes() {
        return nodeService.getAllNodes();
    }

    @DeleteMapping("deleteNode/{id}")
    public String rmNodeById(@PathVariable int id) {
        Optional<Node> node = getNodeById(id);
        if (node.isPresent()) {
            if (node.get().getChilds().size() > 0) {
               /* ArrayList<Integer> a = nodeService.getIds(id);
                Collections.sort(a);
                Collections.reverse(a);
                a.stream().forEach((i)->{
                    //System.out.println("println 2" + i);
                    //nodeService.deleteNodeById(i);
                    //
                });*/
                System.out.println("Id: " + id + " " + node.get().getChilds().size());
                nodeService.deleteNodeById(id);

            }else{
                nodeService.deleteNodeById(id);
                return "Has no childs " + node.get().getChilds().size();
            }
            return "Removed Successfully " + node.get().getChilds().size();
        }else
            return "Already removed ";
    }

    @DeleteMapping("/rm")
    public String rmNodeByNode(@RequestBody Node node) {
        nodeService.deleteNode(node);
        return "removed successfully";
    }
}
