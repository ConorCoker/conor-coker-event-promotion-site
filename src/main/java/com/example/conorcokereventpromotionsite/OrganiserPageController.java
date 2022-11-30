package com.example.conorcokereventpromotionsite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrganiserPageController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxEventLocation;

    @FXML
    private ChoiceBox<String> choiceBoxEventType;

    @FXML
    private TextField textFieldEventDescription;

    @FXML
    private TextField textFieldEventTitle;

    @FXML
    private TextField textFieldEventVenue;

    @FXML
    private TextField textFieldInputBox;

    @FXML
    private Text textOutput;

    @FXML
    private TextField textFieldTicketPrice;

    @FXML
    private TextArea textAreaOutput;

    @FXML
    private Button buttonRegisterEvent;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ImageView imageView;

    @FXML
    private Button buttonUploadImage;

    private Organiser loggedInOrganiser;

    private final Utilities utils = new Utilities();

    private int eventId;

    private Image image;
    private String imageFileLocation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getOrganiser();
        setupCheckBoxes();
    }

    private void getOrganiser() {

        loggedInOrganiser = WhatEventApp.getLoggedInUser();
    }

    @FXML
    void buttonDisplayDetailsOnClick() {

        if (!textFieldInputBox.getText().isBlank()) {

            eventId = Integer.parseInt(textFieldInputBox.getText());
            textAreaOutput.setText(utils.getEventById(eventId).toString());
            textFieldInputBox.clear();
            textOutput.setText("");
        } else {
            textOutput.setText("Please Enter an Event ID!");
        }
    }

    @FXML
    void buttonExitOnClick(ActionEvent event) throws IOException {

        utils.switchScreen(event, "whatevent-homepage.fxml", "What Event Ireland - Home");
    }

    @FXML
    void buttonRegisterEventOnClick() {

        createEvent();
    }

    @FXML
    void buttonSummaryOfAllEventsOnClick() {

        if (WhatEventApp.getEvents().size() > 0) {

            StringBuilder stringToPrint = new StringBuilder();

            for (Event event : WhatEventApp.getEvents()) {
                if (event.getOrganiser().getOrganiserId() == loggedInOrganiser.getOrganiserId()) {
                    stringToPrint.append(event.getEventId()).append(": ").append(event.getTitle()).append("\n\n");
                }
            }
            textAreaOutput.setText(stringToPrint.toString());
        } else {
            textOutput.setText("You have No Registered Events!");
        }
    }

    @FXML
    void uploadAnImageOnClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(WhatEventApp.getMainStage());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.getAbsolutePath());
            imageView.setImage(image);
            this.image = image;
            imageFileLocation = selectedFile.getAbsolutePath();
        } else {
            textOutput.setText("Error uploading image please try again!");
        }
    }

    @FXML
    void buttonUpdateDetailsOnClick() {

        if (!textFieldInputBox.getText().isBlank()) {

            if (utils.getEventById(Integer.parseInt(textFieldInputBox.getText())) != null) {

                eventId = Integer.parseInt(textFieldInputBox.getText());

                if (utils.getEventById(eventId).getOrganiser().getOrganiserId()==loggedInOrganiser.getOrganiserId()) {

                    if (utils.checkAreAllTextFieldsFilled(textFieldEventTitle, textFieldEventDescription, textFieldEventVenue, textFieldTicketPrice) && !datePicker.getEditor().getText().isBlank()) {
                        utils.getEventById(eventId).setTitle(textFieldEventTitle.getText());
                        utils.getEventById(eventId).setDescription(textFieldEventDescription.getText());
                        utils.getEventById(eventId).setType(choiceBoxEventType.getValue());
                        utils.getEventById(eventId).setLocation(choiceBoxEventLocation.getValue());
                        utils.getEventById(eventId).setVenue(textFieldEventVenue.getText());
                        utils.getEventById(eventId).setPrice(textFieldTicketPrice.getText());
                        utils.getEventById(eventId).setDate(datePicker.getValue().toString());
                        utils.getEventById(eventId).setImage(imageView.getImage());
                        textOutput.setText("Event Updated!");
                        utils.clearTextFields(textFieldEventTitle, textFieldEventDescription, textFieldEventVenue, textFieldTicketPrice, datePicker.getEditor(), textFieldInputBox);
                        imageView.setImage(null);
                        buttonRegisterEvent.setDisable(false);
                    } else {
                        textOutput.setText("Change Event Details Above!");
                        buttonUploadImage.setText("Change/Add Image?");
                        textFieldEventTitle.setText(utils.getEventById(eventId).getTitle());
                        textFieldEventDescription.setText(utils.getEventById(eventId).getDescription());
                        choiceBoxEventType.setValue(utils.getEventById(eventId).getType());
                        choiceBoxEventLocation.setValue(utils.getEventById(eventId).getLocation());
                        textFieldEventVenue.setText(utils.getEventById(eventId).getVenue());
                        textFieldTicketPrice.setText(utils.getEventById(eventId).getPrice());
                        imageView.setImage(utils.getEventById(eventId).getImage());
                        buttonRegisterEvent.setDisable(true);
                    }
                }
                else{
                    textOutput.setText("You dont have permission to change this event!");
                }
            }
            else{
                textOutput.setText("Please enter a valid Event ID!");
            }


        } else {
            textOutput.setText("Please Enter an Event ID!");
        }
    }


    private void createEvent() {
        if (requiredFieldsEntered()) {
            WhatEventApp.getEvents().add(new Event(loggedInOrganiser, textFieldEventTitle.getText(), textFieldEventDescription.getText(),
                    textFieldEventVenue.getText(), textFieldTicketPrice.getText(), datePicker.getValue().toString()));
            WhatEventApp.getEvents().get(WhatEventApp.getEvents().size() - 1).setType(choiceBoxEventType.getValue());
            WhatEventApp.getEvents().get(WhatEventApp.getEvents().size() - 1).setLocation(choiceBoxEventLocation.getValue());
            if (checkIsUserUploadingAnImage()) {
                WhatEventApp.getEvents().get(WhatEventApp.getEvents().size() - 1).setImage(image);
                WhatEventApp.getEvents().get(WhatEventApp.getEvents().size() - 1).setImageFileLocation(imageFileLocation);
                this.image = null;
                imageFileLocation = null;
            }
            utils.clearTextFields(textFieldEventTitle, textFieldEventDescription, textFieldEventVenue, textFieldTicketPrice, datePicker.getEditor());
            imageView.setImage(null);
        } else {
            textOutput.setText("Please Enter All Required Fields!");
        }
    }

    private boolean checkIsUserUploadingAnImage() {
        return image != null;
    }


    private boolean requiredFieldsEntered() {
        return !textFieldEventTitle.getText().isBlank() && !textFieldEventDescription.getText().isBlank() &&
                !textFieldTicketPrice.getText().isBlank() && !textFieldEventVenue.getText().isBlank() && !datePicker.getEditor().getText().isBlank();
    }

    private void setupCheckBoxes() {
        choiceBoxEventLocation = utils.fillAndReturnChoiceBoxes(choiceBoxEventLocation, "locations");
        choiceBoxEventType = utils.fillAndReturnChoiceBoxes(choiceBoxEventType, "types");
    }

}

