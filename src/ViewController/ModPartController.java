
package ViewController;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;



/**
 * ModPartController is the controller class for the Modify Part screen.
 *
 * @author Trevor Ross, tros114@wgu.edu
 */
public class ModPartController implements Initializable {

    Stage stage;
    Parent scene;
    
    private boolean inHouseBool;
    protected int IDindex = Inventory_GUIController.partModIdIndex;
    
    @FXML
    private RadioButton modPartInHouseRadio;

    @FXML
    private RadioButton modPartOutsourceRadio;

    @FXML
    private TextField modPartIDtxt;

    @FXML
    private TextField modPartNametxt;

    @FXML
    private TextField modPartInvtxt;

    @FXML
    private TextField modPartCosttxt;

    @FXML
    private TextField modPartMaxtxt;

    @FXML
    private TextField modPartVaryingtxt;

    @FXML
    private TextField modPartMintxt;

    @FXML
    private Button modPartSaveButton;

    @FXML
    private Button modPartCancelButton;
    
    @FXML
    private Label modPartTypeLabel;
    

    
    /**
     * Returns the user to the main screen.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionCancelModPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Inventory_GUI.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Saves the modified part and returns the user to the main screen.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionSaveModPart(ActionEvent event) throws IOException {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        try {
                
            int minP = Integer.parseInt(modPartMintxt.getText());
            int maxP = Integer.parseInt(modPartMaxtxt.getText());
            String nameP = new String(modPartNametxt.getText());
            int invP = Integer.parseInt(modPartInvtxt.getText());
            double costP = Double.parseDouble(modPartCosttxt.getText());
        
            String typeP = modPartVaryingtxt.getText();
                
            if(minP > maxP){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to modify Part!");
                alert.setContentText("The minimum quantity cannot be greater than the maximum.");
                alert.showAndWait();
                scene = FXMLLoader.load(getClass().getResource("/ViewController/ModPart.fxml"));
                stage.show();
            } 
            else if(invP > maxP){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to modify part!");
                alert.setContentText("The number of parts in Inventory cannot be greater than the maximum.");
                alert.showAndWait();
                scene = FXMLLoader.load(getClass().getResource("/ViewController/ModPart.fxml"));
                stage.show();
            }
            else if(invP < minP) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to modify part!");
                alert.setContentText("The number of parts in Inventory cannot be smaller than the minimum.");
                alert.showAndWait(); 
                scene = FXMLLoader.load(getClass().getResource("/ViewController/ModPart.fxml"));
                stage.show();
            }

            else {
                if(inHouseBool){
                    int machineID = Integer.parseInt(typeP);
                    InHouse part = new InHouse(IDindex+1, nameP, costP, invP, minP, maxP, machineID);
                    Inventory.updatePart(IDindex, part);
                } else {
                    String companyName = typeP;
                    Outsourced part = new Outsourced(IDindex+1, nameP, costP, invP, minP, maxP, companyName);
                    Inventory.updatePart(IDindex, part);
                }
            scene = FXMLLoader.load(getClass().getResource("/ViewController/Inventory_GUI.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            }
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to modify Part!");
                alert.setContentText("Part could not be modified.  Validate proper data type is being entered in all fields.");
                alert.showAndWait();
        }
        
        //Go back to main screen once part is modified

    }   

    /**
     * Changes the varying label to "Machine ID" and sets inHouseBool for use
     * in determining the part subclass.
     * 
     * @param event 
     */
    @FXML
    void onActionSelectInHouseModPart(ActionEvent event) {
       modPartTypeLabel.setText("Machine ID");
       inHouseBool = true;
    }

    /**
     * Changes the varying label to "Company Name" and sets inHouseBool for use
     * in determining the part subclass.
     * 
     * @param event 
     */
    @FXML
    void onActionSelectOutsourcedModPart(ActionEvent event) {
       modPartTypeLabel.setText("Company Name");
       inHouseBool = false;
    }
    
    /**
     * Fills the text fields and selections with appropriate part data
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //modPartIDtxt.setText(Integer.toString(IDindex+1));
        Part partMod = Inventory.getAllParts().get(IDindex);
        modPartIDtxt.setText(Integer.toString(partMod.getId()));
        modPartNametxt.setText(partMod.getName());
        modPartInvtxt.setText(Integer.toString(partMod.getStock()));
        modPartMintxt.setText(Integer.toString(partMod.getMin()));
        modPartMaxtxt.setText(Integer.toString(partMod.getMax()));
        modPartCosttxt.setText(Double.toString(partMod.getPrice()));
        
        if(partMod instanceof InHouse){
            modPartInHouseRadio.setSelected(true);
            modPartTypeLabel.setText("Machine ID");
            modPartVaryingtxt.setText(Integer.toString(((InHouse)Inventory.getAllParts().get(IDindex)).getMachineID()));  //Downcast to InHouse subclass to retrieve machineID
            
        } else {
            modPartOutsourceRadio.setSelected(true);
            modPartTypeLabel.setText("Company Name");
            modPartVaryingtxt.setText(((Outsourced)Inventory.getAllParts().get(IDindex)).getCompanyName());  //Downcast to Outsourced subclass to retrieve companyName
        }
    }    
    
}
