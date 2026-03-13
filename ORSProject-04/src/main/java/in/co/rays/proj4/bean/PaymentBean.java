package in.co.rays.proj4.bean;

/**
 * PaymentBean represents payment information within the system.
 * It includes details such as payment code, user ID, payment amount,
 * and payment status.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class PaymentBean extends BaseBean {

    /** Unique code for the payment. */
    private String paymentCode;

    /** ID of the user who made the payment. */
    private long userId;

    /** Amount paid by the user. */
    private Double amount;

    /** Status of the payment (Pending, Completed, Failed). */
    private String paymentStatus;

    /**
     * Gets the payment code.
     *
     * @return paymentCode
     */
    public String getPaymentCode() {
        return paymentCode;
    }

    /**
     * Sets the payment code.
     *
     * @param paymentCode the payment code
     */
    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    /**
     * Gets the user ID.
     *
     * @return userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the payment amount.
     *
     * @return amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Sets the payment amount.
     *
     * @param amount the payment amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Gets the payment status.
     *
     * @return paymentStatus
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the payment status.
     *
     * @param paymentStatus the payment status
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
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
     * Returns the display value of the payment.
     *
     * @return paymentCode
     */
    @Override
    public String getValue() {
        return paymentCode;
    }
}