package org.example;

import org.example.model.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Metoda e denumita loadUserByUsername, dar cautarea se face dupa email
    // E obligatorie implementarea metodei din interfata UserDetailsService
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("****************");

        System.out.println(email);

        System.out.println("__________");

        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        System.out.println(optionalUser);
        if (optionalUser.isEmpty()) {

            System.out.println("!!!!!!!!!!!!!");

            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        System.out.println("&&&&&&&&&");

        return new org.springframework.security.core.userdetails.User(
                optionalUser.get().getEmail(),
                optionalUser.get().getParola(),
                new ArrayList<>()
        );
    }
}