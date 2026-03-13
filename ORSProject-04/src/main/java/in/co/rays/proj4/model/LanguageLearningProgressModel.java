package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.LanguageLearningProgressBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * LanguageLearningProgressModel performs CRUD operations for
 * LanguageLearningProgressBean.
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */

public class LanguageLearningProgressModel {

    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "select max(id) from language_learning_progress");
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

    public long add(LanguageLearningProgressBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        try {

            pk = nextPk();

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into language_learning_progress values(?,?,?,?,?,?,?,?)");

            pstmt.setLong(1, pk);
            pstmt.setString(2, bean.getStudentName());
            pstmt.setString(3, bean.getLanguage());
            pstmt.setString(4, bean.getLevel());
            pstmt.setString(5, bean.getCompletionStatus());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());

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
                        "Exception : add rollback exception " + ex.getMessage());
            }

            throw new ApplicationException(
                    "Exception : Exception in add LanguageLearningProgress");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(LanguageLearningProgressBean bean)
            throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(

                    "update language_learning_progress set student_name=?, language=?, level=?, completion_status=?, created_by=?, modified_by=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getStudentName());
            pstmt.setString(2, bean.getLanguage());
            pstmt.setString(3, bean.getLevel());
            pstmt.setString(4, bean.getCompletionStatus());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
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
                        "Exception : Update rollback exception " + ex.getMessage());
            }

            throw new ApplicationException(
                    "Exception in updating Language Learning Progress");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(LanguageLearningProgressBean bean)
            throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "delete from language_learning_progress where id=?");

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
                        "Exception : Delete rollback exception " + ex.getMessage());
            }

            throw new ApplicationException(
                    "Exception : Exception in delete LanguageLearningProgress");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public LanguageLearningProgressBean findByPk(long pk)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer(
                "select * from language_learning_progress where id=?");

        LanguageLearningProgressBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new LanguageLearningProgressBean();

                bean.setId(rs.getLong(1));
                bean.setStudentName(rs.getString(2));
                bean.setLanguage(rs.getString(3));
                bean.setLevel(rs.getString(4));
                bean.setCompletionStatus(rs.getString(5));

                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (CJCommunicationsException e) {

            e.printStackTrace();
            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {

            throw new ApplicationException(
                    "Exception : Exception in getting record by PK");

        } finally {

            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public List<LanguageLearningProgressBean> list()
            throws ApplicationException {

        return search(null, 0, 0);
    }

    public List<LanguageLearningProgressBean> search(
            LanguageLearningProgressBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer(
                "select * from language_learning_progress where 1=1");

        if (bean != null) {

            if (bean.getStudentName() != null
                    && bean.getStudentName().length() > 0) {
                sql.append(" and student_name like '%"
                        + bean.getStudentName() + "%'");
            }

            if (bean.getLanguage() != null
                    && bean.getLanguage().length() > 0) {
                sql.append(" and language like '%"
                        + bean.getLanguage() + "%'");
            }

            if (bean.getLevel() != null
                    && bean.getLevel().length() > 0) {
                sql.append(" and level like '%"
                        + bean.getLevel() + "%'");
            }

            if (bean.getCompletionStatus() != null
                    && bean.getCompletionStatus().length() > 0) {
                sql.append(" and completion_status like '%"
                        + bean.getCompletionStatus() + "%'");
            }
        }

        if (pageSize > 0) {

            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList<LanguageLearningProgressBean> list = new ArrayList<>();

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new LanguageLearningProgressBean();

                bean.setId(rs.getLong(1));
                bean.setStudentName(rs.getString(2));
                bean.setLanguage(rs.getString(3));
                bean.setLevel(rs.getString(4));
                bean.setCompletionStatus(rs.getString(5));

                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (CJCommunicationsException e) {

            e.printStackTrace();
            throw new DatabaseServerDownException("Database Server Down!!!");

        } catch (Exception e) {

            throw new ApplicationException(
                    "Exception : Exception in search LanguageLearningProgress");

        } finally {

            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}