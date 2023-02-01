package com.example.arniepanblog.config;

import com.example.arniepanblog.models.Account;
import com.example.arniepanblog.models.Authority;
import com.example.arniepanblog.models.Post;
import com.example.arniepanblog.repositories.AuthorityRepository;
import com.example.arniepanblog.services.AccountService;
import com.example.arniepanblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception
    {
        List<Post> posts = postService.getAll();

        if (posts.size() == 0)
        {
            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);


            Account account1 = new Account();
            Account account2 = new Account();

            account1.setFirstName("user");
            account1.setLastName("user");
            account1.setEmail("user.user@gmail.com");
            account1.setPassword("1");
            Set<Authority> authoritySet1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authoritySet1::add);
            account1.setAuthorities(authoritySet1);

            account2.setFirstName("admin");
            account2.setLastName("admin");
            account2.setEmail("admin.admin@gmail.com");
            account2.setPassword("1");
            Set<Authority> authoritySet2 = new HashSet<>();
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authoritySet2::add);
            account2.setAuthorities(authoritySet2);

            accountService.save(account1);
            accountService.save(account2);

            Post post1 = new Post();
            post1.setTitle("Title of post 1");
            post1.setBody("The body of post 1");
            post1.setAccount(account1);

            Post post2 = new Post();
            post2.setTitle("Title of post 2");
            post2.setBody("The body of post 2");
            post2.setAccount(account2);

            postService.save(post1);
            postService.save(post2);
        }
    }
}
