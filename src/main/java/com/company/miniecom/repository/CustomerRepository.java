package com.company.miniecom.repository;

import com.company.miniecom.domains.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {

    Customer getCustomerByAuthUser_Username(String username);

}
