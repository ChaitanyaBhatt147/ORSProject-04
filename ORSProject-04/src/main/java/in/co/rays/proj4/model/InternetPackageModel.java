package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.InternetPackageBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * InternetPackageModel provides CRUD and search operations
 * for InternetPackageBean.
 * 
 * author Chaitanya Bhatt
 */

public class InternetPackageModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"select max(id) from st_internet_package");

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

	public long add(InternetPackageBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_internet_package values(?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getPackageName());
			pstmt.setDouble(3, bean.getPrice());
			pstmt.setInt(4, bean.getDataLimit());
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
				throw new ApplicationException("Exception : add rollback exception");
			}

			throw new ApplicationException("Exception : Exception in add Internet Package");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(InternetPackageBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_internet_package set package_name=?, price=?, data_limit=?, modified_by=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getPackageName());
			pstmt.setDouble(2, bean.getPrice());
			pstmt.setInt(3, bean.getDataLimit());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getModifiedDatetime());
			pstmt.setLong(6, bean.getId());

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
				throw new ApplicationException("Exception : rollback exception");
			}

			throw new ApplicationException("Exception in updating Internet Package");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(InternetPackageBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"delete from st_internet_package where id=?");

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
				throw new ApplicationException("Exception : rollback exception");
			}

			throw new ApplicationException("Exception : Exception in delete Internet Package");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	public InternetPackageBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer(
				"select * from st_internet_package where id=?");

		InternetPackageBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new InternetPackageBean();

				bean.setId(rs.getLong(1));
				bean.setPackageName(rs.getString(2));
				bean.setPrice(rs.getDouble(3));
				bean.setDataLimit(rs.getInt(4));
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

			throw new ApplicationException("Exception : Exception in getting Internet Package by pk");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<InternetPackageBean> search(InternetPackageBean bean,
			int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer(
				"select * from st_internet_package where 1=1");

		if (bean != null) {

			if (bean.getPackageName() != null && bean.getPackageName().length() > 0) {

				sql.append(" and package_name like '%" + bean.getPackageName() + "%'");
			}

			if (bean.getPrice() > 0) {

				sql.append(" and price=" + bean.getPrice());
			}

			if (bean.getDataLimit() > 0) {

				sql.append(" and data_limit=" + bean.getDataLimit());
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<InternetPackageBean> list = new ArrayList<>();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new InternetPackageBean();

				bean.setId(rs.getLong(1));
				bean.setPackageName(rs.getString(2));
				bean.setPrice(rs.getDouble(3));
				bean.setDataLimit(rs.getInt(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search Internet Package");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public List<InternetPackageBean> list() throws ApplicationException {

		return search(null, 0, 0);
	}
}