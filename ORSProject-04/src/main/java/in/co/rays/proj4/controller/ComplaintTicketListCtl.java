package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

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
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ComplaintTicketListCtl", urlPatterns = { "/ctl/ComplaintTicketListCtl" })
public class ComplaintTicketListCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(ComplaintTicketListCtl.class);

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("ComplaintTicketListCtl populateBean() called");

		ComplaintTicketBean bean = new ComplaintTicketBean();

		bean.setComplaintTicketId(DataUtility.getLong(request.getParameter("complaintTicketId")));
		bean.setIssueType(DataUtility.getString(request.getParameter("issueType")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("ComplaintTicketListCtl doGet() started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		ComplaintTicketBean bean = (ComplaintTicketBean) populateBean(request);
		ComplaintTicketModel model = new ComplaintTicketModel();

		try {

			List list = model.search(bean, pageNo, pageSize);
			List next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			log.error("ApplicationException in ComplaintTicketListCtl", e);

			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("ComplaintTicketListCtl doPost() started");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0)
				? DataUtility.getInt(PropertyReader.getValue("page.size"))
				: pageSize;

		ComplaintTicketBean bean = (ComplaintTicketBean) populateBean(request);
		ComplaintTicketModel model = new ComplaintTicketModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)
					|| OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.COMPLAINT_TICKET_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					ComplaintTicketBean deletebean = new ComplaintTicketBean();

					for (String id : ids) {

						deletebean.setId(DataUtility.getLong(id));

						model.delete(deletebean);
					}

					ServletUtility.setSuccessMessage("Complaint Ticket deleted successfully", request);

				} else {

					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.COMPLAINT_TICKET_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.COMPLAINT_TICKET_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);

			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			log.error("ApplicationException in ComplaintTicketListCtl doPost()", e);

			ServletUtility.handleException(e, request, response);
		}
	}

	@Override
	protected String getView() {

		return ORSView.COMPLAINT_TICKET_LIST_VIEW;
	}
}