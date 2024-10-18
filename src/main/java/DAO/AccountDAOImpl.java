package DAO;

import java.sql.*;
import Util.ConnectionUtil;
import Model.Account;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public boolean isUsernameTaken(String username) {
        String query = "SELECT * FROM account WHERE username = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int register(String username, String password) {
        String query = "INSERT INTO account (username, password) VALUES (?, ?)";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, username);
            statement.setString(2, password);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Account login(String username, String password) {
        Account account = null;
        String query = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int accountId = resultSet.getInt("account_id");
                    String dbUsername = resultSet.getString("username");
                    String dbPassword = resultSet.getString("password");
                    account = new Account(accountId, dbUsername, dbPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Account getAccountById(int accountId) {
        String query = "SELECT * FROM account WHERE account_id = ?";
        Account account = null;
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("account_id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    account = new Account(id, username, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account; 
    }
}
