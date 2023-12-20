package org.example.service;

import org.example.dao.*;
import org.example.entity.Account;
import org.example.entity.Transaction;
import org.example.entity.TransactionEnum;
import org.example.entity.User;
import org.example.util.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BankService {
    private Connection connection;
    private UserDao userDao;
    private AccountDao accountDao;

    public BankService(){
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
            throw new RuntimeException(e);
        }
    }

    public boolean deleteUser(User user){
        try {
            List<Account> userAccounts=userDao.getAccount(user);
            for(Account i:userAccounts){
                accountDao.delete(i);
            }
            return userDao.delete(user);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public User verifyUser(String firstName,String lastName,String phone){
        User user=new User(lastName,firstName,phone);
        try {
           user=userDao.userExist(user);
           if(user.getId()==0){
               createUser(firstName, lastName, phone);
           }
           return user;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Account> getAccount(User user){
        try {
             return userDao.getAccount(user);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean makeTransaction(Transaction transaction,Account account){
        try {
            if(!accountDao.addTransaction(transaction,account))
                return false;
            if(transaction.getStatus()== TransactionEnum.DEPOSIT){
                account.setBalance(account.getBalance() + transaction.getAmount());
                return accountDao.update(account);
            }else{
                account.setBalance(account.getBalance()- transaction.getAmount());
                return accountDao.update(account);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Account getAccount(long id){
        try{
            return accountDao.get(id);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean createAccount(double balance,User user){
        Account account=new Account();
        account.setBalance(balance);
        account.setUser(user);
        try {
            return accountDao.save(account);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean deleteAccount(Account account){
        try {
            return accountDao.delete(account);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
