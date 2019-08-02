package com.codegym.controller;

import com.codegym.model.Receptionist;
import com.codegym.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistController {
    @Autowired
    private ReceptionistService<Receptionist> receptionistService;

    @GetMapping("/list")
    public ModelAndView findAll() {
        List<Receptionist> receptionistList = receptionistService.findAll();
        ModelAndView modelAndView = new ModelAndView("/receptionist/list");
        modelAndView.addObject("receptionistList", receptionistList);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/receptionist/create");
        modelAndView.addObject("receptionist", new Receptionist());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveReceptionist(@ModelAttribute("receptionist") Receptionist receptionist) {
        receptionist.setId((int)(Math.random() * 10000));
        receptionistService.save(receptionist);
        ModelAndView modelAndView = new ModelAndView("/receptionist/create");
        modelAndView.addObject("receptionist", new Receptionist());
        modelAndView.addObject("message", "created!");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Integer id) {
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
    public ModelAndView updateReceptionist(Receptionist receptionist) {
        ModelAndView modelAndView = new ModelAndView("/receptionist/edit");
        receptionistService.update(receptionist.getId(), receptionist);
        modelAndView.addObject("receptionist", receptionist);
        modelAndView.addObject("message", "updated!");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Integer id) {
        Receptionist receptionist = receptionistService.findById(id);
        if (receptionist != null) {
            ModelAndView modelAndView = new ModelAndView("/receptionist/delete");
            modelAndView.addObject("receptionist", receptionist);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/remove")
    public ModelAndView removeReceptionist(@ModelAttribute("receptionist") Receptionist receptionist) {
        receptionistService.remove(receptionist.getId());
        ModelAndView modelAndView = new ModelAndView("/receptionist/delete");
        modelAndView.addObject("message", "delete receptionist successfully");
        return modelAndView;
    }

    @GetMapping("/information/{id}")
    public ModelAndView infoReceptionist(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("/receptionist/information");
        modelAndView.addObject("receptionist", receptionistService.findById(id));
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView findName(@RequestParam("word") String word) {
        List<Receptionist> filteredList = receptionistService.search(word);
        ModelAndView modelAndView = new ModelAndView("/receptionist/list");
        modelAndView.addObject("receptionistList", filteredList);
        return modelAndView;
    }

}
