package com.java.projects.service;

import com.java.projects.model.Role;
import com.java.projects.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role getUserRole() throws Exception{
        return roleRepository.findRoleByName("ROLE_USER")
                .orElseThrow(()-> new Exception("Nie znaleziono roli użytkownika"));
    }
}
