
package ViewController;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * Inventory_GUIController is the controller class for the main inventory
 * screen.
 *
 * @author Trevor Ross, tros114@wgu.edu
 */
public class Inventory_GUIController implements Initializable {

    Stage stage;
    Parent scene;
    
    protected static int partModIdIndex;
    protected static int productModIdIndex;
    
    
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
    private TextField searchParttxt;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productIDCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryLevelCol;

    @FXML
    private TableColumn<Product, Double> productCostCol;

    @FXML
    private TextField searchProducttxt;

    @FXML
    private Button addPartButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private Button addProductButton;

    @FXML
    private Button exitButton;
    
    @FXML
    private Button searchPartButton;
     
    @FXML
    private Button searchProductButton;
    
    /**
     * Fill data tables with all part and product data.
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
        
        productTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<> ("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<> ("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<> ("price"));
    }    
    
    /**
     * Provides search functionality for the part table and returns an
     * informational alert if no parts are found for the search data.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionSearchPart(KeyEvent event) throws IOException {
  
        FilteredList<Part> filteredPartList = new FilteredList<>(Inventory.getAllParts(), b -> true);
        searchParttxt.textProperty().addListener((observable, oldValue, newValue) -> {
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
                
                if(!filteredPartList.isEmpty()) {
                    partTable.setItems(filteredPartList); }
                else {
                    searchParttxt.setText(searchParttxt.getText().substring(0, searchParttxt.getText().length()-1)); 
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alert");
                        alert.setHeaderText("Part Not Found!");
                        alert.setContentText("No parts found with that search entry.  Try again.");
                        alert.showAndWait(); 
                }
        }); 
    }
    
   
    /**
     * Provides search functionality for the product table and returns an
     * informational alert if no products are found for the search data.
     * 
     * @param event
     * @throws IOException 
     */            
    @FXML
    void onActionSearchProduct(KeyEvent event) throws IOException {    
    
        FilteredList<Product> filteredProductList = new FilteredList<>(Inventory.getAllProducts(), b -> true);
        searchProducttxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProductList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Integer.toString(product.getId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            }); 

            if(!filteredProductList.isEmpty()) {
                productTable.setItems(filteredProductList); }
            else { 
                searchProducttxt.setText(searchProducttxt.getText().substring(0, searchProducttxt.getText().length()-1)); 
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Product Not Found!");
                    alert.setContentText("No products found with that search entry.  Try again.");
                    alert.showAndWait(); }
        });

    }
    
    /**
     * Opens the Add Part screen.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Opens the Add Product screen.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * Deletes a highlighted part from the part table upon user confirmation.
     * 
     * @param event 
     */
    @FXML
    void onActionDeletePart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();  
        Part partToRemove = partTable.getSelectionModel().getSelectedItem();
            
            if(partToRemove == null) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error");
                    alert2.setHeaderText("No part selected.");
                    alert2.setContentText("You must select (highlight) a part in order to delete it");
                    alert2.showAndWait();
                    scene = FXMLLoader.load(getClass().getResource("/ViewController/Inventory_GUI.fxml"));
                    stage.show(); 
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Part Removal");
                    alert.setHeaderText("Do you really want to delete this part?");
                    alert.setContentText("");
                    Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            Inventory.deletePart(partToRemove);
                            partTable.setItems(Inventory.getAllParts());
                        }  
                        else {
                            partTable.setItems(Inventory.getAllParts());
                        }
            }            
    }

    /**
     * Deletes a product from the product table upon user confirmation (products
     * that have an associated part require an additional confirmation since 
     * deleting a product with an associated part is not desirable).
     * 
     * @param event 
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();  
        Product productToRemove = productTable.getSelectionModel().getSelectedItem();
        
            if(productToRemove == null) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("No product selected.");
                alert2.setContentText("You must select (highlight) a product in order to delete it");
                alert2.showAndWait();
                scene = FXMLLoader.load(getClass().getResource("/ViewController/Inventory_GUI.fxml"));
                stage.show(); 
            }
            else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm Product Delete");
                        alert.setHeaderText("Are you sure you want to delete this product?");
                        alert.setContentText("");
                        Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) { 
                    if(productToRemove.getAllAssociatedParts().isEmpty()) {                       
                        Inventory.deleteProduct(productToRemove);
                        productTable.setItems(Inventory.getAllProducts());  
                        }
                    else {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Error");
                        alert1.setHeaderText("Part Association Issue!");
                        alert1.setContentText("Parts must be disassociated from the product before deleting.");
                        alert1.showAndWait();
                        productTable.setItems(Inventory.getAllProducts());  
                    } } 
                    else {
                        productTable.setItems(Inventory.getAllProducts());
                    }
            }
        }

    /**
     * Exits the application.
     * 
     * @param event 
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Opens the modify part screen for the highlighted part.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        partModIdIndex = Inventory.getAllParts().indexOf((partTable.getSelectionModel().getSelectedItem())); 
        if(partModIdIndex == -1) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("No part Selected!");
                alert1.setContentText("Select (highlight) a part to modify it.");
                alert1.showAndWait();
                scene = FXMLLoader.load(getClass().getResource("/ViewController/Inventory_GUI.fxml"));
                stage.show(); }
        else { scene = FXMLLoader.load(getClass().getResource("ModPart.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();        
        }
    }      
        
    
    
    /**
     * Opens the modify product screen for the highlighted product.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        productModIdIndex = Inventory.getAllProducts().indexOf((productTable.getSelectionModel().getSelectedItem()));
        if(productModIdIndex == -1) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("No product Selected!");
                alert1.setContentText("Select (highlight) a product to modify it.");
                alert1.showAndWait();
                scene = FXMLLoader.load(getClass().getResource("/ViewController/Inventory_GUI.fxml"));
                stage.show(); }
        else { scene = FXMLLoader.load(getClass().getResource("ModProduct.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();        
        }
    }
}
