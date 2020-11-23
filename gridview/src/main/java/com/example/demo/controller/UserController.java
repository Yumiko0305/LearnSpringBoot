package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, Object> findAll(
            @RequestParam("page") Integer pageNow,
            @RequestParam("rows") Integer pageSize,
            @RequestParam(value = "coloumName",required = false, defaultValue = "") String name,
            @RequestParam(value = "coloumValue",required = false, defaultValue = "") String value
            ) {
        List<User> users = userService.findAll(pageNow, pageSize,name,value);
        long totals = userService.countTableRows("user");
        //result total rows
        HashMap<String, Object> result = new HashMap<>();
        result.put("rows", users);
        result.put("total", totals);
        return result;
    }

    @RequestMapping("/delete")
    public Map<String, Object> delete(String id) {
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
            stringObjectHashMap.put("ok", true);
        } catch (Exception e) {
            e.printStackTrace();
            stringObjectHashMap.put("ok", false);
            stringObjectHashMap.put("message", e.getMessage());
        }
        return stringObjectHashMap;
    }

    @RequestMapping("/find")
    public String find(@RequestParam("id")String id){
        System.out.print(id);
        User user = userService.find(id);
        System.out.print(user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping("/update")
    public Map<String,Object> update(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                     @RequestParam(value = "age", required = false, defaultValue = "") int age,
                                     @RequestParam(value ="id") String id){
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
