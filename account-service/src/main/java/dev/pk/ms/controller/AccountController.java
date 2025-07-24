package dev.pk.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.pk.ms.AccountServiceApplication;
import dev.pk.ms.model.Account;
import dev.pk.ms.repository.AccountRepository;

@RestController
@RequestMapping("account")
public class AccountController {

    private final AccountServiceApplication accountServiceApplication;
	
	private AccountRepository accountRepository;

    AccountController(AccountServiceApplication accountServiceApplication) {
        this.accountServiceApplication = accountServiceApplication;
    }
	
	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@PostMapping
	public Account createAccount(@RequestBody Account account) {
		boolean isAccountAdded = accountRepository.createAccount(account);
		return isAccountAdded ? account : null;
	}
	
	@GetMapping
	public List<Account> getAllAccounts(){
		return accountRepository.getAllAccounts();
	}
	
	@GetMapping("/customer/{customerId}")
	public List<Account> getAccountsByCustomerId(@PathVariable Long customerId){
		return accountRepository.getAccountsByCustomerId(customerId);
	}
	
	@DeleteMapping
	public String removeAllAccounts() {
		boolean isAccountRemoved = accountRepository.removeAllAccounts();
		if(isAccountRemoved) {
			return "Accounts removed Successfully";
		}
		return "Failed to remove Accounts";
	}
}
