package ar.com.alkemy.blog.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.alkemy.blog.entities.Post;
import ar.com.alkemy.blog.repos.PostRepository;
import ar.com.alkemy.blog.repos.UsuarioRepository;

@Service
public class PostService {

    @Autowired
    PostRepository repo;

    @Autowired
    UsuarioRepository urepo;

    public boolean createPost(Post p) {
        if(existe(p.getTitle()))
        return false;
        repo.save(p);
        return true;  
    }

    public boolean existe(String title){
        Post p = repo.findByTitle(title);
        return p != null;
    }

    public List<Post> getPosts() {
        return repo.findAll();
    }

    public Post getPostById(Integer postId) {
        return repo.findByPostId(postId);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    public Post findByTitle(String title) {
        return repo.findByTitle(title);
    }

    public List<Post> findByCategory(String category) {
        return repo.findByCategory(category);
    }

    public Post update(Post p) {
        return repo.save(p);
    }

    public List<Post> postsByOrder() {
        return this.getPosts().stream().sorted(Comparator.comparing(Post::getCreationdate).reversed()).collect(Collectors.toList());
    }



}
