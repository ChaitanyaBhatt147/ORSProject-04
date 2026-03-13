package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.LanguageLearningProgressBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.LanguageLearningProgressModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * LanguageLearningProgressCtl handles add, update and view
 * operations for LanguageLearningProgress.
 *
 * author Chaitanya Bhatt
 * version 1.0
 */

@WebServlet(name = "LanguageLearningProgressCtl", urlPatterns = { "/ctl/LanguageLearningProgressCtl" })
public class LanguageLearningProgressCtl extends BaseCtl {

    private static final Logger log = Logger.getLogger(LanguageLearningProgressCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("LanguageLearningProgressCtl validate started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("studentName"))) {
            request.setAttribute("studentName",
                    PropertyReader.getValue("error.require", "Student Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("language"))) {
            request.setAttribute("language",
                    PropertyReader.getValue("error.require", "Language"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("level"))) {
            request.setAttribute("level",
                    PropertyReader.getValue("error.require", "Level"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("completionStatus"))) {
            request.setAttribute("completionStatus",
                    PropertyReader.getValue("error.require", "Completion Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("LanguageLearningProgressCtl populateBean started");

        LanguageLearningProgressBean bean = new LanguageLearningProgressBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setStudentName(DataUtility.getString(request.getParameter("studentName")));
        bean.setLanguage(DataUtility.getString(request.getParameter("language")));
        bean.setLevel(DataUtility.getString(request.getParameter("level")));
        bean.setCompletionStatus(DataUtility.getString(request.getParameter("completionStatus")));

        populateDTO(bean, request);

        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("LanguageLearningProgressCtl doGet started");

        long id = DataUtility.getLong(request.getParameter("id"));
        LanguageLearningProgressModel model = new LanguageLearningProgressModel();

        if (id > 0) {
            try {

                LanguageLearningProgressBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);

            } catch (ApplicationException e) {

                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("LanguageLearningProgressCtl doPost started");

        String op = DataUtility.getString(request.getParameter("operation"));
        LanguageLearningProgressModel model = new LanguageLearningProgressModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            LanguageLearningProgressBean bean = (LanguageLearningProgressBean) populateBean(request);

            try {

                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Language Learning Progress added successfully", request);

            } catch (ApplicationException | DuplicateRecordException e) {

                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            LanguageLearningProgressBean bean = (LanguageLearningProgressBean) populateBean(request);

            try {

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Language Learning Progress updated successfully", request);

            } catch (ApplicationException e) {

                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.LANGUAGE_LEARNING_PROGRESS_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.LANGUAGE_LEARNING_PROGRESS_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.LANGUAGE_LEARNING_PROGRESS_VIEW;
    }
}