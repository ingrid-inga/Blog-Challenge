package ar.com.alkemy.blog.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.alkemy.blog.entities.Post;
import ar.com.alkemy.blog.entities.Usuario;
import ar.com.alkemy.blog.models.request.PostRequest;
import ar.com.alkemy.blog.models.response.GenericResponse;
import ar.com.alkemy.blog.models.response.PostResponse;
import ar.com.alkemy.blog.services.PostService;
import ar.com.alkemy.blog.services.UsuarioService;

@RestController
public class PostController {

    @Autowired
    private PostService service;

    @Autowired
    private UsuarioService uservice;

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody PostRequest pr) {

        GenericResponse r = new GenericResponse();
        Post p = new Post();
        p.setTitle(pr.title);
        p.setContent(pr.content);
        p.setImagen(pr.image);
        p.setCategory(pr.category);
        p.setCreationdate(pr.creationDate);
        
        Usuario usuario = uservice.buscarPor(pr.userId);
        usuario.addPost(p);

        if(service.createPost(p)){
           r.isOk = true;
           r.id = p.getPostId();
           r.message = "Success!, the post has been created";
           return ResponseEntity.ok(r);
        }else {
           r.isOk = false;
           r.message = "Wrong!, the post has already been created";
        return ResponseEntity.badRequest().body(r);
        }
    }

    @GetMapping("/posts/order")
    public ResponseEntity<List<Post>> postsByOrder() {

        return ResponseEntity.ok(service.postsByOrder());
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getPosts() {
        List<Post> p = service.getPosts();

        List<PostResponse> lista = new ArrayList<>();
        for (Post post : p) {
            PostResponse pR = new PostResponse();
            pR.postId = post.getPostId();
            pR.title = post.getTitle();
            pR.image = post.getImagen();
            pR.category = post.getCategory();
            pR.creationDate = post.getCreationdate();
            lista.add(pR);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Integer postId) {
        GenericResponse r = new GenericResponse();
        if (service.getPostById(postId) == null) {
            r.isOk = false;
            r.message = "Id number doesn't exist";
            return ResponseEntity.badRequest().body(r);
        }
        return ResponseEntity.ok(service.getPostById(postId));
    }
    
    @GetMapping("/api/posts/{title}")
    public ResponseEntity<?> findByTitle(@PathVariable String title) {
        GenericResponse r = new GenericResponse();

        if (service.findByTitle(title) == null) {
            r.isOk = false;
            r.message = "Title doesn't exist";
            return ResponseEntity.badRequest().body(r);
        }
        return ResponseEntity.ok(service.findByTitle(title));
    }

    @GetMapping("/api/post/{category}")
    public ResponseEntity<?> findByCategory(@PathVariable String category) {
        GenericResponse r = new GenericResponse();
        List<Post> p = service.findByCategory(category);

        if ( p == null) {
            r.isOk = false;
            r.message = "Category doesn't exist";
            return ResponseEntity.badRequest().body(r);
        }
        return ResponseEntity.ok(service.findByCategory(category));
    }
    @GetMapping("posts/notDeleted")
    public Iterable<Post> findAll(@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        return service.findAll(isDeleted);
    }

    @PatchMapping("/post/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PostRequest pr) {
        Post p = service.getPostById(id);

        if (p != null) {
            p.setTitle(pr.title);
            p.setContent(pr.content);
            p.setImagen(pr.image);
            p.setCategory(pr.category);
            p.setCreationdate(pr.creationDate);
            p.setUsuario(uservice.buscarPor(pr.userId));
            service.update(p);

            return ResponseEntity.ok("Success!, Post " + p.getPostId() + " has been updated");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id number doesn't exist");
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
