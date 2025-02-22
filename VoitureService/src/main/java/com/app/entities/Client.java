package com.app.entities;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


public class Client {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private Float age;

    public Client(Long id, String nom, Float age) {
        this.id = id;
        this.nom = nom;
        this.age = age;
    }

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getAge() {
        return age;
    }

    public void setAge(Float age) {
        this.age = age;
    }
}