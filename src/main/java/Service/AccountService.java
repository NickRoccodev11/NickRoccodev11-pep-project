package Service;

import Model.Account;
import DAO.AccountDAOImpl;

public class AccountService {
  
    private final AccountDAOImpl accountDAOImpl; 

    
    public AccountService(AccountDAOImpl accountDAOImpl) {
        this.accountDAOImpl = accountDAOImpl;
    }


    public boolean isUsernameTaken(String username) {

        return accountDAOImpl.isUsernameTaken(username);

    }

    public int register(String username, String password) {
        
        if(username.isBlank()){
            return -2;
        }

        if(password.length() < 4){
            return -2;
        }
       
        boolean isNameTaken  = accountDAOImpl.isUsernameTaken(username);
        if(isNameTaken){
            return -3;
        }

        return accountDAOImpl.register(username, password);

    }

    public Account login(String username, String password) {

        return accountDAOImpl.login(username, password);

    }

    public Account getAccountById(int accountId) {

        return accountDAOImpl.getAccountById(accountId);

    }
}
