package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ComplaintTicketBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.ComplaintTicketModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ComplaintTicketCtl", urlPatterns = { "/ctl/ComplaintTicketCtl" })
public class ComplaintTicketCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(ComplaintTicketCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("ComplaintTicketCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("complaintTicketId"))) {

			request.setAttribute("complaintTicketId",
					PropertyReader.getValue("error.require", "Complaint Ticket ID"));
			pass = false;

		} else if (!DataValidator.isLong(request.getParameter("complaintTicketId"))) {

			request.setAttribute("complaintTicketId",
					"Complaint Ticket ID must be a number");
			pass = false;

		} else if (DataUtility.getLong(request.getParameter("complaintTicketId")) <= 0) {

			request.setAttribute("complaintTicketId",
					"Complaint Ticket ID must be greater than 0");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("issueType"))) {
			request.setAttribute("issueType",
					PropertyReader.getValue("error.require", "Issue Type"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("issueType"))) {
			request.setAttribute("issueType", "Shoule not be number");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("createdDate"))) {
			request.setAttribute("createdDate",
					PropertyReader.getValue("error.require", "Created Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("createdDate"))) {
			request.setAttribute("createdDate",
					PropertyReader.getValue("error.date", "Created Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status",
					PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("ComplaintTicketCtl populateBean() called");

		ComplaintTicketBean bean = new ComplaintTicketBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setComplaintTicketId(DataUtility.getLong(request.getParameter("complaintTicketId")));
		bean.setIssueType(DataUtility.getString(request.getParameter("issueType")));
		bean.setCreatedDate(DataUtility.getDate(request.getParameter("createdDate")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("ComplaintTicketCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));

		ComplaintTicketModel model = new ComplaintTicketModel();

		if (id > 0) {

			try {

				ComplaintTicketBean bean = model.findByPk(id);

				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				log.error("Error fetching complaint ticket", e);

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("ComplaintTicketCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		ComplaintTicketModel model = new ComplaintTicketModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			ComplaintTicketBean bean = (ComplaintTicketBean) populateBean(request);

			try {

				long pk = model.add(bean);

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Complaint Ticket added successfully", request);

			} catch (ApplicationException e) {

				log.error("Error adding complaint ticket", e);
				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			ComplaintTicketBean bean = (ComplaintTicketBean) populateBean(request);

			try {

				if (id > 0) {

					model.update(bean);
				}

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Complaint Ticket updated successfully", request);

			} catch (ApplicationException e) {

				log.error("Error updating complaint ticket", e);
				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COMPLAINT_TICKET_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COMPLAINT_TICKET_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.COMPLAINT_TICKET_VIEW;
	}
}