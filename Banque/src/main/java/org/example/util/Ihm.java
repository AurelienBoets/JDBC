package org.example.util;

import org.example.entity.Account;
import org.example.entity.Transaction;
import org.example.entity.TransactionEnum;
import org.example.entity.User;
import org.example.service.BankService;

import java.util.List;
import java.util.Scanner;

public class Ihm {
    private static Scanner sc=new Scanner(System.in);
    private static BankService bankService=new BankService();
    private static User user;

    public static void start(){
        user=getUser();
        int choice;
        do {
            menu();
            choice= sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 0-> System.out.println("Fin du programme");
                case 1->createAccount();
                case 2->getAccounts();
                case 3->getTransactions();
                case 4->deleteAccount();
                case 5->deleteUser();
                case 6->makeTransaction();
                case 7->user=getUser();
            }
        }while(choice!=0);
    }

    private static void makeTransaction() {
        System.out.println("Tapez le numéro de compte");
        long id= sc.nextInt();
        Account account=bankService.getAccount(id);
        if(account==null || account.getUser().getId()!=user.getId()){
            System.out.println("Aucun compte correspondant");
            return;
        }
        TransactionEnum transactionEnum;
        System.out.println("""
                1.Dépot
                2.Retrait""");
        if(sc.nextInt()==1){
            transactionEnum=TransactionEnum.DEPOSIT;
        }else if(sc.nextInt()==2){
            transactionEnum=TransactionEnum.WITHDRAWAL;
        }else{
            System.out.println("Erreur");
            return;
        }
        System.out.println("Montant:");
        double amount=sc.nextDouble();
        Transaction transaction=new Transaction();
        transaction.setAmount(amount);
        transaction.setStatus(transactionEnum);
        if(bankService.makeTransaction(transaction,account))
            System.out.println("Transaction réalisé");
        else
            System.out.println("Erreur lors de la transaction");
    }

    private static void deleteUser() {
        if(bankService.deleteUser(user)) {
            System.out.println("Création d'un nouvel utilisateur");
            user = getUser();
        }else{
            System.out.println("Erreur lors de la suppression de l'utilisateur");
        }
    }

    private static void deleteAccount() {
        System.out.println("Tapez le numéro de compte");
        long id= sc.nextInt();
        Account account=bankService.getAccount(id);
        if(account==null || account.getUser().getId()!=user.getId()){
            System.out.println("Aucun compte correspondant");
            return;
        }
        if(bankService.deleteAccount(account)){
            System.out.println("Suppression du compte");
        }else{
            System.out.println("Erreur lors de la suppression du compte");
        }
    }

    private static void getTransactions() {
        System.out.println("Tapez le numéro de compte");
        long id= sc.nextInt();
        Account account=bankService.getAccount(id);
        if(account==null || account.getUser().getId()!=user.getId()){
            System.out.println("Aucun compte correspondant");
            return;
        }
        for(Transaction i: account.getTransactions()){
            System.out.println(i);
        }
    }

    private static void getAccounts() {
        List<Account> accounts=bankService.getAccount(user);
        if(accounts.isEmpty()){
            System.out.println("Veuillez créer un compte");
            return;
        }
        for(Account i:accounts){
            System.out.println(i);
        }
    }

    private static void createAccount() {
        if(bankService.createAccount(0,user)){
            System.out.println("Creation du compte");
        }else{
            System.out.println("Erreur lors de la création d'un compte");
        }
    }

    private static User getUser(){
        System.out.println("Indiquez votre nom");
        String lastName=sc.nextLine();
        System.out.println("Indiquez votre prénom");
        String firstName=sc.nextLine();
        System.out.println("Indiquez votre numéro de téléphone");
        String phone=sc.nextLine();
        return bankService.verifyUser(firstName,lastName,phone);
    }

    private static void menu(){
        System.out.println("""
                1.Créez compte
                2.Voir liste de compte
                3.Voir transaction
                4.Supprimez compte
                5.Supprimez compte utilisateur
                6.Faire une transaction
                7.Changez d'utilisateur
                0.Quittez""");
    }
}
