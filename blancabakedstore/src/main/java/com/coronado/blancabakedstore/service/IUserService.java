package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.UserDto;
import com.coronado.blancabakedstore.model.User;

import java.util.List;

public interface IUserService {

    User createUser(UserDto userDto);

    User getUserByUserName(String userName);

    List<User> getAllUsers();

    User deleteUser(String userName);

    User editUser(User user);

    UserDto login(UserDto userDto);
}