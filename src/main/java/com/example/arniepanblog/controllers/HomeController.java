package com.example.arniepanblog.controllers;

import com.example.arniepanblog.config.SeedData;
import com.example.arniepanblog.models.Account;
import com.example.arniepanblog.models.Post;
import com.example.arniepanblog.services.AccountService;
import com.example.arniepanblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    public PostService postService;

    @Autowired
    public AccountService accountService;

    @GetMapping("/")
    public String home(Model model) {
        List<Post> posts = postService.getAll();
        Account account = accountService.findByEmail(SeedData.getCurrentUserEmail()).get();
        model.addAttribute("posts", posts); // Send data to views
        model.addAttribute("account", account);
        return "home";
    }
}
