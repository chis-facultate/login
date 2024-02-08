package org.example.service_controller;

import org.example.model.CredentialeLogin;
import org.example.model.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class AuthController {
    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    /**
     * Authentication authenticate = authenticationManager.authenticate(...)
     * It attempts to authenticate the user by using the authenticationManager.
     * It creates an Authentication object with the provided UsernamePasswordAuthenticationToken,
     * which contains the user's login credentials.
     *
     * SecurityContextHolder.getContext().setAuthentication(authenticate)
     * After successful authentication, the authenticated Authentication object is set in the security context.
     * This step effectively logs the user in.
     *
     * @param credentialeLogin obiect de transfer continand credentialele care trebuie verificate
     * @return HTTP STATUS CREATED
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CredentialeLogin credentialeLogin,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        String email = credentialeLogin.getEmail();
        String parola = credentialeLogin.getParola();

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^");

        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, parola));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        System.out.println("#############################");

        Optional<UserEntity> optional = userRepository.findByEmail(email);
        UserEntity userEntity = optional.get();
        // trimitem un cookie cu user id catre client
        Cookie userCookie = new Cookie("userId", String.valueOf(userEntity.getId()));
        userCookie.setMaxAge(-1); // cookie valabil pe durata sesiunii
        userCookie.setPath("/");
        response.addCookie(userCookie);

        return new ResponseEntity<>("Login successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("Logout successfully!", HttpStatus.OK);
    }
}