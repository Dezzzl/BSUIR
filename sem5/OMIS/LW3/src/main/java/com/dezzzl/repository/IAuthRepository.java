package com.dezzzl.repository;

import com.dezzzl.entity.user.User;

public interface IAuthRepository {
    User getUser(String email);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(String email);
}
