package com.codegym.dao;

import com.codegym.model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private final String jdbcURL =
            "jdbc:mysql://localhost:3306/demo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "password";

    private static final String SQL_INSERT =
            "INSERT INTO Employee (name, salary, created_Date) VALUES (?,?,?)";
    private static final String SQL_UPDATE =
            "UPDATE Employee SET salary=? WHERE name=?";
    private static final String SQL_TABLE_CREATE =
            "CREATE TABLE Employee (" +
            " id INT(11) AUTO_INCREMENT," +
            " name VARCHAR(100) NOT NULL," +
            " salary DECIMAL(15, 2) NOT NULL," +
            " created_Date TIMESTAMP," +
            " PRIMARY KEY (id)" +
            ")";
    private static final String SQL_TABLE_DROP =
            "DROP TABLE IF EXISTS Employee";

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("country")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User selectUser(int id) {
        String sql = "SELECT * FROM users WHERE id=?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("country")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users(name,email,country) VALUES(?,?,?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.executeUpdate();
        }
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name=?,email=?,country=? WHERE id=?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setInt(4, user.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public void insertUpdateWithoutTransaction() {
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             PreparedStatement psInsert = conn.prepareStatement(SQL_INSERT);
             PreparedStatement psUpdate = conn.prepareStatement(SQL_UPDATE)) {

            statement.execute(SQL_TABLE_DROP);
            statement.execute(SQL_TABLE_CREATE);

            psInsert.setString(1, "Quynh");
            psInsert.setBigDecimal(2, new BigDecimal(10));
            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            psInsert.execute();

            psInsert.setString(1, "Ngan");
            psInsert.setBigDecimal(2, new BigDecimal(20));
            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            psInsert.execute();

            // Cố tình gán sai để tạo lỗi theo yêu cầu bài thực hành.
            psUpdate.setBigDecimal(2, new BigDecimal("999.99"));
            psUpdate.setString(2, "Quynh");
            psUpdate.execute();

        } catch (Exception e) {
            System.out.println("Đã bắt được lỗi trong quá trình thực thi SQL:");
            e.printStackTrace();
        }
    }
}
