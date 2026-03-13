package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TravelPackageBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.TravelPackageModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Controller for Travel Package List operations
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
@WebServlet(name = "TravelPackageListCtl", urlPatterns = { "/ctl/TravelPackageListCtl" })
public class TravelPackageListCtl extends BaseCtl {

    private static final Logger log =
            Logger.getLogger(TravelPackageListCtl.class);

    /**
     * Populate Bean (Search Filters)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        TravelPackageBean bean = new TravelPackageBean();

        bean.setTravelerName(
                DataUtility.getString(request.getParameter("travelerName")));
        bean.setDestination(
                DataUtility.getString(request.getParameter("destination")));

        return bean;
    }

    /**
     * GET Request
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize =
                DataUtility.getInt(PropertyReader.getValue("page.size"));

        TravelPackageBean bean =
                (TravelPackageBean) populateBean(request);

        TravelPackageModel model = new TravelPackageModel();

        try {

            List list = model.search(bean, pageNo, pageSize);
            List next = model.search(bean, pageNo + 1, pageSize);

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
            ServletUtility.handleException(e, request, response);
        }
    }

    /**
     * POST Request
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        TravelPackageBean bean =
                (TravelPackageBean) populateBean(request);

        TravelPackageModel model = new TravelPackageModel();

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
                        ORSView.TRAVEL_PACKAGE_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    TravelPackageBean deleteBean =
                            new TravelPackageBean();

                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage(
                            "Record deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.TRAVEL_PACKAGE_LIST_CTL,
                        request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.TRAVEL_PACKAGE_LIST_CTL,
                        request, response);
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
            ServletUtility.handleException(e, request, response);
        }
    }

    /**
     * View Page
     */
    @Override
    protected String getView() {
        return ORSView.TRAVEL_PACKAGE_LIST_VIEW;
    }
}