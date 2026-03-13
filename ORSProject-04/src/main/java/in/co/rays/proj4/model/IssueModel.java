package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.IssueBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * IssueModel provides CRUD and search operations for IssueBean,
 * interacting with the {@code st_issue} table via JDBC.
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */
public class IssueModel {

	/**
	 * Returns next primary key value for st_issue table.
	 */
	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_issue");
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
	 * Add new Issue
	 */
	public long add(IssueBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		IssueBean existBean = findByIssueCode(bean.getIssueCode());
		if (existBean != null) {
			throw new DuplicateRecordException("Issue Code already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_issue values(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getIssueCode());
			pstmt.setString(3, bean.getStudentName());
			pstmt.setDate(4, new java.sql.Date(bean.getIssueDate().getTime()));
			pstmt.setDate(5, new java.sql.Date(bean.getReturnDate().getTime()));
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (CJCommunicationsException e) {
			throw new DatabaseServerDownException("Database Server Down!!!");
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception");
			}
			throw new ApplicationException("Exception in add Issue");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Update Issue
	 */
	public void update(IssueBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		IssueBean existBean = findByIssueCode(bean.getIssueCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Issue Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_issue set issue_code=?, student_name=?, issue_date=?, return_date=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getIssueCode());
			pstmt.setString(2, bean.getStudentName());
			pstmt.setDate(3, new java.sql.Date(bean.getIssueDate().getTime()));
			pstmt.setDate(4, new java.sql.Date(bean.getReturnDate().getTime()));
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (CJCommunicationsException e) {
			throw new DatabaseServerDownException("Database Server Down!!!");
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception");
			}
			throw new ApplicationException("Exception in update Issue");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Delete Issue
	 */
	public void delete(IssueBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"delete from st_issue where id=?");
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
				throw new ApplicationException("Delete rollback exception");
			}
			throw new ApplicationException("Exception in delete Issue");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Find by PK
	 */
	public IssueBean findByPk(long pk) throws ApplicationException {

		IssueBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"select * from st_issue where id=?");
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new IssueBean();
				bean.setId(rs.getLong(1));
				bean.setIssueCode(rs.getString(2));
				bean.setStudentName(rs.getString(3));
				bean.setIssueDate(rs.getDate(4));
				bean.setReturnDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
			pstmt.close();

		} catch (CJCommunicationsException e) {
			throw new DatabaseServerDownException("Database Server Down!!!");
		} catch (Exception e) {
			throw new ApplicationException("Exception in find Issue by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Find by Issue Code
	 */
	public IssueBean findByIssueCode(String issueCode) throws ApplicationException {

		IssueBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"select * from st_issue where issue_code=?");
			pstmt.setString(1, issueCode);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new IssueBean();
				bean.setId(rs.getLong(1));
				bean.setIssueCode(rs.getString(2));
				bean.setStudentName(rs.getString(3));
				bean.setIssueDate(rs.getDate(4));
				bean.setReturnDate(rs.getDate(5));
			}
			rs.close();
			pstmt.close();

		} catch (CJCommunicationsException e) {
			throw new DatabaseServerDownException("Database Server Down!!!");
		} catch (Exception e) {
			throw new ApplicationException("Exception in find Issue by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * List all Issues
	 */
	public List<IssueBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Search Issues
	 */
	public List<IssueBean> search(IssueBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_issue where 1=1");

		if (bean != null) {
			if (bean.getIssueCode() != null && bean.getIssueCode().length() > 0) {
				sql.append(" and issue_code like '%" + bean.getIssueCode() + "%'");
			}
			if (bean.getStudentName() != null && bean.getStudentName().length() > 0) {
				sql.append(" and student_name like '%" + bean.getStudentName() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		List<IssueBean> list = new ArrayList<IssueBean>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new IssueBean();
				bean.setId(rs.getLong(1));
				bean.setIssueCode(rs.getString(2));
				bean.setStudentName(rs.getString(3));
				bean.setIssueDate(rs.getDate(4));
				bean.setReturnDate(rs.getDate(5));
				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (CJCommunicationsException e) {
			throw new DatabaseServerDownException("Database Server Down!!!");
		} catch (Exception e) {
			throw new ApplicationException("Exception in search Issue");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}