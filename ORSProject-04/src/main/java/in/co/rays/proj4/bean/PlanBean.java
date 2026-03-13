package in.co.rays.proj4.bean;

/**
 * PlanBean represents subscription plan information within the system.
 * It includes plan details such as plan name, price, and validity days.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class PlanBean extends BaseBean {

    /** Name of the plan. */
    private String planName;

    /** Price of the plan. */
    private Double price;

    /** Validity of the plan in days. */
    private Integer validityDays;

    /**
     * Gets the plan name.
     *
     * @return planName
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * Sets the plan name.
     *
     * @param planName the plan name
     */
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    /**
     * Gets the price of the plan.
     *
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of the plan.
     *
     * @param price the price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Gets the validity days of the plan.
     *
     * @return validityDays
     */
    public Integer getValidityDays() {
        return validityDays;
    }

    /**
     * Sets the validity days of the plan.
     *
     * @param validityDays the validity days
     */
    public void setValidityDays(Integer validityDays) {
        this.validityDays = validityDays;
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
     * Returns the display value of the plan,
     * typically the plan name.
     *
     * @return planName
     */
    @Override
    public String getValue() {
        return planName;
    }
}