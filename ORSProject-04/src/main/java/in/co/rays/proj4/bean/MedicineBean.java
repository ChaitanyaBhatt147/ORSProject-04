package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * MedicineBean represents medicine information within the system.
 * It includes details such as medicine name, price,
 * and expiry date.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class MedicineBean extends BaseBean {

    /** Unique ID of the medicine. */
    private Long medicineId;

    /** Name of the medicine. */
    private String medicineName;

    /** Price of the medicine. */
    private Double price;

    /** Expiry date of the medicine. */
    private Date expiryDate;

    /**
     * Gets the medicine ID.
     *
     * @return medicineId
     */
    public Long getMedicineId() {
        return medicineId;
    }

    /**
     * Sets the medicine ID.
     *
     * @param medicineId the medicine ID
     */
    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    /**
     * Gets the name of the medicine.
     *
     * @return medicineName
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * Sets the name of the medicine.
     *
     * @param medicineName the medicine name
     */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * Gets the price of the medicine.
     *
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of the medicine.
     *
     * @param price the price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Gets the expiry date of the medicine.
     *
     * @return expiryDate
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the medicine.
     *
     * @param expiryDate the expiry date
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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
     * Returns the display value of the medicine,
     * typically the medicine name.
     *
     * @return medicineName
     */
    @Override
    public String getValue() {
        return medicineName;
    }
}