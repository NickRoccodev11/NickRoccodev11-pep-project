package DAO;

import Model.Account;

public interface AccountDAO {

    boolean isUsernameTaken(String username);

    int register(String username, String password);

    Account login(String username, String password);

    Account getAccountById(int accountId);
}

