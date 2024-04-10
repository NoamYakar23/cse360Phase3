package homeworkOne;

import java.text.DecimalFormat;


// Noam Yakar's Running  Homework 4
import javafx.application.Application;
import javafx.scene.text.Text;
import java.nio.file.Path;

import java.lang.Object.*;

import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.Scene;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.util.HashMap;
import javafx.scene.text.FontWeight;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class homeworkOne extends Application {

    @Override
    public void start(Stage primaryStage) {
    	
    	// Welcome message
        Label lblWelcome = new Label("Welcome to Heart Health Imaging and Recording System");
        lblWelcome.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
        
        // Buttons with inline CSS for styling
        Button btnPatientIntake = new Button("Patient Intake");
        btnPatientIntake.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");
        
        
        Button btnCTScanTechView = new Button("CT Scan Tech View");
        btnCTScanTechView.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");
        
        Button btnPatientView = new Button("Patient View");
        btnPatientView.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        // Event Handlers for buttons to navigate to different scenes
        btnPatientIntake.setOnAction(e -> showPatientIntakeStage());
        btnCTScanTechView.setOnAction(e -> showCTScanTechView());
        btnPatientView.setOnAction(e -> showPatientView());

        // Layout for Main Scene with alignment and spacing
        VBox layoutMain = new VBox(10); // 10 is the spacing between elements
        layoutMain.setAlignment(Pos.CENTER);
        layoutMain.setStyle("-fx-background-color: #f0f0f0;"); // Light grey background
        layoutMain.getChildren().addAll(lblWelcome, btnPatientIntake, btnCTScanTechView, btnPatientView);

        Scene mainScene = new Scene(layoutMain, 400, 300); // Adjust the size as needed
        primaryStage.setTitle("Heart Health Imaging and Recording System");
        primaryStage.setScene(mainScene);
        showLoginScreen(primaryStage);
    }

    private void showBlankStage(String title) {
        VBox layout = new VBox(20); // 20 is the spacing between elements
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 400, 300); // Adjust the size as needed
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    
    private void showDoctorView() {
    	Stage doctorStage = new Stage();
    	doctorStage.setTitle("Doctor Dashboard");
    	Label doctorLabel = new Label("Doctor's View");
    	VBox layout = new VBox(10, doctorLabel);
    	Scene scene = new Scene(layout, 400, 500);
    	doctorStage.setScene(scene);
    	doctorStage.show();
    }
    
    
    private void showNurseView() {
    	Stage nurseStage = new Stage();
    	nurseStage.setTitle("Nurse Dashboard");
    	Label nurseLabel = new Label("Nurse's View");
    	VBox layout = new VBox(10, nurseLabel);
    	Scene scene = new Scene(layout, 400, 500);
    	nurseStage.setScene(scene);
    	nurseStage.show();
    }
    
    private void showPatientIntakeStage() {
        Stage intakeStage = new Stage();
        intakeStage.setTitle("Patient Intake Form");

        // Create the form elements
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        
        // add relevant field for the user to enter
        Label lblFirstName = new Label("First Name:");
        TextField txtFirstName = new TextField();
        gridPane.add(lblFirstName, 0, 0);
        gridPane.add(txtFirstName, 1, 0);

        Label lblLastName = new Label("Last Name:");
        TextField txtLastName = new TextField();
        gridPane.add(lblLastName, 0, 1);
        gridPane.add(txtLastName, 1, 1);

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        gridPane.add(lblEmail, 0, 2);
        gridPane.add(txtEmail, 1, 2);

        Label lblPhoneNumber = new Label("Phone Number:");
        TextField txtPhoneNumber = new TextField();
        gridPane.add(lblPhoneNumber, 0, 3);
        gridPane.add(txtPhoneNumber, 1, 3);

        Label lblHealthHistory = new Label("Health History:");
        TextArea txtHealthHistory = new TextArea();
        gridPane.add(lblHealthHistory, 0, 4);
        gridPane.add(txtHealthHistory, 1, 4);

        Label lblInsuranceID = new Label("Insurance ID:");
        TextField txtInsuranceID = new TextField();
        gridPane.add(lblInsuranceID, 0, 5);
        gridPane.add(txtInsuranceID, 1, 5);

        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> savePatientInfo(
                txtFirstName.getText(),
                txtLastName.getText(),
                txtEmail.getText(),
                txtPhoneNumber.getText(),
                txtHealthHistory.getText(),
                txtInsuranceID.getText()
        ));
        gridPane.add(btnSave, 1, 6);

        Scene intakeScene = new Scene(gridPane, 350, 450);
        intakeStage.setScene(intakeScene);
        intakeStage.show();
    }

    
    private void savePatientInfo(String firstName, String lastName, String email, String phone, String healthHistory, String insuranceID) {
        // Generate a unique 5-digit patient ID
        String patientID = generatePatientID();
        String directoryName = "patient_data"; // Name of the folder to store patient data files
        String fileName = patientID + "_PatientInfo.txt";

        // Ensure the directory exists
        Paths.get(directoryName).toFile().mkdirs();

        // Create the file path including the directory
        String filePath = Paths.get(directoryName, fileName).toString();

        String content = "Patient ID: " + patientID + "\n" +
                "First Name: " + firstName + "\n" +
                "Last Name: " + lastName + "\n" +
                "Email: " + email + "\n" +
                "Phone Number: " + phone + "\n" +
                "Health History: " + healthHistory + "\n" +
                "Insurance ID: " + insuranceID;

        try {
            // Save the patient info to a file within the directory
            Files.write(Paths.get(filePath), content.getBytes());
            showAlert(Alert.AlertType.INFORMATION, "Information Saved", "Patient information has been saved successfully in 'patient_data' directory.");
        } catch (IOException ex) {
            showAlert(Alert.AlertType.ERROR, "Error Saving Information", "Failed to save patient information.");
        }
    }


    private String generatePatientID() {
        Random random = new Random();
        return String.format("%05d", random.nextInt(100000));
    }


    
    private void writeUserToFile(String username, String password, String role) {
        String directoryName = "user_data";
        String fileName = "users.txt";

        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File userFile = new File(directory, fileName);
        try (FileWriter fw = new FileWriter(userFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(username + "," + password + "," + role);

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the user file: " + e.getMessage());
        }
    }

 
    private void showCTScanTechView() {
        Stage techStage = new Stage();
        techStage.setTitle("CT Scan Technician View");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Fields for the form
        TextField txtPatientID = new TextField();
        TextField txtTotalCACScore = new TextField();
        Label lblVesselLevelScore = new Label("Vessel level Agatston CAC score"); // New label
        TextField txtLM = new TextField();
        TextField txtLAD = new TextField();
        TextField txtLCX = new TextField();
        TextField txtRCA = new TextField();
        TextField txtPDA = new TextField();

        // Add all the form fields to the gridPane
        gridPane.addRow(0, new Label("Patient ID:"), txtPatientID);
        gridPane.addRow(1, new Label("The total Agatston CAC score:"), txtTotalCACScore);
        gridPane.add(lblVesselLevelScore, 0, 2, 2, 1); // Span 2 columns
        gridPane.addRow(3, new Label("LM:"), txtLM);
        gridPane.addRow(4, new Label("LAD:"), txtLAD);
        gridPane.addRow(5, new Label("LCX:"), txtLCX);
        gridPane.addRow(6, new Label("RCA:"), txtRCA);
        gridPane.addRow(7, new Label("PDA:"), txtPDA);

        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> {
            if (isAllFieldsFilled(txtPatientID, txtTotalCACScore, txtLM, txtLAD, txtLCX, txtRCA, txtPDA)) {
                saveCTScanData(txtPatientID.getText(), txtTotalCACScore.getText(), 
                               txtLM.getText(), txtLAD.getText(), txtLCX.getText(),
                               txtRCA.getText(), txtPDA.getText());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
            }
        });

        gridPane.add(btnSave, 1, 8); // Adjust row index according to your layout

        Scene techScene = new Scene(gridPane, 450, 500); // Adjust size as necessary
        techStage.setScene(techScene);
        techStage.show();
    }


    private boolean isAllFieldsFilled(TextField... fields) {
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

private void saveCTScanData(String patientID, String totalCACScore, String lm, String lad, String lcx, String rca, String pda) {
    String directoryName = "patient_data";
    String fileName = patientID + "CTResults.txt";
    
    // Construct the content string
    String content = String.format(
        "Patient ID: %s\nTotal CAC Score: %s\nLM: %s\nLAD: %s\nLCX: %s\nRCA: %s\nPDA: %s\n",
        patientID, totalCACScore, lm, lad, lcx, rca, pda);

    try {
        Files.createDirectories(Paths.get(directoryName));
        Files.write(Paths.get(directoryName, fileName), content.getBytes());
        showAlert(Alert.AlertType.INFORMATION, "Success", "Data saved successfully.");
    } catch (IOException ex) {
        showAlert(Alert.AlertType.ERROR, "File Error", "Could not save the file.");
    }
}

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String getPatientName(String patientID) {
        String directoryName = "patient_data";
        String fileName = patientID + "_PatientInfo.txt";
        Path filePath = Paths.get(directoryName, fileName);

        if (Files.exists(filePath)) {
            try {
                List<String> lines = Files.readAllLines(filePath);
                for (String line : lines) {
                    if (line.startsWith("First Name:")) {
                        return line.substring(line.indexOf(":") + 1).trim(); // Returns the name after the colon
                    }
                }
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to read patient information.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Patient ID does not exist.");
        }
        return ""; // Return empty if name is not found or if there's an error
    }  
    private void showPatientView() {
        Stage patientStage = new Stage();
        patientStage.setTitle("Patient View (Seeing the Results)");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // A field for the patient to enter their ID
        TextField txtPatientID = new TextField();
        gridPane.add(new Label("Patient ID:"), 0, 0);
        gridPane.add(txtPatientID, 1, 0);

        // Non-editable fields to display the results
        TextField txtTotalCACScore = new TextField();
        TextField txtLM = new TextField();
        TextField txtLAD = new TextField();
        TextField txtLCX = new TextField();
        TextField txtRCA = new TextField();
        TextField txtPDA = new TextField();

        // Set text fields to be non-editable
        txtTotalCACScore.setEditable(false);
        txtLM.setEditable(false);
        txtLAD.setEditable(false);
        txtLCX.setEditable(false);
        txtRCA.setEditable(false);
        txtPDA.setEditable(false);

        // Add non-editable fields to the gridPane
        gridPane.addRow(1, new Label("The total Agatston CAC score:"), txtTotalCACScore);
        gridPane.addRow(2, new Label("LM:"), txtLM);
        gridPane.addRow(3, new Label("LAD:"), txtLAD);
        gridPane.addRow(4, new Label("LCX:"), txtLCX);
        gridPane.addRow(5, new Label("RCA:"), txtRCA);
        gridPane.addRow(6, new Label("PDA:"), txtPDA);

        Button btnViewResults = new Button("View Results");
        btnViewResults.setOnAction(e -> {
            String patientID = txtPatientID.getText();
            if (!patientID.isEmpty()) {
                String patientName = getPatientName(patientID); // Retrieve patient's name using the ID
                if (!patientName.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "Hello", "Hello " + patientName); // Greet the patient by name
                    // Now load and display the CT scan data
                    loadAndDisplayCTScanData(patientID, txtTotalCACScore, txtLM, txtLAD, txtLCX, txtRCA, txtPDA);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No such patient ID found.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a Patient ID.");
            }
        });

        gridPane.add(btnViewResults, 1, 7);

        Scene patientScene = new Scene(gridPane, 400, 450);
        patientStage.setScene(patientScene);
        patientStage.show();
    }

private void loadAndDisplayCTScanData(String patientID, TextField txtTotalCACScore, TextField txtLM, TextField txtLAD, TextField txtLCX, TextField txtRCA, TextField txtPDA) {
    String directoryName = "patient_data";
    String fileName = patientID + "CTResults.txt";

    try {
        Path filePath = Paths.get(directoryName, fileName);
        if (Files.exists(filePath)) {
            List<String> lines = Files.readAllLines(filePath);

            // Assuming the data is in the same order as the form
            txtTotalCACScore.setText(getValueAfterColon(lines.get(1)));
            txtLM.setText(getValueAfterColon(lines.get(2)));
            txtLAD.setText(getValueAfterColon(lines.get(3)));
            txtLCX.setText(getValueAfterColon(lines.get(4)));
            txtRCA.setText(getValueAfterColon(lines.get(5)));
            txtPDA.setText(getValueAfterColon(lines.get(6)));
        } else {
            showAlert(Alert.AlertType.INFORMATION, "No Data", "No CT scan data available yet.");
        }
    } catch (IOException ex) {
        showAlert(Alert.AlertType.ERROR, "Error", "Failed to load data.");
    } catch (IndexOutOfBoundsException | NumberFormatException ex) {
        showAlert(Alert.AlertType.ERROR, "Error", "Data format error.");
    }
}

private String getValueAfterColon(String line) {
    return line.split(":\\s*", 2)[1];
}


private void showLoginScreen(Stage primaryStage) {
    Stage loginStage = new Stage();
    loginStage.setTitle("Login");

    ComboBox<String> cbRole = new ComboBox<>();
    cbRole.getItems().addAll("Doctor", "Nurse", "Patient");
    cbRole.setStyle("-fx-padding: 5; -fx-font-size: 14; -fx-pref-width: 200;");

    TextField txtUsername = new TextField();
    txtUsername.setStyle("-fx-padding: 5; -fx-font-size: 14;");
    txtUsername.setPromptText("Username");

    PasswordField txtPassword = new PasswordField();
    txtPassword.setStyle("-fx-padding: 5; -fx-font-size: 14;");
    txtPassword.setPromptText("Password");

    Button btnLogin = new Button("Login");
    btnLogin.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14;");

    Button btnSignUp = new Button("Sign Up");
    btnSignUp.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14;");

    cbRole.setOnAction(e -> btnSignUp.setVisible("Patient".equals(cbRole.getValue())));
    btnSignUp.setVisible(false);

    btnLogin.setOnAction(e -> handleLogin(cbRole.getValue(), txtUsername.getText(), txtPassword.getText(), loginStage, primaryStage));
    btnSignUp.setOnAction(e -> showSignUpScreen());

    VBox layout = new VBox(10, cbRole, txtUsername, txtPassword, btnLogin, btnSignUp);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(15, 20, 15, 20));
    layout.setStyle("-fx-background-color: #F5F5F5;");

    Scene scene = new Scene(layout, 350, 250);
    loginStage.setScene(scene);
    loginStage.show();
}


private void showSignUpScreen() {
    Stage signUpStage = new Stage();
    signUpStage.setTitle("Patient Sign Up");

    TextField txtUsername = new TextField();
    txtUsername.setPromptText("Username");
    PasswordField txtPassword = new PasswordField();
    txtPassword.setPromptText("Password");
    Button btnSignUp = new Button("Sign Up");

    btnSignUp.setOnAction(e -> signUpPatient(txtUsername.getText(), txtPassword.getText(), signUpStage));

    VBox layout = new VBox(10, txtUsername, txtPassword, btnSignUp);
    layout.setAlignment(Pos.CENTER);
    Scene scene = new Scene(layout, 300, 200);
    signUpStage.setScene(scene);
    signUpStage.show();
}




private void signUpPatient(String username, String password, Stage signUpStage) {
    if (usernameExists(username)) {
        showAlert(Alert.AlertType.ERROR, "Error", "Username already exists.");
        return;
    }
    
    writeUserToFile(username, password, "Patient");
    showAlert(Alert.AlertType.INFORMATION, "Sign Up Successful", "Patient account created successfully.");
    signUpStage.close(); 
}

private boolean usernameExists(String username) {
    String directoryName = "user_data";
    String fileName = "users.txt";
    File userFile = new File(directoryName, fileName);

    try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");
            if (userData[0].equals(username)) {
                return true;
            }
        }
    } catch (IOException e) {
        System.out.println("Error checking username: " + e.getMessage());
    }

    return false;
}
private boolean checkCredentials(String username, String password, String role) {
    String directoryName = "user_data";
    String fileName = "users.txt";
    File userFile = new File(directoryName, fileName);

    try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");
            if (userData[0].equals(username) && userData[1].equals(password) && userData[2].equals(role)) {
                return true;
            }
        }
    } catch (IOException e) {
        System.out.println("An error occurred while reading the user file: " + e.getMessage());
    }

    return false;
}

