package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SeminarBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.SeminarModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * SeminarListCtl handles listing, searching, pagination and delete operations
 * for Seminar entities.
 * 
 * @author Chaitanya Bhatt
 * @version 1.0
 */
@WebServlet(name = "SeminarListCtl", urlPatterns = { "/ctl/SeminarListCtl" })
public class SeminarListCtl extends BaseCtl {

    private static final Logger log = Logger.getLogger(SeminarListCtl.class);

    /**
     * Populate search bean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("SeminarListCtl populateBean() called");

        SeminarBean bean = new SeminarBean();

        bean.setSeminarTitle(
                DataUtility.getString(request.getParameter("seminarTitle")));
        bean.setSpeaker(
                DataUtility.getString(request.getParameter("speaker")));

        return bean;
    }

    /**
     * Initial Load
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        log.info("SeminarListCtl doGet() started");

        int pageNo = 1;
        int pageSize =
                DataUtility.getInt(PropertyReader.getValue("page.size"));

        SeminarBean bean = (SeminarBean) populateBean(request);
        SeminarModel model = new SeminarModel();

        try {

            List<SeminarBean> list =
                    model.search(bean, pageNo, pageSize);

            List<SeminarBean> next =
                    model.search(bean, pageNo + 1, pageSize);

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
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    /**
     * Handle Operations
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        log.info("SeminarListCtl doPost() started");

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        SeminarBean bean = (SeminarBean) populateBean(request);
        SeminarModel model = new SeminarModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op)
                    || OP_NEXT.equalsIgnoreCase(op)
                    || OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.SEMINAR_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    SeminarBean deleteBean = new SeminarBean();

                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage(
                            "Seminar deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)
                    || OP_BACK.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.SEMINAR_LIST_CTL, request, response);
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
            log.error(e);
            ServletUtility.handleException(e, request, response);
        }
    }

    /**
     * Return View
     */
    @Override
    protected String getView() {
        return ORSView.SEMINAR_LIST_VIEW;
    }
}