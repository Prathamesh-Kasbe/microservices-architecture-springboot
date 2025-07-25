package dev.pk.ms.client;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import dev.pk.ms.model.Account;


@HttpExchange
public interface AccountClient {

	@GetExchange("/account/customer/{customerId}")
	public List<Account> getAccountsByCustomerId(@PathVariable Long customerId);
}
