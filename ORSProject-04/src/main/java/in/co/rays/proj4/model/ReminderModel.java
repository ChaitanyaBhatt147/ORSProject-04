package in.co.rays.proj4.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ReminderBean;
import in.co.rays.proj4.exception.*;
import in.co.rays.proj4.util.JDBCDataSource;

public class ReminderModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_reminder");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	// ================= ADD =================
	public long add(ReminderBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		ReminderBean duplicate = findByCode(bean.getReminderCode());
		if (duplicate != null) {
			throw new DuplicateRecordException("Reminder Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_reminder values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getReminderCode());
			pstmt.setString(3, bean.getReminderTitle());
			pstmt.setTimestamp(4, (bean.getReminderDate()));
			pstmt.setString(5, bean.getReminderStatus());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
			}
			throw new ApplicationException("Exception in add Reminder");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	// ================= UPDATE =================
	public void update(ReminderBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		ReminderBean exist = findByCode(bean.getReminderCode());
		if (exist != null && exist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Reminder Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_reminder set reminder_code=?, reminder_title=?, reminder_date=?, reminder_status=?, modified_by=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getReminderCode());
			pstmt.setString(2, bean.getReminderTitle());
			pstmt.setTimestamp(3, (bean.getReminderDate()));
			pstmt.setString(4, bean.getReminderStatus());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getModifiedDatetime());
			pstmt.setLong(7, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
			}
			throw new ApplicationException("Exception in update Reminder");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= DELETE =================
	public void delete(ReminderBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from st_reminder where id=?");

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in delete Reminder");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================
	public ReminderBean findByPk(long pk) throws ApplicationException {

		ReminderBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_reminder where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = populateBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	// ================= FIND BY CODE =================
	public ReminderBean findByCode(String code) throws ApplicationException {

		ReminderBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_reminder where reminder_code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = populateBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByCode");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	// ================= SEARCH =================
	public List<ReminderBean> search(ReminderBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_reminder where 1=1");

		if (bean != null) {
			if (bean.getReminderCode() != null && bean.getReminderCode().length() > 0) {
				sql.append(" and reminder_code like '%" + bean.getReminderCode() + "%'");
			}
			if (bean.getReminderTitle() != null && bean.getReminderTitle().length() > 0) {
				sql.append(" and reminder_title like '%" + bean.getReminderTitle() + "%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		List<ReminderBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(populateBean(rs));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Reminder");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	// ================= COMMON METHOD =================
	private ReminderBean populateBean(ResultSet rs) throws Exception {

		ReminderBean bean = new ReminderBean();

		bean.setId(rs.getLong(1));
		bean.setReminderCode(rs.getString(2));
		bean.setReminderTitle(rs.getString(3));
		bean.setReminderDate(rs.getTimestamp(4));
		bean.setReminderStatus(rs.getString(5));
		bean.setCreatedBy(rs.getString(6));
		bean.setModifiedBy(rs.getString(7));
		bean.setCreatedDatetime(rs.getTimestamp(8));
		bean.setModifiedDatetime(rs.getTimestamp(9));

		return bean;
	}
}