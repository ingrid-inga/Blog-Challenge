package ar.com.alkemy.blog.repos;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.alkemy.blog.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAll();

    Post findByTitle(String title);
    Post findByPostId(Integer postId);

    List<Post> findByCategory(String category);

    
}
