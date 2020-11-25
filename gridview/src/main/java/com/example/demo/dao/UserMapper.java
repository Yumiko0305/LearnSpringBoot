package com.example.demo.dao;

import com.example.demo.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

// :)
@Repository
@Mapper
public interface UserMapper {

    List<User> findAll(@Param("page")Integer page, @Param("total") Integer total,@Param("name") String name,@Param("value") String  value);

    User find(String id);

    Long findTotals();

    long save(User user);

    long update(User user);

    long delete(String id);

    long deleteAll(List<String> list);

    long counter(@Param("tableName") String tableName);
}
