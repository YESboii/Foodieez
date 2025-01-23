package com.foodiezz.project.service;

import com.foodiezz.project.model.CustomUserDetail;
import com.foodiezz.project.model.User;
import com.foodiezz.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User>user = userRepository.findUserByEmail(email);
        user.orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        return user.map(CustomUserDetail::new).get();
    }
}
