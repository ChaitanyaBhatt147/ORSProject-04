package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ReminderBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.ReminderModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * ReminderListCtl handles listing, searching, pagination and bulk operations
 * for Reminder entities.
 *
 * Supported operations:
 * Search, Next, Previous, New, Delete, Reset, Back
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */
@WebServlet(name = "ReminderListCtl", urlPatterns = { "/ctl/ReminderListCtl" })
public class ReminderListCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(ReminderListCtl.class);

	/**
	 * Populate ReminderBean for search
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("ReminderListCtl populateBean() called");

		ReminderBean bean = new ReminderBean();

		bean.setReminderCode(
				DataUtility.getString(request.getParameter("reminderCode")));
		bean.setReminderTitle(
				DataUtility.getString(request.getParameter("reminderTitle")));

		return bean;
	}

	/**
	 * Handles GET request (initial list load)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		log.info("ReminderListCtl doGet() started");

		int pageNo = 1;
		int pageSize =
				DataUtility.getInt(PropertyReader.getValue("page.size"));

		ReminderBean bean = (ReminderBean) populateBean(request);
		ReminderModel model = new ReminderModel();

		try {

			List<ReminderBean> list =
					model.search(bean, pageNo, pageSize);
			List<ReminderBean> next =
					model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage(
						"No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error("Exception in ReminderListCtl doGet()", e);
			ServletUtility.handleException(e, request, response);
		}
	}

	/**
	 * Handles POST request
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		log.info("ReminderListCtl doPost() started");

		List list = null;
		List next = null;

		int pageNo =
				DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize =
				DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0)
				? DataUtility.getInt(
						PropertyReader.getValue("page.size"))
				: pageSize;

		ReminderBean bean =
				(ReminderBean) populateBean(request);
		ReminderModel model = new ReminderModel();

		String op =
				DataUtility.getString(
						request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op)
					|| OP_NEXT.equalsIgnoreCase(op)
					|| OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op)
						&& pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(
						ORSView.REMINDER_CTL,
						request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {
					for (String id : ids) {
						ReminderBean deleteBean =
								new ReminderBean();
						deleteBean.setId(
								DataUtility.getLong(id));
						model.delete(deleteBean);
					}
					ServletUtility.setSuccessMessage(
							"Reminder deleted successfully",
							request);
				} else {
					ServletUtility.setErrorMessage(
							"Select at least one record",
							request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)
					|| OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(
						ORSView.REMINDER_LIST_CTL,
						request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage(
						"No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error("Exception in ReminderListCtl doPost()", e);
			ServletUtility.handleException(e, request, response);
		}
	}

	/**
	 * Returns Reminder List JSP
	 */
	@Override
	protected String getView() {
		return ORSView.REMINDER_LIST_VIEW;
	}
}