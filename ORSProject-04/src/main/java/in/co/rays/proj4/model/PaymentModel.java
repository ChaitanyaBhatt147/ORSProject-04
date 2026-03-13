package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.PaymentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * PaymentModel provides CRUD and search operations for PaymentBean.
 * It interacts with the st_payment table using JDBC.
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */
public class PaymentModel {

    /**
     * Returns next primary key of st_payment table
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_payment");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            e.printStackTrace();
            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    /**
     * Add Payment
     */
    public long add(PaymentBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        PaymentBean existBean = findByPaymentCode(bean.getPaymentCode());

        if (existBean != null) {
            throw new DuplicateRecordException("Payment Code already exists");
        }

        try {

            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_payment values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setLong(1, pk);
            pstmt.setString(2, bean.getPaymentCode());
            pstmt.setLong(3, bean.getUserId());
            pstmt.setDouble(4, bean.getAmount());
            pstmt.setString(5, bean.getPaymentStatus());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();

            pstmt.close();

        } catch (CJCommunicationsException e) {
            e.printStackTrace();
            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Add rollback exception " + ex.getMessage());
            }

            e.printStackTrace();
            throw new ApplicationException("Exception in add Payment");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    /**
     * Update Payment
     */
    public void update(PaymentBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        PaymentBean existBean = findByPaymentCode(bean.getPaymentCode());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Payment Code already exists");
        }

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_payment set payment_code=?, user_id=?, amount=?, payment_status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getPaymentCode());
            pstmt.setLong(2, bean.getUserId());
            pstmt.setDouble(3, bean.getAmount());
            pstmt.setString(4, bean.getPaymentStatus());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setTimestamp(8, bean.getModifiedDatetime());
            pstmt.setLong(9, bean.getId());

            pstmt.executeUpdate();
            conn.commit();

            pstmt.close();

        } catch (CJCommunicationsException e) {
            e.printStackTrace();
            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Update rollback exception " + ex.getMessage());
            }

            e.printStackTrace();
            throw new ApplicationException("Exception in updating Payment");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Delete Payment
     */
    public void delete(PaymentBean bean) throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "delete from st_payment where id=?");

            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            e.printStackTrace();
            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Delete rollback exception " + ex.getMessage());
            }

            throw new ApplicationException("Exception in delete Payment");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Find Payment by PK
     */
    public PaymentBean findByPk(long pk) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_payment where id=?");

        PaymentBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new PaymentBean();

                bean.setId(rs.getLong(1));
                bean.setPaymentCode(rs.getString(2));
                bean.setUserId(rs.getLong(3));
                bean.setAmount(rs.getDouble(4));
                bean.setPaymentStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            e.printStackTrace();
            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Payment by PK");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * Find Payment by Payment Code
     */
    public PaymentBean findByPaymentCode(String code) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_payment where payment_code=?");

        PaymentBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            pstmt.setString(1, code);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new PaymentBean();

                bean.setId(rs.getLong(1));
                bean.setPaymentCode(rs.getString(2));
                bean.setUserId(rs.getLong(3));
                bean.setAmount(rs.getDouble(4));
                bean.setPaymentStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Payment by Code");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * List all payments
     */
    public List<PaymentBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Search Payment
     */
    public List<PaymentBean> search(PaymentBean bean, int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_payment where 1=1");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }

            if (bean.getPaymentCode() != null && bean.getPaymentCode().length() > 0) {
                sql.append(" and payment_code like '%" + bean.getPaymentCode() + "%'");
            }

            if (bean.getUserId() > 0) {
                sql.append(" and user_id = " + bean.getUserId());
            }

            if (bean.getPaymentStatus() != null && bean.getPaymentStatus().length() > 0) {
                sql.append(" and payment_status like '%" + bean.getPaymentStatus() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<PaymentBean> list = new ArrayList<PaymentBean>();

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new PaymentBean();

                bean.setId(rs.getLong(1));
                bean.setPaymentCode(rs.getString(2));
                bean.setUserId(rs.getLong(3));
                bean.setAmount(rs.getDouble(4));
                bean.setPaymentStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search Payment");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}