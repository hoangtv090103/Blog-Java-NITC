package com.example.arniepanblog.config;

import com.example.arniepanblog.models.Account;
import com.example.arniepanblog.models.Authority;
import com.example.arniepanblog.models.Post;
import com.example.arniepanblog.repositories.AuthorityRepository;
import com.example.arniepanblog.services.AccountService;
import com.example.arniepanblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityRepository authorityRepository;

    public static String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();
        Optional<Account> adminOptionalAccount = accountService.findByEmail("admin.admin@gmail.com");

        // Create Admin account if it hasn't exist
        if (posts.size() == 0 && adminOptionalAccount.isEmpty()) {
//            Authority user = new Authority();
//            user.setName("ROLE_USER");
//            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);


//            Account account1 = new Account();
            Account adminAccount = new Account();

//            account1.setFirstName("user");
//            account1.setLastName("user");
//            account1.setEmail("user.user@gmail.com");
//            account1.setPassword("1");
//            Set<Authority> authoritySet1 = new HashSet<>();
//            authorityRepository.findById("ROLE_USER").ifPresent(authoritySet1::add);
//            account1.setAuthorities(authoritySet1);

            adminAccount.setFirstName("admin");
            adminAccount.setLastName("admin");
            adminAccount.setEmail("admin.admin@gmail.com");
            adminAccount.setPassword("superadmin");
            Set<Authority> authoritySet2 = new HashSet<>();
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authoritySet2::add);
            adminAccount.setAuthorities(authoritySet2);

//            accountService.save(account1);
            accountService.save(adminAccount);
        }
    }
}
