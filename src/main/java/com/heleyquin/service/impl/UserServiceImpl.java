package com.heleyquin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heleyquin.dto.Test;
import com.heleyquin.dto.UserDTO;
import com.heleyquin.dto.UserSearchRequest;
import com.heleyquin.entity.User;
import com.heleyquin.error.RecordNotFoundException;
import com.heleyquin.repository.UserRepository;
import com.heleyquin.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Page<UserDTO> getUsers(Pageable pageable, @RequestParam UserSearchRequest userSearchRequest) {
        List<User> users = getUsersByFields(Test.getParams(userSearchRequest));
        Page<User> page = new PageImpl<>(users, pageable, users.size());
        return page.map(user -> objectMapper.convertValue(user, UserDTO.class));
    }

    @Override
    public UserDTO getUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RecordNotFoundException(String.valueOf(id));
        }
        return objectMapper.convertValue(optionalUser.get(), UserDTO.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = objectMapper.convertValue(userDTO, User.class);
        return objectMapper.convertValue(userRepository.save(user), UserDTO.class);
    }

    @SneakyThrows(JsonProcessingException.class)
    @Override
    public UserDTO updateUser(int id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RecordNotFoundException(String.valueOf(id));
        }
        User user = objectMapper.readerForUpdating(optionalUser.get()).readValue(objectMapper.writeValueAsString(userDTO));
        return objectMapper.convertValue(userRepository.save(user), UserDTO.class);
    }

    @Override
    public void deleteUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RecordNotFoundException(String.valueOf(id));
        }
        userRepository.deleteById(id);
    }

    /**
     * {@link User}
     * @param params
     * @return
     */
    public List<User> getUsersByFields(Map<String, Object> params) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select * from users");
        List<String> whereClause = new ArrayList<>();
        for (String key: params.keySet()) {
            Object value = params.get(key);
            if (value.getClass().equals(String.class)) {
                whereClause.add(String.format(" %s like %s ", key, ":" + key));
            } else if (value.getClass().equals(Date.class)) {
                whereClause.add(String.format(" date(%s) = :%s ", key, key));
            } else {
                whereClause.add(String.format(" %s = :%s ", key, key));
            }
        }
        if (!params.isEmpty()) {
            queryBuilder.append(" where").append(String.join("and", whereClause));
        }
        Query query = entityManager.createNativeQuery(queryBuilder.toString(), User.class);
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (value.getClass().equals(String.class)) {
                query.setParameter(key, "%" + params.get(key).toString().trim() + "%");
            } else {
                query.setParameter(key, params.get(key));
            }
        }
        return query.getResultList();
    }
}
