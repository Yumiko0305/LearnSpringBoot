package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service("UserService")
public class UserServiceImpl implements UserService{

    final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public List<User> findAll(Integer page, Integer total) {
        int start = (page-1)*total;
        return userMapper.findAll(start,total);
    }

    @Override
    public User find(long id) {
        return userMapper.find(id);
    }

    @Override
    public Long findTotals() {
        return userMapper.findTotals();
    }

    @Override
    public long save(User user) {
        Random rid = new Random();
        long id = rid.nextLong();
        user.setId(id);
        user.setBir(new Date());
        return userMapper.save(user);
    }

    @Override
    public long update(User user) {
        return userMapper.update(user);
    }

    @Override
    public long delete(long id) {
        return userMapper.delete(id);
    }

    @Override
    public long deleteAll(List<Long> list) {
        return userMapper.deleteAll(list);
    }
}
