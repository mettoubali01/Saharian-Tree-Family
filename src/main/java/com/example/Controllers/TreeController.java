package com.example.Controllers;

import com.example.beans.Tree;
import com.example.service.ITreeService;
import org.hibernate.annotations.common.reflection.XMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TreeController {

    @Autowired
    private ITreeService iTreeService;

    @GetMapping("/Trees")
    public List<Tree> getTrees(){
        return iTreeService.getTrees();
    }

    @PostMapping("saveTree")
    public Tree addTree(@RequestBody Tree tree){
        return iTreeService.saveTree(tree);
    }

    @GetMapping("Tree")
    public Tree getTreeByName(@RequestParam("name") String name){
        return iTreeService.getTreeByName(name);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="deleteTree/{name}")
    public void deleteById(@PathVariable String name){
        iTreeService.rmByID(name);
    }

    @DeleteMapping("deleteTreee")
    public void deleteByTree(@RequestBody Tree tree){
        iTreeService.rmTree(tree);
    }
}
