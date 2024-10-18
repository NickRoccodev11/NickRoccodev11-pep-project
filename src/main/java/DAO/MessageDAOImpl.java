package DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAOImpl implements MessageDAO {

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        return ConnectionUtil.getConnection();
    }

    @Override
    public List<Message> getMessages() {

        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM message";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int message_id = resultSet.getInt("message_id");
                int posted_by = resultSet.getInt("posted_by");
                String message_text = resultSet.getString("message_text");
                long time_posted_epoch = resultSet.getLong("time_posted_epoch");
                messages.add(new Message(message_id, posted_by, message_text, time_posted_epoch));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Message getMessageById(int messageId) {

        Message message = null;
        String query = "SELECT * FROM message WHERE message_id = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setInt(1, messageId);
                    
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int message_id = resultSet.getInt("message_id");
                    int posted_by = resultSet.getInt("posted_by");
                    String message_text = resultSet.getString("message_text");
                    long time_posted_epoch = resultSet.getInt("time_posted_epoch");

                    message = new Message(message_id, posted_by, message_text, time_posted_epoch);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return message;
        }
        return message;
    }

    @Override
    public int postMessage(Message message) {

        String query = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, message.getPosted_by());
            statement.setString(2, message.getMessage_text());
            statement.setLong(3, message.getTime_posted_epoch());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public int deleteMessage(Message message) {

        String query = "DELETE FROM message WHERE message_id = ?";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, message.getMessage_id());
            return statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int updateMessage(Message message) {

        String query = "UPDATE message SET message_text = ? WHERE message_id = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, message.getMessage_text());
            statement.setInt(2, message.getMessage_id());
            return statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<Message> getMessagesByAccountId(int accountId) {

        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM message m INNER JOIN account a ON a.account_id = m.posted_by WHERE account_id = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, accountId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int message_id = resultSet.getInt("message_id");
                    int posted_by = resultSet.getInt("posted_by");
                    String message_text = resultSet.getString("message_text");
                    long time_posted_epoch = resultSet.getLong("time_posted_epoch");
                    messages.add(new Message(message_id, posted_by, message_text, time_posted_epoch));
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
