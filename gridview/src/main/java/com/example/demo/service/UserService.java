package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("UserService")
public interface UserService {
    List<User> findAll(Integer page, Integer total, String name, String value);

    User find(String id);

    Long findTotals();

    long save(User user);

    long update(User user);

    long delete(String id);

    long deleteAll(List<Long> list);

    /**
     * 查询表内有多少条记录
     * @param tableName 表名
     * @return 结果
     */
    long countTableRows(String tableName);
}
