
package ViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;


/**
 * The AddPartController class is the controller class for the Add Part screen.
 *
 * @author Trevor Ross, tros114@wgu.edu
 */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;
    
    private int IDindex;
    private boolean inHouseBool;
    
    @FXML
    private RadioButton partInHouseRadio;

    @FXML
    private RadioButton partOutsourceRadio;

    @FXML
    private TextField partIDtxt;

    @FXML
    private TextField partNametxt;

    @FXML
    private TextField partInvtxt;

    @FXML
    private TextField partCosttxt;

    @FXML
    private TextField partMaxtxt;

    @FXML
    private TextField partVaryingtxt;

    @FXML
    private TextField partMintxt;

    @FXML
    private Button savePartButton;
        
    @FXML
    private Label partTypeLabel;
    
    
    /**
     * Cancels adding the new part and returns the user to the main screen.
     * 
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionCancelNewPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Inventory_GUI.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Saves a new part and associated data.
     * 
     * A runtime error was encountered and corrected here.  
     * I renamed partVaryingtxt in AddPartController.java without 
     * renaming the corresponding fx:id in AddPart.fxml.  
     * The error was corrected after I was able to trace it back to 
     * the FXML file.
     * 
     * @param event
     * @throws IOException 
     */      
    @FXML
    private void onActionSaveNewPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        try {
                
            int minP = Integer.parseInt(partMintxt.getText());
            int maxP = Integer.parseInt(partMaxtxt.getText());
            String nameP = new String(partNametxt.getText());
            int invP = Integer.parseInt(partInvtxt.getText());
            double costP = Double.parseDouble(partCosttxt.getText());
        
        /*
            */
            String typeP = partVaryingtxt.getText();
                
                    //Check for errors in the user-entered data prior to evaluating part.
                    if(minP > maxP){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Failed to add Part!");
                        alert.setContentText("The minimum quantity cannot be greater than the maximum.");
                        alert.showAndWait();
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/ModPart.fxml"));
                        stage.show();
                    } 
                 
                    else if(invP > maxP){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Failed to add part!");
                        alert.setContentText("The number of parts in Inventory cannot be greater than the maximum.");
                        alert.showAndWait();
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/ModPart.fxml"));
                        stage.show();   
                    }
                    else if(invP < minP) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Failed to add part!");
                        alert.setContentText("The number of parts in Inventory cannot be smaller than the minimum.");
                        alert.showAndWait();
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/ModPart.fxml"));
                        stage.show();
                    }
                    
                    
                    else {
                        if(inHouseBool){
                            int machineID = Integer.parseInt(typeP);
                            InHouse part = new InHouse(IDindex, nameP, costP, invP, minP, maxP, machineID);
                            Inventory.addPart(part);
                        } else {
                            String companyName = typeP;
                            Outsourced part = new Outsourced(IDindex, nameP, costP, invP, minP, maxP, companyName);
                            Inventory.addPart(part);
                        }
                        //Returns user to main screen after part is added.
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/Inventory_GUI.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();  
                    }
                    
                } 
        //Return error if user data is of incorrect format
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to add Part!");
                alert.setContentText("Part could not be added.  Validate proper data type is being entered in all fields.");
                alert.showAndWait();
                scene = FXMLLoader.load(getClass().getResource("/ViewController/ModPart.fxml"));
                stage.show();
        }
    }   

    /**
     * Changes the label to "Machine ID" if the In House radio button is
     * selected and sets inHouseBool value for use in determining which subclass
     * of part will be added.
     * 
     * @param event 
     */
    @FXML
    private void onActionSelectInHouse(ActionEvent event) {
        partTypeLabel.setText("Machine ID");
        inHouseBool = true;
    }

    /**
     * Changes the label to "Company Name" if the Outsourced radio button is
     * selected and sets inHouseBool value for use in determining which subclass
     * of part will be added.
     * 
     * @param event 
     */
    @FXML
    void onActionSelectOutsourced(ActionEvent event) {
       partTypeLabel.setText("Company Name");
       inHouseBool = false;
    }
    
    /**
     * Sets IDindex to a new part ID number and sets ID text field.
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IDindex = Inventory.newPartID();
        partIDtxt.setText(Integer.toString(IDindex));
    }    

}