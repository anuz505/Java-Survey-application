package com.example.assignmentdraft2;

import com.opencsv.CSVReader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.opencsv.CSVWriter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class HelloController {

    @FXML
    private TextField Email;

    @FXML
    private PasswordField Password;

    @FXML
    private Label userINFO;

    @FXML
    private Label userINFO1;

    @FXML
            private Label LoginINFO;

    //user
    @FXML
    private TextField userEmail;
    @FXML
    private PasswordField userPassword;

    @FXML
    private TextField SCemail;

    @FXML
    private PasswordField SCpassword;

    @FXML
    private RadioButton SCradioM;

    @FXML
    private RadioButton SCradioF;

    @FXML
    private ChoiceBox<String> SCnation;

    @FXML
    private String gender;

    @FXML
            private AnchorPane dynamicBox;

    String pathtoCSV = "C:/Users/anuz/Documents/JAVA Practice subit/AssignmentDraft2/src/main/resources/AdminData.csv";

    @FXML
    private Label SubmitL;

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
                FileReader fileReader1 = new FileReader(SCpathtoCSV);
                CSVReader csvReader1 = new CSVReader(fileReader1);
                String[] rows1;
                boolean found1 = false;
                while((rows1 = csvReader1.readNext())!=null){
                    if(emailText.equals(rows1[0]) && passwordText.equals(rows1[1])){
                        LoginINFO.setText("login successful");
                        loadStage("/com/example/assignmentdraft2/SurveyCreator.fxml");
                        found1 = true;
                        break;
                    }
                }
                if (!found1){
                    LoginINFO.setText("invalid Credentials");
                }
            }
        } catch (Exception e) {
            System.out.println("error" + e);
        }
    }

    //surveyCreator
    public void SCsignUP()throws IOException{
        loadStage("/com/example/assignmentdraft2/SurveySignup.fxml");
    }
    @FXML
    public void initialize() {
        if (SCnation != null) {
            SCnation.getItems().addAll("Nepal", "India", "Japan", "China");
        } else {
            System.out.println("ChoiceBox nation is null!");
        }
    }

    String SCpathtoCSV = "C:/Users/anuz/Documents/JAVA Practice subit/AssignmentDraft2/src/main/resources/SCData.csv";
    public void SCsignupBTN(){
        String Scemail = SCemail.getText();
        String Scpw = SCpassword.getText();


        if (SCradioM.isSelected()) {
            gender = "male";
        } else {
            gender = "female";
        }
        initialize();
        String nationality = SCnation.getValue();

        String userInfolabel = "Email: " + SCemail + "\n" +
                "Gender: " + gender + "\n" +
                "Nationality: " + nationality;
        userINFO.setText(userInfolabel);

        // Save to CSV
        try {
            FileWriter fileWriter = new FileWriter(SCpathtoCSV, true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            String[] data = {Scemail, Scpw, gender, nationality};
            csvWriter.writeNext(data);
            csvWriter.close();
            loadStage("/com/example/assignmentdraft2/AdminLogin.fxml");
        } catch (Exception e) {
            userINFO.setText(e.getMessage());
        }
    }

    //surveycreator functions
   public void CreateSurvey() throws IOException {
      userINFO.setText("Survey Created click on view survey");

       FileWriter fileWriter1 = new FileWriter("C:\\Users\\anuz\\Documents\\JAVA Practice subit\\AssignmentDraft2\\src\\main\\resources\\surveyCode.csv",true);
       CSVWriter csvWriter1 = new CSVWriter(fileWriter1);
       String data1 = generateRandomWord();
       String[] data2 = {data1};
       csvWriter1.writeNext(data2);
       csvWriter1.close();
       userINFO.setText("data1");

       //load stage to survey questions
       loadStage("/com/example/assignmentdraft2/Surveyquestions.fxml");


   }

    @FXML
    public void backtologin() {
        try {
            if (userINFO == null) {
                System.err.println("userINFO is null. Ensure it's properly initialized.");
                return;
            }
            if (userINFO.getScene() == null) {
                System.err.println("userINFO does not have a scene. Check FXML loading.");
                return;
            }
            loadStage("/com/example/assignmentdraft2/AdminLogin.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            if (userINFO1 != null) {
                userINFO1.setText(e.getMessage());
            }
        }
    }

    private static final String DIRECTORY_PATH = "C:\\Users\\anuz\\Documents\\JAVA Practice subit\\AssignmentDraft2\\src\\main\\resources\\com\\example\\assignmentdraft2\\Surveryquestions\\"; // Change this to your desired directory
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final int WORD_LENGTH = 6;
    public static String generateRandomWord() {
        StringBuilder word = new StringBuilder(WORD_LENGTH);
        Random random = new Random();

        for (int i = 0; i < WORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            word.append(CHARACTERS.charAt(index));
        }

        return word.toString();
    }
    public static  int fileCounter = 0;
    public static void createCSVFile(String directoryPath) {
        // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = directoryPath + "/" + fileCounter + "Survey_" + fileCounter + ".csv";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            System.out.println("CSV file created: " + fileName);
            fileWriter.write(generateRandomWord());
        } catch (IOException e) {
            System.err.println("Error creating CSV file: " + e.getMessage());
        }
        fileCounter++;
    }




    //adding new element
    private int tQuestionCount = 0;
    private int mcqCount = 0;
    private int polarCount = 0;

    @FXML
    public void addTquestion() {
        TextField newTextField = new TextField();
        newTextField.setId("tquestion-" + tQuestionCount++);
        Button addTquestionBtn = new Button("Add Tquestion");
        Button polarQuestionsBtn = new Button("Polar Questions");
        Button mcqBtn = new Button("MCQ");

        newTextField.setPrefWidth(380);
        newTextField.setPrefHeight(20);

        addTquestionBtn.setOnAction(e -> addTquestion());
        polarQuestionsBtn.setOnAction(e -> addPolarQuestions());
        mcqBtn.setOnAction(e -> addMCQ());

        layoutTextField(newTextField);
        layoutBelow(newTextField, addTquestionBtn);
        layoutBelow(addTquestionBtn, polarQuestionsBtn);
        layoutBelow(polarQuestionsBtn, mcqBtn);

        dynamicBox.getChildren().addAll(newTextField, addTquestionBtn, polarQuestionsBtn, mcqBtn);
    }

    @FXML
    public void addMCQ() {
        TextField newTextField1 = new TextField();
        newTextField1.setId("mcq-" + mcqCount++);
        TextField newTextField2 = new TextField();
        newTextField2.setId("mcq-" + mcqCount++);
        TextField newTextField3 = new TextField();
        newTextField3.setId("mcq-" + mcqCount++);
        TextField newTextField4 = new TextField();
        newTextField4.setId("mcq-" + mcqCount++);
        TextField newTextField5 = new TextField();
        newTextField5.setId("mcq-" + mcqCount++);

        Button addTquestionBtn = new Button("Add Tquestion");
        Button polarQuestionsBtn = new Button("Polar Questions");
        Button mcqBtn = new Button("MCQ");

        newTextField1.setPrefWidth(380);
        newTextField1.setPrefHeight(20);
        newTextField2.setPrefWidth(380);
        newTextField2.setPrefHeight(20);
        newTextField3.setPrefWidth(380);
        newTextField3.setPrefHeight(20);
        newTextField4.setPrefWidth(380);
        newTextField4.setPrefHeight(20);
        newTextField5.setPrefWidth(380);
        newTextField5.setPrefHeight(20);

        addTquestionBtn.setOnAction(e -> addTquestion());
        polarQuestionsBtn.setOnAction(e -> addPolarQuestions());
        mcqBtn.setOnAction(e -> addMCQ());

        layoutTextField(newTextField1);
        layoutTextFieldBelow(newTextField2, newTextField1);
        layoutTextFieldBelow(newTextField3, newTextField2);
        layoutTextFieldBelow(newTextField4, newTextField3);
        layoutTextFieldBelow(newTextField5, newTextField4);
        layoutBelow(newTextField5, addTquestionBtn);
        layoutBelow(addTquestionBtn, polarQuestionsBtn);
        layoutBelow(polarQuestionsBtn, mcqBtn);

        dynamicBox.getChildren().addAll(newTextField1, newTextField2, newTextField3, newTextField4, newTextField5, addTquestionBtn, polarQuestionsBtn, mcqBtn);
    }

    @FXML
    public void addPolarQuestions() {
        TextField newTextField = new TextField();
        newTextField.setId("polar-" + polarCount++);
        Button addTquestionBtn = new Button("Add Tquestion");
        Button polarQuestionsBtn = new Button("Polar Questions");
        Button mcqBtn = new Button("MCQ");

        newTextField.setPrefWidth(380);
        newTextField.setPrefHeight(20);

        addTquestionBtn.setOnAction(e -> addTquestion());
        polarQuestionsBtn.setOnAction(e -> addPolarQuestions());
        mcqBtn.setOnAction(e -> addMCQ());

        layoutTextField(newTextField);
        layoutBelow(newTextField, addTquestionBtn);
        layoutBelow(addTquestionBtn, polarQuestionsBtn);
        layoutBelow(polarQuestionsBtn, mcqBtn);

        dynamicBox.getChildren().addAll(newTextField, addTquestionBtn, polarQuestionsBtn, mcqBtn);
    }

    //layout
    private void layoutTextField(TextField textField) {
        Node lastNode = dynamicBox.getChildren().get(dynamicBox.getChildren().size() - 1);
        double layoutY = lastNode.getLayoutY();
        textField.setLayoutX(50);
        textField.setLayoutY(layoutY + lastNode.getBoundsInParent().getHeight() + 20);
    }

    private void layoutTextFieldBelow(TextField textField, Node aboveNode) {
        double layoutY = aboveNode.getLayoutY();
        textField.setLayoutX(50);
        textField.setLayoutY(layoutY + aboveNode.getBoundsInParent().getHeight() + 20);
    }

    private void layoutBelow(Node aboveNode, Node belowNode) {
        double layoutY = aboveNode.getLayoutY();
        belowNode.setLayoutX(50);
        belowNode.setLayoutY(layoutY + aboveNode.getBoundsInParent().getHeight() + 20);
    }

    //saving to csv
    @FXML
    public void submitquestions() {
        String baseDir = "C:/Users/anuz/Documents/JAVA Practice subit/AssignmentDraft2/src/main/resources/com/example/assignmentdraft2/Surveyquestions";
        File newDir = createNewDirectory(baseDir);

        saveTQuestions(newDir.getAbsolutePath());
        savePolarQuestions(newDir.getAbsolutePath());
        saveMCQs(newDir.getAbsolutePath());

        // Use the path of the newly created or existing 1Surveyquestions directory
        createCSVFile(newDir.getAbsolutePath());
        SubmitL.setText("Saved");
    }


    private File createNewDirectory(String baseDir) {
        File baseDirectory = new File(baseDir);
        if (!baseDirectory.exists()) {
            baseDirectory.mkdirs();
        }

        int nextNumber = 1;
        try (Stream<Path> paths = Files.list(Paths.get(baseDir))) {
            nextNumber = paths.filter(Files::isDirectory)
                    .map(path -> path.getFileName().toString())
                    .filter(name -> name.matches("\\d+Surveyquestion"))
                    .map(name -> Integer.parseInt(name.replace("Surveyquestion", "")))
                    .max(Comparator.naturalOrder())
                    .orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        File newDir = new File(baseDir, nextNumber + "Surveyquestion");
        if (!newDir.exists()) {
            newDir.mkdirs();
        }
        return newDir;
    }

    @FXML
    public void saveTQuestions(String dirPath) {
        saveQuestionsToCSV("tquestion", dirPath + "/tquestions.csv");
    }

    @FXML
    public void saveMCQs(String dirPath) {
        saveQuestionsToCSV("mcq", dirPath + "/mcq.csv");
    }

    @FXML
    public void savePolarQuestions(String dirPath) {
        saveQuestionsToCSV("polar", dirPath + "/polar.csv");

    }

    private void saveQuestionsToCSV(String questionPrefix, String fileName) {
        List<String> questions = new ArrayList<>();
        for (Node node : dynamicBox.getChildren()) {
            if (node instanceof TextField && node.getId() != null && node.getId().startsWith(questionPrefix)) {
                questions.add(((TextField) node).getText());
            }
        }

        try (FileWriter writer = new FileWriter(fileName)) {

            for (String question : questions) {
                writer.write(question + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
