package org.example.entity;


public class Voiture {
    private long id;
    private String nom;
    private int annee;
    private int puissance;
    private long prix;

    public Voiture(String nom, int annee, int puissance, long prix) {
        this.nom = nom;
        this.annee = annee;
        this.puissance = puissance;
        this.prix = prix;
    }

    public Voiture(long id,String nom, int annee, int puissance, long prix){
        this(nom, annee, puissance, prix);
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getPuissance() {
        return puissance;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public long getPrix() {
        return prix;
    }

    public void setPrix(long prix) {
        this.prix = prix;
    }
}
