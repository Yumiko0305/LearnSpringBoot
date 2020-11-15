package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public interface UserService {
    List<User> findAll(Integer page,Integer total);

    User find(long id);

    Long findTotals();

    long save(User user);

    long update(User user);

    long delete(long id);

    long deleteAll(List<Long> list);
}
