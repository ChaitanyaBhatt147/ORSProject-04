package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * TravelPackageBean represents travel package booking information.
 * It contains traveler details, destination information,
 * travel dates, package cost, and booking status.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class TravelPackageBean extends BaseBean {

    /** Name of the traveler */
    private String travelerName;

    /** Travel destination */
    private String destination;

    /** Type of package (Standard, Premium, etc.) */
    private String packageType;

    /** Travel start date */
    private Date travelDate;

    /** Return date */
    private Date returnDate;

    /** Cost of the package */
    private Long packageCost;

    /** Booking status (Booked, Cancelled, Pending) */
    private String bookingStatus;

    /**
     * Gets traveler name.
     * 
     * @return travelerName
     */
    public String getTravelerName() {
        return travelerName;
    }

    /**
     * Sets traveler name.
     * 
     * @param travelerName
     */
    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }

    /**
     * Gets destination.
     * 
     * @return destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets destination.
     * 
     * @param destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets package type.
     * 
     * @return packageType
     */
    public String getPackageType() {
        return packageType;
    }

    /**
     * Sets package type.
     * 
     * @param packageType
     */
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    /**
     * Gets travel date.
     * 
     * @return travelDate
     */
    public Date getTravelDate() {
        return travelDate;
    }

    /**
     * Sets travel date.
     * 
     * @param travelDate
     */
    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    /**
     * Gets return date.
     * 
     * @return returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Sets return date.
     * 
     * @param returnDate
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets package cost.
     * 
     * @return packageCost
     */
    public Long getPackageCost() {
        return packageCost;
    }

    /**
     * Sets package cost.
     * 
     * @param packageCost
     */
    public void setPackageCost(Long packageCost) {
        this.packageCost = packageCost;
    }

    /**
     * Gets booking status.
     * 
     * @return bookingStatus
     */
    public String getBookingStatus() {
        return bookingStatus;
    }

    /**
     * Sets booking status.
     * 
     * @param bookingStatus
     */
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    /**
     * Returns unique key (ID).
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns display value.
     */
    @Override
    public String getValue() {
        return travelerName + " - " + destination;
    }
}