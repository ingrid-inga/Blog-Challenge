package ar.com.alkemy.blog.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.alkemy.blog.entities.Post;
import ar.com.alkemy.blog.models.response.GenericResponse;
import ar.com.alkemy.blog.services.PostService;

@RestController
public class PostController {

    @Autowired
    private PostService service;

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody Post post) {

        GenericResponse r = new GenericResponse();
        service.createPost(post);

        r.isOk = true;
        r.id = post.getId();
        r.message = "Success!, the post has been created";

        return ResponseEntity.ok(r);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(service.getPosts());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Integer id) {
        GenericResponse r = new GenericResponse();
        if (service.getPostById(id) == null) {
            r.isOk = false;
            r.message = "Id number doesn't exist";
            return ResponseEntity.badRequest().body(r);
        }
        return ResponseEntity.ok(service.getPostById(id));
    }
    
    @GetMapping("/posts/{title}")
    public ResponseEntity<?> findByTitle(@PathVariable String title) {
        GenericResponse r = new GenericResponse();

        if (service.findByTitle(title) == null) {
            r.isOk = false;
            r.message = "Title doesn't exist";
            return ResponseEntity.badRequest().body(r);
        }
        return ResponseEntity.ok(service.findByTitle(title));
    }

    @GetMapping("/posts/{category}")
    public ResponseEntity<?> findByCategory(@PathVariable String category) {
        GenericResponse r = new GenericResponse();

        if (service.findByCategory(category) == null) {
            r.isOk = false;
            r.message = "Title doesn't exist";
            return ResponseEntity.badRequest().body(r);
        }
        return ResponseEntity.ok(service.findByCategory(category));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        GenericResponse r = new GenericResponse();

        if (service.getPostById(id) == null) {
                r.isOk = false;
                r.message = "The id number doesn't exist";
                return ResponseEntity.badRequest().body(r);
    
            } else {
    
                service.deleteById(id);
                r.isOk = true;
                r.message = "Success!, the post has been deleted";
                return ResponseEntity.ok(r);
    
            }
    }

}
