package org.example.dao;

import org.example.entity.Client;
import org.example.entity.Evenement;
import org.example.entity.Lieu;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EvenementDao extends BaseDao<Evenement> {
    public EvenementDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Evenement element) throws SQLException {
        request = "INSERT INTO evenements (nom,evenement_date,heure,prix,lieu_id) VALUES (?,?,?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, element.getNom());
        statement.setDate(2, (Date) element.getDate());
        statement.setString(3, element.getHeure());
        statement.setFloat(4, element.getPrix());
        statement.setLong(5, element.getLieu().getId());
        int nbRows = statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            element.setId(resultSet.getLong(1));
        }
        return nbRows == 1;
    }

    @Override
    public boolean update(Evenement element) throws SQLException {
        request = "UPDATE evenements SET nom=? AND evenement_date=? AND heure=? AND prix=? AND lieu_id=? AND nbre_billet_vendu=? WHERE id=?";
        statement = _connection.prepareStatement(request);
        statement.setString(1, element.getNom());
        statement.setDate(2, (Date) element.getDate());
        statement.setString(3, element.getHeure());
        statement.setFloat(4, element.getPrix());
        statement.setLong(5, element.getLieu().getId());
        statement.setInt(6, element.getNbrBilletVendu());
        statement.setLong(7, element.getId());
        int nbRows = statement.executeUpdate();
        return nbRows == 1;
    }

    @Override
    public boolean delete(Evenement element) throws SQLException {
        try {
            _connection.setAutoCommit(false);
            deleteClients(element);
            request = "DELETE FROM evenements WHERE id=?";
            statement = _connection.prepareStatement(request);
            statement.setLong(1, element.getId());
            int nbRows = statement.executeUpdate();
            return nbRows == 1;
        }catch (SQLException e){
            _connection.rollback();
            throw e;
        }finally {
            _connection.setAutoCommit(true);
        }
    }

    @Override
    public Evenement get(long id) throws SQLException {
        request = "SELECT * FROM evenements INNER JOIN lieux ON evenements.lieu_id=evenements.id WHERE evenements.id=?";
        Evenement evenement = null;
        statement = _connection.prepareStatement(request);
        statement.setLong(1, id);
        resultSet = statement.executeQuery();
        if (resultSet.next()) {
            evenement = new Evenement(
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
            evenement.setNbrBilletVendu(7);
        }
        return evenement;
    }

    @Override
    public List<Evenement> get() throws SQLException {
        List<Evenement> result = new ArrayList<>();
        request = "SELECT * FROM evenements INNER JOIN lieux ON evenements.lieu_id=evenements.id";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Evenement evenement = new Evenement(
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

    private void deleteClients(Evenement evenement) throws SQLException {
        request="DELETE from evenements_clients WHERE evenement_id=?";
        statement= _connection.prepareStatement(request);
        statement.setLong(1,evenement.getId());
        statement.executeUpdate();
    }

    public boolean addEvenement(Client client, Evenement evenement) throws SQLException {
        try{
            _connection.setAutoCommit(false);
            if(evenement.getNbrBilletVendu()<evenement.getLieu().getCapacite()){

                evenement.setNbrBilletVendu(evenement.getNbrBilletVendu()+1);
                this.update(evenement);
                request="INSERT INTO evenements_clients (evenement_id,client_id) VALUES (?,?)";
                statement= _connection.prepareStatement(request);
                statement.setLong(1,evenement.getId());
                statement.setLong(2,client.getId());
                return statement.executeUpdate()==1;
            }
            return false;
        }catch (SQLException e){
            _connection.rollback();
            throw e;
        }finally {
            _connection.setAutoCommit(true);
        }
    }

    public boolean removeEvenement(Client client,Evenement evenement) throws SQLException{
        try{
            _connection.setAutoCommit(false);
                evenement.setNbrBilletVendu(evenement.getNbrBilletVendu()-1);
                this.update(evenement);
                request="DELETE FROM evenements_clients WHERE evenement_id=? AND client_id=?";
                statement= _connection.prepareStatement(request);
                statement.setLong(1,evenement.getId());
                statement.setLong(2,client.getId());
                return statement.executeUpdate()==1;
        }catch (SQLException e){
            _connection.rollback();
            throw e;
        }finally {
            _connection.setAutoCommit(true);
        }
    }

    public List<Evenement> getEvenementAvailable()throws SQLException{
        List<Evenement> result=new ArrayList<>();
        request="SELECT * FROM evenements INNER JOIN lieux ON lieu_id=lieux.id WHERE nbre_billet_vendu<capacite";
        statement= _connection.prepareStatement(request);
        resultSet=statement.executeQuery();
        while (resultSet.next()){
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
}
