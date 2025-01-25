package com.dezzzl.repository;

import com.dezzzl.entity.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository implements IAuthRepository {
    @Override
    public User getUser(String email) {
        return null;
    }

    @Override
    public void createUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(String email) {

    }
}
