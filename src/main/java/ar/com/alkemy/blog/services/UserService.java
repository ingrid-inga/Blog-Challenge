package ar.com.alkemy.blog.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ar.com.alkemy.blog.repos.UserRepository;
import ar.com.alkemy.blog.security.Crypto;

@Service
public class UserService {


 @Autowired
 UserRepository userRepository;

  public User buscarPorUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User login(String username, String password) {

    /**
     * Metodo IniciarSesion recibe usuario y contraseña validar usuario y contraseña
     */

    User u = buscarPorUsername(username);

    if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getEmail().toLowerCase()))) {

      throw new BadCredentialsException("Usuario o contraseña invalida");
    }

    return u;
  }

  public User crearUser(String email, String password) {
return null;
}

  public User crearUser(String email, String password){

    User user = new User();
    user.setUsername(email);
    user.setPassword(Crypto.encrypt(password, email.toLowerCase()));

    return user;
  }

  public User buscarPorEmail(String email) {

    return userRepository.findByEmail(email);
  }

  public User buscarPor(Integer id) {
    Optional<User> usuarioOp = userRepository.findById(id);

    if (userOp.isPresent()) {
      return userOp.get();
    }

    return null;
  }

/*  public Map<String, Object> getUserClaims(User user) {
    Map<String, Object> claims = new HashMap<>();

    claims.put("userType", user.getTipoUsuario());

    if (user.obtenerEntityId() != null)
      claims.put("entityId", user.obtenerEntityId());

    return claims;
  }*/

  public UserDetails getUserAsUserDetail(User user) {
    UserDetails uDetails;

    uDetails = new User(user.getUsername(), user.getPassword(), getAuthorities(user));

    return uDetails;
  }

  // Usamos el tipo de datos SET solo para usar otro diferente a List private
 /* Set<? extends GrantedAuthority> getAuthorities(User user) {

    Set<SimpleGrantedAuthority> authorities = new HashSet<>();

    String userType = user.getUsername();

    authorities.add(new SimpleGrantedAuthority("CLAIM_userType_" + userType.toString()));

    if (user.obtenerEntityId() != null)
      authorities.add(new SimpleGrantedAuthority("CLAIM_entityId_" + user.obtenerEntityId()));
    return authorities;
  }*/



}   
