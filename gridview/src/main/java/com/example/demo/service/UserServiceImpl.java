package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service("UserService")
public class UserServiceImpl implements UserService{

    final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public List<User> findAll(Integer page, Integer total,String name,String value) {
        int start = (page-1)*total;
        return userMapper.findAll(start,total,name,value);
    }

    @Override
    public User find(String id) {
        return userMapper.find(id);
    }

    @Override
    public Long findTotals() {
        return userMapper.findTotals();
    }

    @Override
    public long save(User user) {
        user.setId(UUID.randomUUID().toString());
        LocalDateTime ldt =LocalDateTime.now().plusDays(1);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        String formatter = formatter1.format(ldt);
        user.setBir(formatter);
        return userMapper.save(user);
    }

    @Override
    public long update(User user) {
        return userMapper.update(user);
    }

    @Override
    public long delete(String id) {
        return userMapper.delete(id);
    }

    @Override
    public long deleteAll(List<String> list) {
        return userMapper.deleteAll(list);
    }

    @Override
    public long countTableRows(String tableName){
        return userMapper.counter(tableName);
    }
}
