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
    public List<User> findAll(Integer page, Integer total,String name,String value) {
        int start = (page-1)*total;
//        if(name!=null & name!="" & value!=null)
//        {
//            if(name=="age")
//            {
//                int v = Integer.parseInt(value);
//                return userMapper.findAll(start,total,name,v);
//            }
//        }
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
        user.setBir(new Date());
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
    public long deleteAll(List<Long> list) {
        return userMapper.deleteAll(list);
    }

    @Override
    public long countTableRows(String tableName){
        return userMapper.counter(tableName);
    }
}
