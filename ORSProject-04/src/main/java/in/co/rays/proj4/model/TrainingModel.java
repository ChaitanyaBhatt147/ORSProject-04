package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.TrainingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * TrainingModel provides CRUD and search operations for TrainingBean,
 * interacting with the {@code st_training} table via JDBC.
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */
public class TrainingModel {

	/**
	 * Returns next primary key value for st_training table.
	 *
	 * @return next primary key
	 * @throws DatabaseException
	 */
	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_training");
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
	 * Adds a new training record.
	 */
	public long add(TrainingBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		TrainingBean existBean = findByTrainingCode(bean.getTrainingCode());
		int pk = 0;

		if (existBean != null) {
			throw new DuplicateRecordException("Training Code already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_training values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getTrainingCode());
			pstmt.setString(3, bean.getTrainingName());
			pstmt.setString(4, bean.getTrainerName());
			pstmt.setDate(5, new java.sql.Date(bean.getTrainingDate().getTime()));
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
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Training");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Updates an existing training record.
	 */
	public void update(TrainingBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		TrainingBean existBean = findByTrainingCode(bean.getTrainingCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Training Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_training set training_code=?, training_name=?, trainer_name=?, training_date=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getTrainingCode());
			pstmt.setString(2, bean.getTrainingName());
			pstmt.setString(3, bean.getTrainerName());
			pstmt.setDate(4, new java.sql.Date(bean.getTrainingDate().getTime()));
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
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in update Training");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Deletes a training record.
	 */
	public void delete(TrainingBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_training where id=?");
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
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Training");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Finds training by primary key.
	 */
	public TrainingBean findByPk(long pk) throws ApplicationException {

		TrainingBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_training where id=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new TrainingBean();
				bean.setId(rs.getLong(1));
				bean.setTrainingCode(rs.getString(2));
				bean.setTrainingName(rs.getString(3));
				bean.setTrainerName(rs.getString(4));
				bean.setTrainingDate(rs.getDate(5));
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
			throw new ApplicationException("Exception : Exception in find Training by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Finds training by unique training code.
	 */
	public TrainingBean findByTrainingCode(String code) throws ApplicationException {

		TrainingBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("select * from st_training where training_code=?");
			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new TrainingBean();
				bean.setId(rs.getLong(1));
				bean.setTrainingCode(rs.getString(2));
				bean.setTrainingName(rs.getString(3));
				bean.setTrainerName(rs.getString(4));
				bean.setTrainingDate(rs.getDate(5));
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
			throw new ApplicationException("Exception : Exception in find Training by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Returns all training records.
	 */
	public List<TrainingBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Searches training records with pagination support.
	 */
	public List<TrainingBean> search(TrainingBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_training where 1=1");

		if (bean != null) {
			if (bean.getTrainingCode() != null && bean.getTrainingCode().length() > 0) {
				sql.append(" and training_code like '%" + bean.getTrainingCode() + "%'");
			}
			if (bean.getTrainingName() != null && bean.getTrainingName().length() > 0) {
				sql.append(" and training_name like '%" + bean.getTrainingName() + "%'");
			}
			if (bean.getTrainerName() != null && bean.getTrainerName().length() > 0) {
				sql.append(" and trainer_name like '%" + bean.getTrainerName() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		List<TrainingBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new TrainingBean();
				bean.setId(rs.getLong(1));
				bean.setTrainingCode(rs.getString(2));
				bean.setTrainingName(rs.getString(3));
				bean.setTrainerName(rs.getString(4));
				bean.setTrainingDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (CJCommunicationsException e) {
			throw new DatabaseServerDownException("Database Server Down!!!");
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Training");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}