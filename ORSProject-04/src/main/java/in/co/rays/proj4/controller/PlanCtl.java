package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PlanBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.PlanModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * PlanCtl handles Plan Add / Update / View operations.
 * 
 * @author Chaitanya Bhatt
 */

@WebServlet(name = "PlanCtl", urlPatterns = { "/ctl/PlanCtl" })
public class PlanCtl extends BaseCtl {

    private static final Logger log = Logger.getLogger(PlanCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("PlanCtl validate() started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("planName"))) {
            request.setAttribute("planName",
                    PropertyReader.getValue("error.require", "Plan Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("planName"))) {
        	request.setAttribute("planName","Should be name");
            pass = false;
		}

        if (DataValidator.isNull(request.getParameter("price"))) {
            request.setAttribute("price",
                    PropertyReader.getValue("error.require", "Price"));
            pass = false;

        } else if (DataUtility.getDouble(request.getParameter("price")) <= 0) {
            request.setAttribute("price", "Price should be greater than 0");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("validityDays"))) {
            request.setAttribute("validityDays",
                    PropertyReader.getValue("error.require", "Validity Days"));
            pass = false;

        } else if (DataUtility.getInt(request.getParameter("validityDays")) <= 0) {
            request.setAttribute("validityDays", "Validity Days should be greater than 0");
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("PlanCtl populateBean() called");

        PlanBean bean = new PlanBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setPlanName(DataUtility.getString(request.getParameter("planName")));
        bean.setPrice(DataUtility.getDouble(request.getParameter("price")));
        bean.setValidityDays(DataUtility.getInt(request.getParameter("validityDays")));

        populateDTO(bean, request);

        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("PlanCtl doGet() started");

        long id = DataUtility.getLong(request.getParameter("id"));

        PlanModel model = new PlanModel();

        if (id > 0) {

            try {

                PlanBean bean = model.findByPk(id);

                ServletUtility.setBean(bean, request);

            } catch (ApplicationException e) {

                log.error("Error fetching Plan", e);

                ServletUtility.handleException(e, request, response);

                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("PlanCtl doPost() started");

        String op = DataUtility.getString(request.getParameter("operation"));

        PlanModel model = new PlanModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            PlanBean bean = (PlanBean) populateBean(request);

            try {

                long pk = model.add(bean);

                ServletUtility.setBean(bean, request);

                ServletUtility.setSuccessMessage("Plan added successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);

                ServletUtility.setErrorMessage("Plan already exists", request);

            } catch (ApplicationException e) {

                ServletUtility.handleException(e, request, response);

                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            PlanBean bean = (PlanBean) populateBean(request);

            try {

                if (id > 0) {

                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);

                ServletUtility.setSuccessMessage("Plan updated successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);

                ServletUtility.setErrorMessage("Plan already exists", request);

            } catch (ApplicationException e) {

                ServletUtility.handleException(e, request, response);

                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.PLAN_LIST_CTL, request, response);

            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.PLAN_CTL, request, response);

            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {

        return ORSView.PLAN_VIEW;
    }
}