package ar.com.alkemy.blog.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ar.com.alkemy.blog.repos.UsuarioRepository;
import ar.com.alkemy.blog.security.Crypto;
import ar.com.alkemy.blog.entities.Usuario;

@Service
public class UsuarioService {

 @Autowired
 UsuarioRepository repo;

  public Usuario buscarPorUsername(String username) {
    return repo.findByUsername(username);
  }

  public Usuario login(String username, String password) {

    Usuario u = buscarPorUsername(username);

    if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getEmail().toLowerCase()))) {

      throw new BadCredentialsException("Usuario o contraseña invalida");
    }

    return u;
  }

  public Usuario crearUsuario(String email, String password){
    Usuario u = new Usuario();
    u.setUsername(email);
    u.setEmail(email);
    u.setPassword(Crypto.encrypt(password, email.toLowerCase()));

     repo.save(u);

     return u;
  }

  public Usuario buscarPorEmail(String email) {

    return repo.findByEmail(email);
  }

  public Usuario buscarPor(Integer id) {
    Optional<Usuario> usuarioOp = repo.findById(id);

    if (usuarioOp.isPresent()) {
      return usuarioOp.get();
    }

    return null;
  }

  public Map<String, Object> getUserClaims(Usuario usuario) {
    Map<String, Object> claims = new HashMap<>();

    claims.put("userType", usuario.getUserId());

    return claims;
  }

  public UserDetails getUserAsUserDetail(Usuario usuario) {
    UserDetails uDetails;

    uDetails = new User(usuario.getUsername(), usuario.getPassword(), getAuthorities(usuario));

    return uDetails;
  }

  // Usamos el tipo de datos SET solo para usar otro diferente a List private
  Set<? extends GrantedAuthority> getAuthorities(Usuario usuario) {

    Set<SimpleGrantedAuthority> authorities = new HashSet<>();

    Integer userType = usuario.getUserId();

    authorities.add(new SimpleGrantedAuthority("CLAIM_userType_" + userType.toString()));

    return authorities;
  }




}   
