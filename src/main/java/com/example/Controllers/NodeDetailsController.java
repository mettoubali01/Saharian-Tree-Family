package com.example.Controllers;

import com.example.beans.NodeDetails;
import com.example.service.NodeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class NodeDetailsController {
    @Autowired
    NodeDetailsService nodeDetailsService;

    @RequestMapping("/image/{id}")
    @ResponseBody
    public String getImageByid(@PathVariable int id){
        String image = nodeDetailsService.getImageById(id);
        return "{\"image\":\""+ image + "\"}";
    }

    @RequestMapping("/name/{id}")
    @ResponseBody
    public String getNameById(@PathVariable int id){
        String name = nodeDetailsService.getNameById(id);
        return "{\"name\":\""+ name + "\"}";
    }

    @PostMapping("/person/save")
    public NodeDetails savePerson(@RequestBody NodeDetails person){
        return nodeDetailsService.addNodeDetails(person);
    }

    @PutMapping("/person/update")
    public NodeDetails updatePerson(@RequestBody NodeDetails person){
        return nodeDetailsService.updateNodeDatails(person);
    }

    @GetMapping("/person/{id}")
    public NodeDetails getPersonById(@PathVariable long id){
        return nodeDetailsService.findById(id);
    }

    @GetMapping("/persons")
    public List<NodeDetails> getAllPersons(){
        return nodeDetailsService.getNodeDetails();
    }

    @DeleteMapping("/person/rm")
    public String rmPerson(@RequestParam int id) {
        nodeDetailsService.deleteNodeDetails(id);
        return "removed successfully";
    }

    @GetMapping("/person/{birthPlace}")
    public List<String> searshPersonByName(@PathVariable String birthPlace){
        return nodeDetailsService.getNodeDetailsByBirthPlace(birthPlace);
    }
}