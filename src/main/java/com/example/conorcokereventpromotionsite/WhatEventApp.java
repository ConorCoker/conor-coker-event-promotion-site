package com.example.conorcokereventpromotionsite;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WhatEventApp extends Application {

    private static HashMap<String, Organiser> users;
    private static ArrayList<Event> events = new ArrayList<>();

    private static Organiser loggedInUser = null;
    private static Stage mainStage;

    public static HashMap<String, Organiser> getUsers() {
        return users;
    }

    public static Organiser getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(Organiser loggedInUser) {
        WhatEventApp.loggedInUser = loggedInUser;
    }

    public static ArrayList<Event> getEvents() {
        return events;
    }


    public boolean registerUser(String key, Organiser organiser) {
        int numBeforeAdd = users.size();
        users.put(key, organiser);
        return users.size() > numBeforeAdd;
    }

    @Override
    public void start(Stage stage) throws IOException {
        users = new HashMap<>();
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(WhatEventApp.class.getResource("whatevent-homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("What Event Ireland - Home");
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void main(String[] args) {
        launch();
    }

    public static void save(){
        try {
            FileOutputStream fileOutputStreamEvents = new FileOutputStream("events.xml");
            ObjectOutputStream objectOutputStreamEvents = new ObjectOutputStream(fileOutputStreamEvents);
            objectOutputStreamEvents.writeObject(events);
            FileOutputStream fileOutputStreamOrganisers = new FileOutputStream("organisers.xml");
            ObjectOutputStream objectOutputStreamOrganisers = new ObjectOutputStream(fileOutputStreamOrganisers);
            objectOutputStreamOrganisers.writeObject(users);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStreamEvents = new FileInputStream("events.xml");
        ObjectInputStream inputStreamEvents = new ObjectInputStream(fileInputStreamEvents);
        events = (ArrayList<Event>) inputStreamEvents.readObject();
        FileInputStream fileInputStreamOrganisers = new FileInputStream("organisers.xml");
        ObjectInputStream inputStreamOrganisers = new ObjectInputStream(fileInputStreamOrganisers);
        users = (HashMap<String, Organiser>) inputStreamOrganisers.readObject();
    }

}

