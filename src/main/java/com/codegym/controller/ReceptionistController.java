package com.codegym.controller;

import com.codegym.model.Receptionist;
import com.codegym.model.ReceptionistForm;
import com.codegym.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistController {
    @Autowired
    private ReceptionistService<Receptionist> receptionistService;

    @Autowired
    Environment env;

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
        modelAndView.addObject("receptionistForm", new ReceptionistForm());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveReceptionist(@ModelAttribute("receptionistForm") ReceptionistForm receptionistForm) {
        MultipartFile multipartFile = receptionistForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("upload_file".toString());
        try {
            FileCopyUtils.copy(receptionistForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Receptionist receptionist = new Receptionist(receptionistForm.getId(), receptionistForm.getName(), receptionistForm.getAge(), receptionistForm.getAddress(), receptionistForm.getHobby(), fileName);
        receptionist.setId((int) (Math.random() * 100000));
        receptionistService.save(receptionist);
        ModelAndView modelAndView = new ModelAndView("/receptionist/create");
        modelAndView.addObject("message", "Created new receptionist succesfully!");
        modelAndView.addObject("receptionistForm", new ReceptionistForm());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Integer id) {
        Receptionist receptionist = receptionistService.findById(id);
        if (receptionist != null) {
            ReceptionistForm receptionistForm = new ReceptionistForm(receptionist.getId(), receptionist.getName(),
                    receptionist.getAge(), receptionist.getAddress(), receptionist.getHobby(), null);
            ModelAndView modelAndView = new ModelAndView("/receptionist/edit");
            modelAndView.addObject("receptionistForm", receptionistForm);
            modelAndView.addObject("receptionist", receptionist);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error-404");
            return modelAndView;
        }
    }

    @PostMapping("/update")
    public ModelAndView updateReceptionist(@ModelAttribute ReceptionistForm receptionistForm) {
        MultipartFile multipartFile = receptionistForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        try {
            FileCopyUtils.copy(receptionistForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Receptionist receptionist = new Receptionist(receptionistForm.getId(), receptionistForm.getName(),
                receptionistForm.getAge(), receptionistForm.getAddress(), receptionistForm.getHobby(), fileName);
        receptionistService.update(receptionist.getId(), receptionist);
        ModelAndView modelAndView = new ModelAndView("/receptionist/edit");
        modelAndView.addObject("receptionistForm", receptionistForm);
        modelAndView.addObject("message", "Updated new receptionist information successfully");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Integer id) {
        Receptionist receptionist = receptionistService.findById(id);
        if (receptionist != null) {
            ReceptionistForm receptionistForm = new ReceptionistForm(receptionist.getId(), receptionist.getName(),
                    receptionist.getAge(), receptionist.getAddress(), receptionist.getHobby(), null);
            ModelAndView modelAndView = new ModelAndView("/receptionist/delete");
            modelAndView.addObject("receptionist", receptionist);
            modelAndView.addObject("receptionistForm", receptionistForm);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error-404");
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
        Receptionist receptionist = receptionistService.findById(id);
        if (receptionist != null) {
            ReceptionistForm receptionistForm = new ReceptionistForm(receptionist.getId(), receptionist.getName(),
                    receptionist.getAge(), receptionist.getAddress(), receptionist.getHobby(), null);
            ModelAndView modelAndView = new ModelAndView("/receptionist/information");
            modelAndView.addObject("receptionistForm", receptionistForm);
            modelAndView.addObject("receptionist", receptionist);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error-404");
            return modelAndView;
        }
    }

    @GetMapping("/search")
    public ModelAndView findName(@RequestParam("word") String word) {
        List<Receptionist> filteredList = receptionistService.search(word);
        ModelAndView modelAndView = new ModelAndView("/receptionist/list");
        modelAndView.addObject("receptionistList", filteredList);
        return modelAndView;
    }
}
