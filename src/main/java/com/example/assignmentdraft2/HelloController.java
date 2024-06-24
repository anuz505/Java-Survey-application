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

  //Survey enter code wala section
    public TextField SCode;
    @FXML
    private Label validationLabel;

    private  String baseDir = "C:/Users/anuz/Documents/JAVA Practice subit/AssignmentDraft2/src/main/resources/com/example/assignmentdraft2/Surveyquestions";

    @FXML
    public void EnterSurvey() {
        String inputCode = SCode.getText();
        boolean isCodeVerified = false;

        try (Stream<Path> paths = Files.list(Paths.get(baseDir))) {
            for (Path path : (Iterable<Path>) paths::iterator) {
                if (Files.isDirectory(path) && path.getFileName().toString().matches("\\d+Surveyquestion")) {
                    File randomCSVFile = new File(path.toString(), "0Survey_0.csv"); // Assuming this is the random generated CSV file

                    if (randomCSVFile.exists()) {
                        try (BufferedReader br = new BufferedReader(new FileReader(randomCSVFile))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                if (line.trim().equals(inputCode)) {
                                    isCodeVerified = true;
                                    validationLabel.setText("Code verified. Reading other CSV files...");
                                    readOtherCSVFiles(path.toString());
                                    generateFXMLFromCSV(path.toString());
                                    break;
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            validationLabel.setText("Error reading the random generated CSV file.");
                        }
                    }
                }
                if (isCodeVerified) {
                    break;
                }
            }

            if (!isCodeVerified) {
                validationLabel.setText("Invalid code. Please try again.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            validationLabel.setText("Error accessing directories.");
        }
    }

    private void readOtherCSVFiles(String directoryPath) {
        // Read mcq.csv
        readCSVFile(new File(directoryPath, "mcq.csv"));
        // Read polar.csv
        readCSVFile(new File(directoryPath, "polar.csv"));
        // Read tquestions.csv
        readCSVFile(new File(directoryPath, "tquestions.csv"));
    }

    private void readCSVFile(File csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Process the line read from the CSV file
                System.out.println("Read from " + csvFile.getName() + ": " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading " + csvFile.getName());
        }
    }
    public void Back() throws IOException {
        loadStage("/com/example/assignmentdraft2/hello-view.fxml");
    }


    //Generating fxml
    private void generateFXMLFromCSV(String directoryPath) {
        List<String> tQuestions = readCSV(new File(directoryPath, "tquestion.csv"));
        List<MCQQuestion> mcqQuestions = readMCQCSV(new File(directoryPath, "mcq.csv"));
        List<String> polarQuestions = readCSV(new File(directoryPath, "polar.csv"));

        if (tQuestions.isEmpty() && mcqQuestions.isEmpty() && polarQuestions.isEmpty()) {
            System.out.println("No questions found in the CSV files.");
            return;
        }

        String fxmlContent = generateFXMLContent(tQuestions, mcqQuestions, polarQuestions);
        writeFXMLFile(fxmlContent, new File(directoryPath, "SurveyQuestions.fxml"));
    }

    private List<String> readCSV(File csvFile) {
        List<String> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                questions.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading " + csvFile.getName());
        }
        return questions;
    }

    private List<MCQQuestion> readMCQCSV(File csvFile) {
        List<MCQQuestion> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    String question = parts[0].trim();
                    List<String> options = new ArrayList<>();
                    for (int i = 1; i < parts.length; i++) {
                        options.add(parts[i].trim());
                    }
                    questions.add(new MCQQuestion(question, options));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading " + csvFile.getName());
        }
        return questions;
    }

    private String generateFXMLContent(List<String> tQuestions, List<MCQQuestion> mcqQuestions, List<String> polarQuestions) {
        StringBuilder fxmlBuilder = new StringBuilder();
        fxmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        fxmlBuilder.append("<?import javafx.scene.control.*?>\n");
        fxmlBuilder.append("<?import javafx.scene.layout.*?>\n");
        fxmlBuilder.append("<VBox xmlns=\"http://javafx.com/javafx/8\" xmlns:fx=\"http://javafx.com/fxml/1\" spacing=\"10\">\n");

        for (String question : tQuestions) {
            fxmlBuilder.append("    <VBox spacing=\"5\">\n");
            fxmlBuilder.append("        <Label text=\"").append(question).append("\" />\n");
            fxmlBuilder.append("        <TextField fx:id=\"").append(generateFXID(question)).append("\" />\n");
            fxmlBuilder.append("    </VBox>\n");
        }

        for (MCQQuestion mcq : mcqQuestions) {
            fxmlBuilder.append("    <VBox spacing=\"5\">\n");
            fxmlBuilder.append("        <Label text=\"").append(mcq.getQuestion()).append("\" />\n");
            for (String option : mcq.getOptions()) {
                fxmlBuilder.append("        <RadioButton text=\"").append(option).append("\" />\n");
            }
            fxmlBuilder.append("    </VBox>\n");
        }

        for (String question : polarQuestions) {
            fxmlBuilder.append("    <VBox spacing=\"5\">\n");
            fxmlBuilder.append("        <Label text=\"").append(question).append("\" />\n");
            fxmlBuilder.append("        <HBox spacing=\"10\">\n");
            fxmlBuilder.append("            <RadioButton text=\"Yes\" />\n");
            fxmlBuilder.append("            <RadioButton text=\"No\" />\n");
            fxmlBuilder.append("        </HBox>\n");
            fxmlBuilder.append("    </VBox>\n");
        }

        fxmlBuilder.append("</VBox>\n");
        return fxmlBuilder.toString();
    }

    private String generateFXID(String question) {
        return "tf_" + question.replaceAll("[^a-zA-Z0-9]", "");
    }

    private void writeFXMLFile(String content, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            System.out.println("FXML file written to: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing FXML file.");
        }
    }


    private static class MCQQuestion {
        private final String question;
        private final List<String> options;

        public MCQQuestion(String question, List<String> options) {
            this.question = question;
            this.options = options;
        }

        public String getQuestion() {
            return question;
        }

        public List<String> getOptions() {
            return options;
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
