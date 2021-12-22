package com.eatza.order.service.itemservice;

import com.eatza.order.model.OrderedItem;

import java.util.List;

public interface ItemService {

    public OrderedItem saveItem(OrderedItem item);

    public List<OrderedItem> findbyOrderId(Long id);

    public void deleteItemsById(Long id);

}
