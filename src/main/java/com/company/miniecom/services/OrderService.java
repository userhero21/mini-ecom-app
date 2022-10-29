package com.company.miniecom.services;

import com.company.miniecom.domains.Address;
import com.company.miniecom.domains.Billing;
import com.company.miniecom.domains.Cart;
import com.company.miniecom.domains.OrderLines;
import com.company.miniecom.domains.enums.CartStatus;
import com.company.miniecom.domains.enums.OrderStatus;
import com.company.miniecom.dto.AddressDTO;
import com.company.miniecom.dto.BillingDTO;
import com.company.miniecom.repository.AddressRepository;
import com.company.miniecom.repository.BillingRepository;
import com.company.miniecom.repository.CartRepository;
import com.company.miniecom.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final BillingRepository billingRepository;
    private final AddressRepository addressRepository;


    public void create(BillingDTO billingDTO, AddressDTO addressDTO, Cart cart) {

        Address address = Address.builder()
                .streetAddress(addressDTO.getStreetAddress())
                .city(addressDTO.getCity())
                .countryState(addressDTO.getCountryState())
                .zipcode(addressDTO.getZipcode())
                .build();
        addressRepository.save(address);

        Billing billing = Billing.builder()
                .firstName(billingDTO.getFirstName())
                .lastName(billingDTO.getLastName())
                .address(address)
                .phone(billingDTO.getPhone())
                .email(billingDTO.getEmail())
                .notes(billingDTO.getNotes())
                .build();
        billingRepository.save(billing);

        OrderLines orderLines = OrderLines.builder()
                .billing(billing)
                .cart(cart)
                .orderStatus(OrderStatus.PENDING)
                .build();

        cart.setStatus(CartStatus.ORDERING);
        cartRepository.save(cart);

        orderRepository.save(orderLines);
    }
}
