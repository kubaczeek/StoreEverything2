
package com.example.storeeverything.service;

import com.example.storeeverything.dto.UserDto;
import com.example.storeeverything.model.Role;
import com.example.storeeverything.model.User;
import com.example.storeeverything.repository.RoleRepository;
import com.example.storeeverything.repository.UserRepository;
import java.util.Arrays;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null) {
            throw new RuntimeException("No user logged in");
        } else {
            return this.userRepository.findByName(username);
        }
    }

    public void saveUser(UserDto user) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role = this.roleRepository.findByName("USER");
        newUser.setRoles(Arrays.asList(role));
        this.userRepository.save(newUser);
    }

    public User findUserByName(String name) {
        return this.userRepository.findByName(name);
    }

    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setName(str[0]);
        userDto.setId(user.getId());
        return userDto;
    }
}
