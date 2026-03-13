package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TrainingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TrainingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * TrainingCtl is a controller servlet responsible for handling Training related
 * operations such as add, update, view and navigation.
 *
 * Supported operations include Save, Update, Cancel and Reset.
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */
@WebServlet(name = "TrainingCtl", urlPatterns = { "/ctl/TrainingCtl" })
public class TrainingCtl extends BaseCtl {

	/** Log4j Logger */
	private static final Logger log = Logger.getLogger(TrainingCtl.class);

	/**
	 * Validates Training form fields.
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("TrainingCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("trainingCode"))) {
			request.setAttribute("trainingCode", PropertyReader.getValue("error.require", "Training Code"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("trainingCode"))) {
			request.setAttribute("trainingCode", "Training Code should be number");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("trainingName"))) {
			request.setAttribute("trainingName", PropertyReader.getValue("error.require", "Training Name"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("trainingName"))) {
			request.setAttribute("trainingName", "Training Name not number");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("trainerName"))) {
			request.setAttribute("trainerName", PropertyReader.getValue("error.require", "Trainer Name"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("trainerName"))) {
			request.setAttribute("trainerName", "Trainer Name not number");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("trainingDate"))) {
			request.setAttribute("trainingDate", PropertyReader.getValue("error.require", "Training Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("trainingDate"))) {
			request.setAttribute("trainingDate", PropertyReader.getValue("error.date", "Training Date"));
			pass = false;
		}

		return pass;
	}

	/**
	 * Populates TrainingBean from request parameters.
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("TrainingCtl populateBean() called");

		TrainingBean bean = new TrainingBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setTrainingCode(DataUtility.getString(request.getParameter("trainingCode")));
		bean.setTrainingName(DataUtility.getString(request.getParameter("trainingName")));
		bean.setTrainerName(DataUtility.getString(request.getParameter("trainerName")));
		bean.setTrainingDate(DataUtility.getDate(request.getParameter("trainingDate")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handles HTTP GET requests.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("TrainingCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));
		TrainingModel model = new TrainingModel();

		if (id > 0) {
			try {
				TrainingBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error("Error fetching training record", e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.info("TrainingCtl doGet() completed");
	}

	/**
	 * Handles HTTP POST requests.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("TrainingCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));
		TrainingModel model = new TrainingModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			TrainingBean bean = (TrainingBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Training added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Training Code already exists", request);

			} catch (ApplicationException e) {
				log.error("Error while adding training", e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TrainingBean bean = (TrainingBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Training updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Training Code already exists", request);

			} catch (ApplicationException e) {
				log.error("Error while updating training", e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TRAINING_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TRAINING_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.info("TrainingCtl doPost() completed");
	}

	/**
	 * Returns Training view page.
	 */
	@Override
	protected String getView() {
		return ORSView.TRAINING_VIEW;
	}
}