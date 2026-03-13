package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.OrderBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class OrderModel {
	 public Integer nextPk() throws DatabaseException {
	        Connection conn = null;
	        int pk = 0;
	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_order");
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                pk = rs.getInt(1);
	            }
	            rs.close();
	            pstmt.close();
	        } catch (CJCommunicationsException e) {
	        	e.printStackTrace();
	        	throw new DatabaseServerDownException("Database Server Down!!!");
			}catch (Exception e) {
	            throw new DatabaseException("Exception in Marksheet getting PK");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        return pk + 1;
	    }
	 
	 public long add(OrderBean bean) throws ApplicationException, DuplicateRecordException {

	        Connection conn = null;
	        int pk = 0;

	        OrderBean duplicateOrder = findByOrderId(bean.getOrderId());

	        if (duplicateOrder != null) {
	            throw new DuplicateRecordException("Order already exists");
	        }

	        try {
	            conn = JDBCDataSource.getConnection();
	            pk = nextPk();
	            conn.setAutoCommit(false); // Begin transaction
	            PreparedStatement pstmt = conn.prepareStatement(
	                    "insert into st_order values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	            pstmt.setInt(1, pk);
	            pstmt.setInt(2, bean.getOrderId());
	            pstmt.setString(3, bean.getName());
	            pstmt.setString(4, bean.getNumber());
	            pstmt.setString(5, bean.getAddress());
	            pstmt.setString(6, bean.getAmount());
	            pstmt.setString(7, bean.getCreatedBy());
	            pstmt.setString(8, bean.getModifiedBy());
	            pstmt.setTimestamp(9, bean.getCreatedDatetime());
	            pstmt.setTimestamp(10, bean.getModifiedDatetime());
	            pstmt.executeUpdate();
	            conn.commit(); // End transaction
	            pstmt.close();
	        } catch (CJCommunicationsException e) {
	        	e.printStackTrace();
	        	throw new DatabaseServerDownException("Database Server Down!!!");
			}catch (Exception e) {
	            e.printStackTrace();
	            try {
	                if (conn != null) {
	                    conn.rollback();
	                }
	            } catch (Exception ex) {
	                throw new ApplicationException("add rollback exception " + ex.getMessage());
	            }
	            throw new ApplicationException("Exception in add Order");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        return pk;
	    }
	 
	 public void update(OrderBean bean) throws ApplicationException, DuplicateRecordException {

	        Connection conn = null;

	        OrderBean beanExist = findByOrderId(bean.getOrderId());

	        if (beanExist != null && beanExist.getId() != bean.getId()) {
	            throw new DuplicateRecordException("Order already exist");
	        }


	        try {
	            conn = JDBCDataSource.getConnection();

	            conn.setAutoCommit(false); // Begin transaction
	            PreparedStatement pstmt = conn.prepareStatement(
	                    "update st_order set order_id = ?, name = ?, number = ?, address = ?, amount = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
	            pstmt.setLong(10, bean.getId());
	            pstmt.setInt(1, bean.getOrderId());
	            pstmt.setString(2, bean.getName());
	            pstmt.setString(3, bean.getNumber());
	            pstmt.setString(4, bean.getAddress());
	            pstmt.setString(5, bean.getAmount());
	            pstmt.setString(6, bean.getCreatedBy());
	            pstmt.setString(7, bean.getModifiedBy());
	            pstmt.setTimestamp(8, bean.getCreatedDatetime());
	            pstmt.setTimestamp(9, bean.getModifiedDatetime());
	            pstmt.executeUpdate();
	            conn.commit(); // End transaction
	            pstmt.close();
	        } catch (CJCommunicationsException e) {
	        	e.printStackTrace();
	        	throw new DatabaseServerDownException("Database Server Down!!!");
			}catch (Exception e) {
	            try {
	                if (conn != null) {
	                    conn.rollback();
	                }
	            } catch (Exception ex) {
	                throw new ApplicationException("Update rollback exception " + ex.getMessage());
	            }
	            throw new ApplicationException("Exception in updating order ");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	    }
	 
	 public void delete(OrderBean bean) throws ApplicationException {

	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            conn.setAutoCommit(false); // Begin transaction
	            PreparedStatement pstmt = conn.prepareStatement("delete from st_order where id = ?");
	            pstmt.setLong(1, bean.getId());
	            pstmt.executeUpdate();
	            conn.commit(); // End transaction
	            pstmt.close();
	        }catch (CJCommunicationsException e) {
	        	e.printStackTrace();
	        	throw new DatabaseServerDownException("Database Server Down!!!");
			} catch (Exception e) {
	            try {
	                if (conn != null) {
	                    conn.rollback();
	                }
	            } catch (Exception ex) {
	                throw new ApplicationException("Delete rollback exception " + ex.getMessage());
	            }
	            throw new ApplicationException("Exception in delete marksheet");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	    }
	 
	 public OrderBean findByPk(long pk) throws ApplicationException {

	        StringBuffer sql = new StringBuffer("select * from st_order where id = ?");
	        OrderBean bean = null;
	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            pstmt.setLong(1, pk);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                bean = new OrderBean();
	                bean.setId(rs.getLong(1));
	                bean.setOrderId(rs.getInt(2));
	                bean.setName(rs.getString(3));
	                bean.setNumber(rs.getString(4));
	                bean.setAddress(rs.getString(5));
	                bean.setAmount(rs.getString(6));
	                bean.setCreatedBy(rs.getString(7));
	                bean.setModifiedBy(rs.getString(8));
	                bean.setCreatedDatetime(rs.getTimestamp(9));
	                bean.setModifiedDatetime(rs.getTimestamp(10));
	            }
	            rs.close();
	            pstmt.close();
	        } catch (Exception e) {
	            throw new ApplicationException("Exception in getting order by pk");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        return bean;
	    }
	 
	 public OrderBean findByOrderId(int orderId) throws ApplicationException {

	        StringBuffer sql = new StringBuffer("select * from st_order where order_id = ?");
	        OrderBean bean = null;
	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            pstmt.setInt(1, orderId);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                bean = new OrderBean();
	                bean.setId(rs.getLong(1));
	                bean.setOrderId(rs.getInt(2));
	                bean.setName(rs.getString(3));
	                bean.setNumber(rs.getString(4));
	                bean.setAddress(rs.getString(5));
	                bean.setAmount(rs.getString(6));
	                bean.setCreatedBy(rs.getString(7));
	                bean.setModifiedBy(rs.getString(8));
	                bean.setCreatedDatetime(rs.getTimestamp(9));
	                bean.setModifiedDatetime(rs.getTimestamp(10));
	            }
	            rs.close();
	            pstmt.close();
	        } catch (CJCommunicationsException e) {
	        	e.printStackTrace();
	        	throw new DatabaseServerDownException("Database Server Down!!!");
			}catch (Exception e) {
	            throw new ApplicationException("Exception in getting order by OrderId");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        return bean;
	    }
	 
	 public List<OrderBean> list() throws ApplicationException {
	        return search(null, 0, 0);
	    }
	 
	 public List<OrderBean> search(OrderBean bean, int pageNo, int pageSize) throws ApplicationException {

	        StringBuffer sql = new StringBuffer("select * from st_order where 1=1");

	        if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" and id = " + bean.getId());
	            }
	            if (bean.getOrderId() != null && bean.getOrderId() > 0) {
	                sql.append(" and order_id = '" + bean.getOrderId() + "'");
	            }
	            if (bean.getName() != null && bean.getName().length() > 0) {
	                sql.append(" and name like '%" + bean.getName() + "%'");
	            }
	            if (bean.getNumber() != null && bean.getNumber().length() > 0) {
	                sql.append(" and number like '%" + bean.getNumber() + "%'");
	            }
	            if (bean.getAddress() != null && bean.getAddress().length() > 0) {
	                sql.append(" and number like '%" + bean.getAddress() + "%'");
	            }
	            if (bean.getAmount() != null && bean.getAmount().length() > 0) {
	                sql.append(" and number like '%" + bean.getAmount() + "%'");
	            }
	        }

	        if (pageSize > 0) {
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + ", " + pageSize);
	        }

	        ArrayList<OrderBean> list = new ArrayList<OrderBean>();
	        Connection conn = null;
	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {

	                OrderBean b = new OrderBean();

	                b.setId(rs.getLong(1));                 // id
	                b.setOrderId((int) rs.getLong(2));      // order_id
	                b.setName(rs.getString(3));             // name
	                b.setNumber(rs.getString(4));           // number
	                b.setAddress(rs.getString(5));          // address
	                b.setAmount(rs.getString(6));           // amount
	                b.setCreatedBy(rs.getString(7));        // created_by
	                b.setModifiedBy(rs.getString(8));       // modified_by
	                b.setCreatedDatetime(rs.getTimestamp(9)); 
	                b.setModifiedDatetime(rs.getTimestamp(10));

	                list.add(b);
	            }

	            rs.close();
	            pstmt.close();
	        } catch (CJCommunicationsException e) {
	        	e.printStackTrace();
	        	throw new DatabaseServerDownException("Database Server Down!!!");
			}catch (Exception e) {
	            throw new ApplicationException("Exception in search Order: " + e.getMessage());
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        return list;
	    }

}
