package Service;

import java.util.List;
import Model.Message;
import Model.Account;
import DAO.AccountDAOImpl;
import DAO.MessageDAOImpl;

public class MessageService {

    private final MessageDAOImpl messageDAOImpl;
    private final AccountDAOImpl accountDAOImpl;

    public MessageService(MessageDAOImpl messageDAOImpl, AccountDAOImpl accountDAOImpl) {

        this.messageDAOImpl = messageDAOImpl;
        this.accountDAOImpl = accountDAOImpl;

    }

    public List<Message> getMessages() {

        return messageDAOImpl.getMessages();

    }

    public Message getMessageById(int messageId) {

        return messageDAOImpl.getMessageById(messageId);

    }

    public int postMessage(int postedBy, String messageText, long timePostedEpoch) {

        Account existingAccount = accountDAOImpl.getAccountById(postedBy);

        if (messageText.isBlank() || messageText.length() > 255 || existingAccount == null) {
            return -1;
        }

        Message newMessage = new Message(postedBy, messageText, timePostedEpoch);

        return messageDAOImpl.postMessage(newMessage);

    }

    public int updateMessage(int messageId, String messageText) {

        Message previousMessage = messageDAOImpl.getMessageById(messageId);

        if (messageText.isBlank() || messageText.length() >= 255 || previousMessage == null) {
            return -1;
        }

        previousMessage.setMessage_text(messageText);

        return messageDAOImpl.updateMessage(previousMessage);

    }

    public int deleteMessage(int messageId) {

        Message previousMessage = messageDAOImpl.getMessageById(messageId);

        if (previousMessage == null) {
            return -1;
        }

        return messageDAOImpl.deleteMessage(previousMessage);

    }

    public List<Message> getMessagesByAccountId(int accountId) {

        return messageDAOImpl.getMessagesByAccountId(accountId);

    }

}
