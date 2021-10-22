package ar.com.alkemy.blog.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.alkemy.blog.entities.User;
import ar.com.alkemy.blog.models.request.*;
import ar.com.alkemy.blog.models.response.LoginResponse;
import ar.com.alkemy.blog.models.response.RegistrationResponse;
import ar.com.alkemy.blog.security.jwt.JWTTokenUtil;
import ar.com.alkemy.blog.services.JWTUserDetailsService;
import ar.com.alkemy.blog.services.UserService;

@RestController

public class AuthController {
    @Autowired
    UserService userService;


    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;


    @PostMapping("api/auth/sign_up")
    public ResponseEntity<RegistrationResponse> postRegisterUser(@RequestBody RegistrationRequest req,
            BindingResult results) {
        RegistrationResponse r = new RegistrationResponse();

        User user = userService.crearUser(req.email, req.password);

        r.isOk = true;
        r.message = "Te registraste con éxitoooo!!!!!!!";
        r.userId = user.getUserId(); 

        return ResponseEntity.ok(r);

    }

    @PostMapping("api/auth/login") 
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest,
            BindingResult results) throws Exception {

        User userLogueado = userService.login(authenticationRequest.username, authenticationRequest.password);

        UserDetails userDetails = userService.getUserAsUserDetail(userLogueado);
        Map<String, Object> claims = userService.getUserClaims(userLogueado);

        // Genero los roles pero con los Claims(los propositos)
        // En este caso nuestros claims tienen info del tipo de usuario
        // y de la entidad que representa
        // Esta info va a viajar con el token, o sea, cualquiera puede
        // ver esos ids de que user pertenecen si logran interceptar el token
        // Por eso es que en cada request debemos validar el token(firma)
        String token = jwtTokenUtil.generateToken(userDetails, claims);

        // Cambio para que devuelva el full perfil
        User u = userService.buscarPorUsername(authenticationRequest.username);

        LoginResponse r = new LoginResponse();
        r.id = u.getUserId();
        r.username = authenticationRequest.username;
        r.email = u.getEmail();
        r.token = token;

        return ResponseEntity.ok(r);

    }

} 


