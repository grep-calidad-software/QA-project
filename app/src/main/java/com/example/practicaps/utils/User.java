package com.example.practicaps.utils;

public class User {
    private String uid, email, name, surname;

    public User(String uid, String email, String name, String surname) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public String getUid() {return uid;}
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name;}
    public String getSurname() {return surname;}


    @Override
    public String toString() {
        return "Usuarios{" +
                ", apellido='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + name + '\'' +
                "uid='" + uid + '\'' +
                '}';
    }

    public User(){}
}
