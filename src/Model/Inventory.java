
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * The Inventory class provides data definition and manipulation
 * methods to be used by the application.
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class Inventory {
    
    private static int partIDIndex = 0;
    private static int prodIDIndex = 0;
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    /**
     * Adds a new part to the allParts list.
     * 
     * @param newPart New part to be added
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    /**
     * Adds a new product to the allProducts list.
     * 
     * @param newProduct New product to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    /**
     * Increments the partIDindex variable when a new part is created.
     * 
     * @return partIDIndex Index variable for part
     */
    public static int newPartID() {
        partIDIndex++;
        return partIDIndex;
    }
    
    /**
     * Increments the prodIDindex variable when a new product is created.
     * 
     * @return prodIDIndex Index variable for product
     */
    public static int newProdID(){
        prodIDIndex++;
        return prodIDIndex;
    }
    
    /**
     * Looks up a part in the allParts table based on the part's ID number.
     * 
     * @param partID Part ID
     * @return allParts.get(i)
     */
    
    public static Part lookupPart(int partID){
        if(!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++){
                if(allParts.get(i).getId() == partID+1){
                    return allParts.get(i);
                }
            }
        }
        return null;
    }
    
    /**
     * Looks up a product in the allProducts list based on the productID.
     * 
     * @param productID
     * @return allProduct.get(i)
     */
    public static Product lookupProduct(int productID) {
        if(!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if(allProducts.get(i).getId() == productID+1){
                    return allProducts.get(i);
                }
            }
        }     
        return null;
    }
    
    /**
     * Generates an observable list of parts containing the string partName.
     * 
     * @param partName Some or all of part name
     * @return list List of parts
     */
    public static ObservableList<Part> lookupPart(String partName){
    
        ObservableList<Part> list = FXCollections.observableArrayList();
        
        if(!allParts.isEmpty()){
            for (int i = 0; i < allParts.size(); i++){
                if(allParts.get(i).getName().toLowerCase().contains(partName.toLowerCase())){
                    list.add(allParts.get(i));
                }
            }
        } else{
            return list;
        }
        return list;
    } 
    
    /**
     * Generates an observable list of products containing the string
     * productName.
     * 
     * @param productName Name of Product
     * @return results Resulting partial or full matches
     */    
    public static ObservableList<Product> lookupProduct(String productName){
       
    ObservableList<Product> results = FXCollections.observableArrayList();
    
        if(!allProducts.isEmpty()){
            for (int i = 0; i < allProducts.size(); i++){
                if(allProducts.get(i).getName().toLowerCase().equals(productName.toLowerCase())){
                    results.add(allProducts.get(i));
                }
            }
        } else {
        return results;
        }
        return results;
    }
    
    /**
     * Updates the part at a selected index location.
     * 
     * @param index Index location of part
     * @param selectedPart  Selected part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    
    /**
     * Updates the product at a selected index location.
     * 
     * @param index Index location of part
     * @param selectedProduct Selected product
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }
    
    /**
     * Deletes a selected part from the allParts list.
     * 
     * @param selectedPart Part to be deleted
     * @return allParts.remove(selectedPart) Returns list with part removed
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);        
    }
    
    /**
     * Deletes a selected product from the allProducts list.
     * 
     * @param selectedProduct Product to be deleted
     * @return allProducts.remove(selectedProduct) Returns list with pproduct removed
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }
    
    /**
     * Gets the allParts list.
     * 
     * @return allParts List of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
        
    }
    
     /**
     * Gets the allProducst list.
     * 
     * @return allProducts List of all products
     */
    public static ObservableList<Product> getAllProducts () {
        return allProducts;
        
    }
    
    /**
     * Checks if a part is associated with a product.
     * 
     * @param part Part to be checked
     * @return allProducts.get(i).getName() Name of associated products (if
     * found)
     * 
     */
    public static String checkPartInProd(Part part){
        for(int i = 0; i<allParts.size() ; i++){
            if(allProducts.get(i).getAllAssociatedParts().contains(part)){
                return allProducts.get(i).getName();
            }
        }
        return null;
    }
    
}

