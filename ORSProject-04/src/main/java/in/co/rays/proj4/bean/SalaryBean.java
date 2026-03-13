package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * SalaryBean represents salary slip information within the system.
 * It includes employee salary details such as salary slip code,
 * employee name, salary amount, bonus, and salary date.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class SalaryBean extends BaseBean {

    /** Unique salary slip code. */
    private String salarySlipCode;

    /** Name of the employee. */
    private String employeeName;

    /** Basic salary amount. */
    private Double basicSalary;

    /** Bonus amount. */
    private Double bonus;

    /** Salary payment date. */
    private Date salaryDate;

    /**
     * Gets salary slip code.
     *
     * @return salarySlipCode
     */
    public String getSalarySlipCode() {
        return salarySlipCode;
    }

    /**
     * Sets salary slip code.
     *
     * @param salarySlipCode
     */
    public void setSalarySlipCode(String salarySlipCode) {
        this.salarySlipCode = salarySlipCode;
    }

    /**
     * Gets employee name.
     *
     * @return employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * Sets employee name.
     *
     * @param employeeName
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * Gets basic salary.
     *
     * @return basicSalary
     */
    public Double getBasicSalary() {
        return basicSalary;
    }

    /**
     * Sets basic salary.
     *
     * @param basicSalary
     */
    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    /**
     * Gets bonus amount.
     *
     * @return bonus
     */
    public Double getBonus() {
        return bonus;
    }

    /**
     * Sets bonus amount.
     *
     * @param bonus
     */
    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    /**
     * Gets salary date.
     *
     * @return salaryDate
     */
    public Date getSalaryDate() {
        return salaryDate;
    }

    /**
     * Sets salary date.
     *
     * @param salaryDate
     */
    public void setSalaryDate(Date salaryDate) {
        this.salaryDate = salaryDate;
    }

    /**
     * Returns unique key (ID).
     *
     * @return id as String
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns display value.
     *
     * @return salarySlipCode
     */
    @Override
    public String getValue() {
        return salarySlipCode;
    }
}