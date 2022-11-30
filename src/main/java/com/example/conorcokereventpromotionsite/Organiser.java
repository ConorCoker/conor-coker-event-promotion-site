package com.example.conorcokereventpromotionsite;


import java.io.Serializable;
import java.util.Random;

public class Organiser implements Serializable {

    private final String name;
    private final String email;
    private final String password;
    private String phone = "User did not set a Phone Number";
    private String website = "User did not set a Website";
    private String socialMedia1 = "User did not set a Social Media";
    private String socialMedia2 = "User did not set a Social Media";
    private int organiserId;
    private boolean isAdmin = false;
    private boolean isEnabled = true;

    public Organiser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        if (name.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
            isAdmin = true;
        }
        do {
            Random random = new Random();
            organiserId = random.nextInt(100) + 1000;
        } while (!isOrganiserIdUnique(organiserId));
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    public void setPhone(String phone) {
        if (!phone.isBlank()) {
            this.phone = phone;
        }
    }

    public void setWebsite(String website) {
        if (!website.isBlank()) {
            this.website = website;
        }
    }

    public void setSocialMedia1(String socialMedia1) {
        if (!socialMedia1.isBlank()) {
            this.socialMedia1 = socialMedia1;
        }
    }

    public void setSocialMedia2(String socialMedia2) {
        if (!socialMedia2.isBlank()) {
            this.socialMedia2 = socialMedia2;
        }

    }

    public int getOrganiserId() {
        return organiserId;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isOrganiserIdUnique(int organiserId) {

        for (Object o : WhatEventApp.getUsers().values()) {
            Organiser tempOrganiser = (Organiser) o;
            if (tempOrganiser.getOrganiserId() == organiserId) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "ORGANISER DETAILS - \n" +
                "Name - " + name + " \n" +
                "Email -  " + email + " \n" +
                "Phone - " + phone + " \n" +
                "Website - " + website + " \n" +
                "Social Media 1 - " + socialMedia1 + " \n" +
                "Social Media 2 - " + socialMedia2 + " \n" +
                "Organiser ID - " + organiserId + "\n";
    }

    public String getEmail() {
        return email;
    }
}
