package com.example.demo.dao;

import com.example.demo.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

// :)
@Repository
@Mapper
public interface UserMapper {

    List<User> findAll(Integer page,Integer total);

    User find(long id);

    Long findTotals();

    long save(User user);

    long update(User user);

    long delete(long id);

    long deleteAll(List<Long> list);
}
