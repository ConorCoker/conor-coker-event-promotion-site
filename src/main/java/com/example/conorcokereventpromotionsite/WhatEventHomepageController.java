package com.example.conorcokereventpromotionsite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WhatEventHomepageController implements Initializable {

    @FXML
    private ChoiceBox<String> eventCategoryChoiceBox;

    @FXML
    private ChoiceBox<String> eventLocationChoiceBox;

    @FXML
    private Label labelNumOfOrganisers;

    @FXML
    private TableView<Event> tableView;

    @FXML
    private TableColumn<Event, Image> imageTableColumn;

    @FXML
    private TableColumn<Event, String> nameTableColumn;

    @FXML
    private TableColumn<Event, String> typeTableColumn;

    @FXML
    private TableColumn<Event, String> locationTableColumn;

    @FXML
    private TableColumn<Event, String> priceTableColumn;

    @FXML
    private TableColumn<Event, Organiser> organiserTableColumn;

    @FXML
    private TableColumn<Event, String> dateTableColumn;

    private final Utilities utils = new Utilities();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelNumOfOrganisers.setText("Number of Organisers Registered = " + RegistrationPageController.getNumberOfUsers());
        fillCheckboxes();
        setupTable();


    }

    private void setupTable() {
        ObservableList<Event> observableListOfEvents = FXCollections.observableArrayList(WhatEventApp.getEvents());

        imageTableColumn.setCellFactory(param -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            TableCell<Event, Image> cell = new TableCell<Event, Image>() {
                public void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageView.setImage(item);
                    }
                }
            };
            cell.setGraphic(imageView);
            return cell;
        });

        imageTableColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        organiserTableColumn.setCellValueFactory(new PropertyValueFactory<>("organiser"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        imageTableColumn.setGraphic(new ImageView());

        tableView.setItems(observableListOfEvents);
    }

    private void fillCheckboxes() {
        ObservableList<String> areas = FXCollections.observableArrayList("Any Area", "Waterford", "Kilkenny", "Galway", "Dublin", "Limerick");
        eventLocationChoiceBox.setItems(areas);
        eventLocationChoiceBox.setValue("Any Area");

        ObservableList<String> types = FXCollections.observableArrayList("Any Category", "Music", "Drama", "Poetry", "Comedy", "Entertainment", "Educational");
        eventCategoryChoiceBox.setItems(types);
        eventCategoryChoiceBox.setValue("Any Category");
    }

    @FXML
    void loginButtonOnClick(ActionEvent event) throws IOException {

        utils.switchScreen(event, "whatevent-login-page.fxml", "What Event Ireland - Login");

    }

    @FXML
    void registerButtonOnClick(ActionEvent event) throws IOException {

        utils.switchScreen(event, "whatevent-registration-page.fxml", "What Event Ireland - Registration");
    }

//    @FXML
//    void buttonFindOnClick() {
//
//        if (eventCategoryChoiceBox.getValue().equals("Any Category") && !eventLocationChoiceBox.getValue().equals("Any Area")) {
//            showEventsTextArea.setText(generateStringToOutput(filterEventsByLocation(eventLocationChoiceBox.getValue())));
//        } else if (!eventCategoryChoiceBox.getValue().equals("Any Category") && eventLocationChoiceBox.getValue().equals("Any Area")) {
//            showEventsTextArea.setText(generateStringToOutput(filterEventsByCategory(eventCategoryChoiceBox.getValue())));
//        } else if (!eventLocationChoiceBox.getValue().equals("Any Area") && !eventCategoryChoiceBox.getValue().equals("Any Category")) {
//            showEventsTextArea.setText(generateStringToOutput(filterEventsByCategoryAndLocation(eventCategoryChoiceBox.getValue(), eventLocationChoiceBox.getValue())));
//        } else {
//            showEventsTextArea.setText(generateStringToOutput(WhatEventApp.getEvents()));
//        }
//    }

    private ArrayList<Event> filterEventsByCategoryAndLocation(String category, String location) {

        ArrayList<Event> eventsMatchingFilter = new ArrayList<>();

        for (Event event : WhatEventApp.getEvents()) {

            if (event.getType().equals(category) && event.getLocation().equals(location)) {
                eventsMatchingFilter.add(event);
            }


        }
        return eventsMatchingFilter;
    }

    private ArrayList<Event> filterEventsByCategory(String category) {

        ArrayList<Event> eventsMatchingFilter = new ArrayList<>();

        for (Event event : WhatEventApp.getEvents()) {

            if (event.getType().equals(category)) {
                eventsMatchingFilter.add(event);
            }


        }
        return eventsMatchingFilter;

    }

    private ArrayList<Event> filterEventsByLocation(String location) {

        ArrayList<Event> eventsMatchingFilter = new ArrayList<>();

        for (Event event : WhatEventApp.getEvents()) {

            if (event.getLocation().equals(location)) {
                eventsMatchingFilter.add(event);
            }
        }
        return eventsMatchingFilter;
    }

    private String generateStringToOutput(ArrayList<Event> filteredArray) {

        StringBuilder stringBuilder = new StringBuilder();

        for (Event event : filteredArray) {
            stringBuilder.append(event).append("\n").append("\n");
        }

        return stringBuilder.toString();
    }
}