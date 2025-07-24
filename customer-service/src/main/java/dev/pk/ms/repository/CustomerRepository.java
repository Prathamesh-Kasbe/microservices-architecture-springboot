package dev.pk.ms.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import dev.pk.ms.model.Customer;

@Repository
public class CustomerRepository {

	public static final List<Customer> customers = new ArrayList<>();
	
	public List<Customer> getAllCustomers(){
		return customers;
	}
	
	public boolean createCustomer(Customer customer) {
	    return customers.add(customer);
	    
	}

	public boolean removeAllCustomers() {
		return customers.removeAll(customers);
	}

	public List<Customer> getAllCustomersWithAccounts() {
		// TODO Auto-generated method stub
		return null;
	}
}
