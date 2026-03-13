package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.PlanBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * PlanModel provides CRUD and search operations for PlanBean.
 * It interacts with the st_plan table via JDBC.
 * 
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class PlanModel {

    /**
     * Returns next primary key value.
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_plan");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {
            throw new DatabaseException("Exception : Exception in getting PK");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    /**
     * Adds a new Plan.
     */
    public long add(PlanBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        PlanBean existBean = findByName(bean.getPlanName());

        int pk = 0;

        if (existBean != null) {
            throw new DuplicateRecordException("Plan already exists");
        }

        try {

            pk = nextPk();

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_plan values(?,?,?,?,?,?,?,?)");

            pstmt.setLong(1, pk);
            pstmt.setString(2, bean.getPlanName());
            pstmt.setDouble(3, bean.getPrice());
            pstmt.setInt(4, bean.getValidityDays());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setTimestamp(8, bean.getModifiedDatetime());

            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (CJCommunicationsException e) {

            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : add rollback exception");
            }

            throw new ApplicationException("Exception : Exception in add Plan");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    /**
     * Updates Plan
     */
    public void update(PlanBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        PlanBean existBean = findByName(bean.getPlanName());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Plan Name already exists");
        }

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_plan set plan_name=?, price=?, validity_days=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getPlanName());
            pstmt.setDouble(2, bean.getPrice());
            pstmt.setInt(3, bean.getValidityDays());
            pstmt.setString(4, bean.getCreatedBy());
            pstmt.setString(5, bean.getModifiedBy());
            pstmt.setTimestamp(6, bean.getCreatedDatetime());
            pstmt.setTimestamp(7, bean.getModifiedDatetime());
            pstmt.setLong(8, bean.getId());

            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (CJCommunicationsException e) {

            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : rollback exception");
            }

            throw new ApplicationException("Exception in updating Plan");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes Plan
     */
    public void delete(PlanBean bean) throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "delete from st_plan where id=?");

            pstmt.setLong(1, bean.getId());

            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (CJCommunicationsException e) {

            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : rollback exception");
            }

            throw new ApplicationException("Exception : Exception in delete Plan");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Find Plan by PK
     */
    public PlanBean findByPk(long pk) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_plan where id=?");

        PlanBean bean = null;

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new PlanBean();

                bean.setId(rs.getLong(1));
                bean.setPlanName(rs.getString(2));
                bean.setPrice(rs.getDouble(3));
                bean.setValidityDays(rs.getInt(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));

            }

            rs.close();
            pstmt.close();

        } catch (CJCommunicationsException e) {

            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {

            throw new ApplicationException("Exception in getting Plan by pk");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * Find Plan by Name
     */
    public PlanBean findByName(String name) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_plan where plan_name=?");

        PlanBean bean = null;

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new PlanBean();

                bean.setId(rs.getLong(1));
                bean.setPlanName(rs.getString(2));
                bean.setPrice(rs.getDouble(3));
                bean.setValidityDays(rs.getInt(4));

            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {

            throw new ApplicationException("Exception in getting Plan by Name");

        } finally {

            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * Search Plan
     */
    public List<PlanBean> search(PlanBean bean, int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_plan where 1=1");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id=" + bean.getId());
            }

            if (bean.getPlanName() != null && bean.getPlanName().length() > 0) {
                sql.append(" and plan_name like '%" + bean.getPlanName() + "%'");
            }
        }

        if (pageSize > 0) {

            pageNo = (pageNo - 1) * pageSize;

            sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList<PlanBean> list = new ArrayList<>();

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new PlanBean();

                bean.setId(rs.getLong(1));
                bean.setPlanName(rs.getString(2));
                bean.setPrice(rs.getDouble(3));
                bean.setValidityDays(rs.getInt(4));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {

            throw new ApplicationException("Exception in search Plan");

        } finally {

            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}