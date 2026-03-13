package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TravelPackageBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TravelPackageModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Controller for Travel Package Booking Module
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
@WebServlet(name = "TravelPackageCtl", urlPatterns = { "/ctl/TravelPackageCtl" })
public class TravelPackageCtl extends BaseCtl {

    private static final Logger log = Logger.getLogger(TravelPackageCtl.class);

    /**
     * Validation
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("TravelPackageCtl validate() started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("travelerName"))) {
            request.setAttribute("travelerName",
                    PropertyReader.getValue("error.require", "Traveler Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("travelerName"))) {
        	request.setAttribute("travelerName","Should be name");
            pass = false;
		}

        if (DataValidator.isNull(request.getParameter("destination"))) {
            request.setAttribute("destination",
                    PropertyReader.getValue("error.require", "Destination"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("destination"))) {
        	request.setAttribute("destination","Should be name");
            pass = false;
		}

        if (DataValidator.isNull(request.getParameter("packageType"))) {
            request.setAttribute("packageType",
                    PropertyReader.getValue("error.require", "Package Type"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("travelDate"))) {
            request.setAttribute("travelDate",
                    PropertyReader.getValue("error.require", "Travel Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("travelDate"))) {
            request.setAttribute("travelDate",
                    PropertyReader.getValue("error.date", "Travel Date"));
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

        if (DataValidator.isNull(request.getParameter("packageCost"))) {

            request.setAttribute("packageCost",
                    PropertyReader.getValue("error.require", "Package Cost"));
            pass = false;

        } else if (!DataValidator.isLong(request.getParameter("packageCost"))) {

            request.setAttribute("packageCost",
                    "Package Cost must be numeric");
            pass = false;

        } else if (DataUtility.getLong(request.getParameter("packageCost")) <= 0) {

            request.setAttribute("packageCost",
                    "Package Cost must be greater than 0");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("bookingStatus"))) {
            request.setAttribute("bookingStatus",
                    PropertyReader.getValue("error.require", "Booking Status"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populate Bean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("TravelPackageCtl populateBean() called");

        TravelPackageBean bean = new TravelPackageBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setTravelerName(
                DataUtility.getString(request.getParameter("travelerName")));
        bean.setDestination(
                DataUtility.getString(request.getParameter("destination")));
        bean.setPackageType(
                DataUtility.getString(request.getParameter("packageType")));
        bean.setTravelDate(
                DataUtility.getDate(request.getParameter("travelDate")));
        bean.setReturnDate(
                DataUtility.getDate(request.getParameter("returnDate")));
        bean.setPackageCost(
                DataUtility.getLong(request.getParameter("packageCost")));
        bean.setBookingStatus(
                DataUtility.getString(request.getParameter("bookingStatus")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * GET Request
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        log.info("TravelPackageCtl doGet() started");

        long id = DataUtility.getLong(request.getParameter("id"));
        TravelPackageModel model = new TravelPackageModel();

        if (id > 0) {
            try {
                TravelPackageBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * POST Request
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        log.info("TravelPackageCtl doPost() started");
        System.out.println("in do post");

        String op = DataUtility.getString(request.getParameter("operation"));
        TravelPackageModel model = new TravelPackageModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            TravelPackageBean bean =
                    (TravelPackageBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage(
                        "Travel Package added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(
                        "Record already exists", request);

            } catch (ApplicationException e) {
            	e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            TravelPackageBean bean =
                    (TravelPackageBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage(
                        "Travel Package updated successfully", request);

            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(
                    ORSView.TRAVEL_PACKAGE_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(
                    ORSView.TRAVEL_PACKAGE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * View Page
     */
    @Override
    protected String getView() {
        return ORSView.TRAVEL_PACKAGE_VIEW;
    }
}