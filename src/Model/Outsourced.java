
package Model;

/**
 * The Outsourced class is a subclass of the Part superclass
 * allowing definition of the company name.
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class Outsourced extends Part {
    private String companyName;
    
    /**
     * Constructor for Outsourced part.
     * 
     * @param id Part ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Quantity of part in inventory
     * @param min Minimum allowable quantity of part in inventory
     * @param max Maximum allowable quantity of part in inventory
     * @param companyName Name of company to which part is outsourced
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    /**
     * Gets the company name.
     *
     * @return companyName Name of company to which part is outsourced 
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the company name.
     * 
     * @param name Name of company to which part is outsourced 
     */
    public void setCompanyName(String name) {
        this.companyName = name;
    }
}
