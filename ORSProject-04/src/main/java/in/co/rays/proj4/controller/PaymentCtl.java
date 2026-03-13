package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PaymentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.PaymentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * PaymentCtl handles add, update, view and navigation operations for Payment.
 * 
 * author Chaitanya Bhatt
 */
@WebServlet(name = "PaymentCtl", urlPatterns = { "/ctl/PaymentCtl" })
public class PaymentCtl extends BaseCtl {

    private static final Logger log = Logger.getLogger(PaymentCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("PaymentCtl validate() started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("paymentCode"))) {
            request.setAttribute("paymentCode",
                    PropertyReader.getValue("error.require", "Payment Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("userId"))) {
            request.setAttribute("userId",
                    PropertyReader.getValue("error.require", "User ID"));
            pass = false;
        }else if (!DataValidator.isLong(request.getParameter("userId"))) {
        	request.setAttribute("userId", "should be number");
            pass = false;
		}

        if (DataValidator.isNull(request.getParameter("amount"))) {
            request.setAttribute("amount",
                    PropertyReader.getValue("error.require", "Amount"));
            pass = false;

        } else if (!DataValidator.isDouble(request.getParameter("amount"))) {
            request.setAttribute("amount", "Amount must be number");
            pass = false;

        } else if (DataUtility.getDouble(request.getParameter("amount")) <= 0) {
            request.setAttribute("amount", "Amount must be greater than 0");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("paymentStatus"))) {
            request.setAttribute("paymentStatus",
                    PropertyReader.getValue("error.require", "Payment Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("PaymentCtl populateBean() called");

        PaymentBean bean = new PaymentBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setPaymentCode(DataUtility.getString(request.getParameter("paymentCode")));
        bean.setUserId(DataUtility.getLong(request.getParameter("userId")));
        bean.setAmount(DataUtility.getDouble(request.getParameter("amount")));
        bean.setPaymentStatus(DataUtility.getString(request.getParameter("paymentStatus")));

        populateDTO(bean, request);

        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("PaymentCtl doGet Started");

        long id = DataUtility.getLong(request.getParameter("id"));
        PaymentModel model = new PaymentModel();

        if (id > 0) {
            try {

                PaymentBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("PaymentCtl doPost Started");

        String op = DataUtility.getString(request.getParameter("operation"));
        PaymentModel model = new PaymentModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            PaymentBean bean = (PaymentBean) populateBean(request);

            try {

                long pk = model.add(bean);

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Payment added successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Payment Code already exists", request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            PaymentBean bean = (PaymentBean) populateBean(request);

            try {

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Payment updated successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Payment Code already exists", request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.PAYMENT_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.PAYMENT_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.PAYMENT_VIEW;
    }
}