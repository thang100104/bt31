package com.codegym.dao;

import com.codegym.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    List<User> selectAllUsers();
    User selectUser(int id);
    void insertUser(User user) throws SQLException;
    boolean updateUser(User user) throws SQLException;
    boolean deleteUser(int id) throws SQLException;

    void insertUpdateWithoutTransaction() throws SQLException;
}
