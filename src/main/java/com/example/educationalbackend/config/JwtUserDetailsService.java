package com.example.educationalbackend.config;

import com.example.educationalbackend.entity.UserEntity;
import com.example.educationalbackend.exception.WrongLoginCredentialsException;
import com.example.educationalbackend.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
        return User.withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(userEntity.getUserRole()).build();
    }

    public UserDetails verifyUser(String username, String password) throws WrongLoginCredentialsException {
        UserEntity user = userRepository.login(username, password).orElseThrow(WrongLoginCredentialsException::new);
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getUserRole()).build();
    }
}
