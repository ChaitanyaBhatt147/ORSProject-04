package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * ComplaintTicketBean represents complaint ticket information within the system.
 * It includes details such as issue type, created date, and complaint status.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class ComplaintTicketBean extends BaseBean {

    /** Unique complaint ticket ID. */
    private Long complaintTicketId;

    /** Type of issue reported in the complaint. */
    private String issueType;

    /** Date when the complaint was created. */
    private Date createdDate;

    /** Current status of the complaint. */
    private String status;

    /**
     * Gets the complaint ticket ID.
     *
     * @return complaintTicketId
     */
    public Long getComplaintTicketId() {
        return complaintTicketId;
    }

    /**
     * Sets the complaint ticket ID.
     *
     * @param complaintTicketId the complaint ticket ID
     */
    public void setComplaintTicketId(Long complaintTicketId) {
        this.complaintTicketId = complaintTicketId;
    }

    /**
     * Gets the issue type of the complaint.
     *
     * @return issueType
     */
    public String getIssueType() {
        return issueType;
    }

    /**
     * Sets the issue type of the complaint.
     *
     * @param issueType the issue type
     */
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    /**
     * Gets the complaint creation date.
     *
     * @return createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the complaint creation date.
     *
     * @param createdDate the created date
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the complaint status.
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the complaint status.
     *
     * @param status the complaint status
     */
    public void setStatus(String status) {
        this.status = status;
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
     * Returns the display value of the complaint ticket,
     * typically the issue type.
     *
     * @return issueType
     */
    @Override
    public String getValue() {
        return issueType;
    }
}