package com.dezzzl.service;

import com.dezzzl.entity.user.User;
import io.minio.credentials.Jwt;

public interface IAuthService {
    boolean register(User user);

    boolean login(String email, String password);

    boolean logout(Jwt jwt);

    void updateUser(User user);

    void deleteUser(int id);
}
