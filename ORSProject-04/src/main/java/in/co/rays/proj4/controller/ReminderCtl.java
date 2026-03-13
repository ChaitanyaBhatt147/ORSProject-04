package in.co.rays.proj4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ReminderBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ReminderModel;
import in.co.rays.proj4.util.*;

@WebServlet(name = "ReminderCtl", urlPatterns = { "/ctl/ReminderCtl" })
public class ReminderCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(ReminderCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("reminderCode"))) {
			request.setAttribute("reminderCode",
					PropertyReader.getValue("error.require", "Reminder Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("reminderTitle"))) {
			request.setAttribute("reminderTitle",
					PropertyReader.getValue("error.require", "Reminder Title"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("reminderDate"))) {
			request.setAttribute("reminderDate",
					PropertyReader.getValue("error.require", "Reminder Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("reminderStatus"))) {
			request.setAttribute("reminderStatus",
					PropertyReader.getValue("error.require", "Reminder Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		ReminderBean bean = new ReminderBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setReminderCode(DataUtility.getString(request.getParameter("reminderCode")));
		bean.setReminderTitle(DataUtility.getString(request.getParameter("reminderTitle")));
		bean.setReminderDate(
				DataUtility.getTimestamp(request.getParameter("reminderDate")));
		bean.setReminderStatus(
				DataUtility.getString(request.getParameter("reminderStatus")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		ReminderModel model = new ReminderModel();

		if (id > 0) {
			try {
				ReminderBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		ReminderModel model = new ReminderModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			ReminderBean bean = (ReminderBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(
						"Reminder added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(
						"Reminder Code already exists", request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			ReminderBean bean = (ReminderBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(
						"Reminder updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(
						"Reminder Code already exists", request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.REMINDER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.REMINDER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.REMINDER_VIEW;
	}
}