package com.dezzzl;

public class Admin extends User{
    public static final Class<Admin> CLASS = Admin.class;
    public Admin(int id,  String email, String username, String password) {
        super(id, email, username, password);
    }


}
