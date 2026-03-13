package in.co.rays.proj4.bean;

/**
 * InternetPackageBean represents internet package information in the system.
 * It contains package details such as package name, price, and data limit.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class InternetPackageBean extends BaseBean {

    /** Name of the internet package */
    private String packageName;

    /** Price of the package */
    private Double price;

    /** Data limit of the package */
    private Integer dataLimit;

    /**
     * Gets the package name.
     * 
     * @return packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Sets the package name.
     * 
     * @param packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Gets the package price.
     * 
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the package price.
     * 
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Gets the data limit of the package.
     * 
     * @return dataLimit
     */
    public Integer getDataLimit() {
        return dataLimit;
    }

    /**
     * Sets the data limit of the package.
     * 
     * @param dataLimit
     */
    public void setDataLimit(Integer dataLimit) {
        this.dataLimit = dataLimit;
    }

    /**
     * Returns the unique key (ID) as a string.
     * 
     * @return the key
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value of the package.
     * 
     * @return packageName
     */
    @Override
    public String getValue() {
        return packageName;
    }
}