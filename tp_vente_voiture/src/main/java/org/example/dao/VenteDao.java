package org.example.dao;

import org.example.entity.Personne;
import org.example.entity.Vente;
import org.example.entity.Voiture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenteDao {
    private Connection _connection;
    private PreparedStatement statement;
    private String request;
    private ResultSet resultSet;

    public VenteDao(Connection _connection) {
        this._connection = _connection;
    }

    public boolean save(Vente element) throws SQLException {
        request="INSERT INTO vente (personne_id,voiture_id,date) VALUES (?,?,?)";
        statement = _connection.prepareStatement(request);
        statement.setLong(1, element.getPersonne().getId());
        statement.setLong(2, element.getPersonne().getId());
        statement.setDate(3, new Date(element.getDate().getTime()));
        int nbRows=statement.executeUpdate();
        return nbRows == 1;
    }

    public void delete(Voiture voiture) throws SQLException {
        request="DELETE FROM vente WHERE voiture_id=?";
        statement= _connection.prepareStatement(request);
        statement.setLong(1,voiture.getId());
        statement.executeUpdate();
    }

    public void delete(Personne personne) throws SQLException {
        request="DELETE FROM vente WHERE personne_id=?";
        statement= _connection.prepareStatement(request);
        statement.setLong(1,personne.getId());
        statement.executeUpdate();
    }

    public List<Vente> get() throws SQLException{
        request="SELECT * FROM vente INNER JOIN personne ON personne_id=personne.id INNER JOIN voiture ON voiture_id=voiture.id;";
        statement= _connection.prepareStatement(request);
        List<Vente> result=new ArrayList<>();
        resultSet=statement.executeQuery();
        while (resultSet.next()){
            Vente vente=new Vente();
            vente.setDate(resultSet.getDate(3));
            vente.setPersonne(new Personne(
                    resultSet.getLong(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
            ));
            vente.setVoiture(new Voiture(
                    resultSet.getLong(8),
                    resultSet.getString(9),
                    resultSet.getInt(10),
                    resultSet.getInt(11),
                    resultSet.getLong(12)
            ));
            result.add(vente);
        }
        return result;
    }
    //todo getVenteByPersonne
}
