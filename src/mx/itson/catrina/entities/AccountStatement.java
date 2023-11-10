/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itson.catrina.entities;

import com.google.gson.Gson;
import java.util.List;


/**
 * Attributes of the AccountStatement class and its getters and setters.
 * @author José Alvarez
 */
public class AccountStatement {    
    private String account;
    private String clabe;
    private String currency;
    private Customer customer;
    private List<Movements> transactions;
    
    public AccountStatement deserialized(String json){
        AccountStatement accountStatement = new AccountStatement();
        try{
            accountStatement = new Gson().fromJson(json, AccountStatement.class);
        }catch(Exception ex){
            System.err.print("An error occurred: " + ex.getMessage());
        }
        return accountStatement;
    }
    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the clabe
     */
    public String getClabe() {
        return clabe;
    }

    /**
     * @param clabe the clabe to set
     */
    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the transactions
     */
    public List<Movements> getTransactions() {
        return transactions;
    }

    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(List<Movements> transactions) {
        this.transactions = transactions;
    }
    
    
}
