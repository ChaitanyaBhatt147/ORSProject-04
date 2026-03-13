package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SeminarBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.SeminarModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * SeminarCtl handles Add, Update, View operations for Seminar.
 * 
 * @author Chaitanya Bhatt
 * @version 1.0
 */
@WebServlet(name = "SeminarCtl", urlPatterns = { "/ctl/SeminarCtl" })
public class SeminarCtl extends BaseCtl {

    private static final Logger log = Logger.getLogger(SeminarCtl.class);

    /**
     * Validate Seminar Form
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("SeminarCtl validate() started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("seminarTitle"))) {
            request.setAttribute("seminarTitle",
                    PropertyReader.getValue("error.require", "Seminar Title"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("seminarDate"))) {
            request.setAttribute("seminarDate",
                    PropertyReader.getValue("error.require", "Seminar Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("seminarDate"))) {
            request.setAttribute("seminarDate",
                    PropertyReader.getValue("error.date", "Seminar Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("speaker"))) {
            request.setAttribute("speaker",
                    PropertyReader.getValue("error.require", "Speaker"));
            pass = false;
        }
        else if (!DataValidator.isName(request.getParameter("speaker"))) {
        	request.setAttribute("speaker","Should be name");
            pass = false;
		}

        return pass;
    }

    /**
     * Populate SeminarBean from request
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("SeminarCtl populateBean() called");

        SeminarBean bean = new SeminarBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setSeminarTitle(
                DataUtility.getString(request.getParameter("seminarTitle")));
        bean.setSeminarDate(
                DataUtility.getDate(request.getParameter("seminarDate")));
        bean.setSpeaker(
                DataUtility.getString(request.getParameter("speaker")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Load Seminar by ID
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        log.info("SeminarCtl doGet() started");

        long id = DataUtility.getLong(request.getParameter("id"));
        SeminarModel model = new SeminarModel();

        if (id > 0) {
            try {
                SeminarBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handle Save, Update, Cancel, Reset
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        log.info("SeminarCtl doPost() started");

        String op = DataUtility.getString(request.getParameter("operation"));
        SeminarModel model = new SeminarModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            SeminarBean bean = (SeminarBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage(
                        "Seminar added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(
                        "Seminar already exists", request);

            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            SeminarBean bean = (SeminarBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage(
                        "Seminar updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(
                        "Seminar already exists", request);

            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(
                    ORSView.SEMINAR_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(
                    ORSView.SEMINAR_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Return View Page
     */
    @Override
    protected String getView() {
        return ORSView.SEMINAR_VIEW;
    }
}