package dao;

import models.Reimbursement;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDaoImpl implements ReimbursementDao {
    String url = ConnectionUtility.url;
    String dbUsername = ConnectionUtility.dbUsername;
    String dbPassword = ConnectionUtility.dbPassword;
    private static ReimbursementDao reimbursementDao;

    private ReimbursementDaoImpl(){
        try{
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static ReimbursementDao getInstance() {
        if (reimbursementDao == null) {
            reimbursementDao = new ReimbursementDaoImpl();
        }

        return reimbursementDao;
    }


    @Override
    public List<Reimbursement> getAllReimbursements() {
        List<Reimbursement> reimbursements = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT r.*, u.user_first_name, u.user_last_name FROM ers_reimbursements r\n" +
                    "INNER JOIN ers_users u ON r.reimb_author = u.ers_users_id;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reimbursements.add(new Reimbursement(rs.getInt("reimb_id"),
                        rs.getBigDecimal("reimb_amount"), rs.getTimestamp("reimb_submitted"),
                        rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"),
                        rs.getInt("reimb_author"), rs.getInt("reimb_resolver"),
                        rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"),
                        rs.getString("user_first_name"), rs.getString("user_last_name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursements;
    }

    @Override
    public List<Reimbursement> getAllReimbursementsByUserId(Integer userId) {
        List<Reimbursement> reimbursements = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT r.*, u.user_first_name, u.user_last_name FROM ers_reimbursements r\n" +
                    "INNER JOIN ers_users u ON r.reimb_author = u.ers_users_id\n" +
                    "WHERE reimb_author = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reimbursements.add(new Reimbursement(rs.getInt("reimb_id"),
                        rs.getBigDecimal("reimb_amount"), rs.getTimestamp("reimb_submitted"),
                        rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"),
                        rs.getInt("reimb_author"), rs.getInt("reimb_resolver"),
                        rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"),
                        rs.getString("user_first_name"), rs.getString("user_last_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursements;
    }

    @Override
    public void createReimbursement(Reimbursement reimbursement) {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO ers_reimbursements VALUES (DEFAULT, ?, CURRENT_TIMESTAMP, NULL, ?, NULL, ?, NULL, 3, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setBigDecimal(1, reimbursement.getAmount());
            ps.setString(2, reimbursement.getDescription());
            ps.setInt(3, reimbursement.getAuthorId());
            ps.setInt(4, reimbursement.getTypeId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateReimbursement(User resolver, Reimbursement reimbursement) {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "UPDATE ers_reimbursements " +
            "SET reimb_resolved = current_timestamp, reimb_resolver = ?, reimb_status_id = ? " +
            "WHERE reimb_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, resolver.getUserId());
            ps.setInt(2, reimbursement.getStatusId());
            ps.setInt(3, reimbursement.getReimbId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
