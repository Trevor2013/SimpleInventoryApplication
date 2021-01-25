
package inventoryapp;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class InventoryApp launches the Inventory Application.
 * @author Trevor Ross, tros114@wgu.edu
 */
public class InventoryApp extends Application {
    
    /**
     * The main method launches the application and loads sample data into the application.
     * @param args 
     */
    public static void main(String[] args) {
        
        Part part1 = new InHouse(Inventory.newPartID(), "Gear", 12.99, 10, 5, 15, 1234);
        Part part2 = new InHouse(Inventory.newPartID(), "Rack and Pinion", 4.99, 8, 1, 10, 4321);
        Part part3 = new Outsourced(Inventory.newPartID(), "Pulley", 1.99, 2, 0, 5, "Health Supply Shop");
        Part part4 = new Outsourced(Inventory.newPartID(), "Gizmo", 19.99, 15, 5, 20, "Time Management Solutions, Inc.");
        Part part5 = new InHouse(Inventory.newPartID(), "Gadget", 1.25, 20, 5, 30, 5689);
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        
        Product product1 = new Product(Inventory.newProdID(), "Trusty Bike",119.99, 1, 0, 2);
        Product product2 = new Product(Inventory.newProdID(), "Time Machine",112.13, 4, 1, 4);
        
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        
        product2.addAssociatedPart(part3);
        product2.addAssociatedPart(part4);
        product2.addAssociatedPart(part5);
       
        launch(args);
    }
       
    /**
     * Loads the Main screen.
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewController/Inventory_GUI.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Application");
        stage.show();
    }
 
}
