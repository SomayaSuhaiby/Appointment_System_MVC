package com.example.appointmentsystem.services;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.appointmentsystem.exceptions.UserAlreadyExistsException;
import com.example.appointmentsystem.DTOs.LoginRequestDTO;
import com.example.appointmentsystem.DTOs.UserDTO;
import com.example.appointmentsystem.exceptions.InvalidCredentialsException;
import com.example.appointmentsystem.exceptions.RoleNotFoundException;
import com.example.appointmentsystem.model.Role;
import com.example.appointmentsystem.model.User;
import com.example.appointmentsystem.repositories.RoleRepository;
import com.example.appointmentsystem.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    // Registeration
    public void register(UserDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = new User();

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        Role role = roleRepository.findByName("USER");
        if (role == null) {
            throw new RoleNotFoundException("USER role not found");
        }
        // bind role with user

        user.setRoles(Set.of(role));

        userRepository.save(user);
    }

    // login
    public User login(LoginRequestDTO dto) {

        User foundUser = userRepository.findByEmail(dto.getEmail());

        if (foundUser != null && passwordEncoder.matches(dto.getPassword(), foundUser.getPassword())) {
            return foundUser;
        }
        throw new InvalidCredentialsException("Invalid email or password");
    }
}
