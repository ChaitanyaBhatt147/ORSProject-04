package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Error functionality controller.perform error page operation
 * 
 * @author Chaitanya Bhatt
 *
 */
@WebServlet(name = "ErrorCtl", urlPatterns = { "/ErrorCtl" })
public class ErrorCtl extends BaseCtl {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {


		// 1️⃣ container attribute (standard)
		String lastCtl = (String) request.getAttribute("javax.servlet.error.request_uri");

		// 2️⃣ fallback from FrontController
		if (lastCtl == null) {
			lastCtl = (String) request.getAttribute("lastCtl");
		}

		Throwable ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

		System.out.println("Error on controller: " + lastCtl);

		String view = getViewFromCtl(lastCtl);

		// ✅ sirf preload controllers ko list do
//		if (ex instanceof ApplicationException && isPreloadController(lastCtl)) {
//
//			ApplicationException ae = (ApplicationException) ex;
//
//			if (ae.getDemoList() != null) {
//				ServletUtility.setList(ae.getDemoList(), request);
//
//			}
//		}
		ServletUtility.setErrorMessage("Database server down!!!", request);
		// ===== LIST PAGE SAFETY =====
		if (lastCtl != null && lastCtl.contains("ListCtl")) {

		    if (ServletUtility.getList(request) == null) {
		        ServletUtility.setList(new java.util.ArrayList(), request);
		    }

		    request.setAttribute("pageNo", 1);
		    request.setAttribute("pageSize", 10);
		    request.setAttribute("nextListSize", 0);
		}


		// ✅ ALWAYS JSP — NEVER controller
		ServletUtility.forward(view, request, response);

	}

	private String getViewFromCtl(String ctl) {

		if (ctl == null)
			return ORSView.ERROR_VIEW;

		// ===== MASTER FORMS =====
		if (ctl.endsWith(ORSView.USER_CTL))
			return ORSView.USER_VIEW;

		if (ctl.endsWith(ORSView.ROLE_CTL))
			return ORSView.ROLE_VIEW;

		if (ctl.endsWith(ORSView.COLLEGE_CTL))
			return ORSView.COLLEGE_VIEW;

		if (ctl.endsWith(ORSView.STUDENT_CTL))
			return ORSView.STUDENT_VIEW;

		if (ctl.endsWith(ORSView.FACULTY_CTL))
			return ORSView.FACULTY_VIEW;

		if (ctl.endsWith(ORSView.COURSE_CTL))
			return ORSView.COURSE_VIEW;

		if (ctl.endsWith(ORSView.SUBJECT_CTL))
			return ORSView.SUBJECT_VIEW;

		if (ctl.endsWith(ORSView.TIMETABLE_CTL))
			return ORSView.TIMETABLE_VIEW;

		if (ctl.endsWith(ORSView.HOSTEL_CTL))
			return ORSView.HOSTEL_VIEW;

		if (ctl.endsWith(ORSView.MARKSHEET_CTL))
			return ORSView.MARKSHEET_VIEW;

		if (ctl.endsWith(ORSView.GET_MARKSHEET_CTL))
			return ORSView.GET_MARKSHEET_VIEW;

		if (ctl.endsWith(ORSView.CHANGE_PASSWORD_CTL))
			return ORSView.CHANGE_PASSWORD_VIEW;

		if (ctl.endsWith(ORSView.MY_PROFILE_CTL))
			return ORSView.MY_PROFILE_VIEW;

		if (ctl.endsWith(ORSView.FORGET_PASSWORD_CTL))
			return ORSView.FORGET_PASSWORD_VIEW;

		if (ctl.endsWith(ORSView.LOGIN_CTL))
			return ORSView.LOGIN_VIEW;

		if (ctl.endsWith(ORSView.WELCOME_CTL))
			return ORSView.WELCOME_VIEW;

		if (ctl.endsWith(ORSView.USER_REGISTRATION_CTL))
			return ORSView.USER_REGISTRATION_VIEW;

		// ===== LIST PAGES =====
		if (ctl.endsWith(ORSView.USER_LIST_CTL))
			return ORSView.USER_LIST_VIEW;

		if (ctl.endsWith(ORSView.ROLE_LIST_CTL))
			return ORSView.ROLE_LIST_VIEW;

		if (ctl.endsWith(ORSView.COLLEGE_LIST_CTL))
			return ORSView.COLLEGE_LIST_VIEW;

		if (ctl.endsWith(ORSView.STUDENT_LIST_CTL))
			return ORSView.STUDENT_LIST_VIEW;

		if (ctl.endsWith(ORSView.FACULTY_LIST_CTL))
			return ORSView.FACULTY_LIST_VIEW;

		if (ctl.endsWith(ORSView.COURSE_LIST_CTL))
			return ORSView.COURSE_LIST_VIEW;

		if (ctl.endsWith(ORSView.SUBJECT_LIST_CTL))
			return ORSView.SUBJECT_LIST_VIEW;

		if (ctl.endsWith(ORSView.TIMETABLE_LIST_CTL))
			return ORSView.TIMETABLE_LIST_VIEW;

		if (ctl.endsWith(ORSView.HOSTEL_LIST_CTL))
			return ORSView.HOSTEL_LIST_VIEW;

		if (ctl.endsWith(ORSView.MARKSHEET_LIST_CTL))
			return ORSView.MARKSHEET_LIST_VIEW;

		if (ctl.endsWith(ORSView.MARKSHEET_MERIT_LIST_CTL))
			return ORSView.MARKSHEET_MERIT_LIST_VIEW;

		return ORSView.ERROR_VIEW;
	}

//	private boolean isPreloadController(String ctl) {
//
//		if (ctl == null)
//			return false;
//
//		return ctl.endsWith(ORSView.USER_CTL) || ctl.endsWith(ORSView.ROLE_CTL) || ctl.endsWith(ORSView.COLLEGE_CTL)
//				|| ctl.endsWith(ORSView.COURSE_CTL) || ctl.endsWith(ORSView.FACULTY_CTL);
//	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
