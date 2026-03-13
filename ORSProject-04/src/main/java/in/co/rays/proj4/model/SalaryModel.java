package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.SalaryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * SalaryModel provides CRUD operations for SalaryBean.
 * 
 * author Chaitanya Bhatt
 */
public class SalaryModel {

    /**
     * Get next PK
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select max(id) from st_salary");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            throw new DatabaseServerDownException("Database Server Down!!!");
        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    /**
     * Add Salary
     */
    public long add(SalaryBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        SalaryBean existBean =
                findBySalarySlipCode(bean.getSalarySlipCode());

        if (existBean != null) {
            throw new DuplicateRecordException(
                    "Salary Slip Code already exists");
        }

        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_salary values(?,?,?,?,?,?,?,?,?,?)");

            pstmt.setLong(1, pk);
            pstmt.setString(2, bean.getSalarySlipCode());
            pstmt.setString(3, bean.getEmployeeName());
            pstmt.setDouble(4, bean.getBasicSalary());
            pstmt.setDouble(5, bean.getBonus());
            pstmt.setDate(6,
                    new java.sql.Date(bean.getSalaryDate().getTime()));
            pstmt.setString(7, bean.getCreatedBy());
            pstmt.setString(8, bean.getModifiedBy());
            pstmt.setTimestamp(9, bean.getCreatedDatetime());
            pstmt.setTimestamp(10, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            throw new DatabaseServerDownException("Database Server Down!!!");
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception");
            }
            throw new ApplicationException("Exception in add Salary");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    /**
     * Update Salary
     */
    public void update(SalaryBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        SalaryBean existBean =
                findBySalarySlipCode(bean.getSalarySlipCode());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException(
                    "Salary Slip Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_salary set salary_slip_code=?, employee_name=?, "
                    + "basic_salary=?, bonus=?, salary_date=?, "
                    + "created_by=?, modified_by=?, created_datetime=?, "
                    + "modified_datetime=? where id=?");

            pstmt.setString(1, bean.getSalarySlipCode());
            pstmt.setString(2, bean.getEmployeeName());
            pstmt.setDouble(3, bean.getBasicSalary());
            pstmt.setDouble(4, bean.getBonus());
            pstmt.setDate(5,
                    new java.sql.Date(bean.getSalaryDate().getTime()));
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());
            pstmt.setLong(10, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            throw new DatabaseServerDownException("Database Server Down!!!");
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception");
            }
            throw new ApplicationException("Exception in update Salary");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Delete Salary
     */
    public void delete(SalaryBean bean)
            throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement(
                            "delete from st_salary where id=?");

            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            throw new DatabaseServerDownException("Database Server Down!!!");
        } catch (Exception e) {
            throw new ApplicationException("Exception in delete Salary");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Find by PK
     */
    public SalaryBean findByPk(long pk)
            throws ApplicationException {

        SalaryBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement(
                            "select * from st_salary where id=?");

            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = populateBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException(
                    "Exception in findByPk Salary");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * Find by Salary Slip Code (UNIQUE)
     */
    public SalaryBean findBySalarySlipCode(String code)
            throws ApplicationException {

        SalaryBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement(
                            "select * from st_salary where salary_slip_code=?");

            pstmt.setString(1, code);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = populateBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException(
                    "Exception in findBySalarySlipCode");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * List
     */
    public List<SalaryBean> list()
            throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Search
     */
    public List<SalaryBean> search(
            SalaryBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql =
                new StringBuffer("select * from st_salary where 1=1");

        if (bean != null) {

            if (bean.getEmployeeName() != null &&
                    bean.getEmployeeName().length() > 0) {
                sql.append(" and employee_name like '%"
                        + bean.getEmployeeName() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList<SalaryBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(populateBean(rs));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException(
                    "Exception in search Salary");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    /**
     * Populate Bean
     */
    private SalaryBean populateBean(ResultSet rs)
            throws Exception {

        SalaryBean bean = new SalaryBean();

        bean.setId(rs.getLong(1));
        bean.setSalarySlipCode(rs.getString(2));
        bean.setEmployeeName(rs.getString(3));
        bean.setBasicSalary(rs.getDouble(4));
        bean.setBonus(rs.getDouble(5));
        bean.setSalaryDate(rs.getDate(6));
        bean.setCreatedBy(rs.getString(7));
        bean.setModifiedBy(rs.getString(8));
        bean.setCreatedDatetime(rs.getTimestamp(9));
        bean.setModifiedDatetime(rs.getTimestamp(10));

        return bean;
    }
}