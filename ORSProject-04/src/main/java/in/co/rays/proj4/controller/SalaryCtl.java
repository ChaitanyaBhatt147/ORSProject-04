package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SalaryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.SalaryModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * SalaryCtl handles Add, Update, View operations of Salary.
 * 
 * author Chaitanya Bhatt
 */
@WebServlet(name = "SalaryCtl", urlPatterns = { "/ctl/SalaryCtl" })
public class SalaryCtl extends BaseCtl {

    private static final Logger log = Logger.getLogger(SalaryCtl.class);

    /**
     * Validation
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("SalaryCtl validate() started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("salarySlipCode"))) {
            request.setAttribute("salarySlipCode",
                    PropertyReader.getValue("error.require", "Salary Slip Code"));
            pass = false;
        } else if (!DataValidator.isLong(request.getParameter("salarySlipCode"))) {
        	request.setAttribute("salarySlipCode","should be number");
            pass = false;
		}

        if (DataValidator.isNull(request.getParameter("employeeName"))) {
            request.setAttribute("employeeName",
                    PropertyReader.getValue("error.require", "Employee Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("employeeName"))) {
            request.setAttribute("employeeName", "Invalid Employee Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("basicSalary"))) {
            request.setAttribute("basicSalary",
                    PropertyReader.getValue("error.require", "Basic Salary"));
            pass = false;
        } else if (DataUtility.getDouble(request.getParameter("basicSalary")) <= 0) {

            request.setAttribute("basicSalary",
                    "Basic Salary must be greater than 0");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("salaryDate"))) {
            request.setAttribute("salaryDate",
                    PropertyReader.getValue("error.require", "Salary Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("salaryDate"))) {
            request.setAttribute("salaryDate",
                    PropertyReader.getValue("error.date", "Salary Date"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populate Bean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("SalaryCtl populateBean() called");

        SalaryBean bean = new SalaryBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setSalarySlipCode(
                DataUtility.getString(request.getParameter("salarySlipCode")));
        bean.setEmployeeName(
                DataUtility.getString(request.getParameter("employeeName")));
        bean.setBasicSalary(
                DataUtility.getDouble(request.getParameter("basicSalary")));
        bean.setBonus(
                DataUtility.getDouble(request.getParameter("bonus")));
        bean.setSalaryDate(
                DataUtility.getDate(request.getParameter("salaryDate")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Load record for edit
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("SalaryCtl doGet started");

        long id = DataUtility.getLong(request.getParameter("id"));
        SalaryModel model = new SalaryModel();

        if (id > 0) {
            try {
                SalaryBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handle POST
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("SalaryCtl doPost started");

        String op = DataUtility.getString(request.getParameter("operation"));

        SalaryModel model = new SalaryModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            SalaryBean bean = (SalaryBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage(
                        "Salary added successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(
                        "Salary Slip Code already exists", request);

            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            SalaryBean bean = (SalaryBean) populateBean(request);

            try {

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage(
                        "Salary updated successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(
                        "Salary Slip Code already exists", request);

            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(
                    ORSView.SALARY_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(
                    ORSView.SALARY_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * View Page
     */
    @Override
    protected String getView() {
        return ORSView.SALARY_VIEW;
    }
}