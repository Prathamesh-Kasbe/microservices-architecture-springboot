package dev.pk.ms.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import dev.pk.ms.model.Customer;
import jakarta.annotation.PostConstruct;

@Repository
public class CustomerRepository {
	
	public List<Customer> customers;

	@PostConstruct
	private void initializeCustomer() {
		customers = new ArrayList<>();
		customers.add(new Customer("Rohit", "Sharma", 0001l, new ArrayList<>()));
		customers.add(new Customer("Virat", "Kohli", 0002l, new ArrayList<>()));
		customers.add(new Customer("Sachin", "Tendulkar", 0003l, new ArrayList<>()));
		customers.add(new Customer("Saurav", "Ganguly", 0004l, new ArrayList<>()));
	}
	
	public List<Customer> getAllCustomers(){
		return customers;
	}
	
	public boolean createCustomer(Customer customer) {
	    return customers.add(customer);
	    
	}

	public boolean removeCustomerByCustomerId(Long customerId) {
		return customers.removeIf(customer -> customer.getCustomerId() == customerId);
	}

	public List<Customer> getAllCustomersWithAccounts() {
		// TODO Auto-generated method stub
		return null;
	}
}
