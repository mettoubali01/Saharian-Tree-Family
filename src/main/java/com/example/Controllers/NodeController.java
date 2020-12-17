package com.example.Controllers;

import com.example.beans.User;
import com.example.beans.Node;
import com.example.service.UserService;
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

    @Autowired
    UserService userService;

    @PostMapping("admin/{id}/node/add")
    public Node saveNodeAsRoot(@RequestBody Node node, @PathVariable int id){
       // System.out.println(node.getParent() + " " + node.getNodeDetails());
        User user = userService.searchAdminById(id);
        node.setUser(user);
        nodeService.saveNode(node);
        return node;
    }

    @GetMapping("/node/children/{id}")
    public List<Node> getChildrenNode(@PathVariable int id){
        Optional<Node> node = getNodeById(id);

        return node.get().getChilds();
    }

    @PutMapping("/admin/{userId}/Node/{id}/AddChild")
    public Node addNodeAsChild(@RequestBody Node node, @PathVariable int id, @PathVariable int userId){
        Node parent = nodeService.getNodeById(id).get();
        User user = userService.searchAdminById(userId);
        node.setParent(parent);
        //node.setAdmin(admin);
        node.getNodeDetails().setNode(node);
        return nodeService.saveNode(node);
    }

    @PutMapping("/Update")
    public Node updatePerson(@RequestBody Node node){
        return nodeService.updateNode(node);
    }

    @GetMapping("/{id}")
    public Optional<Node> getNodeById(@PathVariable int id){
        Node node = nodeService.getNodeById(id).get();
        //System.out.println("EEEE" + node.getAdmin());
        return nodeService.getNodeById(id);
    }

    @GetMapping("/nodes")
    public List<Node> getNodes() {
        return nodeService.getAllNodes();
    }

    @DeleteMapping("deleteNode/{id}")
    @Transactional
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
                System.out.println("Else ********************* Before " + id);
                nodeService.deleteNodeById(id);
                System.out.println("Else ********************* After " + id);

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
