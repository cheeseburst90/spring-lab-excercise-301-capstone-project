package com.eatza.customer.customerregistration.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerForm {

    private String firstName;

    private String lastName;

    private String foodPreference;

    private String defaultAddress;

    private String defaultPaymentMode;

}
