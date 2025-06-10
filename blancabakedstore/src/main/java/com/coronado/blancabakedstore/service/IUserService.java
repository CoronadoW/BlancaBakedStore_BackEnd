package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.UserDto;
import com.coronado.blancabakedstore.dto.UserResponseDto;
import com.coronado.blancabakedstore.model.User;

import java.util.List;

public interface IUserService {

    UserResponseDto createUser(UserDto userDto);

    UserResponseDto getUserByUserName(String userName);

    List<UserResponseDto> getAllUsers();

    User deleteUser(String userName);

    User editUser(User user);

    UserDto login(UserDto userDto);
}