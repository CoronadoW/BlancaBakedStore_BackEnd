package com.coronado.blancabakedstore.controller;

import com.coronado.blancabakedstore.model.Role;
import com.coronado.blancabakedstore.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final IRoleService iRoleServ;
    @Autowired
    public RoleController(IRoleService iRoleServ) {
        this.iRoleServ = iRoleServ;
    }

    @PostMapping("/create/{roleType}")
    public ResponseEntity<Role> createRole(@PathVariable String roleType){
        return new ResponseEntity<>(iRoleServ.createRole(roleType), HttpStatus.CREATED);
    }

    @GetMapping("/get/{roleType}")
    public ResponseEntity<Role> getRoleByRoleType(@PathVariable String roleType){
        return new ResponseEntity<>(iRoleServ.getRoleByRoleType(roleType), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(iRoleServ.getAllRoles(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{roleType}")
    public ResponseEntity<Role> deleteRole(@PathVariable String roleType){
        return new ResponseEntity<>(iRoleServ.deleteRole(roleType), HttpStatus.OK);
    }

}
