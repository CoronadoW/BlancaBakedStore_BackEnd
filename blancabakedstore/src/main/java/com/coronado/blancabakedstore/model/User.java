package com.coronado.blancabakedstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String userName;
    private String password;

    @ManyToOne
    @JoinColumn(name = "fk_roleId")
   // @JsonBackReference // To avoid  recursive serialization with Role(stackoverflow)
    private Role role;

}
