package main;

import java.text.DecimalFormat;


// Noam Yakar's Running  Homework 4
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Arrays;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;





public class homeworkOne extends Application {

    private String patientID = "defaultPatientID";
    @Override
    public void start(Stage primaryStage) {



       
        showLoginScreen(primaryStage);
    }
    public ObservableList<Map<String, String>> readPatientData() {
        ObservableList<Map<String, String>> allPatients = FXCollections.observableArrayList();
        File folder = new File("./patient_data/");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith("_patientinfo.txt"));

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                Map<String, String> patientData = new HashMap<>();
                try {
                    List<String> lines = Files.readAllLines(file.toPath());
                    for (String line : lines) {
                        String[] parts = line.split(":\\s+", 2);
                        if (parts.length == 2) {
                            patientData.put(parts[0].trim(), parts[1].trim());
                        }
                    }
                    allPatients.add(patientData);
                } catch (IOException e) {
                    System.out.println("An error occurred while reading file: " + e.getMessage());
                }
            }
        }

        return allPatients;
    }


    public void showPatientListStage() {
        Stage patientListStage = new Stage();
        patientListStage.setTitle("List of Patients");

        TableView<Map<String, String>> table = new TableView<>();
        ObservableList<Map<String, String>> data = readPatientData();

        // Add Patient ID column for interaction
        TableColumn<Map<String, String>, String> idColumn = new TableColumn<>("Patient ID");
        idColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get("Patient ID")));
        table.getColumns().add(idColumn);

        // Define columns with the data keys
        String[] columns = {"First Name", "Last Name", "Phone Number", "Health History", "Insurance ID"};
        for (String column : columns) {
            TableColumn<Map<String, String>, String> col = new TableColumn<>(column);
            col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(column)));
            table.getColumns().add(col);
        }

        table.setItems(data);

        // Set row factory for handling clicks
        table.setRowFactory(tv -> {
            TableRow<Map<String, String>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
                    Map<String, String> rowData = row.getItem();
                    String patientID = rowData.get("Patient ID");
                    if (patientID != null && !patientID.isEmpty()) {
                        showNurseView(patientID); // Implement this method as per the previous explanation
                    }
                }
            });
            return row;
        });

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox);
        patientListStage.setScene(scene);
        patientListStage.show();
    }
    
    public void showPatientListStageDoctor() {
        Stage patientListStage = new Stage();
        patientListStage.setTitle("List of Patients");

        TableView<Map<String, String>> table = new TableView<>();
        ObservableList<Map<String, String>> data = readPatientData();

        // Add Patient ID column for interaction
        TableColumn<Map<String, String>, String> idColumn = new TableColumn<>("Patient ID");
        idColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get("Patient ID")));
        table.getColumns().add(idColumn);

        // Define columns with the data keys
        String[] columns = {"First Name", "Last Name", "Phone Number", "Health History", "Insurance ID"};
        for (String column : columns) {
            TableColumn<Map<String, String>, String> col = new TableColumn<>(column);
            col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(column)));
            table.getColumns().add(col);
        }

        table.setItems(data);

        // Set row factory for handling clicks
        table.setRowFactory(tv -> {
            TableRow<Map<String, String>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
                    Map<String, String> rowData = row.getItem();
                    String patientID = rowData.get("Patient ID");
                    if (patientID != null && !patientID.isEmpty()) {
                        showDoctorView(patientID); // Implement this method as per the previous explanation
                    }
                }
            });
            return row;
        });

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox);
        patientListStage.setScene(scene);
        patientListStage.show();
    }

    
    private void savePatientData(String patientID, String height /*, other parameters */) {
        String directoryName = "./patient_data/";
        String fileName = patientID + "_PatientInfo.txt";
        Path filePath = Paths.get(directoryName, fileName);

        // Read existing data and update it
        Map<String, String> patientData = getPatientData(patientID);
        patientData.put("Height", height);
        // ... update other data

        // Now write this data back to the file
        try {
            List<String> lines = new ArrayList<>();
            patientData.forEach((key, value) -> lines.add(key + ": " + value));
            Files.write(filePath, lines);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void showNurseView(String patientID) {
        Stage nurseStage = new Stage();
        nurseStage.setTitle("Nurse View for Patient: " + patientID);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Patient data is assumed to be already retrieved from the file
        Map<String, String> patientData = getPatientData(patientID);

        // Display patient information
        gridPane.add(new Label("Name"), 0, 0);
        gridPane.add(new Text(patientData.get("First Name") + " " + patientData.get("Last Name")), 1, 0);
        
        // ... Other patient details
        // Retrieve patient data from file

        // Create input fields for nurse to enter new data
        TextField heightInput = new TextField(patientData.getOrDefault("Height", ""));
        TextField weightInput = new TextField(patientData.getOrDefault("Weight", ""));
        TextField bodyTempInput = new TextField(patientData.getOrDefault("Body Temperature", ""));
        TextField bloodPressureSysInput = new TextField(patientData.getOrDefault("Blood Pressure Systolic", ""));
        TextField bloodPressureDiaInput = new TextField(patientData.getOrDefault("Blood Pressure Diastolic", ""));
        TextField currentMedicationsInput = new TextField(patientData.getOrDefault("Current Medications", ""));
        TextField patientAllergiesInput = new TextField(patientData.getOrDefault("Patient Allergies", ""));

        // Add input fields to the gridPane
        gridPane.addRow(0, new Label("Height"), heightInput);
        gridPane.addRow(1, new Label("Weight"), weightInput);
        gridPane.addRow(2, new Label("Body Temperature"), bodyTempInput);
        gridPane.addRow(3, new Label("Blood Pressure Systolic"), bloodPressureSysInput);
        gridPane.addRow(4, new Label("Blood Pressure Diastolic"), bloodPressureDiaInput);
        gridPane.addRow(5, new Label("Current Medications"), currentMedicationsInput);
        gridPane.addRow(6, new Label("Patient Allergies"), patientAllergiesInput);

        // Save button with its action
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Save the new data to the patient's file
            savePatientData(
                    patientID,
                    heightInput.getText(),
                    weightInput.getText(),
                    bodyTempInput.getText(),
                    bloodPressureSysInput.getText(),
                    bloodPressureDiaInput.getText(),
                    currentMedicationsInput.getText(),
                    patientAllergiesInput.getText()
            );
            nurseStage.close(); // Close the stage after saving
        });
        gridPane.add(saveButton, 1, 7);

        // Add everything to the scene and display it
        Scene scene = new Scene(gridPane, 400, 600);
        nurseStage.setScene(scene);
        nurseStage.show();
    }
    
    private void showDoctorView(String patientID) {
        Stage doctorStage = new Stage();
        doctorStage.setTitle("Physical Examination for Patient: " + patientID);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Patient data is assumed to be already retrieved from the file
        Map<String, String> patientData = getPatientData(patientID);

        // Display patient information in a non-editable form (using Text instead of TextField)
        gridPane.add(new Label("Full Name"), 0, 0);
        gridPane.add(new Text(patientData.getOrDefault("First Name", "") + " " + patientData.getOrDefault("Last Name", "")), 1, 0);
        gridPane.add(new Label("Insurance ID"), 0, 1);
        gridPane.add(new Text(patientData.getOrDefault("Insurance ID", "")), 1, 1);
        gridPane.add(new Label("Height"), 0, 2);
        gridPane.add(new Text(patientData.getOrDefault("Height", "")), 1, 2);
        gridPane.add(new Label("Weight"), 0, 3);
        gridPane.add(new Text(patientData.getOrDefault("Weight", "")), 1, 3);
        gridPane.add(new Label("Body Temperature"), 0, 4);
        gridPane.add(new Text(patientData.getOrDefault("Body Temperature", "")), 1, 4);
        gridPane.add(new Label("Blood Pressure Systolic"), 0, 5);
        gridPane.add(new Text(patientData.getOrDefault("Blood Pressure Systolic", "")), 1, 5);
        gridPane.add(new Label("Blood Pressure Diastolic"), 0, 6);
        gridPane.add(new Text(patientData.getOrDefault("Blood Pressure Diastolic", "")), 1, 6);
     // Additional fields for the doctor to enter data
        TextArea notesInput = new TextArea();
        TextField prescriptionInput = new TextField();
        TextField frontOfficeInput = new TextField();

        // Adding new fields to the gridPane
        gridPane.addRow(7, new Label("Notes"), notesInput);
        gridPane.addRow(8, new Label("Prescription"), prescriptionInput);
        gridPane.addRow(9, new Label("Front Office"), frontOfficeInput);

        // Complete Visit button
        Button completeVisitButton = new Button("Complete Visit");
        completeVisitButton.setOnAction(e -> {
            savePatientDataDoctor(
                patientID,
                notesInput.getText(),
                prescriptionInput.getText(),
                frontOfficeInput.getText()
            );
            doctorStage.close(); // Close the stage after saving
        });
        gridPane.add(completeVisitButton, 1, 10);

        // Add everything to the scene and display it
        Scene scene = new Scene(gridPane, 400, 600);
        doctorStage.setScene(scene);
        doctorStage.show();
    }

    private void savePatientDataDoctor(
            String patientID,
            String notes,
            String prescription,
            String frontOffice
    ) {
        String directoryName = "./patient_data/";
        String fileName = patientID + "_PatientInfo.txt";
        Path filePath = Paths.get(directoryName, fileName);

        try {
            List<String> lines = Files.readAllLines(filePath);
            List<String> newLines = new ArrayList<>();
            boolean bloodPressureFound = false;
            for (String line : lines) {
                newLines.add(line);
                if (!bloodPressureFound && line.startsWith("Blood Pressure Diastolic")) {
                    bloodPressureFound = true;
                    // Add new doctor's data after "Blood Pressure" field
                    newLines.add("Notes: " + notes);
                    newLines.add("Prescription: " + prescription);
                    newLines.add("Front Office: " + frontOffice);
                }
            }

            // Write the updated content back to the file
            Files.write(filePath, newLines);
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exceptions appropriately
        }
    }


    private void savePatientData(
            String patientID,
            String height,
            String weight,
            String bodyTemperature,
            String bloodPressureSystolic,
            String bloodPressureDiastolic,
            String currentMedications,
            String patientAllergies
    ) {
        String directoryName = "./patient_data/";
        String fileName = patientID + "_PatientInfo.txt";
        Path filePath = Paths.get(directoryName, fileName);

        try {
            // Read all lines up to "Insurance ID"
            List<String> lines = Files.readAllLines(filePath);
            List<String> newLines = new ArrayList<>();
            for (String line : lines) {
                newLines.add(line);
                if (line.startsWith("Insurance ID")) {
                    break;
                }
            }

            // Append new data after the "Insurance ID" line
            String newContent = String.join("\n",
                    "Height: " + height,
                    "Weight: " + weight,
                    "Body Temperature: " + bodyTemperature,
                    "Blood Pressure Systolic: " + bloodPressureSystolic,
                    "Blood Pressure Diastolic: " + bloodPressureDiastolic,
                    "Current Medications: " + currentMedications,
                    "Patient Allergies: " + patientAllergies
            );
            newLines.add(newContent);

            // Write the updated content back to the file
            Files.write(filePath, newLines);
        } catch (IOException ex) {
        }
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

    private void showPatientProfile(String patientID) {
        Stage profileStage = new Stage();
        profileStage.setTitle("Patient Profile");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Retrieve patient data from file
        Map<String, String> patientData = getPatientData(patientID);

        // Create form elements populated with existing patient data
        Label lblFirstName = new Label("First Name:");
        lblFirstName.setStyle("-fx-font-weight: bold;");
        TextField txtFirstName = new TextField(patientData.getOrDefault("First Name", ""));

        Label lblLastName = new Label("Last Name:");
        lblLastName.setStyle("-fx-font-weight: bold;");
        TextField txtLastName = new TextField(patientData.getOrDefault("Last Name", ""));

        Label lblEmail = new Label("Email:");
        lblEmail.setStyle("-fx-font-weight: bold;");
        TextField txtEmail = new TextField(patientData.getOrDefault("Email", ""));

        Label lblPhoneNumber = new Label("Phone Number:");
        lblPhoneNumber.setStyle("-fx-font-weight: bold;");
        TextField txtPhoneNumber = new TextField(patientData.getOrDefault("Phone Number", ""));

        Label lblHealthHistory = new Label("Health History:");
        lblHealthHistory.setStyle("-fx-font-weight: bold;");
        TextArea txtHealthHistory = new TextArea(patientData.getOrDefault("Health History", ""));

        Label lblInsuranceID = new Label("Insurance ID:");
        lblInsuranceID.setStyle("-fx-font-weight: bold;");
        TextField txtInsuranceID = new TextField(patientData.getOrDefault("Insurance ID", ""));

        // Add form elements to the gridPane
        gridPane.addRow(0, new Label("First Name:"), txtFirstName);
        gridPane.addRow(1, new Label("Last Name:"), txtLastName);
        gridPane.addRow(2, new Label("Email:"), txtEmail);
        gridPane.addRow(3, new Label("Phone Number:"), txtPhoneNumber);
        gridPane.addRow(4, new Label("Health History:"), txtHealthHistory);
        gridPane.addRow(5, new Label("Insurance ID:"), txtInsuranceID);

        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> savePatientInfo(
                patientID, txtFirstName.getText(), txtLastName.getText(),
                txtEmail.getText(), txtPhoneNumber.getText(),
                txtHealthHistory.getText(), txtInsuranceID.getText()
        ));
        gridPane.add(btnSave, 1, 6);

        Scene profileScene = new Scene(gridPane, 400, 450);
        profileStage.setScene(profileScene);
        profileStage.show();
    }
    private Map<String, String> getPatientData(String patientID) {
        Map<String, String> data = new HashMap<>();
        String directoryName = "patient_data";
        String fileName = patientID + "_PatientInfo.txt";
        Path filePath = Paths.get(directoryName, fileName);

        if (Files.exists(filePath)) {
            try {
                List<String> lines = Files.readAllLines(filePath);
                for (String line : lines) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        data.put(parts[0].trim(), parts[1].trim());
                    }
                }
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to read patient information.");
            }
        }
        return data;
    }

    private void savePatientInfo(String patientID, String firstName, String lastName, String email, String phone, String healthHistory, String insuranceID) {
        String directoryName = "patient_data";
        String fileName = patientID + "_PatientInfo.txt";
        String filePath = Paths.get(directoryName, fileName).toString();

        String content = String.format(
                "Patient ID: %s\nFirst Name: %s\nLast Name: %s\nEmail: %s\nPhone Number: %s\nHealth History: %s\nInsurance ID: %s",
                patientID, firstName, lastName, email, phone, healthHistory, insuranceID
        );

        try {
            Files.write(Paths.get(filePath), content.getBytes());
            showAlert(Alert.AlertType.INFORMATION, "Information Updated", "Patient information has been updated successfully.");
        } catch (IOException ex) {
            showAlert(Alert.AlertType.ERROR, "Error Updating Information", "Failed to update patient information.");
        }
    }
    private void showUpcomingAppointments(String username) {
        Stage appointmentsStage = new Stage();
        appointmentsStage.setTitle("Upcoming Appointments");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        String directoryName = "patient_data";
        System.out.println("Username is " + username);
        String patientID = getPatientIDByUsername(username);
        String fileName = patientID + "_Appointments.txt";
        Path filePath = Paths.get(directoryName, fileName);

        System.out.println("Looking for appointments in file: " + filePath.toString()); // Print the file path being checked

        try {
            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
                System.out.println("Contents of " + fileName + ":"); // Print the file name being read
                for (String line : lines) {
                    System.out.println(line); // Print each line read from the file
                    layout.getChildren().add(new Label(line));
                }
            } else {
                System.out.println("No appointments file found for patient ID: " + patientID); // Inform if the file doesn't exist
                layout.getChildren().add(new Label("No upcoming appointments."));
            }
        } catch (IOException ex) {
            System.out.println("Failed to read the file: " + filePath.toString()); // Print error if file reading fails
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load appointments.");
        }

        Scene appointmentsScene = new Scene(layout, 300, 200);
        appointmentsStage.setScene(appointmentsScene);
        appointmentsStage.show();
    }


    private String getPatientIDByUsername(String username) {
        String directoryName = "user_data";
        String fileName = "users.txt";
        File userFile = new File(directoryName, fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(username) && userData[2].equals("Patient")) {
                    // Return the patient ID if username matches and the role is Patient
                	System.out.println("whats good" + userData[3]);
                    return userData[3];
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking username: " + e.getMessage());
        }

        // Return empty string if username is not found or if an error occurs
        return "";
    }



    private void showPatientView(String patientID) {
        Stage patientStage = new Stage();
        patientStage.setTitle("Patient View");

        Button btnUpdateProfile = new Button("Update Profile");
        btnUpdateProfile.setOnAction(e -> showPatientProfile(patientID));

        Button btnViewAppointments = new Button("View Appointments");
        btnViewAppointments.setOnAction(e -> showUpcomingAppointments(getCurrentUser()));

        VBox layout = new VBox(10, btnUpdateProfile, btnViewAppointments);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 200);
        patientStage.setScene(scene);
        patientStage.show();
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



    private void writeUserToFile(String username, String password, String role, String patientID) {
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

            // Write username, password, role, and patient ID to the file
            out.println(username + "," + password + "," + role + "," + patientID);
            System.out.println("Login details saved to: " + userFile.getAbsolutePath());

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

        // Image setup
        Image image = new Image("file:clear_health.png"); // Load the image
        ImageView imageView = new ImageView(image); // Create an ImageView for the image
        imageView.setFitHeight(150); // Set the height of the image
        imageView.setPreserveRatio(true); // Preserve the ratio

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

        VBox layout = new VBox(10);
        layout.getChildren().addAll(imageView, cbRole, txtUsername, txtPassword, btnLogin, btnSignUp);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15, 20, 15, 20));
        layout.setStyle("-fx-background-color: #F5F5F5;");

        Scene scene = new Scene(layout, 350, 400); // Adjust the size based on your image and layout needs
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
        } else {
            // New user, so generate patient ID, write user to file, etc.
            String newPatientID = generatePatientID();
            writeUserToFile(username, password, "Patient", newPatientID); // Pass the generated patient ID
            addDefaultAppointments(newPatientID); // Call addDefaultAppointments here
            showAlert(Alert.AlertType.INFORMATION, "Sign Up Successful", "Patient account created successfully. Default appointments added.");
            signUpStage.close();
        }
    }


    private void addDefaultAppointments(String patientID) {
        String directoryName = "patient_data";
        String fileName = patientID + "_Appointments.txt";
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        List<String> appointments = Arrays.asList(
                "Appointment with Dr. Smith on 2024-06-15 at 10:00 AM",
                "Follow-up on 2024-06-29 at 02:00 PM"
        );
        try {
            Path filePath = Paths.get(directoryName, fileName);
            Files.write(filePath, appointments);
        } catch (IOException e) {
            System.out.println("Error creating default appointments: " + e.getMessage());
        }
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
        if (!usernameExists(username) || !checkCredentials(username, password, role)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid credentials.");
            handleLoginAttempt(loginStage);
        } else {
            // Successful login
            setCurrentUser(username);  // Set the current user in the file
            loginAttemptCount = 0;
            loginStage.close(); // Close the login window
            if ("Patient".equals(role)) {
                showPatientView(getPatientIDByUsername(username));
            } // Inside your login handling method
            if ("Nurse".equals(role) && checkCredentials(username, password, role)) {
                showPatientListStage(); // Show the patient list if the user is a nurse
            }
            if ("Doctor".equals(role) && checkCredentials(username, password, role)) {
            	showPatientListStageDoctor(); 
            }
            else {
                primaryStage.show();
            }
        }
    }

    private void setCurrentUser(String username) {
        String fileName = "current_user.txt";
        try {
            Files.write(Paths.get(fileName), username.getBytes());
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the current_user file: " + e.getMessage());
        }
    }
    
    private String getCurrentUser() {
        String fileName = "current_user.txt";
        try {
            return new String(Files.readAllBytes(Paths.get(fileName))).trim();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the current_user file: " + e.getMessage());
            return "";  // Return empty string in case of error
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
