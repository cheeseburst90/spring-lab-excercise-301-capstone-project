package com.eatza.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {

    private Long customerId;
    private Long restaurantId;
    private List<OrderedItemsDto> items;


}
