package com.company.miniecom.repository;

import com.company.miniecom.domains.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing, Long> {
}
