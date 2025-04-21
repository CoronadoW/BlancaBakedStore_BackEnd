package com.coronado.blancabakedstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

    private String userName;
    private String password;
    private String roleType;
    private String jwtToken;
}
