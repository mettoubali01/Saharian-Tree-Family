package com.example.Controllers;

import com.example.beans.NodeDetails;
import com.example.beans.Tree;
import com.example.beans.User;
import com.example.beans.Node;
import com.example.dto.NodeDetailsDto;
import com.example.dto.ParentDto;
import com.example.service.TreeService;
import com.example.service.UserService;
import com.example.service.NodeDetailsService;
import com.example.service.NodeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    NodeService nodeService;

    @Autowired
    TreeService treeService;

    @Autowired
    NodeDetailsService nodeDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping("/")
    public String getHome(Model model, Principal principal){
        System.out.println("Hola: " + nodeService.getAllNodes());
        model.addAttribute("nodes", nodeService.getAllNodes());
        model.addAttribute("page_title", "Home");
        model.addAttribute("counted_root_node",nodeService.countRootNodes());
        return "home";
    }

    @PostMapping("/new-member")
    public String addNewMember(@Valid NodeDetailsDto nodeDetailsDto, @Valid ParentDto parentDto, BindingResult bindingResult, Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.searchUserByEmail("mohamed@mohamed.com");
        Tree tree = treeService.getTreeByName("Ettoubali");

        // related with the parent
        System.out.println("Parent " + parentDto.getParentName() + " = " + nodeService.getNodeByName(parentDto.getParentName()));
        Node parent = nodeService.getNodeByName(parentDto.getParentName());

        //related with details of the node
        NodeDetails nodeDetails = modelMapper.map(nodeDetailsDto, NodeDetails.class);
        //System.out.println(nodeDetails);
        //nodeDetailsService.addNodeDetails(nodeDetails);

        Node node = new Node();
        node.setNodeDetails(nodeDetails);
        node.setUser(user);
        node.setTree(tree);
        node.setRoot(false);
        //nodeService.saveNode(node);
        node.setParent(parent);
        nodeService.saveNode(node);

        if (bindingResult.hasErrors()) {
            System.out.println("Errores del form " + bindingResult.getFieldErrors());
        }
        return "home";
    }

    @GetMapping("/node/children/{id}")
    public List<Node> getChildrenNode(@PathVariable int id){
        Optional<Node> node = getNodeById(id);

        return node.get().getChilds();
    }

    @PutMapping("/;'p")
    public Node addNodeAsChild(@RequestBody Node node, @PathVariable int id, @PathVariable int userId){
        Node parent = nodeService.getNodeById(id).get();
        User user = userService.searchUserById(userId);
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
    @ResponseBody
    public Optional<Node> getNodeById(@PathVariable int id){
        Node node = nodeService.getNodeById(id).get();
        //System.out.println("EEEE" + node.getAdmin());
        return nodeService.getNodeById(id);
    }

    @GetMapping("/nodes")
    @ResponseBody
    public Node getNodes() {
        return nodeService.getAllNodes().get(0);
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
