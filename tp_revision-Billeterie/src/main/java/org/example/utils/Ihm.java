package org.example.utils;

import org.example.entity.Client;
import org.example.entity.Evenement;
import org.example.entity.Lieu;
import org.example.service.BilleterieService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Ihm {

    private Scanner s = new Scanner(System.in);
    private BilleterieService service = new BilleterieService();

    public Ihm() {
    }

    public void start() {
        this.menuGenerale();
    }

    public void menuGenerale() {
        try {
            System.out.println("----------menu---------");
            System.out.println("1/ action Lieux");
            System.out.println("2/ action Evenements");
            System.out.println("3/ action client");
            System.out.println("0/ quitter");
            System.out.println("entrer votre choix :");
            int entry = s.nextInt();
            switch (entry) {
                case 1:
                    this.menuLieux();
                    break;
                case 2:
                    this.menuEvenement();
                    break;
                case 3:
                    this.menuCLient();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("entrer une valeur correspondant a un choix");
                    this.menuGenerale();
                    break;
            }

        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.menuGenerale();
        }

    }

    //gestion des lieux

    public void menuLieux() {
        try {
            System.out.println("----------menu Lieux---------");
            System.out.println("1/ ajouter un lieu");
            System.out.println("2/ modifier un lieu");
            System.out.println("3/ supprimer un lieu");
            System.out.println("0/ retourner au menu generale");
            System.out.println("entrer votre choix :");
            int entry = s.nextInt();
            switch (entry) {
                case 1:
                    this.addLieux();
                    break;
                case 2:
                    this.modifLieu();
                    break;
                case 3:
                    this.suprLieu();
                    break;
                case 0:
                    this.menuGenerale();
                    break;
                default:
                    System.out.println("entrer une valeur correspondant a un choix");
                    this.menuLieux();
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.menuLieux();
        }
    }

    public void addLieux() {
        try {
            System.out.println("--------ajouter lieu----------");
            System.out.println("entrer le nom du lieu :");
            String nomLieux = s.next();
            System.out.println("entrer l'adresse du lieu :");
            String adresse = s.next();
            int capacite = this.entryCapacity();
            if (service.creerLieu(nomLieux, adresse, capacite))
                System.out.println("Lieux ajouté");
            else
                System.out.println("Erreur lors de la création d'un lieu");
            this.menuLieux();
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.addLieux();
        }
    }

    public void modifLieu() {
        try {
            try {
                System.out.println("--------modifier lieu----------");
                afficheList(service.getLieux());
                System.out.println("quelle lieux vouler vous modifier : ");
                long entry = s.nextLong();
                System.out.println("entrer le nouveau nom : ");
                String nom = s.next();
                System.out.println("entrer la nouvelle adresse : ");
                String adresse = s.next();
                int capacite = this.entryCapacity();
                if (service.modifierLieu(entry, nom, adresse, capacite))
                    System.out.println("Lieu modifier");
                else
                    System.out.println("Erreur lors de la modification");
                this.menuLieux();
            } catch (IndexOutOfBoundsException e) {
                this.menuLieux();
            }
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.modifLieu();
        }
    }

    public void suprLieu() {
        try {
            System.out.println("--------supr lieu----------");
            afficheList(service.getLieux());
            System.out.println("quelle lieux vouler vous supprimer (0 pour retour) : ");
            long entry = s.nextLong();

            if (entry == 0) {
                this.menuLieux();
            } else {
                Lieu lieu = service.getLieu(entry);
                if (lieu == null) {
                    System.out.println("Lieux inexistant");
                    this.menuLieux();
                } else {
                    if (service.supprimerLieu(lieu))
                        System.out.println("Suppression du lieu");
                    else
                        System.out.println("Erreur lors de la suppression");
                    this.menuLieux();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.suprLieu();
        }
    }

    public int entryCapacity() {
        int entry = 0;
        do {
            System.out.println("entrer la capacité du lieu :");
            entry = s.nextInt();
        }
        while (entry <= 0);

        return entry;
    }


    // gestion des evenements

    public void menuEvenement() {
        try {
            System.out.println("----------menu Evenement---------");
            System.out.println("1/ ajouter un Evenement");
            System.out.println("2/ modifier un Evenement");
            System.out.println("3/ supprimer un Evenement");
            System.out.println("4/ afficher la liste des evenements");
            System.out.println("0/ retourner au menu generale");
            System.out.println("entrer votre choix :");
            int entry = s.nextInt();
            switch (entry) {
                case 1:
                    this.addEvenement();
                    break;
                case 2:
                    this.modifEvenement();
                    break;
                case 3:
                    this.suprEvenement();
                    break;
                case 4:
//                    this.afficheList(this.listEvenement);
                    break;
                case 0:
                    this.menuGenerale();
                    break;
                default:
                    System.out.println("entrer une valeur correspondant a un choix");
                    this.menuEvenement();
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.menuCLient();
        }
    }

    public void addEvenement() {
        try {
            System.out.println("--------ajouter Evenement----------");

            System.out.println("entrer le nom de l'evenement:");
            String nom = s.next();

            System.out.println("entrer la date de l'evenement (format dd-MM-yyyy):");
            String date_string = s.next();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(date_string);

            System.out.println("entrer l'heure de l'evenement :");
            String heure = s.next();

            System.out.println();
            this.afficheList(service.getLieux());
            System.out.println("selectionner le lieu de l'evenement :");
            long idLieu = s.nextLong();
            Lieu lieu = service.getLieu(idLieu);
            if (lieu == null) {
                System.out.println("Lieu inexistant");
                this.menuEvenement();
            } else {
                System.out.println("entrer le prix du billet");
                float prix = s.nextFloat();
                if (service.creerEvenement(nom, date, heure, prix, lieu)) {
                    System.out.println("Evenement creer");
                } else
                    System.out.println("Erreur lors de la création");
                this.menuEvenement();
            }
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.addEvenement();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void suprEvenement() {
        try {
            System.out.println("--------supr evenement----------");
            afficheList(service.getEvenements());
            System.out.println("quelle evenement vouler vous supprimer (0 pour retour) : ");
            long entry = s.nextLong();
            if (entry == 0)
                this.menuEvenement();
            else {
                Evenement evenement = service.getEvenement(entry);
                if (evenement == null) {
                    System.out.println("Evenement inexistant");
                    this.menuEvenement();
                } else {

                    if (service.supprimerEvenement(evenement)) {
                        System.out.println("l'evenement a bien ete supprimer");
                    } else
                        System.out.println("Erreur lors de la suppression");
                    this.menuEvenement();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.suprEvenement();
        }
    }

    public void modifEvenement() {
        try {
            System.out.println("--------modifier Evenement----------");
            this.afficheList(service.getEvenements());
            System.out.println("quelle evenement vouler vous modifier (0 pour retour) : ");
            long entry = s.nextLong();
            if (entry == 0)
                this.menuEvenement();
            else {

                System.out.println("entrer le nom de l'evenement:");
                String nom = s.next();

                System.out.println("entrer la date de l'evenement (format dd-MM-yyyy):");
                String date_string = s.next();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = formatter.parse(date_string);

                System.out.println("entrer l'heure de l'evenement :");
                String heure = s.next();

                System.out.println();
                this.afficheList(service.getLieux());
                System.out.println("selectionner le lieu de l'evenement :");
                long idLieu = s.nextLong();
                Lieu lieu = service.getLieu(idLieu);
                if (lieu == null) {
                    System.out.println("Lieu inexistant");
                    this.menuEvenement();
                } else {
                    System.out.println("entrer le prix du billet");
                    float prix = s.nextFloat();
                    if (service.modifierEvenement(entry, nom, date, heure, prix, lieu)) {
                        System.out.println("Evenement modifier");
                    } else
                        System.out.println("Erreur lors de la modification");
                    this.menuEvenement();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.addEvenement();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //gestion client

    public void menuCLient() {
        try {
            System.out.println("----------menu Client---------");
            System.out.println("1/ ajouter un Client");
            System.out.println("2/ modifier un Client");
            System.out.println("3/ supprimer un Client");
            System.out.println("4/ acheter un billet");
            System.out.println("5/ annuler un billet");
            System.out.println("6/ afficher la liste des billets d'un clients");
            System.out.println("0/ retourner au menu generale");
            System.out.println("entrer votre choix :");
            int entry = s.nextInt();
            switch (entry) {
                case 1:
                    this.addClient();
                    break;
                case 2:
                    this.modifClient();
                    break;
                case 3:
                    this.suprClient();
                    break;
                case 4:
                    achatBillet(true);
                    break;
                case 5:
                    achatBillet(false);
                    break;
                case 6:
                    affichageBillet();
                    break;
                case 0:
                    this.menuGenerale();
                    break;
                default:
                    System.out.println("entrer une valeur correspondant a un choix");
                    this.menuCLient();
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.menuCLient();
        }
    }

    public void addClient() {
        try {
            System.out.println("--------ajouter CLient----------");
            System.out.println("entrer le nom du clien :");
            String nom = s.next();

            System.out.println("entrer le prenom du client :");
            String prenom = s.next();

            System.out.println("entrer l'email du client : ");
            String email = s.next();

            if (service.creerClient(nom, prenom, email))
                System.out.println("le CLient a bien ete ajouter :");
            else
                System.out.println("Erreur lors de la création");
            this.menuEvenement();
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.addEvenement();
        }
    }

    public void modifClient() {
        try {
            try {
                System.out.println("--------modifier Client----------");
                afficheList(service.getClients());
                System.out.println("quelle Client vouler vous modifier : ");
                long entry = s.nextLong();
                System.out.println("entrer le nouveau nom : ");
                String nom = s.next();
                System.out.println("entrer le nouveau prenom : ");
                String prenom = s.next();
                System.out.println("entrer la nouvelle adresse email : ");
                String email = s.next();
                if (service.modifierClient(entry, nom, prenom, email))
                    System.out.println("Modification du client");
                else
                    System.out.println("Erreur lors de la modification");
                this.menuCLient();
            } catch (IndexOutOfBoundsException e) {
                this.menuCLient();
            }
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.modifClient();
        }
    }

    public void suprClient() {
        try {
            System.out.println("--------supr CLient----------");
            afficheList(service.getClients());
            System.out.println("quelle Client vouler vous supprimer (0 pour retour) : ");
            long entry = s.nextLong();

            if (entry == 0) {
                this.menuCLient();
            } else {
                Client client = service.getClient(entry);
                if (client == null) {
                    System.out.println("Client inexistant");
                    this.menuCLient();
                } else {
                    if (service.supprimerClient(client))
                        System.out.println("le CLient a bien ete supprimer");
                    else
                        System.out.println("Erreur lors de la suppression");
                    this.menuCLient();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur numerique ");
            this.suprClient();
        }
    }

    public void achatBillet(boolean achat) {
        try {
            if (achat) {
                System.out.println("---------achat billet -----------");
            } else {
                System.out.println("---------Annulation billet -----------");
            }

            afficheList(service.getClients());
            System.out.println("choisiser un client :");
            long clientId = s.nextLong();
            Client client = service.getClient(clientId);

            afficheList(service.getEvenements());
            System.out.println("choisiser un evenemement");
            long eventId = s.nextLong();
            Evenement evenement=service.getEvenement(eventId);
            if(evenement==null || client==null)
                throw new InputMismatchException();
            if (achat) {
                if(service.achatBillet(client,evenement))
                    System.out.println("Achat d'un billet");
                else
                    System.out.println("Erreur lors de l'achat");
            } else {
                if(service.annulerBillet(client,evenement))
                    System.out.println("Annulation");
                else
                    System.out.println("Erreur lors de l'annulation");
            }
            this.menuCLient();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("index out of bound");
            this.menuCLient();
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur valide");
            this.achatBillet(achat);
        }
    }

    public void affichageBillet() {
        try {
            System.out.println("---------afffichage des billets----------");
               afficheList(service.getClients());
            System.out.println("choisiser un client :");
            long clientId = s.nextLong();
            Client client=service.getClient(clientId);
            if(client==null)
                System.out.println("Client inexistant");
            else{
                this.afficheList(service.getBillet(client));
            }
            this.menuCLient();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("index out of bound");
            this.menuCLient();
        } catch (InputMismatchException e) {
            System.out.println("entrer une valeur valide");
            this.affichageBillet();
        }
    }

    //fonctions global
    public void afficheList(List list) {
        list.forEach(System.out::println);
        System.out.println();
    }


}