private void initializePatientDataDirectory() {
    String directoryName = "user_data";
    String fileName = "users.txt";
    File directory = new File(directoryName);
    
    if (!directory.exists()) {
        directory.mkdirs();
    }

    File userFile = new File(directory, fileName);
    if (!userFile.exists()) {
        try {
            userFile.createNewFile();
            // Write default credentials for doctors and nurses
            try (FileWriter fw = new FileWriter(userFile, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {

                // Example credentials
                out.println("doctor1,password,Doctor");
                out.println("nurse1,password,Nurse");
                // Add more as needed

            } catch (IOException e) {
                System.out.println("An error occurred while writing to the user file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the user file: " + e.getMessage());
        }
    }
}

private int loginAttemptCount = 0;
private static final int MAX_LOGIN_ATTEMPTS = 3;

private void handleLogin(String role, String username, String password, Stage loginStage, Stage primaryStage) {
    if (usernameExists(username) && !checkCredentials(username, password, role)) {
        showAlert(Alert.AlertType.ERROR, "Error", "Invalid password.");
        handleLoginAttempt(loginStage);
        return;
    }

    if (checkCredentials(username, password, role)) {
        loginAttemptCount = 0;
        loginStage.close(); // Close the login window
        switch (role) {
            case "Doctor":
                showDoctorView();
                break;
            case "Nurse":
                showNurseView();
                break;
            case "Patient":
                primaryStage.show();
                break;
            default:
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a role.");
                break;
        }
    }
        else {
        	showAlert(Alert.AlertType.ERROR, "Error", "Authentication failed.");
            handleLoginAttempt(loginStage);
        }
   }

private void handleLoginAttempt(Stage loginStage) {
    loginAttemptCount++;
    if (loginAttemptCount >= MAX_LOGIN_ATTEMPTS) {
        showAlert(Alert.AlertType.ERROR, "Error", "Maximum login attempts exceeded.");
        loginStage.close();
        System.exit(0); // or any other handling
    }
}


    public static void main(String[] args) {
    	homeworkOne myApp = new homeworkOne();
        myApp.initializePatientDataDirectory();
        launch(args);
    }
}
