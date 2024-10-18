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

        boolean isNameTaken = accountDAOImpl.isUsernameTaken(username);

        if (username.isBlank() || password.length() < 4 || isNameTaken) {
            return -1;
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
