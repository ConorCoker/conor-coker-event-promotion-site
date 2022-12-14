package com.example.conorcokereventpromotionsite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class AdminPageController {

    @FXML
    private TextArea textAreaEventOutput;

    @FXML
    private TextArea textAreaMainOutput;

    @FXML
    private TextField textFieldOrganiserIdInput;

    @FXML
    private Text textOutput;

    private final Utilities utils = new Utilities();

    @FXML
    void buttonEnableOrDisableOrganiser() {

        if (utils.checkAreAllTextFieldsFilled(textFieldOrganiserIdInput)) {

            try{
                int organiserId = Integer.parseInt(textFieldOrganiserIdInput.getText());
                for (Organiser o : WhatEventApp.getUsers().values()) {

                    if (o.getOrganiserId() == organiserId) {
                        boolean enabledDisabledStatus = o.isEnabled();
                        o.setEnabled(!enabledDisabledStatus);
                        WhatEventApp.save();
                        textOutput.setText("Success! Account Enabled Status = "+!enabledDisabledStatus);
                        break;
                    }
                }

            }catch (NumberFormatException e){
                textOutput.setText("Please enter a valid ID! (Numbers only)");
            }


        } else {
            textOutput.setText("Please enter a Organiser ID!");
        }
    }


    @FXML
    void buttonListAllEventsOnClick() {

        if (!WhatEventApp.getEvents().isEmpty()) {

            StringBuilder stringToOutput = new StringBuilder();

            for (Event event : WhatEventApp.getEvents()) {
                stringToOutput.append(event.toString()).append("\n").append("\n");
            }

            textAreaEventOutput.setText(stringToOutput.toString());
        } else {
            textOutput.setText("No Events in System!");
        }

    }

    @FXML
    void buttonListAllOrganisersOnClick() {

        if (WhatEventApp.getUsers().size() > 1) {

            StringBuilder output = new StringBuilder();

            for (Organiser o : WhatEventApp.getUsers().values()) {
                if (!o.isAdmin()) {
                    output.append(o.getName()).append(" ").append(o.getOrganiserId()).append(". Enabled - ").append(o.isEnabled()).append("\n");
                }
            }
            textAreaMainOutput.setText(output.toString());
        } else {
            textOutput.setText("No Organisers in System!");
        }
    }


    @FXML
    void buttonListOrganiserEventsOnClick() {

        if (utils.checkAreAllTextFieldsFilled(textFieldOrganiserIdInput)) {

            try {
                int organiserId = Integer.parseInt(textFieldOrganiserIdInput.getText());
                StringBuilder stringToPrint = new StringBuilder();

                for (Organiser o : WhatEventApp.getUsers().values()) {

                    if (o.getOrganiserId() == organiserId) {

                        for (Event event : WhatEventApp.getEvents()) {
                            if (event.getOrganiser().getOrganiserId()==o.getOrganiserId()) {
                                stringToPrint.append(event).append("\n");
                                textAreaMainOutput.setText(stringToPrint.toString());
                                textOutput.setText("");
                            }
                        }
                    }
                }

            } catch (NumberFormatException e) {
                textOutput.setText("Please enter a valid ID! (Numbers only)");

            }


        } else {
            textOutput.setText("Please enter a Organiser ID!");
        }
    }

    @FXML
    void buttonLogoutOnClick(ActionEvent event) throws IOException {

        utils.switchScreen(event, "whatevent-homepage.fxml", "What Event Ireland - Home");


    }

}
