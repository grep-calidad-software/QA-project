package com.example.practicaps.utils;

public class User {
    private String uid, email, nombre, apellido;

    public User(String uid, String email, String nombre, String apellido) {
        this.uid = uid;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }




    @Override
    public String toString() {
        return "Usuarios{" +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                "uid='" + uid + '\'' +
                '}';
    }
}
