package com.elenamukhina.PP_3_1_4.service;

import com.elenamukhina.PP_3_1_4.entity.User;
import com.elenamukhina.PP_3_1_4.repository.UserServiceCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// отличия от класса из прошлого проекта:
// ---- пока что не внедрена в виде поля зависимость PasswordEncoder
// ---- метод saveUser сокращен - убраны дополнения для спринг-секьюрити по паролям

@Service
public class UserServiceImpl {

    // внедряем репозиторий для круд-операций над юзерами
    private UserServiceCrudRepository userServiceCrudRepository;
    @Autowired
    public void setUserServiceCrudRepository(UserServiceCrudRepository userServiceCrudRepository) {
        this.userServiceCrudRepository = userServiceCrudRepository;
    }

    // получение списка всех юзеров
    public List<User> getUsers() {
        return (List<User>) userServiceCrudRepository.findAll();
    }

    // добавление нового юзера в базу
    public void saveUser(User user) {
        user.setUsername(user.getEmail());
        userServiceCrudRepository.save(user);
    }

    // получение юзера по id
    public User getUser(int id) {
        return userServiceCrudRepository.findById(id).get();
    }

    // удаление юзера по id
    public void deleteUser(int id) {
        userServiceCrudRepository.deleteById(id);
    }
}
