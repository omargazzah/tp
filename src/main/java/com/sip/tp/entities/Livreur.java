package com.sip.tp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Livreur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;


    @NotBlank(message = "Address is mandatory")
    @Column(name = "address")
    private String address;


    @NotBlank(message = "Email is mandatory")
    @Column(name = "email")
    private String email;

    @OneToMany
   List<Commande> commandes;


    @Column(name = "logo")
    private String logo;

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public Livreur() {
    }

    public Livreur(String name, String address, String email, String logo) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.logo = logo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
