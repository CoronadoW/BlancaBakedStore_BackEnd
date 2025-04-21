package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.exceptions.EntityAlreadyExistsException;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.Role;
import com.coronado.blancabakedstore.model.User;
import com.coronado.blancabakedstore.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements IRoleService{

    private final IRoleRepository iRoleRepo;
    @Autowired
    public RoleService(IRoleRepository iRoleRepo) {
        this.iRoleRepo = iRoleRepo;
    }


    @Override
    public Role createRole(String roleType) {
        if(iRoleRepo.existsByRoleType(roleType)){
            throw new EntityAlreadyExistsException("Rol ya existente: " + roleType);
        }
        Role role = new Role();
        List<User> usersList = new ArrayList<>();

        role.setRoleType(roleType);
        role.setUserList(usersList);

        return iRoleRepo.save(role);
    }

    @Override
    public Role getRoleByRoleType(String roleType) {
        return iRoleRepo.findByRoleType(roleType)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con: " + roleType));
    }

    @Override
    public List<Role> getAllRoles() {
        return iRoleRepo.findAll();
    }

    @Override
    public Role deleteRole(String roleType) {
        Role role = iRoleRepo.findByRoleType(roleType)
                .orElseThrow(()-> new EntityNotFoundException("Rol no encontrado: " + roleType));
        iRoleRepo.deleteByRoleType(roleType);
        return role;
    }


}
