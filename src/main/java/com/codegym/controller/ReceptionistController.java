package com.codegym.controller;

import com.codegym.model.Receptionist;
import com.codegym.service.ReceptionistService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistController {
    @Autowired
    private ReceptionistService<Receptionist> receptionistService;
    @GetMapping("/list")
    public ModelAndView findAll(){
        List<Receptionist> receptionistList = receptionistService.findAll();
        ModelAndView modelAndView = new ModelAndView("/receptionist/list");
        modelAndView.addObject("receptionistList", receptionistList);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/receptionist/create");
        modelAndView.addObject("receptionist", new Receptionist());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveReceptionist(@ModelAttribute("receptionist") Receptionist receptionist) {
        receptionistService.save(receptionist);
        ModelAndView modelAndView = new ModelAndView("/receptionist/create");
        modelAndView.addObject("receptionist", new Receptionist());
        modelAndView.addObject("message", "created!");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Integer id){
        Receptionist receptionist = receptionistService.findById(id);
        if (receptionist != null) {
            ModelAndView modelAndView = new ModelAndView("/receptionist/edit");
            modelAndView.addObject("receptionist", receptionist);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/update")
    public ModelAndView updateReceptionist(Receptionist receptionist){
        ModelAndView modelAndView = new ModelAndView("/receptionist/edit");
        receptionistService.update(receptionist.getId(), receptionist);
        modelAndView.addObject("receptionist", receptionist);
        modelAndView.addObject("message", "updated!");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Integer id){
        Receptionist receptionist = receptionistService.findById(id);
        if (receptionist != null) {
            ModelAndView modelAndView = new ModelAndView("/receptionist/delete");
            modelAndView.addObject("receptionist", receptionist);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/remove")
    public ModelAndView removeReceptionist(@ModelAttribute("receptionist") Receptionist receptionist){
        receptionistService.remove(receptionist.getId());
        ModelAndView modelAndView = new ModelAndView("/receptionist/delete");
        modelAndView.addObject("message", "delete receptionist successfully");
        return modelAndView;
    }

    @GetMapping("/information/{id}")
    public ModelAndView infoReceptionist(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("/receptionist/information");
        modelAndView.addObject("receptionist", receptionistService.findById(id));
       return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView findName(@RequestParam("word") String word){
        List<Receptionist> filteredList = new ArrayList<>();
        for (Receptionist receptionist: receptionistService.findAll()){
            if(receptionist.getName().toLowerCase().contains(word.trim().toLowerCase())){
                filteredList.add(receptionist);
            }
        }
        ModelAndView modelAndView = new ModelAndView("/receptionist/list");
        modelAndView.addObject("receptionistList",filteredList);
        return modelAndView;
    }

}
