package com.elenamukhina.PP_3_1_4.repository;

import com.elenamukhina.PP_3_1_4.entity.User;
import org.springframework.data.repository.CrudRepository;

// репозиторий для круд-операций над юзерами
public interface UserServiceCrudRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
}
