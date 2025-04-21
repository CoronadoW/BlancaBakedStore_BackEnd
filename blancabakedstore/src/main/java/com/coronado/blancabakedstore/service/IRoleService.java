package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.model.Role;

import java.util.List;

public interface IRoleService {

    Role createRole(String roleType);

    Role getRoleByRoleType(String roleType);

    List<Role> getAllRoles();

    Role deleteRole(String roleType);


}
