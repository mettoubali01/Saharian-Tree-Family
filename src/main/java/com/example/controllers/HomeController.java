package com.example.controllers;

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
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

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

    @GetMapping("/")
    public String getHome(Model model, NodeDetailsDto nodeDetailsDto) throws IOException {
        nodeDetailsService.getAllImages();
        nodeService.getNameNodeWNameParent().entrySet().stream()
                .forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));

        model.addAttribute("nodess", nodeService.getNameNodeWNameParent());
        model.addAttribute("nodes", nodeService.getAllNodes());
        model.addAttribute("page_title", "Home");
        model.addAttribute("counted_root_node",nodeService.countRootNodes());
        return "home";
    }

    @PostMapping("/addMember")
    public String addNewMember(@Valid NodeDetailsDto nodeDetailsDto,
                               BindingResult bindingResult,
                               ParentDto parentDto) throws IOException {

        if (bindingResult.hasErrors()) {
            System.out.println("Errores del form ");
            return "home";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.searchUserByEmail(email);
        Tree tree = treeService.getTreeByName("Ettoubali");

        // related with the parent
        Node parent = null;
        if (parentDto.getParentName() != null) {
            parent = nodeService.getNodeById(Integer.parseInt(parentDto.getParentName())).get();
        }

        NodeDetails nodeDetails = modelMapper.map(nodeDetailsDto, NodeDetails.class);
        nodeDetails.setImage(nodeDetailsDto.getImage().getBytes());
        Node node = new Node();
        node.setNodeDetails(nodeDetails);
        node.setUser(user);
        node.setTree(tree);
        if (parent == null)
            node.setRoot(true);
        else
            node.setRoot(false);
        node.setParent(parent);

        //saving the node
        nodeService.saveNode(node);

        return "redirect:/";
    }

    @PostMapping("/updateMember/{id}")
    public String updateMember(@Valid NodeDetailsDto nodeDetailsDto,
                               BindingResult bindingResult,
                               @Valid ParentDto parentDto,
                               @PathVariable int id) throws IOException {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.searchUserByEmail(email);
        Tree tree = treeService.getTreeByName("Ettoubali");

        Node parent = null;
        if (parentDto.getParentName() != null && !parentDto.getParentName().equals("Choose your parent")) {
            parent = nodeService.getNodeById(Integer.parseInt(parentDto.getParentName())).get();
            System.out.println("sasas===>>> " + parentDto.getParentName());

        }
        NodeDetails nodeDetails = modelMapper.map(nodeDetailsDto, NodeDetails.class);
        Node node = nodeService.getNodeById(id).get();
        node.getNodeDetails().setImage(nodeDetailsDto.getImage().getBytes());
        node.getNodeDetails().setName(nodeDetails.getName());
        node.getNodeDetails().setSurname(nodeDetails.getSurname());
        node.getNodeDetails().setBirthDate(nodeDetails.getBirthDate());
        node.getNodeDetails().setDescription(nodeDetails.getDescription());
        node.getNodeDetails().setBirthPlace(nodeDetails.getBirthPlace());
        node.getNodeDetails().setDeadDate(nodeDetails.getDeadDate());
        if (node.getNodeDetails().getDeadDate() != null)
            node.getNodeDetails().setDead(true);
        else
            node.getNodeDetails().setDead(false);
        node.setUser(user);
        node.setTree(tree);
        node.setRoot(false);
        node.setParent(parent);
        nodeService.saveNode(node);

        if (bindingResult.hasErrors()) {
            System.out.println("Errores del form " + bindingResult.getFieldErrors());
            return "home";
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String rmNodeById(@PathVariable int id) {
        Optional<Node> node = nodeService.getNodeById(id);
        if (node.isPresent()) {
            if (node.get().getChilds().size() > 0) {
                ArrayList<Integer> a = nodeService.getIds(id);
                Collections.sort(a);
                Collections.reverse(a);
                a.stream().forEach((i)->{
                });
                System.out.println("Id: " + id + " " + node.get().getChilds().size());
                nodeService.deleteNodeById(id);

            }else{
                nodeService.deleteNodeById(id);
            }
        }
        return "redirect:/";
    }

    @PutMapping("/addChildNode")
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

}