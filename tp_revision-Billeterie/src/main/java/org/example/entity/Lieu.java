package org.example.entity;

public class Lieu {
    private long id;
    private String nom;
    private String adresse;
    private Integer capacite;

    public Lieu(String nom, String adresse, Integer capacite) {
        this.nom = nom;
        this.adresse = adresse;
        this.capacite = capacite;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    @Override
    public String toString() {
        return "Lieu{" +
                "nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", capacite=" + capacite +
                '}';
    }
}
