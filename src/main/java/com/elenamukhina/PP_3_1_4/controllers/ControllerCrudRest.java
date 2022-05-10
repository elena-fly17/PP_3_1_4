package com.elenamukhina.PP_3_1_4.controllers;

import com.elenamukhina.PP_3_1_4.entity.User;
import com.elenamukhina.PP_3_1_4.repository.UserServiceCrudRepository;
import com.elenamukhina.PP_3_1_4.service.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ControllerCrudRest {

    // внедрение зависимостей через конструктор - сделала через Autowired, но идея посоветовала так
    final UserServiceImpl userServiceImpl; // для вызова методов сервис-слоя
    final UserServiceCrudRepository userServiceCrudRepository; // для вызова метода нахождения юзера
                                                               // по имейлу - исп. в методе save,
                                                               // чтобы в БД не сохранять
                                                               // юзеров с одинаковым имейлом
    public ControllerCrudRest(UserServiceImpl userServiceImpl, UserServiceCrudRepository userServiceCrudRepository) {
        this.userServiceImpl = userServiceImpl;
        this.userServiceCrudRepository = userServiceCrudRepository;
    }

    // получение списка всех юзеров
    @GetMapping("/users")
    public List<User> allUsers() {
        return userServiceImpl.getUsers();
    }

    // получение юзера по id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        try {
            User user = userServiceImpl.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    // добавление нового юзера в БД
    // в этом методе юзера не возвращаем - не помешает ли это потом передать его во фронт?..
    // можем возвращать нового юзера и на фронте через js добавлять его к табл. без ее перезагрузки
    @PostMapping("/users")
    public void save(@RequestBody User user) {
        User userInDB = userServiceCrudRepository.findByEmail(user.getEmail());
        if (userInDB != null) {
            throw new NonUniqueResultException("User with this email already exists");
        }
        userServiceImpl.saveUser(user);
    }

    // изменение юзера в БД
    // если в этом методе не возвращать юзера, то сможем передать его во фронт?..
    // можно для возвращенного юзера на фронте через js изменить нужные ячейки в табл. без перезагрузки
    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        userServiceImpl.saveUser(user);
        return user;
    }

    // удаление юзера
    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable int id) {
        userServiceImpl.getUser(id);
        userServiceImpl.deleteUser(id);
    }
}
