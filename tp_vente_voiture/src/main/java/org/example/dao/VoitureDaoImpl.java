package org.example.dao;

import org.example.entity.Voiture;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class VoitureDaoImpl extends  BaseDao<Voiture> {
    public VoitureDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Voiture element) throws SQLException {
        request="INSERT INTO voiture (nom,annee,puissance,prix) VALUES (?,?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, element.getNom());
        statement.setInt(2, element.getAnnee());
        statement.setInt(3, element.getPuissance());
        statement.setLong(4, element.getPrix());
        int nbRows=statement.executeUpdate();
        resultSet=statement.getGeneratedKeys();
        if(resultSet.next()){
            element.setId(resultSet.getLong(1));
        }
        return nbRows == 1;
    }

    @Override
    public boolean update(Voiture element) throws SQLException {
        request="UPDATE voiture SET nom=? , annee=? , puissance=?,prix=? WHERE id=?";
        statement= _connection.prepareStatement(request);
        statement.setString(1, element.getNom());
        statement.setInt(2, element.getAnnee());
        statement.setInt(3, element.getPuissance());
        statement.setLong(4,element.getPrix());
        statement.setLong(5,element.getId());
        int nbRows=statement.executeUpdate();
        return nbRows == 1;
    }

    @Override
    public boolean delete(Voiture element) throws SQLException {
        //todo supprimer vente
        request="DELETE FROM voiture WHERE id=?";
        statement=_connection.prepareStatement(request);
        statement.setLong(1,element.getId());
        int nbRows= statement.executeUpdate();
        return nbRows==1;
    }

    @Override
    public Voiture get(long id) throws SQLException {
        request="SELECT * FROM voiture WHERE id=?";
        statement= _connection.prepareStatement(request);
        statement.setLong(1,id);
        resultSet= statement.executeQuery();
        Voiture voiture=null;
        if(resultSet.next()){
            voiture=new Voiture(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getLong(5)
                    );
        }
        return voiture;
    }

    @Override
    public List<Voiture> get() throws SQLException {
        request="SELECT * FROM voiture";
        statement= _connection.prepareStatement(request);
        resultSet= statement.executeQuery();
        List<Voiture> result=null;
        while(resultSet.next()){
            Voiture voiture=new Voiture(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getLong(5)
            );
            result.add(voiture);
        }
        return result;
    }
}
