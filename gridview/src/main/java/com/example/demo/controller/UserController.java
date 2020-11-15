package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/findAll")
    public Map<String, Object> findAll(@RequestParam("page") Integer pageNow, @RequestParam("rows") Integer pageSize) {
        List<User> users = userService.findAll(pageNow, pageSize);
        long totals = userService.findTotals();
        //result total rows
        HashMap<String, Object> result = new HashMap<>();
        result.put("rows", users);
        result.put("total", totals);
        return result;
    }

    @RequestMapping("/delete")
    public Map<String, Object> delete(long id) {
        System.out.println("id:" + id);
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        try {
            userService.delete(id);
            stringObjectHashMap.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            stringObjectHashMap.put("success", false);
            stringObjectHashMap.put("message", e.getMessage());
        }
        return stringObjectHashMap;
    }

    @RequestMapping("/deleteAll")
    public Map<String, Object> deleatAll(@RequestParam("id") List<Long> list) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        try {
            list.removeIf(it -> it <= 0);
            if (list.size() != 0) {
                userService.deleteAll(list);
                stringObjectHashMap.put("success", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            stringObjectHashMap.put("success", false);
            stringObjectHashMap.put("message", e.getMessage());
        }
        return stringObjectHashMap;
    }

    @RequestMapping("/save")
    public Map<String,Object> save(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "age") int age) {

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        try {
            User user = new User();
            user.setName(name);
            user.setAge(age);
            userService.save(user);
            stringObjectHashMap.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            stringObjectHashMap.put("success", false);
            stringObjectHashMap.put("message", e.getMessage());
        }
        return stringObjectHashMap;
    }

    @RequestMapping("/update")
    public Map<String,Object> update(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                     @RequestParam(value = "age", required = false, defaultValue = "") int age,
                                     @RequestParam(value ="id") long id){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        try {
            User user = userService.find(id);
            if (user != null) {
                if (!name.equals("")) {
                    user.setName(name);
                }
                if (!String.valueOf(age).equals("")) {
                    user.setAge(age);
                }
                userService.update(user);
                stringObjectHashMap.put("success", true);
            }
        }catch (Exception e){
            e.printStackTrace();
            stringObjectHashMap.put("success", false);
            stringObjectHashMap.put("message", e.getMessage());
        }
        return stringObjectHashMap;
    }

}
