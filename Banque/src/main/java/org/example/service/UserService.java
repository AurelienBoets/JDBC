package org.example.service;

import org.example.dao.*;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.util.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService {
    private Connection connection;
    private UserDao userDao;
    private AccountDao accountDao;

    public UserService(){
        try {
            this.connection=new DatabaseManager().getConnection();
            this.userDao=new UserDao(connection);
            this.accountDao=new AccountDao(connection);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean createUser(String firstName,String lastName,String phone){
        User user=new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        try{
            return userDao.save(user);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
