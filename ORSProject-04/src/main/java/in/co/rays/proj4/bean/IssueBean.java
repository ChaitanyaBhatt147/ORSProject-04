package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * IssueBean represents book issue information within the system.
 * It includes issue details such as issue code, student name,
 * issue date, and return date.
 * This class extends {@link BaseBean}.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class IssueBean extends BaseBean {

    /** Unique issue code */
    private String issueCode;

    /** Name of the student */
    private String studentName;

    /** Date when book is issued */
    private Date issueDate;

    /** Date when book should be returned */
    private Date returnDate;

    /**
     * Gets the issue code.
     *
     * @return issueCode
     */
    public String getIssueCode() {
        return issueCode;
    }

    /**
     * Sets the issue code.
     *
     * @param issueCode the issue code (UNIQUE)
     */
    public void setIssueCode(String issueCode) {
        this.issueCode = issueCode;
    }

    /**
     * Gets the student name.
     *
     * @return studentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Sets the student name.
     *
     * @param studentName the student name
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Gets the issue date.
     *
     * @return issueDate
     */
    public Date getIssueDate() {
        return issueDate;
    }

    /**
     * Sets the issue date.
     *
     * @param issueDate the issue date
     */
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * Gets the return date.
     *
     * @return returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date.
     *
     * @param returnDate the return date
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Returns the unique key (ID) as a string.
     *
     * @return id
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value of the issue,
     * typically the issue code.
     *
     * @return issueCode
     */
    @Override
    public String getValue() {
        return issueCode;
    }
}