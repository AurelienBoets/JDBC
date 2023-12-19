package org.example.dao;

import org.example.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDao<User> {
    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(User element) throws SQLException {
        request="INSERT INTO users (first_name,last_name,phone) VALUES (?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,element.getFirstName());
        statement.setString(2,element.getLastName());
        statement.setString(3,element.getPhone());
        int nbRows = statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()){
            element.setId(resultSet.getInt(1));
        }
        return nbRows == 1;
    }

    @Override
    public boolean update(User element) throws SQLException {
        request="UPDATE users SET first_name=? AND last_name=? AND phone=? WHERE id=?";
        statement = _connection.prepareStatement(request);
        statement.setString(1,element.getFirstName());
        statement.setString(2,element.getLastName());
        statement.setString(3,element.getPhone());
        statement.setLong(4,element.getId());
        int nbRows = statement.executeUpdate();
        return nbRows == 1;
    }

    @Override
    public boolean delete(User element) throws SQLException {
        request="DELETE FROM users WHERE id=?";
        statement=_connection.prepareStatement(request);
        statement.setLong(1,element.getId());
        int nbRows= statement.executeUpdate();
        return nbRows==1;
    }

    @Override
    public User get(int id) throws SQLException {
        request="SELECT * FROM users WHERE id=?";
        User user=null;
        statement = _connection.prepareStatement(request);
        statement.setInt(1,id);
        resultSet = statement.executeQuery();
        if(resultSet.next()){
            user=new User(resultSet.getLong("id"),
                    resultSet.getString("last_name"),
                    resultSet.getString("first_name"),
                    resultSet.getString("phone"));
        }
        return user;
    }

    @Override
    public List<User> get() throws SQLException {
        List<User> result=new ArrayList<>();
        request="SELECT * from users";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            User user=new User(resultSet.getLong("id"),
                    resultSet.getString("last_name"),
                    resultSet.getString("first_name"),
                    resultSet.getString("phone"));
            result.add(user);
        }
        return result;
    }

}
