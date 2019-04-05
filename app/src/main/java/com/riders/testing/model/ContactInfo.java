package com.riders.testing.model;

/**
 * Created by cpu on 09/12/2015.
 */
public class ContactInfo {

    public String name;
    public String surname;
    public String email;

    public static final String NAME_PREFIX = "Name_";
    public static final String SURNAME_PREFIX = "Surname_";
    public static final String EMAIL_PREFIX = "email_";

    public ContactInfo(){

    }

    public ContactInfo(String name){
        this.name = name;
    }

    public ContactInfo(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public ContactInfo(String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public ContactInfo add(String name, String surname, String email){

        ContactInfo contactInfo = new ContactInfo();

        contactInfo.name = name;
        contactInfo.surname = surname;
        contactInfo.email = email;

        return contactInfo;
    }


    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }


    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getSurname(){
        return surname;
    }


    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

}
