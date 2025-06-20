package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.UserDto;
import com.coronado.blancabakedstore.dto.UserResponseDto;
import com.coronado.blancabakedstore.exceptions.EntityAlreadyExistsException;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.exceptions.UnauthorizedException;
import com.coronado.blancabakedstore.model.Role;
import com.coronado.blancabakedstore.model.User;
import com.coronado.blancabakedstore.repository.IRoleRepository;
import com.coronado.blancabakedstore.repository.IUserRepository;
import com.coronado.blancabakedstore.utils.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{

    private final IUserRepository iUserRepo;
    private final IRoleRepository iRoleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(IUserRepository iUserRepo, IRoleRepository iRoleRepo, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.iUserRepo = iUserRepo;
        this.iRoleRepo = iRoleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }


    @Override
    @Transactional
    public UserResponseDto createUser(UserDto userDto) {
        if(iUserRepo.existsByUserName(userDto.getUserName())){
            throw new EntityAlreadyExistsException("Usuario ya existente con en nombre de usuario: " + userDto.getUserName());
        }
        Role role = iRoleRepo.findByRoleType(userDto.getRoleType())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con el nombre: " + userDto.getRoleType()));

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(encryptedPassword);
        user.setRole(role);

        iUserRepo.save(user);

        UserResponseDto userRespDto = new UserResponseDto();
        userRespDto.setUserName(user.getUserName());
        userRespDto.setRoleType(user.getRole().getRoleType());

        return userRespDto;
    }

    @Override
    public UserResponseDto getUserByUserName(String userName) {
         User user = iUserRepo.findByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el Nombre de Usuario: " + userName));
         UserResponseDto userResDto = new UserResponseDto();
         userResDto.setUserName(user.getUserName());
         userResDto.setRoleType(user.getRole().getRoleType());
         return userResDto;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = iUserRepo.findAll();
        return users.stream()
                .map(user -> new UserResponseDto(user.getUserName(), user.getRole().getRoleType()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User deleteUser(String userName) {
        User usr = iUserRepo.findByUserName(userName)
                .orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado con: " + userName));
        iUserRepo.deleteByUserName(userName);
        return usr;
    }

    @Override
    @Transactional
    public User editUser(User user) {
        User usr = iUserRepo.findByUserName(user.getUserName())
                .orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado con: " + user.getUserName()));
        usr.setUserName(user.getUserName());
        usr.setPassword(passwordEncoder.encode(user.getPassword()));
        usr.setRole(user.getRole());
        return iUserRepo.save(usr);
    }

    @Override
    public UserDto  login(UserDto userDto) {
        User user = iUserRepo.findByUserName(userDto.getUserName())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con : " + userDto.getUserName()));

        // Verificar la contraseña
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Password incorrecto");
        }

        String roleType = user.getRole().getRoleType();
        String jwtToken = jwtUtils.generateJwtToken(user.getUserName(), roleType);

        UserDto responseDto = new UserDto();
        responseDto.setUserName(user.getUserName());
        responseDto.setRoleType(roleType);
        responseDto.setJwtToken(jwtToken);

        return responseDto;


    }

}
