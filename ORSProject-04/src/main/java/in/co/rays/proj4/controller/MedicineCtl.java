package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MedicineBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MedicineModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * MedicineCtl handles add, update, view operations for Medicine.
 * 
 * @author Chaitanya Bhatt
 * @version 1.0
 */
@WebServlet(name = "MedicineCtl", urlPatterns = { "/ctl/MedicineCtl" })
public class MedicineCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(MedicineCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("MedicineCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("medicineName"))) {
			request.setAttribute("medicineName",
					PropertyReader.getValue("error.require", "Medicine Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price",
					PropertyReader.getValue("error.require", "Price"));
			pass = false;
		} else if (DataUtility.getDouble(request.getParameter("price")) <= 0) {
			request.setAttribute("price", "Price must be greater than 0");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("expiryDate"))) {
			request.setAttribute("expiryDate",
					PropertyReader.getValue("error.require", "Expiry Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("expiryDate"))) {
			request.setAttribute("expiryDate",
					PropertyReader.getValue("error.date", "Expiry Date"));
			pass = false;
		} else {
			Date expiry = DataUtility.getDate(request.getParameter("expiryDate"));
			if (expiry.before(new Date())) {
				request.setAttribute("expiryDate", "Expiry Date must be future date");
				pass = false;
			}
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("MedicineCtl populateBean() called");

		MedicineBean bean = new MedicineBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setMedicineName(DataUtility.getString(request.getParameter("medicineName")));
		bean.setPrice(DataUtility.getDouble(request.getParameter("price")));
		bean.setExpiryDate(DataUtility.getDate(request.getParameter("expiryDate")));

		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		MedicineModel model = new MedicineModel();

		if (id > 0) {
			try {
				MedicineBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		MedicineModel model = new MedicineModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			MedicineBean bean = (MedicineBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Medicine added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Medicine already exists", request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			MedicineBean bean = (MedicineBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Medicine updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Medicine already exists", request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MEDICINE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MEDICINE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.MEDICINE_VIEW;
	}
}