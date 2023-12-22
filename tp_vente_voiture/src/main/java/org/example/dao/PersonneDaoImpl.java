package org.example.dao;

import org.example.entity.Personne;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonneDaoImpl extends BaseDao<Personne> {
    public PersonneDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Personne element) throws SQLException {
        request="INSERT INTO personne (nom,prenom,age) VALUES (?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, element.getNom());
        statement.setString(2, element.getPrenom());
        statement.setInt(3, element.getAge());
        int nbRows=statement.executeUpdate();
        resultSet=statement.getGeneratedKeys();
        if(resultSet.next()){
            element.setId(resultSet.getLong(1));
        }
        return nbRows == 1;
    }


    @Override
    public boolean update(Personne element) throws SQLException {
        request="UPDATE personne SET nom=? , prenom=? , age=? WHERE id=?";
        statement= _connection.prepareStatement(request);
        statement.setString(1, element.getNom());
        statement.setString(2, element.getPrenom());
        statement.setInt(3, element.getAge());
        statement.setLong(4,element.getId());
        int nbRows=statement.executeUpdate();
        return nbRows == 1;
    }

    @Override
    public boolean delete(Personne element) throws SQLException {
        //todo supprimer vente
        request="DELETE FROM personne WHERE id=?";
        statement=_connection.prepareStatement(request);
        statement.setLong(1,element.getId());
        int nbRows=statement.executeUpdate();
        return nbRows==1;
    }

    @Override
    public Personne get(long id) throws SQLException {
        request="SELECT * FROM personne WHERE id=?";
        Personne personne=null;
        statement = _connection.prepareStatement(request);
        statement.setLong(1,id);
        resultSet = statement.executeQuery();
        if(resultSet.next()){
            personne=new Personne(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            );
        }
        return personne;
    }

    @Override
    public List<Personne> get() throws SQLException {
        request="SELECT * FROM personne";
        List<Personne> result=new ArrayList<>();
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
           Personne personne=new Personne(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            );
           result.add(personne);
        }
        return result;
    }
}
