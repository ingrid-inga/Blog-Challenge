package ar.com.alkemy.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.alkemy.blog.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {


    public Usuario findByEmail(String email);

    public Usuario findByUsername(String username);




} 

