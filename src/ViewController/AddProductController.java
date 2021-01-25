
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
 * The AddProductController class is the controller class for the Add Product
 * screen.
 *
 * @author Trevor Ross, tros114@wgu.edu
 */
public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;
    
    private ObservableList<Part> addedParts = FXCollections.observableArrayList();
    private int IDindex;
    
    @FXML
    private TextField productIDtxt;

    @FXML
    private TextField productNametxt;

    @FXML
    private TextField productInvtxt;

    @FXML
    private TextField productPricetxt;

    @FXML
    private TextField productMaxtxt;

    @FXML
    private TextField productMintxt;

    @FXML
    private Button saveProductButton;

    @FXML
    private Button cancelAddProductButton;

    @FXML
    private TableView<Part> partProductTable;

    @FXML
    private TableColumn<Part, Integer> partProductIDCol;

    @FXML
    private TableColumn<Part, String> partProductNameCol;

    @FXML
    private TableColumn<Part, Integer> partProductInventoryLevelCol;

    @FXML
    private TableColumn<Part, Double> partProductCostCol;

    @FXML
    private Button partAddToProductButton;

    @FXML
    private Button removeAssociatedPartButton;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelCol;

    @FXML
    private TableColumn <Part, Double> partCostCol;
    
    @FXML
    private TextField searchPartProducttxt;
    
    /**
     * Fills initial data in the tables and text fields.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<> ("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<> ("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<> ("price"));
        
        partProductTable.setItems(addedParts);
        partProductIDCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        partProductNameCol.setCellValueFactory(new PropertyValueFactory<> ("name"));
        partProductInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<> ("stock"));
        partProductCostCol.setCellValueFactory(new PropertyValueFactory<> ("price"));
    
        IDindex = Inventory.newProdID();
        productIDtxt.setText(Integer.toString(IDindex));
    }    
    
    
    /**
     * Provides search functionality for the part table search field using a
     * filtered list.
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
            
            //Alerts user when no part is found
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
    
    
    /**
     * Adds (associates) a part with the new product by moving it to the
     * bottom table.
     * 
     * @param event 
     */
    @FXML
    void onActionAddPartToNewProduct(ActionEvent event) {
   
    Part partToAdd = Inventory.lookupPart(Inventory.getAllParts().indexOf(partTable.getSelectionModel().getSelectedItem()));
        addedParts.add(partToAdd);
        partProductTable.setItems(addedParts);
    
    }
    
    /**
     * Cancels product creation and returns user to the main screen.
     * 
     * For future implementation, this could be expanded to prevent the 
     * prodIDIndex from incrementing.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionCancelNewProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Inventory_GUI.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    
    /**
     * Disassociates a part with the new product and removes it from the 
     * bottom table.
     * @param event 
     */
    @FXML
    void onActionRemoveAssociatedPartFromNewProduct(ActionEvent event) {
    Part partToRemove = /*Inventory.lookupPart(Inventory.getAllParts().indexOf*/(partProductTable.getSelectionModel().getSelectedItem())/*)*/;
    
        //Require user to confirm part removal
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Part Removal");
            alert.setHeaderText("Do you really want to remove this part?");
            alert.setContentText("");
            Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    addedParts.remove(partToRemove);
                    partProductTable.setItems(addedParts);
                }  
                else {
                    partProductTable.setItems(addedParts);
                }
    }

    /**
     * Saves the new product along with associated parts.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionSaveNewProduct(ActionEvent event) throws IOException {
         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        try {                
            int minP= Integer.parseInt(productMintxt.getText());
            int maxP = Integer.parseInt(productMaxtxt.getText());
            String nameP = new String(productNametxt.getText());
            int invP = Integer.parseInt(productInvtxt.getText());
            double costP = Double.parseDouble(productPricetxt.getText());
            
                        //Checks the data for errors and returns applicable error messages
                        if(minP > maxP){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Failed to add Product!");
                        alert.setContentText("The minimum quantity cannot be greater than the maximum.");
                        alert.showAndWait();
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/AddProduct.fxml"));                      
                        stage.show();
                    } 

                    else if(invP > maxP){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Failed to add Product!");
                        alert.setContentText("The number of products in Inventory cannot be greater than the maximum.");
                        alert.showAndWait();
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/AddProduct.fxml"));                      
                        stage.show();
                    }
                    else if(invP < minP) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Failed to add Product!");
                        alert.setContentText("The number of products in Inventory cannot be smaller than the minimum.");
                        alert.showAndWait();
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/AddProduct.fxml"));                      
                        stage.show();
                    }
                    
                    
                    else {
                            Product product = new Product(IDindex, nameP, costP, invP, minP, maxP);
                            for (int i = 0; i < addedParts.size(); i++){
                            Part partToAdd = addedParts.get(i);
                            product.addAssociatedPart(partToAdd);    }
                            Inventory.addProduct(product);
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
                        scene = FXMLLoader.load(getClass().getResource("/ViewController/AddProduct.fxml"));
                        stage.show();
                        
                        }
                
                //Return user to the main screen after product is added

    }  
}
    
    

    

