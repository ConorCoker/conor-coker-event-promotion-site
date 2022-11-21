package com.example.conorcokereventpromotionsite;

import java.io.Serializable;
import java.util.Random;

public class Event{

    private String title;
    private String description;
    private String type;
    private String location;
    private String venue;
    private String price;
    private final Organiser organiser;
    private int eventId;

    public Event(Organiser organiser, String title, String description, String venue, String price) {

        this.organiser = organiser;
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.price = price;
        Random random = new Random();
        do {
            eventId = random.nextInt(200) + 2000;
        } while (!isEventIdUnique(eventId));
    }

    public Organiser getOrganiser() {
        return organiser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (!type.isBlank()) {
            this.type = type;
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (!location.isBlank()) {
            this.location = location;
        }
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getEventId() {
        return eventId;
    }

    public boolean isEventIdUnique(int eventId) {

        boolean isUnique = true;

        for (Event event:WhatEventApp.getEvents()) {

            if (eventId != event.getEventId()) {
                continue;
            }
            isUnique=false;
        }
        return isUnique;
    }

    @Override
    public String toString() {
        return "EVENT DETAILS - \n" +
                "Title - " + title + " \n" +
                "Description -  " + description + " \n" +
                "Type - " + type + " \n" +
                "Location - " + location + " \n" +
                "Venue - " + venue + " \n" +
                "Price - €" + price + " \n" +
                "Organised by - " + organiser.getName();
    }
}
