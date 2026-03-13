package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.ComplaintTicketBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ComplaintTicketModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_complaint_ticket");
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

	public long add(ComplaintTicketBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_complaint_ticket values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setLong(2, bean.getComplaintTicketId());
			pstmt.setString(3, bean.getIssueType());
			pstmt.setDate(4, new java.sql.Date(bean.getCreatedDate().getTime()));
			pstmt.setString(5, bean.getStatus());
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
				throw new ApplicationException("Exception : add rollback exception");
			}
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in add Complaint Ticket");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(ComplaintTicketBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
				"update st_complaint_ticket set complaint_ticket_id=?, issue_type=?, created_date=?, status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setLong(1, bean.getComplaintTicketId());
			pstmt.setString(2, bean.getIssueType());
			pstmt.setDate(3, new java.sql.Date(bean.getCreatedDate().getTime()));
			pstmt.setString(4, bean.getStatus());
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
				throw new ApplicationException("Exception : update rollback exception");
			}

			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in update Complaint Ticket");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(ComplaintTicketBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_complaint_ticket where id=?");

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
				throw new ApplicationException("Exception : delete rollback exception");
			}

			throw new ApplicationException("Exception : Exception in delete Complaint Ticket");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public ComplaintTicketBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_complaint_ticket where id=?");

		ComplaintTicketBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new ComplaintTicketBean();

				bean.setId(rs.getLong(1));
				bean.setComplaintTicketId(rs.getLong(2));
				bean.setIssueType(rs.getString(3));
				bean.setCreatedDate(rs.getDate(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));

			}

			rs.close();
			pstmt.close();

		} catch (CJCommunicationsException e) {
			e.printStackTrace();
			throw new DatabaseServerDownException("Database Server Down!!!");
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in findByPk ComplaintTicket");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<ComplaintTicketBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<ComplaintTicketBean> search(ComplaintTicketBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_complaint_ticket where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getIssueType() != null && bean.getIssueType().length() > 0) {
				sql.append(" and issue_type like '%" + bean.getIssueType() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '%" + bean.getStatus() + "%'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<ComplaintTicketBean> list = new ArrayList<ComplaintTicketBean>();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new ComplaintTicketBean();

				bean.setId(rs.getLong(1));
				bean.setComplaintTicketId(rs.getLong(2));
				bean.setIssueType(rs.getString(3));
				bean.setCreatedDate(rs.getDate(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (CJCommunicationsException e) {
			e.printStackTrace();
			throw new DatabaseServerDownException("Database Server Down!!!");
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search ComplaintTicket");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}