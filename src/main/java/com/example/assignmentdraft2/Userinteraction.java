package com.example.assignmentdraft2;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Userinteraction {
    @FXML
    private ToggleGroup mcqToggleGroup;

    @FXML
    private ToggleGroup polarToggleGroup;

    @FXML
    private TextField textq;
    @FXML
    private void handleSubmitButtonAction() {
        // Get selected answers
        RadioButton selectedTravelMode = (RadioButton) mcqToggleGroup.getSelectedToggle();
        RadioButton selectedSatisfaction = (RadioButton) polarToggleGroup.getSelectedToggle();
        String travelOpinion = textq.getText();

        String travelMode = selectedTravelMode != null ? selectedTravelMode.getText() : "";
        String satisfaction = selectedSatisfaction != null ? selectedSatisfaction.getText() : "";

        // Prepare CSV data
        String csvData = String.format("%s,%s,%s\n", travelMode, satisfaction, travelOpinion);

        // Write to CSV file
        String filePath = "C:\\Users\\anuz\\Documents\\JAVA Practice subit\\AssignmentDraft2\\src\\main\\resources\\SurveyData\\survey_data.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}