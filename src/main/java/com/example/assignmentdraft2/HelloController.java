package com.example.assignmentdraft2;

import com.opencsv.CSVReader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.opencsv.CSVWriter;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HelloController {

    @FXML
    private TextField Email;

    @FXML
    private PasswordField Password;

    @FXML
    private Label userINFO;

    @FXML
            private Label LoginINFO;

    //user
    @FXML
    private TextField userEmail;
    @FXML
    private PasswordField userPassword;

    String pathtoCSV = "C:/Users/anuz/Documents/JAVA Practice subit/AssignmentDraft2/src/main/resources/AdminData.csv";



    public void openadminsignin() throws IOException {
        loadStage("/com/example/assignmentdraft2/AdminLogin.fxml");
    }

    public void openadminsignup() throws IOException {
        loadStage("/com/example/assignmentdraft2/AdminSignup.fxml");
    }

    public void adminSignUpClickBTN() {
        String adminEmail  = Email.getText();
        String adminPassword = Password.getText();
        try {
            FileWriter fileWriter = new FileWriter(pathtoCSV, true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            String[] data = {adminEmail, adminPassword};
            csvWriter.writeNext(data);
            csvWriter.close();
            loadStage("/com/example/assignmentdraft2/AdminLogin.fxml");
        } catch (Exception e) {
            userINFO.setText(e.getMessage());
        }
    }

    public void adminSigninCLickBTN() {
        // Implement the sign-in logic here
        String emailText = userEmail.getText();
        String passwordText = userPassword.getText();

        try {
            FileReader fileReader = new FileReader(pathtoCSV);
            CSVReader csvReader = new CSVReader(fileReader);
            String[] rows;
            boolean found = false;
            while ((rows = csvReader.readNext()) != null) {
                if (emailText.equals(rows[0]) && passwordText.equals(rows[1])) {
                    LoginINFO.setText("Login successful");
                    loadStage("/com/example/assignmentdraft2/AdminDashboard.fxml");

                    found = true;
                    break;
                }
            }
            if (!found) {
                //surveyCreator's login check

                LoginINFO.setText("Invalid Credentials");
            }
        } catch (Exception e) {
            System.out.println("error" + e);
        }
    }


    @FXML
    public void loadStage(String sceneName) throws IOException {
        try {
            System.out.println("Loading FXML file: " + sceneName);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneName));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) userINFO.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            userINFO.setText("Failed to load scene: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
