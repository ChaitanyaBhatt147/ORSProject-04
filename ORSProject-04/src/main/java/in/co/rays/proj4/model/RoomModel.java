package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.RoomBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * RoomModel provides CRUD and search operations for RoomBean.
 *
 * author Chaitanya Bhatt
 * version 1.0
 */
public class RoomModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_room");
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

	public long add(RoomBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		RoomBean existBean = findByRoomCode(bean.getRoomCode());

		int pk = 0;

		if (existBean != null) {
			throw new DuplicateRecordException("Room Code already exists");
		}

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_room values(?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getRoomCode());
			pstmt.setString(3, bean.getRoomName());
			pstmt.setInt(4, bean.getCapacity());
			pstmt.setString(5, bean.getRoomStatus());
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
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}

			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Room");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(RoomBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		RoomBean existBean = findByRoomCode(bean.getRoomCode());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Room Code already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_room set room_code=?, room_name=?, capacity=?, room_status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getRoomCode());
			pstmt.setString(2, bean.getRoomName());
			pstmt.setInt(3, bean.getCapacity());
			pstmt.setString(4, bean.getRoomStatus());
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
				throw new ApplicationException("Exception : update rollback exception " + ex.getMessage());
			}

			e.printStackTrace();
			throw new ApplicationException("Exception in updating Room");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(RoomBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_room where id=?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (CJCommunicationsException e) {

			e.printStackTrace();
			throw new DatabaseServerDownException("Database Server Down!!!");

		} catch (Exception e) {

			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in delete Room");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public RoomBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_room where id=?");

		RoomBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new RoomBean();

				bean.setId(rs.getLong(1));
				bean.setRoomCode(rs.getString(2));
				bean.setRoomName(rs.getString(3));
				bean.setCapacity(rs.getInt(4));
				bean.setRoomStatus(rs.getString(5));
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

			throw new ApplicationException("Exception : Exception in getting Room by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public RoomBean findByRoomCode(String roomCode) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_room where room_code=?");

		RoomBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, roomCode);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new RoomBean();

				bean.setId(rs.getLong(1));
				bean.setRoomCode(rs.getString(2));
				bean.setRoomName(rs.getString(3));
				bean.setCapacity(rs.getInt(4));
				bean.setRoomStatus(rs.getString(5));

			}

			rs.close();
			pstmt.close();

		} catch (CJCommunicationsException e) {

			e.printStackTrace();
			throw new DatabaseServerDownException("Database Server Down!!!");

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting Room by Code");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<RoomBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<RoomBean> search(RoomBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_room where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id=" + bean.getId());
			}

			if (bean.getRoomCode() != null && bean.getRoomCode().length() > 0) {
				sql.append(" and room_code like '%" + bean.getRoomCode() + "%'");
			}

			if (bean.getRoomName() != null && bean.getRoomName().length() > 0) {
				sql.append(" and room_name like '%" + bean.getRoomName() + "%'");
			}

			if (bean.getRoomStatus() != null && bean.getRoomStatus().length() > 0) {
				sql.append(" and room_status like '%" + bean.getRoomStatus() + "%'");
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<RoomBean> list = new ArrayList<>();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new RoomBean();

				bean.setId(rs.getLong(1));
				bean.setRoomCode(rs.getString(2));
				bean.setRoomName(rs.getString(3));
				bean.setCapacity(rs.getInt(4));
				bean.setRoomStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (CJCommunicationsException e) {

			e.printStackTrace();
			throw new DatabaseServerDownException("Database Server Down!!!");

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search Room");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}