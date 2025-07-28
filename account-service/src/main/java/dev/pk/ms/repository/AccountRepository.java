package dev.pk.ms.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import dev.pk.ms.AccountServiceApplication;
import dev.pk.ms.model.Account;
import jakarta.annotation.PostConstruct;

@Repository
public class AccountRepository {

    private final AccountServiceApplication accountServiceApplication;

	public List<Account> accounts;
	
	@PostConstruct
	private void initializeAccounts() {
		accounts = new ArrayList<>();
		accounts.add(new Account(0001l, "Savings", "2001", 1000));
		accounts.add(new Account(0002l, "Current", "2002", 2000));
		accounts.add(new Account(0003l, "FD", "2003", 3000));
		accounts.add(new Account(0004l, "PPF", "2004", 4000));
	}

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

	public boolean removeAccountByAccountNumber(String accountNumber) {
		return accounts.removeIf(account -> account.accountNumber() == accountNumber);
	}
}
