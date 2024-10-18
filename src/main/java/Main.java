import Controller.SocialMediaController;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import DAO.MessageDAOImpl;
import DAO.AccountDAOImpl;


/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
   

    public static void main(String[] args) {
        
        MessageDAOImpl messageDAOImpl = new MessageDAOImpl();
        AccountDAOImpl accountDAOImpl = new AccountDAOImpl();
    
        AccountService accountService = new AccountService(accountDAOImpl);
        MessageService messageService = new MessageService(messageDAOImpl, accountDAOImpl);

        SocialMediaController controller = new SocialMediaController(messageService, accountService);
        Javalin app = controller.startAPI();
        app.start(8080);
    }
}
