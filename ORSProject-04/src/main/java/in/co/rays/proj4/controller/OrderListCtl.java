package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.OrderBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.OrderModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * OrderListCtl handles listing, searching, pagination and bulk operations
 * for Order entities.
 * 
 * Supported operations:
 * Search, Next, Previous, New, Delete, Reset, Back
 * 
 * @author Chaitanya Bhatt
 * @version 1.0
 */
@WebServlet(name = "OrderListCtl", urlPatterns = { "/ctl/OrderListCtl" })
public class OrderListCtl extends BaseCtl {

    private static final Logger log = Logger.getLogger(OrderListCtl.class);

    /**
     * Populate OrderBean for search
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("OrderListCtl populateBean() called");

        OrderBean bean = new OrderBean();

        bean.setOrderId(DataUtility.getInt(request.getParameter("orderId")));
//        bean.setOrderId(null);
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setNumber(DataUtility.getString(request.getParameter("number")));

        return bean;
    }

    /**
     * Handles GET request (initial list load)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("in do get of orderList");
        log.info("OrderListCtl doGet() started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        OrderBean bean = (OrderBean) populateBean(request);
        OrderModel model = new OrderModel();
        System.out.println("in do get of orderList");

        try {

            List<OrderBean> list = model.search(bean, pageNo, pageSize);
            List<OrderBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);
            System.out.println("in do get of orderList");

        } catch (ApplicationException e) {
            log.error("Exception in OrderListCtl doGet()", e);
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
            System.out.println("in do get of orderList");
        }
        System.out.println("in do get of orderList");
    }

    /**
     * Handles POST request (search, pagination, delete etc.)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("OrderListCtl doPost() started");

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        OrderBean bean = (OrderBean) populateBean(request);
        OrderModel model = new OrderModel();

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

                ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {
                    for (String id : ids) {
                        OrderBean deleteBean = new OrderBean();
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }
                    ServletUtility.setSuccessMessage("Order deleted successfully", request);
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
                return;
            }

            list = model.search(bean, pageNo, pageSize);
            next = model.search(bean, pageNo + 1, pageSize);

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
            log.error("Exception in OrderListCtl doPost()", e);
            ServletUtility.handleException(e, request, response);
        }
    }

    /**
     * Returns Order List JSP
     */
    @Override
    protected String getView() {
        return ORSView.ORDER_LIST_VIEW;
    }
}
