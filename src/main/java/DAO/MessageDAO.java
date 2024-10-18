package DAO;

import Model.Message;

import java.util.List;

public interface MessageDAO {

  List<Message> getMessages();

  Message getMessageById(int message_id);

  int postMessage(Message message);

  int deleteMessage(Message message);

  int updateMessage(Message message);

  List<Message> getMessagesByAccountId(int accountId);
}
