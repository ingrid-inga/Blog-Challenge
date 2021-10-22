package ar.com.alkemy.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.alkemy.blog.entities.Post;
import ar.com.alkemy.blog.repos.PostRepository;

@Service
public class PostService {

    @Autowired
    PostRepository repo;

    public void createPost(Post post) {
        repo.save(post);
    }

    public List<Post> getPosts() {
        return repo.findAll();
    }

    public Optional<Post> getPostById(Integer id) {
        return repo.findById(id);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    public Post findByTitle(String title) {
        return repo.findByTitle(title);
    }

    public Post findByCategory(String category) {
        return repo.findByCategory(category);
    }



}
