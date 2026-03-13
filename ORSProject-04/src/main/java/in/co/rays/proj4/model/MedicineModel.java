package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.MedicineBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * MedicineModel provides CRUD and search operations for MedicineBean,
 * interacting with the st_medicine table via JDBC.
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */
public class MedicineModel {

    /**
     * Returns next primary key value for st_medicine table.
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_medicine");
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
     * Adds a new medicine record.
     */
    public long add(MedicineBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        MedicineBean existBean = findByName(bean.getMedicineName());
        int pk = 0;

        if (existBean != null) {
            throw new DuplicateRecordException("Medicine already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_medicine values(?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getMedicineName());
            pstmt.setDouble(3, bean.getPrice());
            pstmt.setDate(4, new java.sql.Date(bean.getExpiryDate().getTime()));
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
                throw new ApplicationException("Rollback Exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in add Medicine");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates existing medicine record.
     */
    public void update(MedicineBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        MedicineBean existBean = findByName(bean.getMedicineName());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Medicine already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_medicine set medicine_name=?, price=?, expiry_date=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getMedicineName());
            pstmt.setDouble(2, bean.getPrice());
            pstmt.setDate(3, new java.sql.Date(bean.getExpiryDate().getTime()));
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
                throw new ApplicationException("Rollback Exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Medicine");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes medicine record.
     */
    public void delete(MedicineBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn
                    .prepareStatement("delete from st_medicine where id=?");

            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            throw new DatabaseServerDownException("Database Server Down!!!");
        } catch (Exception e) {
            throw new ApplicationException("Exception in delete Medicine");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds medicine by PK.
     */
    public MedicineBean findByPk(long pk) throws ApplicationException {

        String sql = "select * from st_medicine where id=?";
        MedicineBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new MedicineBean();
                bean.setId(rs.getLong(1));
                bean.setMedicineName(rs.getString(2));
                bean.setPrice(rs.getDouble(3));
                bean.setExpiryDate(rs.getDate(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPk Medicine");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds medicine by Name.
     */
    public MedicineBean findByName(String name) throws ApplicationException {

        String sql = "select * from st_medicine where medicine_name=?";
        MedicineBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new MedicineBean();
                bean.setId(rs.getLong(1));
                bean.setMedicineName(rs.getString(2));
                bean.setPrice(rs.getDouble(3));
                bean.setExpiryDate(rs.getDate(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByName Medicine");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Returns all medicines.
     */
    public List<MedicineBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Search with pagination.
     */
    public List<MedicineBean> search(MedicineBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_medicine where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id=" + bean.getId());
            }
            if (bean.getMedicineName() != null
                    && bean.getMedicineName().length() > 0) {
                sql.append(" and medicine_name like '%"
                        + bean.getMedicineName() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList<MedicineBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new MedicineBean();
                bean.setId(rs.getLong(1));
                bean.setMedicineName(rs.getString(2));
                bean.setPrice(rs.getDouble(3));
                bean.setExpiryDate(rs.getDate(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search Medicine");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}