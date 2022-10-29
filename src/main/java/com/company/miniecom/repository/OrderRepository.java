package com.company.miniecom.repository;

import com.company.miniecom.domains.OrderLines;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderLines, Long> {


}
