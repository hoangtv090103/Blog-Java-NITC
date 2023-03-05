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
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    public PostService postService;

    @Autowired
    public AccountService accountService;

    @GetMapping("/")
    public String home(Model model) {
        List<Post> posts = postService.getAll();
        for (Post post : posts) {
            post.setHasReadPermission(getReadPerm(post.getId()));
        }
        Account account = accountService.findByEmail(SeedData.getCurrentUserEmail()).get();
        model.addAttribute("posts", posts); // Send data to views
        model.addAttribute("account", account);
        return "home";
    }

    public Boolean getReadPerm(Long id) {
        Post post = new Post();
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            post = optionalPost.get();
        }
        Optional<Account> optionalAccount = accountService.findByEmail(SeedData.getCurrentUserEmail());
        Account activeAccount;
        if (optionalAccount.isPresent()) {
            activeAccount = optionalAccount.get();
        } else {
            return false;
        }
        return activeAccount.getEmail().equals(post.getAccount().getEmail()) || activeAccount.getAuthorities().toString().contains("ROLE_ADMIN") || post.getPublishMode().equals("Public");
    }
}
