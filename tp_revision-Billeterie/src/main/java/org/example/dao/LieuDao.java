package org.example.dao;

import org.example.entity.Client;
import org.example.entity.Lieu;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LieuDao extends BaseDao<Lieu> {
    public LieuDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Lieu element) throws SQLException {
        request="INSERT INTO lieux (nom,adresse,capacite) VALUES (?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, element.getNom());
        statement.setString(2, element.getAdresse());
        statement.setInt(3, element.getCapacite());
        int nbRows=statement.executeUpdate();
        resultSet=statement.getGeneratedKeys();
        if(resultSet.next()){
            element.setId(resultSet.getLong(1));
        }
        return nbRows == 1;
    }

    @Override
    public boolean update(Lieu element) throws SQLException {
        request="UPDATE lieux SET nom=?, adresse=? , capacite=? WHERE id=?";
        statement= _connection.prepareStatement(request);
        statement.setString(1, element.getNom());
        statement.setString(2, element.getAdresse());
        statement.setInt(3, element.getCapacite());
        statement.setLong(4,element.getId());
        int nbRows=statement.executeUpdate();
        return nbRows == 1;
    }

    @Override
    public boolean delete(Lieu element) throws SQLException {
        try {
            _connection.setAutoCommit(false);
            deleteEvenements(element);
            request="DELETE FROM lieux WHERE id=?";
            statement= _connection.prepareStatement(request);
            statement.setLong(1,element.getId());
            int nbRows= statement.executeUpdate();
            return nbRows==1;
        }catch (SQLException e){
            _connection.rollback();
            throw e;
        }finally {
            _connection.setAutoCommit(true);
        }
    }

    @Override
    public Lieu get(long id) throws SQLException {
        request="SELECT * FROM lieux WHERE id=?";
        Lieu lieu=null;
        statement = _connection.prepareStatement(request);
        statement.setLong(1,id);
        resultSet = statement.executeQuery();
        if(resultSet.next()){
            lieu=new Lieu(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            );
            lieu.setId(resultSet.getLong(1));
        }
        return lieu;
    }

    @Override
    public List<Lieu> get() throws SQLException {
        List<Lieu> result=new ArrayList<>();
        request="SELECT * from lieux";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            Lieu lieu=new Lieu(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            );
            lieu.setId(resultSet.getLong(1));
            result.add(lieu);
        }
        return result;
    }

    private void deleteEvenements(Lieu lieu) throws SQLException {
        deleteEvenementsClients(lieu);
        request="DELETE FROM evenements WHERE lieu_id=?";
        statement= _connection.prepareStatement(request);
        statement.setLong(1,lieu.getId());
        statement.executeUpdate();
    }

    private void deleteEvenementsClients(Lieu lieu) throws SQLException{
        request="DELETE FROM evenements_clients WHERE evenement_id=(SELECT id FROM evenements WHERE lieu_id=?)";
        statement= _connection.prepareStatement(request);
        statement.setLong(1,lieu.getId());
        statement.executeUpdate();
    }
}
