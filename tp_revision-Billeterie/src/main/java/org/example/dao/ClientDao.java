package org.example.dao;

import org.example.entity.Client;
import org.example.entity.Evenement;
import org.example.entity.Lieu;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDao extends BaseDao<Client> {
    public ClientDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Client element) throws SQLException {
        request="INSERT INTO clients (nom,prenom,mail) VALUES (?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, element.getNom());
        statement.setString(2, element.getPrenom());
        statement.setString(3, element.getEmail());
        int nbRows=statement.executeUpdate();
        resultSet=statement.getGeneratedKeys();
        if(resultSet.next()){
            element.setId(resultSet.getLong(1));
        }
        return nbRows == 1;
    }

    @Override
    public boolean update(Client element) throws SQLException {
        request="UPDATE clients SET nom=? AND prenom=? AND mail=? WHERE id=?";
        statement= _connection.prepareStatement(request);
        statement.setString(1, element.getNom());
        statement.setString(2, element.getPrenom());
        statement.setString(3, element.getEmail());
        statement.setLong(4,element.getId());
        int nbRows=statement.executeUpdate();
        return nbRows == 1;
    }

    @Override
    public boolean delete(Client element) throws SQLException {
        try{
            _connection.setAutoCommit(false);
            deleteEvenements(element);
            request="DELETE FROM clients WHERE id=?";
            statement= _connection.prepareStatement(request);
            statement.setLong(1,element.getId());
            int nbRows= statement.executeUpdate();
            _connection.commit();
            return nbRows==1;
        }catch (SQLException e){
            _connection.rollback();
            throw e;
        }
        finally {
            _connection.setAutoCommit(true);
        }
    }

    @Override
    public Client get(long id) throws SQLException {
        request="SELECT * FROM clients WHERE id=?";
        Client client=null;
        statement = _connection.prepareStatement(request);
        statement.setLong(1,id);
        resultSet = statement.executeQuery();
        if(resultSet.next()){
            client=new Client(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            client.setId(resultSet.getLong(1));
        }
        return client;
    }

    @Override
    public List<Client> get() throws SQLException {
        List<Client> result=new ArrayList<>();
        request="SELECT * from clients";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            Client client=new Client(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            client.setId(resultSet.getLong(5));
            result.add(client);
        }
        return result;
    }

    public List<Evenement> getEvenements(Client client) throws SQLException {
        List<Evenement> result=new ArrayList<>();
        request="SELECT * FROM evenements INNER JOIN lieux ON lieu_id=lieux.id INNER JOIN evenements_clients " +
                "ON evenements.id=evenement_id  WHERE client_id=?";
        statement= _connection.prepareStatement(request);
        statement.setLong(1,client.getId());
        resultSet= statement.executeQuery();
        while(resultSet.next()){
            Evenement evenement=new Evenement(
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                    resultSet.getFloat(5),
                    new Lieu(
                            resultSet.getString(9),
                            resultSet.getString(10),
                            resultSet.getInt(11)
                    )
            );
            evenement.setId(resultSet.getLong(1));
            evenement.setNbrBilletVendu(resultSet.getInt(7));
            evenement.getLieu().setId(8);
            result.add(evenement);
        }
        return result;
    }

    private void deleteEvenements(Client client) throws SQLException{
        request="DELETE FROM evenements_clients WHERE client_id=?";
        statement= _connection.prepareStatement(request);
        statement.setLong(1,client.getId());
        statement.executeUpdate();
    }
}
