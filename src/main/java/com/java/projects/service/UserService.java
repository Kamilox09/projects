package com.java.projects.service;

import com.java.projects.model.User;
import com.java.projects.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository,
                       RoleService roleService){
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void registerUser(User user) throws Exception{
        if(userRepository.existsByUsername(user.getUsername()))
            throw new Exception("Nazwa użytkownika zajęta.");
        user.setRole(roleService.getUserRole());
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserByUsernameIfNotMadeReservation(String username) throws Exception {
        User user = findByUsername(username);
        if(user.getProject()!=null)
            throw new Exception("Użytkownik zarezerwował już projekt");
        return user;
    }
}
