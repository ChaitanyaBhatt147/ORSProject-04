package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoomBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoomModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "RoomCtl", urlPatterns = { "/ctl/RoomCtl" })
public class RoomCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(RoomCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("roomCode"))) {
			request.setAttribute("roomCode",
					PropertyReader.getValue("error.require", "Room Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("roomName"))) {
			request.setAttribute("roomName",
					PropertyReader.getValue("error.require", "Room Name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("roomName"))) {
			request.setAttribute("roomName","Should be name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("capacity"))) {
			request.setAttribute("capacity",
					PropertyReader.getValue("error.require", "Capacity"));
			pass = false;

		} else if (!DataValidator.isInteger(request.getParameter("capacity"))) {
			request.setAttribute("capacity", "Capacity must be number");
			pass = false;

		} else if (DataUtility.getInt(request.getParameter("capacity")) <= 0) {
			request.setAttribute("capacity", "Capacity should be greater than 0");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("roomStatus"))) {
			request.setAttribute("roomStatus",
					PropertyReader.getValue("error.require", "Room Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		RoomBean bean = new RoomBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRoomCode(DataUtility.getString(request.getParameter("roomCode")));
		bean.setRoomName(DataUtility.getString(request.getParameter("roomName")));
		bean.setCapacity(DataUtility.getInt(request.getParameter("capacity")));
		bean.setRoomStatus(DataUtility.getString(request.getParameter("roomStatus")));

		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		RoomModel model = new RoomModel();

		if (id > 0) {
			try {

				RoomBean bean = model.findByPk(id);

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

		RoomModel model = new RoomModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			RoomBean bean = (RoomBean) populateBean(request);

			try {

				long pk = model.add(bean);

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Room added successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Room Code already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			RoomBean bean = (RoomBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Room updated successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Room Code already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ROOM_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ROOM_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.ROOM_VIEW;
	}
}