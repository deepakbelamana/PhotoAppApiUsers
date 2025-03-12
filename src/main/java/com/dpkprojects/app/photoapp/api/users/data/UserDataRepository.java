package com.dpkprojects.app.photoapp.api.users.data;

import org.springframework.data.repository.CrudRepository;

public interface UserDataRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String username);
}
