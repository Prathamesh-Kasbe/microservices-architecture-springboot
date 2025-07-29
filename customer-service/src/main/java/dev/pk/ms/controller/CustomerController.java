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

import dev.pk.ms.client.AccountClient;
import dev.pk.ms.model.Customer;
import dev.pk.ms.repository.CustomerRepository;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	private CustomerRepository customerRepository;
	
    @Autowired
	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
    
    private AccountClient accountClient;
    
    @Autowired
	public void setAccountClient(AccountClient accountClient) {
		this.accountClient = accountClient;
	}

	@GetMapping
	public List<Customer> getAllCustomers(){
		return customerRepository.getAllCustomers();
	}
	
	@PostMapping
	public String createCustomer(@RequestBody Customer customer) {
		boolean isCustomerAdded = customerRepository.createCustomer(customer);
		if(isCustomerAdded) {
			return "Customer added Successfully";
		}
		return "Failed to add Customer";
	}
	
	@DeleteMapping("customerId/{customerId}")
	public String removeCustomerByCustomerId(@PathVariable Long customerId) {
		boolean isCustomerRemoved = customerRepository.removeCustomerByCustomerId(customerId);
		if(isCustomerRemoved) {
			return "Customer removed Successfully";
		}
		return "Failed to remove Customer";
	}
	
	@GetMapping("/withAccounts")
	public Flux<Customer> getAllCustomersWithAccounts() {
	    return Flux.fromIterable(customerRepository.getAllCustomers())
	        .flatMap(customer ->
	            accountClient.getAccountsByCustomerId(customer.getCustomerId())
	                .map(accounts -> {
	                    customer.setAccounts(accounts);
	                    return customer;
	                })
	        );
	}
}
