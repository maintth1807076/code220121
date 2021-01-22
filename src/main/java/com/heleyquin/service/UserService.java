package com.heleyquin.service;

import com.heleyquin.dto.UserDTO;
import com.heleyquin.dto.UserSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDTO> getUsers(Pageable pageable, UserSearchRequest userSearchRequest);

    UserDTO getUser(int id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(int id, UserDTO userDTO);

    void deleteUser(int id);
}
