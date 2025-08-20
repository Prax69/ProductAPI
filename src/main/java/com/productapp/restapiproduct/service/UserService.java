package com.productapp.restapiproduct.service;


import com.productapp.restapiproduct.entity.User;
import com.productapp.restapiproduct.entity.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO save(UserDTO userDTO);          // signup / create user
    UserDTO findById(int id);               // get user by id
    List<UserDTO> findAll();                // get all users
    void deleteById(int id);                // delete user


//    void registerUser(UserDTO dto);
}