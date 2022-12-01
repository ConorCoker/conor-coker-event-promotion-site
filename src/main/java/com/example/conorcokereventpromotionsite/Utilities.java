package com.example.conorcokereventpromotionsite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Utilities {

    public void switchScreen(ActionEvent event, String fxmlName, String title) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(WhatEventApp.class.getResource(fxmlName));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }

    public void clearTextFields(TextField... fieldsToClear) {
        for (TextField textField : fieldsToClear) {
            textField.clear();
        }

    }

    public boolean checkAreAllTextFieldsFilled(TextField... fieldsToClear) {

        for (TextField textField : fieldsToClear) {
            if (textField.getText().isBlank()){
                return false;
            }
        }
        return true;

    }

    public ChoiceBox<String> fillAndReturnChoiceBoxes(ChoiceBox<String> choiceBox, String typeOrLocation) {

        ObservableList<String> types;
        ObservableList<String> locations;

        if (typeOrLocation.equalsIgnoreCase("types")) {

            types = FXCollections.observableArrayList("Music", "Drama", "Poetry", "Comedy","Educational","Entertainment");
            choiceBox.setItems(types);
            choiceBox.setValue("Any Category");
            return choiceBox;

        } else if (typeOrLocation.equalsIgnoreCase("locations")) {

            locations = FXCollections.observableArrayList("Waterford", "Kilkenny", "Galway", "Dublin","Limerick");
            choiceBox.setItems(locations);
            choiceBox.setValue("Any Area");
            return choiceBox;

        } else {
            return null;
        }
    }

    public Event getEventById(int eventId) {

        for (Event event : WhatEventApp.getEvents()) {

            if (eventId == event.getEventId()) {
                return event;
            }
        }
        return null;
    }
}
