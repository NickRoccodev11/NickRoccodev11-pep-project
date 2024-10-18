package Controller;

import java.util.List;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import DAO.MessageDAOImpl;
import DAO.AccountDAOImpl;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
   /**
    * 
    * @return a Javalin app object which defines the behavior of the Javalin
    *         controller.
    */
   private final MessageService messageService;
   private final AccountService accountService;

   public SocialMediaController() {
      this.messageService = new MessageService(new MessageDAOImpl(), new AccountDAOImpl());
      this.accountService = new AccountService(new AccountDAOImpl());
   }

   public SocialMediaController(MessageService messageService, AccountService accountService) {
      this.messageService = messageService;
      this.accountService = accountService;
   }

   public Javalin startAPI() {
      Javalin app = Javalin.create();

      app.post("/register", this::registerAccount);
      app.post("/login", this::loginAccount);
      app.post("/messages", this::postMessage);
      app.get("/messages", this::getMessages);
      app.patch("/messages/{message_id}", this::updateMessage);
      app.get("/messages/{message_id}", this::getMessageById);
      app.delete("/messages/{message_id}", this::deleteMessage);
      app.get("accounts/{account_id}/messages", this::getMessagesByAccountId);

      return app;
   }

   /**
    * This is an example handler for an example endpoint.
    * 
    * @param context The Javalin Context object manages information about both the
    *                HTTP request and response.
    */

   private void registerAccount(Context context) {

      Account account = context.bodyAsClass(Account.class);
      int result = accountService.register(account.getUsername(), account.getPassword());

      if (result >= 0) {
         Account existingAccount = accountService.getAccountById(result);
         context.json(200).json(existingAccount);
      } else {
         context.status(400).json("");
      }
   }

   private void loginAccount(Context context) {

      Account account = context.bodyAsClass(Account.class);

      Account result = accountService.login(account.getUsername(), account.getPassword());

      if (result == null) {
         context.status(401);
      } else {
         context.status(200).json(result);
      }

   }

   private void postMessage(Context context) {

      Message message = context.bodyAsClass(Message.class);

      int result = messageService.postMessage(message.getPosted_by(), message.getMessage_text(),
            message.getTime_posted_epoch());

      if (result < 0) {
         context.status(400).json("");
      } else {
         Message newMessage = messageService.getMessageById(result);
         context.status(200).json(newMessage);
      }

   }

   private void getMessages(Context context) {
      List<Message> result = messageService.getMessages();

      context.status(200).json(result);

   }

   private void updateMessage(Context context) {
     
      int messageId = Integer.parseInt(context.pathParam("message_id"));     
      Message message = context.bodyAsClass(Message.class);

      int result = messageService.updateMessage(messageId, message.getMessage_text());

      if (result < 0) {
         context.status(400).json("");
      } else {
         Message newMessage = messageService.getMessageById(result);
         context.status(200).json(newMessage);
      }

   }

   private void getMessageById(Context context) {
      int messageId = Integer.parseInt(context.pathParam("message_id"));
      Message message = messageService.getMessageById(messageId);
      if (message == null) {
         context.status(200).json("");
      } else {
         context.status(200).json(message);
      }

   }

   private void deleteMessage(Context context) {
      int messageId = Integer.parseInt(context.pathParam("message_id"));
      Message nowDeletedMessage = messageService.getMessageById(messageId);
      int result = messageService.deleteMessage(messageId);

      if (result <= 0) {
         context.status(200).json("");
      } else {
         context.status(200).json(nowDeletedMessage);
      }

   }

   private void getMessagesByAccountId(Context context) {
      int accountId = Integer.parseInt(context.pathParam("account_id"));
      List<Message> messages = messageService.getMessagesByAccountId(accountId);
      context.status(200).json(messages);
   }
}