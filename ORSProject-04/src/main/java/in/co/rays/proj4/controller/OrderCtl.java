package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.OrderBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.OrderModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * OrderCtl handles Order operations like Add, Update, View, Reset and Cancel.
 * It validates input, populates OrderBean and delegates persistence to
 * OrderModel.
 * 
 * @author Chaitanya Bhatt
 * @version 1.0
 */
@WebServlet(name = "OrderCtl", urlPatterns = { "/ctl/OrderCtl" })
public class OrderCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(OrderCtl.class);

	/**
	 * Validation logic for Order form
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("OrderCtl validate() started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("orderId"))) {
			request.setAttribute("orderId", PropertyReader.getValue("error.require", "Order Id"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("orderId"))) {
			request.setAttribute("orderId", "Order Id must be numeric");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("number"))) {
			request.setAttribute("number", PropertyReader.getValue("error.require", "Mobile Number"));
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("number"))) {
			request.setAttribute("number", "Mobile No must be 10 digits");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", "Amount"));
			pass = false;
		}

		return pass;
	}

	/**
	 * Populates OrderBean from request
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("OrderCtl populateBean() started");

		OrderBean bean = new OrderBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setOrderId(DataUtility.getInt(request.getParameter("orderId")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setNumber(DataUtility.getString(request.getParameter("number")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setAmount(DataUtility.getString(request.getParameter("amount")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handles GET request (Edit / View)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.info("OrderCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));
		OrderModel model = new OrderModel();

		if (id > 0) {
			try {
				OrderBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				log.info("Order loaded with id: " + id);
			} catch (ApplicationException e) {
				log.error("Error loading order", e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles POST request (Save / Update / Cancel / Reset)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("OrderCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));
		OrderModel model = new OrderModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			OrderBean bean = (OrderBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Order added successfully", request);
				log.info("Order added with PK: " + pk);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Order Id already exists", request);

			} catch (ApplicationException e) {
				log.error("Error adding order", e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			OrderBean bean = (OrderBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Order updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Order Id already exists", request);

			} catch (ApplicationException e) {
				log.error("Error updating order", e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns Order JSP View
	 */
	@Override
	protected String getView() {
		return ORSView.ORDER_VIEW;
	}
}
