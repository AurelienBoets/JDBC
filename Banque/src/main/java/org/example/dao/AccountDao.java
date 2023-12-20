package org.example.dao;

import org.example.entity.Account;
import org.example.entity.Transaction;
import org.example.entity.TransactionEnum;
import org.example.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDao extends BaseDao<Account> {
    public AccountDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Account element) throws SQLException {
        request="INSERT INTO accounts (balance,user_id) VALUES (?,?)";
        statement=_connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setDouble(1,element.getBalance());
        statement.setLong(2,element.getUser().getId());
        int nbRows=statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()){
            element.setId(resultSet.getLong(1));
        }
        return nbRows==1;
    }

    @Override
    public boolean update(Account element) throws SQLException {
        request="UPDATE accounts SET balance=? WHERE id=?";
        statement=_connection.prepareStatement(request);
        statement.setDouble(1,element.getBalance());
        statement.setLong(2,element.getId());
        int nbRows=statement.executeUpdate();
        return nbRows==1;
    }

    @Override
    public boolean delete(Account element) throws SQLException {
        deleteTransactions(element);
        request="DELETE FROM accounts WHERE id=?";
        statement=_connection.prepareStatement(request);
        statement.setLong(1,element.getId());
        int nbRows=statement.executeUpdate();
        return nbRows==1;
    }

    @Override
    public Account get(long id) throws SQLException {
        request="SELECT * FROM accounts INNER JOIN users ON user_id=users.id WHERE accounts.id=?";
        Account account=null;
        statement = _connection.prepareStatement(request);
        statement.setLong(1,id);
        resultSet = statement.executeQuery();
        if(resultSet.next()){
            account=new Account(resultSet.getLong(1),
                    resultSet.getDouble(2)
                    );
            User user=new User(resultSet.getLong(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7));
            account.setUser(user);
            account.setTransactions(getTransactions(account));
        }
        return account;
    }

    @Override
    public List<Account> get() throws SQLException {
        List<Account> result=new ArrayList<>();
        request="SELECT * from accounts INNER JOIN users ON user_id=users.id;";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            Account account=new Account(resultSet.getLong(1),
                    resultSet.getDouble(2)
                    );
            result.add(account);
        }
        return result;
    }

    public List<Transaction> getTransactions(Account element) throws SQLException{
        List<Transaction> result=new ArrayList<>();
        request="SELECT * from transactions WHERE account_id=?";
        statement = _connection.prepareStatement(request);
        statement.setLong(1,element.getId());
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            Transaction transaction=new Transaction(resultSet.getLong(1),
                    resultSet.getDouble(2),
                    TransactionEnum.valueOf(resultSet.getString(3)));
            result.add(transaction);
        }
        return result;
    }

    private void deleteTransactions(Account element) throws SQLException{
        request="DELETE FROM transactions WHERE account_id=?";
        statement=_connection.prepareStatement(request);
        statement.setLong(1,element.getId());
        statement.executeUpdate();
    }

    public boolean addTransaction(Transaction transaction,Account account) throws SQLException {
        request="INSERT INTO transactions (amount,status,account_id) VALUES(?,?,?)";
        statement=_connection.prepareStatement(request,Statement.RETURN_GENERATED_KEYS);
        statement.setDouble(1,transaction.getAmount());
        statement.setString(2, String.valueOf(transaction.getStatus()));
        statement.setLong(3,account.getId());
        int nbRows=statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()){
            transaction.setId(resultSet.getLong(1));
        }
        return nbRows==1;
    }
}
