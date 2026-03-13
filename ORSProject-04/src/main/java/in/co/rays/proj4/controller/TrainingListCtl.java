package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TrainingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.TrainingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * TrainingListCtl handles listing, searching, pagination and bulk actions
 * for Training entities.
 *
 * Supported operations include Search, Next, Previous, New,
 * Delete, Reset and Back.
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */
@WebServlet(name = "TrainingListCtl", urlPatterns = { "/ctl/TrainingListCtl" })
public class TrainingListCtl extends BaseCtl {

    /** Log4j Logger */
    private static final Logger log = Logger.getLogger(TrainingListCtl.class);

    /**
     * Populates TrainingBean from request parameters for search.
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("TrainingListCtl populateBean() called");

        TrainingBean bean = new TrainingBean();

        bean.setTrainingCode(DataUtility.getString(request.getParameter("trainingCode")));
        bean.setTrainingName(DataUtility.getString(request.getParameter("trainingName")));
        bean.setTrainerName(DataUtility.getString(request.getParameter("trainerName")));

        return bean;
    }

    /**
     * Handles HTTP GET requests.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("TrainingListCtl doGet() started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        TrainingBean bean = (TrainingBean) populateBean(request);
        TrainingModel model = new TrainingModel();

        try {
            List<TrainingBean> list = model.search(bean, pageNo, pageSize);
            List<TrainingBean> next = model.search(bean, pageNo + 1, pageSize);

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
            log.error("Exception in TrainingListCtl doGet()", e);
            ServletUtility.handleException(e, request, response);
            return;
        }

        log.info("TrainingListCtl doGet() completed");
    }

    /**
     * Handles HTTP POST requests.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("TrainingListCtl doPost() started");

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        TrainingBean bean = (TrainingBean) populateBean(request);
        TrainingModel model = new TrainingModel();

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

                ServletUtility.redirect(ORSView.TRAINING_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {
                    TrainingBean deleteBean = new TrainingBean();
                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                        ServletUtility.setSuccessMessage(
                                "Training deleted successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)
                    || OP_BACK.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.TRAINING_LIST_CTL, request, response);
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
            log.error("Exception in TrainingListCtl doPost()", e);
            ServletUtility.handleException(e, request, response);
            return;
        }

        log.info("TrainingListCtl doPost() completed");
    }

    /**
     * Returns Training list JSP view.
     */
    @Override
    protected String getView() {
        return ORSView.TRAINING_LIST_VIEW;
    }
}