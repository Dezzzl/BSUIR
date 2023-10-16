package com.dezzzl;

import org.junit.Test;

public class AuthenticationManagerTest {
    private String username = "TestUsername";
    private String email = "test@gmail.com";
    private String password = "TestPassword";
    private String role = "USER";

    private String tittle = "TestTittle";

    private String description = "TestDescription";

    private String text = "TestText";

    private final DbManager dbManager = new DbManager();

    private AuthenticationManager authenticationManager = new AuthenticationManager();
    @Test
    public void testRegisterUser(){
        authenticationManager.registerUser(username, password, email);
       int userId= dbManager.getUserIdByEmail(email);
        authenticationManager.registerUser(username, password, email);
        authenticationManager.registerUser(username, password, email+1);
        dbManager.deleteUser(userId);
    }

    @Test
    public void testLoginUser(){
        dbManager.addUser(new User(email, username, password));
        authenticationManager.loginUser(username, password);
        int userId= dbManager.getUserIdByEmail(email);
        authenticationManager.loginUser(username+1, password);
        authenticationManager.loginUser(username, password+1);
        dbManager.deleteUser(userId);
    }
}
