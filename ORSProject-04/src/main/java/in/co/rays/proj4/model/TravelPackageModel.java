package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.TravelPackageBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DatabaseServerDownException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * TravelPackageModel provides CRUD operations for TravelPackageBean.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class TravelPackageModel {

    /**
     * Get next primary key
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select max(id) from st_travel_package");
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
     * Add Travel Booking
     */
    public long add(TravelPackageBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_travel_package values(?,?,?,?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getTravelerName());
            pstmt.setString(3, bean.getDestination());
            pstmt.setString(4, bean.getPackageType());
            pstmt.setDate(5, new java.sql.Date(bean.getTravelDate().getTime()));
            pstmt.setDate(6, new java.sql.Date(bean.getReturnDate().getTime()));
            pstmt.setLong(7, bean.getPackageCost());
            pstmt.setString(8, bean.getBookingStatus());
            pstmt.setString(9, bean.getCreatedBy());
            pstmt.setString(10, bean.getModifiedBy());
            pstmt.setTimestamp(11, bean.getCreatedDatetime());
            pstmt.setTimestamp(12, bean.getModifiedDatetime());

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
            e.printStackTrace();
            throw new ApplicationException("Exception in add Travel Package");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    /**
     * Update booking
     */
    public void update(TravelPackageBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_travel_package set traveler_name=?, destination=?, "
                            + "package_type=?, travel_date=?, return_date=?, "
                            + "package_cost=?, booking_status=?, created_by=?, "
                            + "modified_by=?, created_datetime=?, modified_datetime=? "
                            + "where id=?");

            pstmt.setString(1, bean.getTravelerName());
            pstmt.setString(2, bean.getDestination());
            pstmt.setString(3, bean.getPackageType());
            pstmt.setDate(4, new java.sql.Date(bean.getTravelDate().getTime()));
            pstmt.setDate(5, new java.sql.Date(bean.getReturnDate().getTime()));
            pstmt.setLong(6, bean.getPackageCost());
            pstmt.setString(7, bean.getBookingStatus());
            pstmt.setString(8, bean.getCreatedBy());
            pstmt.setString(9, bean.getModifiedBy());
            pstmt.setTimestamp(10, bean.getCreatedDatetime());
            pstmt.setTimestamp(11, bean.getModifiedDatetime());
            pstmt.setLong(12, bean.getId());

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

            throw new ApplicationException("Exception in updating Travel Package");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Delete booking
     */
    public void delete(TravelPackageBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_travel_package where id=?");

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

            throw new ApplicationException("Exception in delete Travel Package");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Find by PK
     */
    public TravelPackageBean findByPk(long pk) throws ApplicationException {

        TravelPackageBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_travel_package where id=?");

            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = populateBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (CJCommunicationsException e) {
            throw new DatabaseServerDownException("Database Server Down!!!");
        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * List all bookings
     */
    public List<TravelPackageBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Search bookings
     */
    public List<TravelPackageBean> search(TravelPackageBean bean,
            int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql =
                new StringBuffer("select * from st_travel_package where 1=1");

        if (bean != null) {

            if (bean.getTravelerName() != null
                    && bean.getTravelerName().length() > 0) {
                sql.append(" and traveler_name like '%"
                        + bean.getTravelerName() + "%'");
            }

            if (bean.getDestination() != null
                    && bean.getDestination().length() > 0) {
                sql.append(" and destination like '%"
                        + bean.getDestination() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList<TravelPackageBean> list = new ArrayList<>();
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

        } catch (CJCommunicationsException e) {
            throw new DatabaseServerDownException("Database Server Down!!!");
        } catch (Exception e) {
            throw new ApplicationException("Exception in search");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    /**
     * Populate Bean
     */
    private TravelPackageBean populateBean(ResultSet rs) throws Exception {

        TravelPackageBean bean = new TravelPackageBean();

        bean.setId(rs.getLong(1));
        bean.setTravelerName(rs.getString(2));
        bean.setDestination(rs.getString(3));
        bean.setPackageType(rs.getString(4));
        bean.setTravelDate(rs.getDate(5));
        bean.setReturnDate(rs.getDate(6));
        bean.setPackageCost(rs.getLong(7));
        bean.setBookingStatus(rs.getString(8));
        bean.setCreatedBy(rs.getString(9));
        bean.setModifiedBy(rs.getString(10));
        bean.setCreatedDatetime(rs.getTimestamp(11));
        bean.setModifiedDatetime(rs.getTimestamp(12));

        return bean;
    }
}