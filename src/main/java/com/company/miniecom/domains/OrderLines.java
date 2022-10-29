package com.company.miniecom.domains;

import com.company.miniecom.domains.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class OrderLines {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private Billing billing;

	@OneToOne
	private Cart cart;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;





}
