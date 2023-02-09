package com.example.arniepanblog.services;

import com.example.arniepanblog.models.Post;
import com.example.arniepanblog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Optional<Post> getById(Long id)
    {
        return postRepository.findById(id);
    }

    public List<Post> getAll()
    {
        return postRepository.findAll();
    }

    public Post save (Post post)
    {
        if (post.getId() == null)
        {
            post.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        }

        return postRepository.save(post);
    }

    public void delete(Post post)
    {
        postRepository.delete(post);
    }

}
