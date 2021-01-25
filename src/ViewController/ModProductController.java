
package ViewController;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * The ModProductController class is the controller class for the Modify Product
 * screen.
 *
 * @author Trevor Ross, tros114@wgu.edu
 */
public class ModProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    private ObservableList<Part> addedParts = FXCollections.observableArrayList();
    protected int modIDindex = Inventory_GUIController.productModIdIndex;
    
    @FXML
    private TextField modProductIDtxt;

    @FXML
    private TextField modProductNametxt;

    @FXML
    private TextField modProductInvtxt;

    @FXML
    private TextField modProductPricetxt;

    @FXML
    private TextField modProductMaxtxt;

    @FXML
    private TextField modProductMintxt;

    @FXML
    private Button modProductSaveButton;

    @FXML
    private Button modProductCancelButton;

    @FXML
    private TableView<Part> modProductPartTable;

    @FXML
    private TableColumn<Part, Integer> modProductPartIDCol;

    @FXML
    private TableColumn<Part, String> modProductPartNameCol;

    @FXML
    private TableColumn<Part, Integer> modProductInventoryLevelCol;

    @FXML
    private TableColumn<Part, Double> modProductCostCol;

    @FXML
    private Button modProductAddButton;

    @FXML
    private Button modProductRemoveAssociatedPartButton;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelCol;

    @FXML
    private TableColumn<Part, Double> partCostCol;
    
    @FXML
    private TextField searchPartProducttxt;
    
    /**
     * Adds (associates) a part with the product and moves it to the bottom
     * table.
     * 
     * @param event 
     */
    @FXML
    void onActionAddPartToModProduct(ActionEvent event) {
    Part partToAdd = Inventory.lookupPart(Inventory.getAllParts().indexOf(partTable.getSelectionModel().getSelectedItem()));
    
    Inventory.getAllProducts().get(modIDindex).addAssociatedPart(partToAdd);
    modProductPartTable.setItems(Inventory.getAllProducts().get(modIDindex).getAllAssociatedParts());
    }

    /**
     * Returns the user to the main screen.
     * 
     * The functionality of this method could be expanded.  Currently, if 
     * a part is disassociated (removed from bottom table) from the product,
     * and then the "cancel" button is clicked, the part remains disassociated.
     * Future functionality would include restoring the removed parts upon
     * clicking the "cancel" button.  This could possibly be implemented by
     * storing removed parts in a temporary list, and then including code in
     * this method to copy values from the temporary list to the associated
     * parts list upon clicking "cancel".
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionCancelModProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Inventory_GUI.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Disassociates a part with the product and removes it from the 
     * bottom table. 
     * 
     * @param event 
     */
    @FXML
    void onActionRemoveAssociatedPartFromModProduct(ActionEvent event) {
    Part partToRemove = modProductPartTable.getSelectionModel().getSelectedItem();
    
     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Part Removal");
                alert.setHeaderText("Do you really want to remove this part?");
                alert.setContentText("");
            
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                Inventory.getAllProducts().get(modIDindex).deleteAssociatedPart(partToRemove);
                modProductPartTable.setItems(Inventory.getAllProducts().get(modIDindex).getAllAssociatedParts());
                }  
                else {
                modProductPartTable.setItems(Inventory.getAllProducts().get(modIDindex).getAllAssociatedParts());
                }
    }

    /**
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionSaveModProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        try {
            
            int Id = Integer.parseInt(modProductIDtxt.getText());
            int minP = Integer.parseInt(modProductMintxt.getText());
            int maxP = Integer.parseInt(modProductMaxtxt.getText());
            String nameP = new String(modProductNametxt.getText());
            int invP = Integer.parseInt(modProductInvtxt.getText());
            double costP = Double.parseDouble(modProductPricetxt.getText());
            
            
         
               
                    if(minP > maxP){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Failed to modify Product!");
                        alert.setContentText("The minimum quantity cannot be greater than the maximum.");
                        alert.showAndWait();
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/ModProduct.fxml"));                                             stage.show();
                    } else if(invP > maxP){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Failed to modify Product!");
                        alert.setContentText("The number of products in Inventory cannot be greater than the maximum.");
                        alert.showAndWait(); 
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/ModProduct.fxml"));
                        stage.show();
                    }
                    else if(invP < minP) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Failed to add Product!");
                        alert.setContentText("The number of products in Inventory cannot be smaller than the minimum.");
                        alert.showAndWait(); 
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/ModProduct.fxml"));                       // stage.setScene(new Scene(scene));
                        stage.show();
                    }
                                       
                    else {
                            Product product = new Product(Id, nameP, costP, invP, minP, maxP);
                            ObservableList<Part> associatedPartsTemp = Inventory.getAllProducts().get(modIDindex).getAllAssociatedParts();
                            for(int i = 0; i < associatedPartsTemp.size(); i++) {
                                product.addAssociatedPart(associatedPartsTemp.get(i));
                            }
                            
                            modProductPartTable.setItems(Inventory.getAllProducts().get(modIDindex).getAllAssociatedParts());
                            Inventory.updateProduct(modIDindex, product);
                            
                            //Go back to main screen once part is added
                            scene = FXMLLoader.load(getClass().getResource("/ViewController/Inventory_GUI.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                        }
                        
                    }
                 catch (NumberFormatException e){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Error");
                            alert.setHeaderText("Failed to add Product!");
                            alert.setContentText("Product could not be added.  Validate proper data type is being entered in all fields.");
                            alert.showAndWait();
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/ModProduct.fxml"));                        stage.setScene(new Scene(scene));
                        stage.show();
                 }
    }

    /**
     * Retrieves and fills initial data in the tables and text fields.
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Product productMod = Inventory.getAllProducts().get(modIDindex);
        modProductIDtxt.setText(Integer.toString(productMod.getId()));
        modProductNametxt.setText(productMod.getName());
        modProductInvtxt.setText(Integer.toString(productMod.getStock()));
        modProductMintxt.setText(Integer.toString(productMod.getMin()));
        modProductMaxtxt.setText(Integer.toString(productMod.getMax()));
        modProductPricetxt.setText(Double.toString(productMod.getPrice()));
        
        
        partTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<> ("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<> ("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<> ("price"));
        
        modProductPartTable.setItems(Inventory.getAllProducts().get(modIDindex).getAllAssociatedParts());
        modProductPartIDCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        modProductPartNameCol.setCellValueFactory(new PropertyValueFactory<> ("name"));
        modProductInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<> ("stock"));
        modProductCostCol.setCellValueFactory(new PropertyValueFactory<> ("price"));
    }
    
    /**
     * Provides search functionality for the part table search field using a
     * filtered list.
     * 
     * @param event 
     */
    @FXML
    private void onActionLookupPart(KeyEvent event) {
        
    FilteredList<Part> filteredPartList = new FilteredList<>(Inventory.getAllParts(), b -> true);
    searchPartProducttxt.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredPartList.setPredicate(part -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            if (part.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else if (Integer.toString(part.getId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            }
            return false;     
        });
            //Alerts user when no part is found.
            if(!filteredPartList.isEmpty()) {
                partTable.setItems(filteredPartList); }
            else { 
                searchPartProducttxt.setText(searchPartProducttxt.getText().substring(0, searchPartProducttxt.getText().length()-1));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Part Not Found!");
                    alert.setContentText("No parts found with that search entry.  Try again.");
                    alert.showAndWait(); }
    });
    partTable.setItems(filteredPartList);
}

}