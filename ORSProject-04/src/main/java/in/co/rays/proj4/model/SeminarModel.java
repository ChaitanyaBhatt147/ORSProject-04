package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.SeminarBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * SeminarModel provides CRUD and search operations for SeminarBean.
 * It interacts with st_seminar table.
 * 
 * @author Chaitanya Bhatt
 * @version 1.0
 */
public class SeminarModel {

    /**
     * Get next primary key
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select max(id) from st_seminar");

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
            throw new DatabaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Add Seminar
     */
    public long add(SeminarBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_seminar values(?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getSeminarTitle());
            pstmt.setDate(3,
                    new java.sql.Date(bean.getSeminarDate().getTime()));
            pstmt.setString(4, bean.getSpeaker());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setTimestamp(8, bean.getModifiedDatetime());

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
                throw new ApplicationException(
                        "Exception : add rollback exception");
            }
            throw new ApplicationException(
                    "Exception : Exception in add Seminar");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Update Seminar
     */
    public void update(SeminarBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_seminar set seminar_title=?, seminar_date=?, speaker=?, "
                            + "created_by=?, modified_by=?, created_datetime=?, "
                            + "modified_datetime=? where id=?");

            pstmt.setString(1, bean.getSeminarTitle());
            pstmt.setDate(2,
                    new java.sql.Date(bean.getSeminarDate().getTime()));
            pstmt.setString(3, bean.getSpeaker());
            pstmt.setString(4, bean.getCreatedBy());
            pstmt.setString(5, bean.getModifiedBy());
            pstmt.setTimestamp(6, bean.getCreatedDatetime());
            pstmt.setTimestamp(7, bean.getModifiedDatetime());
            pstmt.setLong(8, bean.getId());

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
                throw new ApplicationException(
                        "Exception : update rollback exception");
            }
            throw new ApplicationException(
                    "Exception in updating Seminar");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Delete Seminar
     */
    public void delete(SeminarBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_seminar where id=?");

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
                throw new ApplicationException(
                        "Exception : delete rollback exception");
            }
            throw new ApplicationException(
                    "Exception : Exception in delete Seminar");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Find Seminar by PK
     */
    public SeminarBean findByPk(long pk) throws ApplicationException {

        SeminarBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(
                            "select * from st_seminar where id=?");

            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new SeminarBean();
                bean.setId(rs.getLong(1));
                bean.setSeminarTitle(rs.getString(2));
                bean.setSeminarDate(rs.getDate(3));
                bean.setSpeaker(rs.getString(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            e.printStackTrace();
            throw new DatabaseServerDownException("Database Server Down!!!");
        } catch (Exception e) {
            throw new ApplicationException(
                    "Exception : Exception in getting Seminar by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * List All Seminars
     */
    public List<SeminarBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Search Seminar
     */
    public List<SeminarBean> search(SeminarBean bean,
            int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql =
                new StringBuffer("select * from st_seminar where 1=1");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id=" + bean.getId());
            }

            if (bean.getSeminarTitle() != null
                    && bean.getSeminarTitle().length() > 0) {
                sql.append(" and seminar_title like '%"
                        + bean.getSeminarTitle() + "%'");
            }

            if (bean.getSpeaker() != null
                    && bean.getSpeaker().length() > 0) {
                sql.append(" and speaker like '%"
                        + bean.getSpeaker() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList<SeminarBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new SeminarBean();

                bean.setId(rs.getLong(1));
                bean.setSeminarTitle(rs.getString(2));
                bean.setSeminarDate(rs.getDate(3));
                bean.setSpeaker(rs.getString(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            e.printStackTrace();
            throw new DatabaseServerDownException("Database Server Down!!!");
        } catch (Exception e) {
            throw new ApplicationException(
                    "Exception : Exception in search Seminar");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }
}