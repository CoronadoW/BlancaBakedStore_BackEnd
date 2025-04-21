package com.coronado.blancabakedstore.controller;

import com.coronado.blancabakedstore.dto.UserDto;
import com.coronado.blancabakedstore.model.User;
import com.coronado.blancabakedstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService iUserServ;
    @Autowired
    public UserController(IUserService iUserServ) {
        this.iUserServ = iUserServ;
    }


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(iUserServ.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/get/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName){
        return new ResponseEntity<>(iUserServ.getUserByUserName(userName), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(iUserServ.getAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<User> deleteUser(@PathVariable String userName){
        return new ResponseEntity<>(iUserServ.deleteUser(userName), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody User user){
        return new ResponseEntity<>(iUserServ.editUser(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto){
        return new ResponseEntity<>(iUserServ.login(userDto), HttpStatus.OK);
    }
}
