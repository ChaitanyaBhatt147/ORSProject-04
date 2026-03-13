package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.InternetPackageBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.InternetPackageModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * InternetPackageCtl handles Add and Update operations
 * for Internet Package Module.
 * 
 * author Chaitanya Bhatt
 */

@WebServlet(name = "InternetPackageCtl", urlPatterns = { "/ctl/InternetPackageCtl" })
public class InternetPackageCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(InternetPackageCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("InternetPackageCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("packageName"))) {
			request.setAttribute("packageName",
					PropertyReader.getValue("error.require", "Package Name"));
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

		if (DataValidator.isNull(request.getParameter("dataLimit"))) {
			request.setAttribute("dataLimit",
					PropertyReader.getValue("error.require", "Data Limit"));
			pass = false;
		}else if (DataUtility.getInt(request.getParameter("dataLimit")) <= 0) {
		    request.setAttribute("dataLimit", "Data Limit must be greater than 0");
		    pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		InternetPackageBean bean = new InternetPackageBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPackageName(DataUtility.getString(request.getParameter("packageName")));
		bean.setPrice(DataUtility.getDouble(request.getParameter("price")));
		bean.setDataLimit(DataUtility.getInt(request.getParameter("dataLimit")));

		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		InternetPackageModel model = new InternetPackageModel();

		if (id > 0) {

			try {

				InternetPackageBean bean = model.findByPk(id);
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

		String op = DataUtility.getString(request.getParameter("operation"));

		InternetPackageModel model = new InternetPackageModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			InternetPackageBean bean = (InternetPackageBean) populateBean(request);

			try {

				long pk = model.add(bean);

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Internet Package Added Successfully", request);

			} catch (ApplicationException e) {

				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			InternetPackageBean bean = (InternetPackageBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Internet Package Updated Successfully", request);

			} catch (ApplicationException e) {

				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INTERNET_PACKAGE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INTERNET_PACKAGE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.INTERNET_PACKAGE_VIEW;
	}
}