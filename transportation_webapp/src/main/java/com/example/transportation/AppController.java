package com.example.transportation;


import java.util.List;

import com.example.transportation.Config.MyUserDetails;
import com.example.transportation.Config.MyUserDetailsService;
import com.example.transportation.Repo.UserRepo;
import com.example.transportation.TransporationORM;
import com.example.transportation.TransporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    private TransporationService service;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

//    public AppController(TransporationService service, MyUserDetailsService myUserDetailsService) {
//        this.service = service;
//        this.myUserDetailsService = myUserDetailsService;
//    }

    @RequestMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<TransporationORM> listPerf = service.listAll(keyword);
        model.addAttribute("listPerf", listPerf);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewBookForm(Model model) {
        TransporationORM perf = new TransporationORM();
        model.addAttribute("perf", perf);
        return "new_perf";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveBook(@ModelAttribute("perf") TransporationORM perf) {
        service.save(perf);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditAutoForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_perf");
        TransporationORM perf = service.get(id);
        mav.addObject("perf", perf);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteBook(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        myUserDetailsService.register(user); // Используем метод register из MyUserDetailsService
        return "redirect:/login"; // Перенаправление на страницу логина после успешной регистрации
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }
}