package dev.pk.ms.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import dev.pk.ms.AccountServiceApplication;
import dev.pk.ms.model.Account;

@Repository
public class AccountRepository {

    private final AccountServiceApplication accountServiceApplication;

	public List<Account> accounts = new ArrayList<>();

    AccountRepository(AccountServiceApplication accountServiceApplication) {
        this.accountServiceApplication = accountServiceApplication;
    }
	
	public boolean createAccount(Account account) {
		return accounts.add(account);
	}
	
	public List<Account> getAllAccounts(){
		return accounts;
		
	}

	public List<Account> getAccountsByCustomerId(Long customerId) {
		return accounts.stream().
				filter(account -> account.customerId().equals(customerId)).toList();
	}

	public boolean removeAllAccounts() {
		return accounts.removeAll(accounts);
	}
}
