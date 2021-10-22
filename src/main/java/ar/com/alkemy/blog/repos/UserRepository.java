package ar.com.alkemy.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.alkemy.blog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String userName);

    public User findByEmail(String email);

} 

