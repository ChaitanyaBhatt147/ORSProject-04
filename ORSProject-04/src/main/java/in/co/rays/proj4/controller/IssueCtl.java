package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.IssueBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.IssueModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * IssueCtl is a controller servlet responsible for handling
 * book issue related operations such as add, update, view and navigation.
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */
@WebServlet(name = "IssueCtl", urlPatterns = { "/ctl/IssueCtl" })
public class IssueCtl extends BaseCtl {

    private static final Logger log = Logger.getLogger(IssueCtl.class);

    /**
     * Validate Issue form fields
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("IssueCtl validate() started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("issueCode"))) {
            request.setAttribute("issueCode",
                    PropertyReader.getValue("error.require", "Issue Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("studentName"))) {
            request.setAttribute("studentName",
                    PropertyReader.getValue("error.require", "Student Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("issueDate"))) {
            request.setAttribute("issueDate",
                    PropertyReader.getValue("error.require", "Issue Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("issueDate"))) {
            request.setAttribute("issueDate",
                    PropertyReader.getValue("error.date", "Issue Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("returnDate"))) {
            request.setAttribute("returnDate",
                    PropertyReader.getValue("error.require", "Return Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("returnDate"))) {
            request.setAttribute("returnDate",
                    PropertyReader.getValue("error.date", "Return Date"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populate IssueBean from request
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("IssueCtl populateBean() called");

        IssueBean bean = new IssueBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setIssueCode(DataUtility.getString(request.getParameter("issueCode")));
        bean.setStudentName(DataUtility.getString(request.getParameter("studentName")));
        bean.setIssueDate(DataUtility.getDate(request.getParameter("issueDate")));
        bean.setReturnDate(DataUtility.getDate(request.getParameter("returnDate")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles GET request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("IssueCtl doGet() started");

        long id = DataUtility.getLong(request.getParameter("id"));
        IssueModel model = new IssueModel();

        if (id > 0) {
            try {
                IssueBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                log.error("Error fetching issue record", e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
        log.info("IssueCtl doGet() completed");
    }

    /**
     * Handles POST request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("IssueCtl doPost() started");

        String op = DataUtility.getString(request.getParameter("operation"));
        IssueModel model = new IssueModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            IssueBean bean = (IssueBean) populateBean(request);

            try {
                model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Issue added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Issue Code already exists", request);

            } catch (ApplicationException e) {
                log.error("Error adding issue", e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            IssueBean bean = (IssueBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Issue updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Issue Code already exists", request);

            } catch (ApplicationException e) {
                log.error("Error updating issue", e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.ISSUE_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.ISSUE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
        log.info("IssueCtl doPost() completed");
    }

    /**
     * Returns view page
     */
    @Override
    protected String getView() {
        return ORSView.ISSUE_VIEW;
    }
}