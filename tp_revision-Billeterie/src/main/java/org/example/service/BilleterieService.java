package org.example.service;

import org.example.dao.ClientDao;
import org.example.dao.EvenementDao;
import org.example.dao.LieuDao;
import org.example.entity.Client;
import org.example.entity.Evenement;
import org.example.entity.Lieu;
import org.example.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class BilleterieService {
    private Connection connection;
    private ClientDao clientDao;
    private EvenementDao evenementDao;
    private LieuDao lieuDao;

    public BilleterieService() {
        this.connection = DatabaseManager.getConnection();
        this.evenementDao = new EvenementDao(this.connection);
        this.clientDao = new ClientDao(this.connection);
        this.lieuDao = new LieuDao(this.connection);
    }

    public boolean creerClient(String nom, String prenom, String mail) {
        Client client = new Client(nom, prenom, mail);
        try {
            return clientDao.save(client);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean creerEvenement(String nom, Date date, String heure, float prix, Lieu lieu){
        Evenement evenement=new Evenement(nom,date,heure,prix,lieu);
        try {
            return evenementDao.save(evenement);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean creerLieu(String nom,String adresse,Integer capacite){
        Lieu lieu=new Lieu(nom,adresse,capacite);
        try{
            return lieuDao.save(lieu);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean supprimerClient(Client client){
        try {
            return clientDao.delete(client);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean supprimerLieu(Lieu lieu){
        try {
            return lieuDao.delete(lieu);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean supprimerEvenement(Evenement evenement){
        try {
            return evenementDao.delete(evenement);
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public boolean modifierClient(long id,String nom,String prenom,String email){
        try {
            Client client=clientDao.get(id);
            if(client==null){
                return false;
            }
            client.setNom(nom);
            client.setEmail(email);
            client.setPrenom(prenom);
            return clientDao.update(client);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean modifierLieu(long id,String nom,String adresse,Integer capacite){
        try {
            Lieu lieu=lieuDao.get(id);
            if(lieu==null){
                return false;
            }
            lieu.setAdresse(adresse);
            lieu.setCapacite(capacite);
            lieu.setNom(nom);
            return lieuDao.update(lieu);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean modifierEvenement(long id,String nom, Date date, String heure, float prix, Lieu lieu){
        try {
            Evenement evenement=evenementDao.get(id);
            if(evenement==null){
                return false;
            }
            evenement.setDate(date);
            evenement.setNom(nom);
            evenement.setHeure(heure);
            evenement.setPrix(prix);
            evenement.setLieu(lieu);
            return evenementDao.update(evenement);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Evenement> getEvenements(){
        try {
            return evenementDao.get();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Lieu> getLieux(){
        try{
            return lieuDao.get();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Client> getClients(){
        try {
            return clientDao.get();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Client getClient(long id){
        try {
            return clientDao.get(id);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Evenement getEvenement(long id){
        try {
            return evenementDao.get(id);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Lieu getLieu(long id){
        try {
            return lieuDao.get(id);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean achatBillet(Client client,Evenement evenement){
        try{
            if(evenement.getLieu().getCapacite()>evenement.getNbrBilletVendu()){
                evenement.setNbrBilletVendu(evenement.getNbrBilletVendu()+1);
                evenementDao.update(evenement);
                return evenementDao.addEvenement(client,evenement);
            }
            return false;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean annulerBillet(Client client,Evenement evenement){
        try{
            List<Evenement> evenements=clientDao.getEvenements(client);
            if(evenements.stream().anyMatch(element->element.getId()==evenement.getId())){
                return evenementDao.removeEvenement(client,evenement);
            }
            return false;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Evenement> getBillet(Client client){
        try {
            return clientDao.getEvenements(client);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Evenement> evenementDisponible(){
        try {
            return evenementDao.getEvenementAvailable();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
