package com.eatza.customer.customerregistration.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "food_preference")
    private String foodPreference;

    @Column(name = "default_address", nullable = false)
    private String defaultAddress;

    @Column(name = "default_payment_mode")
    private String defaultPaymentMode;

    @Column(name = "active", nullable = false)
    private boolean Active;

}
