
package Model;

/**
 * The InHouse class is a subclass of the Part superclass
 * allowing definition of the machine ID.
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class InHouse extends Part {
    private int machineID;

    /**
     * Constructor for in house part.
     * 
     * @param id Part ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Quantity of part in inventory
     * @param min Minimum allowable quantity of part in inventory
     * @param max Maximum allowable quantity of part in inventory
     * @param machineID Machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID=machineID;
    }
    
    /**
     * Get the machine ID.
     * 
     * @return machineID Machine ID
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     * Set the machine ID.
     * 
     * @param machineID Sets Machine ID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
