
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * The Product class contains the product constructor, setters, getters, and 
 * methods for part association and disassociation.
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
   
    
    /**
     * Product constructor
     * 
     * @param id Product ID
     * @param name Product Name
     * @param price Product Price
     * @param stock Quantity of product in inventory
     * @param min Minimum allowable quantity of product in inventory
     * @param max Maximum allowable quantity of product in inventory
     */
public Product(int id, String name, double price, int stock, int min, int max) {
    this.id=id;
    this.name=name;
    this.price=price;
    this.stock=stock;
    this.min=min;
    this.max=max;    
}

    /**
     * Gets product ID.
     * 
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets product ID.
     * @param id Product ID to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Gets product name.
     * @return Product Name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets product name.
     * @param name Product name to set 
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets product price.
     * @return price Product price
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Sets product price.
     * @param price Product price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Gets product inventory quantity.
     * @return Product stock
     */
    public int getStock() {
        return stock;
    }
    
    /**
     * Sets product inventory quantity.
     * @param stock Set product stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /**
     * Gets minimum product inventory
     * @return Minimum product inventory
     */
    public int getMin() {
        return min;
    }
    
    /**
     * Sets minimum product inventory
     * @param min Set product minimum inventory
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets maximum product inventory
     * @return Maximum product inventory
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets maximum product inventory
     * @param max Set product maximum inventory
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * Adds associated part to product
     * @param part Set associated part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    /**
     * Remove associated part from product if it exists.
     * 
     * @param selectedAssociatedPart 
     * @return 
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if(associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else {
            return false;} 
    }
    
    /**
     * Gets a list of all parts associated with a product.
     * 
     * @return A list of parts associated with the product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
        
    }
}
