package com.example.conorcokereventpromotionsite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventDetailsInDepthController implements Initializable {

    Event eventToDisplay;

    @FXML
    private TextArea textAreaEventDetails;

    @FXML
    private Text textEventNameHeading;

    @FXML
    private ImageView imageViewEventImage;

    @FXML
    private TextArea textAreaOrganiserDetails;

    private final Utilities utils = new Utilities();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        findEventThatWasClickedOn();
        textEventNameHeading.setText(eventToDisplay.getTitle());
        textAreaEventDetails.setText(eventToDisplay.toString());
        imageViewEventImage.setImage(eventToDisplay.getImage());
        textAreaOrganiserDetails.setText(eventToDisplay.getOrganiser().toString());

    }

    @FXML
    void buttonBackOnClick(ActionEvent event) throws IOException {
        eventToDisplay.setInDepthView(false);
        utils.switchScreen(event,"whatevent-homepage.fxml","What Event - Home");

    }

    private void findEventThatWasClickedOn(){
        for (Event event:WhatEventApp.getEvents()) {
            if (event.isInDepthView()){
                eventToDisplay = event;
            }
        }
    }
}
