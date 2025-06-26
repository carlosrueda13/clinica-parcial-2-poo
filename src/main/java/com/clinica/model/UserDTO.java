package com.clinica.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    String id;
    String name;
    String phone;
    String username;
    String password;
    String specialty; // Only for doctors
    MedRecord medRecord;

}

